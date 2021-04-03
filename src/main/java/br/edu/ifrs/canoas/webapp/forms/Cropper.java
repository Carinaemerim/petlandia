package br.edu.ifrs.canoas.webapp.forms;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.helper.ImageResize;
import lombok.Data;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.Set;

@Data
public class Cropper {

    private static final Set<MediaType> ALLOWED_MEDIA_TYPES = Set.of(
            MediaType.IMAGE_JPEG,
            MediaType.IMAGE_PNG
    );

    private MultipartFile image;
    private String currentImage = "/img/placeholder.svg";
    private boolean remove = false;
    private boolean required = false;
    private boolean canRemove = false;
    private float x = 0f;
    private float y = 0f;
    private float width = 0f;
    private float height = 0f;
    private float rotate = 0f;
    private float scaleX = 0f;
    private float scaleY = 0f;

    public void setCurrentImage(String image) {
        if (image != null && image.length() > 3) {
            this.currentImage = "data:image/jpeg;base64, " + image;
            this.canRemove = !required;
        } else {
            this.currentImage = "/img/placeholder.svg";
            this.canRemove = false;
        }
    }

    public boolean isValid() {
        if (this.image == null || this.image.isEmpty()) {
            return false;
        }

        if (this.image.getContentType() == null) {
            return false;
        }

        MediaType mediaType;
        try {
            mediaType = MediaType.parseMediaType(this.image.getContentType());
        } catch (InvalidMediaTypeException e) {
            return false;
        }

        if (!ALLOWED_MEDIA_TYPES.contains(mediaType)) {
            return false;
        }

        if (this.image.getOriginalFilename() == null || this.image.getOriginalFilename().isBlank()) {
            return false;
        }

        return true;
    }

    public String doUpload(BindingResult bindingResult, String field, Dimension imageTarget, Messages messages) throws IOException {
        return this.doUpload(bindingResult, field, imageTarget, messages, null);
    }

    public String doUpload(BindingResult bindingResult, String field, Dimension imageTarget, Messages messages, String currentImage) throws IOException {
        if (bindingResult.hasErrors()) {
            return null;
        }

        if (this.isValid()) {
            return ImageResize.getBase64FromUploadImage(this, imageTarget);
        }

        if (this.isRequired()) {
            if (currentImage != null) {
                return currentImage;
            } else {
                String message = messages.get("validation.announce.cropper.required");
                FieldError error = new FieldError(bindingResult.getObjectName(), field, message);
                bindingResult.addError(error);
            }
        }

        return this.isRemove() ? null : currentImage;
    }
}
