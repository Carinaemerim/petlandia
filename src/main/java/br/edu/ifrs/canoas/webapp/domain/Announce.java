package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @Column(length = 10000)
    private String description;

    @NotNull
    private String address;

    @NotNull @Pattern(regexp="\\d{5}-\\d{3}$")
    private String zipCode;

    @NotNull
    private String neighborhood;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private int addressNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalCastrated animalCastrated;

    @ManyToOne(fetch= FetchType.EAGER)
    private User user;

    @Lob
    private String mainPhoto;

    @Lob
    private String secondPhoto;

    @Lob
    private String thirdPhoto;

    @Override
    public String toString() {
        return "Announce{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + animalType +
                ", date=" + date +
                ", AnimalSize='" + animalSize + '\'' +
                ", description='" + description + '\'' +
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
