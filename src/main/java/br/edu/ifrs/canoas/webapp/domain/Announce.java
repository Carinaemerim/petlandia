package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

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
    private AnnounceStatus status = AnnounceStatus.ACTIVE;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean canModify() {
        if (this.status == AnnounceStatus.INACTIVE) {
            return false;
        }

        if (Auth.isAuthenticated() == false) {
            return false;
        };

        boolean isOwner = this.isOwner();
        boolean isModerator = this.isModerator();

        return isOwner || isModerator;
    }


    public boolean isModerator() {
        return Auth.hasRole(new Role[]{ Role.ROLE_MODERATOR, Role.ROLE_ADMIN });
    }

    public boolean isOwner() {
        User user = Auth.getUser();
        if (user == null) {
            return false;
        }

        return user.getId().equals(this.user.getId());
    }

    public boolean canApprove() {
        if (!this.isModerator()) {
            return false;
        }

        return this.status == AnnounceStatus.WAITING_REVIEW;
    }

    public boolean canEdit() {
        if (!this.isOwner()) {
            return false;
        }

        return this.status == AnnounceStatus.ACTIVE;
    }

    public boolean canRemove() {
        boolean hasPermission = this.isOwner() || this.isModerator();
        boolean hasStatus = this.status == AnnounceStatus.WAITING_REVIEW || this.status == AnnounceStatus.ACTIVE;
        return hasPermission && hasStatus;
    }
}
