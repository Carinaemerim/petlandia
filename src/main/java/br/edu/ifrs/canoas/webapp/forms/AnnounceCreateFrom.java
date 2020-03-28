package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnnounceCreateFrom {
    @NotNull
    private Announce announce;

    @NotBlank
    private MultipartFile mainPhoto;

    private MultipartFile secondPhoto;

    private MultipartFile thirdPhoto;
}
