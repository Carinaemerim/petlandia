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
import java.util.ArrayList;
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

    @NotBlank
    private int age;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalGender animalGender;

    @ManyToOne(fetch= FetchType.EAGER)
    private AnimalType type;

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalSize animalSize;

    @NotBlank
    private String breed;

    @Column(length = 10000)
    private String description;

    @NotBlank
    private String neighborhood;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalCastrated animalCastrated;

    @OneToMany
    private ArrayList<Photo> photo;

    @ManyToOne(fetch= FetchType.EAGER)
    private User user;

    @Override
    public String toString() {
        return "Announce{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", AnimalSize='" + animalSize + '\'' +
                ", description='" + description + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", user=" + user +
                '}';
    }
    //TODO RNG004
    //TODO RNG005
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
