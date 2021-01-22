package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.domain.Announce;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnnounceCreateFrom {
    @NotNull
    private Announce announce;

    private Cropper mainPhotoCropper;
    private Cropper secondPhotoCropper;
    private Cropper thirdPhotoCropper;
}
