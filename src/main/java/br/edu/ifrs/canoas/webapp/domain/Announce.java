package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.helper.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min = 4, max = 120, message = "{validation.announce.title.size}")
    private String title;

    @NotBlank(message = "{field.required}")
    @Size(min = 2, max = 120, message = "{validation.announce.name.size}")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "{field.required}")
    private AnimalAge animalAge;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "{field.required}")
    private AnimalGender animalGender;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "{field.required}")
    private AnimalType animalType;

    @NotNull(message = "{field.required}")
    @Enumerated(EnumType.STRING)
    private AnnounceStatus status = AnnounceStatus.ACTIVE;

    @NotNull(message = "{field.required}")
    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalSize animalSize;

    @NotNull(message = "{field.required}")
    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalColor animalColor;

    @NotBlank(message = "{field.required}")
    @Column(length = 10000)
    @Size(min = 10, max = 10000, message = "{validation.announce.description.size}")
    private String description;

    @NotBlank(message = "{field.required}")
    @Size(min = 3, max = 120, message = "{validation.announce.address.size}")
    private String address;

    @NotBlank(message = "{field.required}")
    @Pattern(regexp = "\\d{5}-\\d{3}$", message = "{validation.announce.zipcode.pattern}")
    @Size(min = 9, max = 9, message = "{validation.announce.zipcode.size}")
    private String zipCode;

    @NotBlank(message = "{field.required}")
    @Size(min = 3, max = 120, message = "{validation.announce.neighborhood.size}")
    private String neighborhood;

    @NotBlank(message = "{field.required}")
    @Size(min = 3, max = 120, message = "{validation.announce.city.size}")
    private String city;

    @NotBlank(message = "{field.required}")
    @Size(min = 2, max = 2, message = "{validation.announce.state.size}")
    private String state;

    @Size(max = 5, message = "{validation.announce.addressNumber.size}")
    private String addressNumber;

    @NotNull(message = "{field.required}")
    @ManyToOne(fetch = FetchType.EAGER)
    private AnimalCastrated animalCastrated;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
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

    @Transient
    private double score = 0;

    public boolean canModify() {
        if (!Auth.isAuthenticated()) {
            return false;
        }

        boolean hasPermission = this.isOwner() || this.isAdmin();
        boolean isActive = this.status == AnnounceStatus.ACTIVE;
        return hasPermission && isActive;
    }


    public boolean isModerator() {
        return Auth.hasRole(new Role[]{Role.ROLE_MODERATOR, Role.ROLE_ADMIN});
    }

    public boolean isAdmin() {
        return Auth.hasRole(new Role[]{Role.ROLE_ADMIN});
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
        return this.canModify();
    }
}
