package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.forms.AnnounceCreateFrom;
import br.edu.ifrs.canoas.webapp.forms.Cropper;
import org.springframework.http.MediaType;

import java.io.IOException;

public class AnnounceFormHelper {
    public static AnnounceCreateFrom createForm() throws IOException {
        AnnounceCreateFrom form = new AnnounceCreateFrom();
        form.setAnnounce(AnnounceHelper.createAnnounce());
        form.setMainPhotoCropper(CropperHelper.createCropper(
                "main_photo",
                new byte[] {},
                MediaType.IMAGE_PNG,
                true
        ));

        form.setSecondPhotoCropper(new Cropper());
        form.setThirdPhotoCropper(new Cropper());

        return form;
    }

    public static AnnounceCreateFrom editForm() throws IOException {
        AnnounceCreateFrom form = new AnnounceCreateFrom();
        form.setAnnounce(AnnounceHelper.createAnnounce());
        form.getAnnounce().setId(100L);

        form.setMainPhotoCropper(new Cropper());
        form.getMainPhotoCropper().setRequired(true);
        form.getMainPhotoCropper().setCurrentImage(form.getAnnounce().getMainPhoto());

        form.setSecondPhotoCropper(new Cropper());
        form.getSecondPhotoCropper().setCurrentImage(form.getAnnounce().getSecondPhoto());

        form.setThirdPhotoCropper(new Cropper());
        form.getThirdPhotoCropper().setCurrentImage(form.getAnnounce().getThirdPhoto());

        return form;
    }
}
