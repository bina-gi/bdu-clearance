package com.bdu.clearance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String middleName;

    @NotBlank
    private String lastName;

    @Column(nullable = false)
    private Boolean isActive = true;

    @NotBlank
    private String password;
    //=== Relations ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id")
    private Role userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_unit_id")
    private OrganizationalUnit organizationalUnit;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Student student;

    @OneToMany(mappedBy = "advisor", fetch = FetchType.LAZY)
    private List<Student> advisedStudents;

    @OneToMany(mappedBy = "issuedBy", fetch = FetchType.LAZY)
    private List<Property> issuedProperties;

    @OneToMany(mappedBy = "approvedBy", fetch = FetchType.LAZY)
    private List<ClearanceApproval> approvedClearances;
}
