
package br.edu.ifrs.canoas.webapp.domain;

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

	@NotNull
	@Size(min = 2, max = 40)
	private String username;

	private boolean active;

	@NotNull
	@Size(min = 4, max = 250)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@Size(min = 3, max = 120)
	private String name;

	@NotBlank
	@CPF
	@Size(min = 14, max = 14)
	private String cpf;

	@Email
	@NotBlank
	@Size(min = 5, max = 120)
	private String email;

	@NotNull
	@Size(min = 4, max = 120)
	private String address;

	@NotNull @Pattern(regexp="\\d{5}-\\d{3}$")
	@Size(min = 9, max = 9)
	private String zipCode;

	@NotNull
	@Size(min = 3, max = 120)
	private String neighborhood;

	@NotNull
	@Size(min = 3, max = 120)
	private String city;

	@NotNull
	@Size(min = 2, max = 2)
	private String state;

	@Max(99999)
	private int addressNumber;

	@NotNull
	@Size(min = 10, max = 11)
	private String residentialPhone;

	@Size(min = 10, max = 11)
	private String celPhone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="user_id")
	private Set<Announce> announces;
/*
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private AnimalAge animalAge;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private AnimalGender animalGender;

	@ManyToOne(fetch= FetchType.EAGER)
	@NotNull
	private AnimalType animalType;

	@ManyToOne(fetch = FetchType.EAGER)
	private AnimalSize animalSize;

	@ManyToOne(fetch = FetchType.EAGER)
	private AnimalColor animalColor;
	*/

}