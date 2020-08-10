
package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.domain.validation.UserCreateGroup;
import br.edu.ifrs.canoas.webapp.domain.validation.UserEditGroup;
import br.edu.ifrs.canoas.webapp.enums.Role;
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
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(groups = {UserCreateGroup.class})
	@Size(min = 2, max = 40, groups = {UserCreateGroup.class})
	private String username;

	private boolean active;

	@NotNull(groups = {UserCreateGroup.class})
	@Size(min = 6, max = 20, groups = {UserCreateGroup.class})
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@NotBlank(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String name;

	@NotBlank(groups = {UserCreateGroup.class})
	@CPF(groups = {UserCreateGroup.class})
	@Size(min = 14, max = 14, groups = {UserCreateGroup.class})
	private String cpf;

	@Email(groups = {UserCreateGroup.class, UserEditGroup.class})
	@NotBlank(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 6, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String email;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String address;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Pattern(regexp="\\d{5}-\\d{3}$", groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 9, max = 9, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String zipCode;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String neighborhood;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String city;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 2, max = 2, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String state;

	@Size(max = 5)
	private String addressNumber;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 14, max = 15, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String residentialPhone;

	@Pattern(regexp="^$|^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
	private String celPhone;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	private AnimalAge animalAge;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	private AnimalGender animalGender;

	@ManyToOne(fetch= FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	private AnimalType animalType;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	private AnimalSize animalSize;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	private AnimalColor animalColor;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
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