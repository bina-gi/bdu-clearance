package com.bdu.clearance.controllers.clearance;

import com.bdu.clearance.services.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/clearance")
@RequiredArgsConstructor
public class ClearancePdfController {

    private final PdfService pdfService;

    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> downloadClearancePdf(@PathVariable("id") Long id, Authentication authentication) {
        String username = authentication.getName();
        ByteArrayInputStream in = pdfService.generateClearancePdf(id, username);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clearance-" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }
}
