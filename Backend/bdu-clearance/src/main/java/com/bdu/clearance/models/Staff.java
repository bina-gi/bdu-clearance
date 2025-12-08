package com.bdu.clearance.models;

import com.bdu.clearance.enums.Campus;
import com.bdu.clearance.enums.StaffRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String staffId;

    private String firstName;
    private String middleName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Enumerated(EnumType.STRING)
    private StaffRole role;

    private Boolean isActive;
    private String password;


}
