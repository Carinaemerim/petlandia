package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 9, max = 120)
    private String title;

    @NotBlank
    @Size(min = 2, max = 120)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private AnimalAge animalAge;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private AnimalGender animalGender;

    @ManyToOne(fetch= FetchType.EAGER)
    @NotNull
    private AnimalType animalType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AnnounceStatus status;

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalSize animalSize;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalColor animalColor;

    @Column(length = 10000)
    @Size(min = 5, max = 10000)
    private String description;

    @NotNull
    @Size(min = 4, max = 120)
    private String address;

    @NotNull @Pattern(regexp="\\d{5}-\\d{3}$")
    @Size(min = 9, max = 9)
    private String zipCode;

    @NotNull
    @Size(min = 4, max = 120)
    private String neighborhood;

    @NotNull
    @Size(min = 4, max = 120)
    private String city;

    @NotNull
    @Size(min = 2, max = 2)
    private String state;

    @Max(999999)
    private int addressNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalCastrated animalCastrated;

    @NotNull
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
