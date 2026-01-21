package com.bdu.clearance.services;

import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.models.ClearanceApproval;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.repositories.ClearanceRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final ClearanceRepository clearanceRepository;

    @Transactional(readOnly = true)
    public ByteArrayInputStream generateClearancePdf(Long clearanceId, String currentUsername) {
        Clearance clearance = clearanceRepository.findById(clearanceId)
                .orElseThrow(() -> new RuntimeException("Clearance not found with id: " + clearanceId));

        // Security Check: Ensure the current user owns the clearance
        Student student = clearance.getStudent();
        if (!student.getUser().getUserId().equals(currentUsername)) {
             throw new AccessDeniedException("You are not authorized to download this clearance.");
        }

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.NORMAL);
            Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.NORMAL);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.NORMAL);

            // Title
            Paragraph universityName = new Paragraph("Bahir Dar University", headerFont);
            universityName.setAlignment(Element.ALIGN_CENTER);
            document.add(universityName);

            Paragraph docTitle = new Paragraph("Student Clearance Form", subHeaderFont);
            docTitle.setAlignment(Element.ALIGN_CENTER);
            docTitle.setSpacingAfter(20);
            document.add(docTitle);

            // Student Details
            PdfPTable studentTable = new PdfPTable(2);
            studentTable.setWidthPercentage(100);
            studentTable.setSpacingAfter(20);
            studentTable.setWidths(new int[]{1, 2});

            addTableRow(studentTable, "Student Name:", student.getUser().getFirstName() + " " + 
                        student.getUser().getMiddleName() + " " + student.getUser().getLastName(), boldFont, normalFont);
            addTableRow(studentTable, "Student ID:", student.getUser().getUserId(), boldFont, normalFont);
            addTableRow(studentTable, "Academic Year:", String.valueOf(clearance.getAcademicYear()), boldFont, normalFont);
            addTableRow(studentTable, "Year of Study:", String.valueOf(clearance.getYearOfStudy()), boldFont, normalFont);
            addTableRow(studentTable, "Semester:", String.valueOf(clearance.getSemester()), boldFont, normalFont);
            addTableRow(studentTable, "Request Date:", clearance.getRequestDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), boldFont, normalFont);

            document.add(studentTable);

            // Approvals Title
            Paragraph approvalsTitle = new Paragraph("Clearance Approvals", subHeaderFont);
            approvalsTitle.setSpacingAfter(10);
            document.add(approvalsTitle);

            // Approvals Table
            PdfPTable approvalTable = new PdfPTable(5);
            approvalTable.setWidthPercentage(100);
            approvalTable.setWidths(new int[]{3, 2, 2, 2, 3});

            // Table Headers
            String[] headers = {"Department/Office", "Status", "Approved By", "Date", "Remarks/Reason"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                cell.setPadding(5);
                approvalTable.addCell(cell);
            }

            // Table Data
            List<ClearanceApproval> approvals = clearance.getApprovals();
            if (approvals != null) {
                for (ClearanceApproval approval : approvals) {
                    approvalTable.addCell(new Phrase(approval.getOrganizationalUnit().getOrganizationName(), normalFont));
                    approvalTable.addCell(new Phrase(String.valueOf(approval.getStatus()), normalFont));
                    
                    String approverName = "-";
                    if (approval.getApprovedBy() != null) {
                        approverName = approval.getApprovedBy().getFirstName() + " " + approval.getApprovedBy().getLastName();
                    }
                    approvalTable.addCell(new Phrase(approverName, normalFont));

                    String date = "-";
                    if (approval.getApprovalDate() != null) {
                        date = approval.getApprovalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    approvalTable.addCell(new Phrase(date, normalFont));

                    String remarks = approval.getRemarks() == null ? "" : approval.getRemarks();
                    approvalTable.addCell(new Phrase(remarks, normalFont));
                }
            }

            document.add(approvalTable);

            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(valueCell);
    }
}
