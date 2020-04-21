
package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.domain.validation.UserCreateGroup;
import br.edu.ifrs.canoas.webapp.domain.validation.UserEditGroup;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
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
	@Size(min = 4, max = 250, groups = {UserCreateGroup.class})
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@NotBlank(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 3, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String name;

	@NotBlank(groups = {UserCreateGroup.class})
	@CPF(groups = {UserCreateGroup.class})
	@Size(min = 14, max = 14, groups = {UserCreateGroup.class})
	private String cpf;

	@Email(groups = {UserCreateGroup.class, UserEditGroup.class})
	@NotBlank(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 5, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
	private String email;

	@NotNull(groups = {UserCreateGroup.class, UserEditGroup.class})
	@Size(min = 4, max = 120, groups = {UserCreateGroup.class, UserEditGroup.class})
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

	@Max(99999)
	private int addressNumber;

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
}