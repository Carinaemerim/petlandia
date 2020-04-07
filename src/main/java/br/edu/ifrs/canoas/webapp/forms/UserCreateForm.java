package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.domain.User;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class UserCreateForm {

    @NotNull
    @Valid
    private User user;

    @NotNull
    private String passwordConfirm;
}
