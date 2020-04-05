package br.edu.ifrs.canoas.webapp.forms;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Cropper {
    private MultipartFile image;
    private float x = 0f;
    private float y = 0f;
    private float width = 0f;
    private float height = 0f;
    private float rotate = 0f;
    private float scaleX = 0f;
    private float scaleY = 0f;
}


