package com.management.user.accessingdatamysql.Model;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "user_role")
@Entity // This tells Hibernate to make a table out of this class
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_role_id", columnDefinition = "VARCHAR(64)")
    private String user_role_id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleEntity role;

    public UserRoleEntity() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
