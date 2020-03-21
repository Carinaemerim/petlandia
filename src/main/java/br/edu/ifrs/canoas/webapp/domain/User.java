
package br.edu.ifrs.canoas.webapp.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rodrigo on 2/21/17.
 */
@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private boolean active;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;
	@NotBlank
	private String name;
	@Email @NotBlank
	private String email;
	private String experience;
	private String skill;
    @OneToOne
    private File picture;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="user_id")
	private Set<Announce> announces;

}