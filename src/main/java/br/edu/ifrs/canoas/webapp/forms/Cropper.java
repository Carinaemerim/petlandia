package br.edu.ifrs.canoas.webapp.forms;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Cropper {
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
            this.currentImage = "data:image/png;base64, " + image;
            this.canRemove = !required;
        } else {
            this.currentImage = "/img/placeholder.svg";
            this.canRemove = false;
        }
    }
}
