package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.forms.Cropper;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Objects;

public class CropperHelper {
    public static Cropper createCropper(String name, byte[] content, MediaType mediaType, Boolean required) throws IOException {
        Resource file = FileHelper.loadPngFile();

        MockMultipartFile mockFile = new MockMultipartFile(
                Objects.requireNonNull(file.getFilename()),
                file.getFilename(),
                mediaType.toString(),
                file.getInputStream()
        );

        Cropper cropper = new Cropper();

        cropper.setImage(mockFile);
        cropper.setRequired(required);
        cropper.setX(14.806867F);
        cropper.setY(0.26824033F);
        cropper.setWidth(1703.2832F);
        cropper.setHeight(958.09686F);
        cropper.setRotate(0);
        cropper.setScaleX(1);
        cropper.setScaleY(1);
        cropper.setRemove(false);

        return cropper;
    }
}
