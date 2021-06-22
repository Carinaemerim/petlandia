package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.domain.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserChangePasswordForm {

    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String repeatPassword;

    public boolean isEqualNewPassword() {
        return this.newPassword.equals(this.repeatPassword);
    }
    public boolean isEqualCurrentPassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }

    public String getHash() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(newPassword);
    }
}
