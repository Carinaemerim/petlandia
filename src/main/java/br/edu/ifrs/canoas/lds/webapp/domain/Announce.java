package br.edu.ifrs.canoas.lds.webapp.domain;

import br.edu.ifrs.canoas.lds.webapp.config.auth.UserImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Announce {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String name;

    @NotNull
    private int age;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalGender animalGender;

    @ManyToOne(fetch= FetchType.EAGER)
    private AnimalType animalType;

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalSize animalSize;

    @NotBlank
    private String breed;

    @Column(length = 10000)
    private String description;

    @NotBlank
    private String local;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalCastrated animalCastrated;

    @ManyToOne(fetch= FetchType.EAGER)
    private User user;

    @Override
    public String toString() {
        return "Announce{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + animalType +
                ", date=" + date +
                ", AnimalSize='" + animalSize + '\'' +
                ", description='" + description + '\'' +
                ", neighborhood='" + local + '\'' +
                ", user=" + user +
                '}';
    }

    public boolean canAlter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }

        UserImpl userImpl = (UserImpl) authentication.getPrincipal();
        User user = userImpl.getUser();
        return this.user.getId().equals(user.getId());

    }
}
