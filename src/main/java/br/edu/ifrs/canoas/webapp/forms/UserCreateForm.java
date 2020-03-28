package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.domain.User;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCreateForm {

    @NotNull
    private User user;

    @NotNull
    private String passwordConfirm;
}
