
package br.edu.ifrs.canoas.webapp.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String username;

	private boolean active;

	@NotNull
	private String birthDate;

	@NotNull
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@NotBlank
	private String name;

	@Email @NotBlank
	private String email;

	@NotNull
	private String address;

	@NotNull
	private String zipCode;

	@NotNull
	private String neighborhood;

	@NotNull
	private String city;

	@NotNull
	private String state;

	private int addressNumber;

	@NotNull
	private String residentialPhone;

	private String celPhone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="user_id")
	private Set<Announce> announces;

}