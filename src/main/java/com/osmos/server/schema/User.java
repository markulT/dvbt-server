package com.osmos.server.schema;

import com.osmos.server.orders.entities.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

//    @Column(name = "login")
//    private String login;
    @Column(name = "email")
    private String email;

    @Column(name = "fullName")
    private String fullName;
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(mappedBy = "orderedBy")
    private List<Order> orders;

    @OneToOne()
    private Order assignedTo;

    public void addRole(Role role) {
        roles.add(role);
    }

}
