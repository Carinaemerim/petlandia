
package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.domain.validation.UserCreateGroup;
import br.edu.ifrs.canoas.webapp.domain.validation.UserEditGroup;
import br.edu.ifrs.canoas.webapp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(groups = {UserCreateGroup.class}, message = "{field.required}")
	@Size(min = 2, max = 40, groups = {UserCreateGroup.class}, message = "{validation.user.username.size}")
	private String username;

	private boolean active;

	@NotNull(groups = {UserCreateGroup.class}, message = "{field.required}")
	@Size(min = 6, max = 20, groups = {UserCreateGroup.class}, message = "{validation.user.password.size}")
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@NotBlank(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{validation.user.name.size}")
	private String name;

	@NotBlank(groups = {UserCreateGroup.class}, message = "{field.required}")
	@CPF(groups = {UserCreateGroup.class}, message="{validation.user.cpf.invalid}")
	@Size(min = 14, max = 14, groups = {UserCreateGroup.class}, message="{validation.user.cpf.size}")
	private String cpf;

	@Email(groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.email.invalid}")
	@NotBlank(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Size(min = 6, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.email.size}")
	private String email;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.address.size}")
	private String address;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Pattern(regexp="\\d{5}-\\d{3}$", groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{validation.user.zipcode.pattern}")
	@Size(min = 9, max = 9, groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.zipcode.size}")
	private String zipCode;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.neighborhood.size}")
	private String neighborhood;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.city.size}")
	private String city;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Size(min = 2, max = 2, groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.state.size}")
	private String state;

	@Size(max = 5, groups = {UserCreateGroup.class, UserEditGroup.class}, message="{validation.user.addressNumber.size}")
	private String addressNumber;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	@Size(min = 14, max = 15, groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{validation.user.residential.size}")
	private String residentialPhone;

	@Size(min = 14, max = 15, message = "{validation.user.celphone.size}", groups = {UserCreateGroup.class, UserEditGroup.class})
	private String celPhone;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	private AnimalAge animalAge;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	private AnimalGender animalGender;

	@ManyToOne(fetch= FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	private AnimalType animalType;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	private AnimalSize animalSize;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	private AnimalColor animalColor;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class}, message = "{field.required}")
	private AnimalCastrated animalCastrated;

	private String avatar;

	public String getAvatarHash() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(this.email.toLowerCase().trim().getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toLowerCase();
		} catch (java.security.NoSuchAlgorithmException ex) {
			return "default";
		}
	}

	public String getAvatar() {
		return "https://www.gravatar.com/avatar/" + this.avatar + "?d=mp&s=200";
	}
}