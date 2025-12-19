package com.bdu.clearance.models;

import com.bdu.clearance.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name", unique = true)
    private UserRole roleName;

    public Role(UserRole roleName) {
        this.roleName = roleName;
    }

    // Relations
    @OneToMany(mappedBy = "userRole", cascade = CascadeType.DETACH, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Users> users;
}
