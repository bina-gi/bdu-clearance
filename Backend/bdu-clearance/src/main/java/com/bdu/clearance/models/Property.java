package com.bdu.clearance.models;

import com.bdu.clearance.enums.BorrowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "property")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String borrowId;
    private String itemName;
    private Integer itemQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus borrowStatus = BorrowStatus.BORROWED;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issued_by_user_id", nullable = false)
    private Users issuedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "returned_to_user_id")
    private Users returnedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_unit_id")
    private OrganizationalUnit organizationalUnit;
}
