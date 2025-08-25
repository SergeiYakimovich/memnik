package com.memnik.dao.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
