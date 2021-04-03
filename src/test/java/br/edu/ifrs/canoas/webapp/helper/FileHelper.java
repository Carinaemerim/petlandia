package br.edu.ifrs.canoas.webapp.helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FileHelper {
    public static final String PNG_PATH = "static/test_images/test_image.png";

    public static Resource loadFile(String path) {
        return new ClassPathResource(path);
    }

    public static Resource loadPngFile() {
        return loadFile(PNG_PATH);
    }

}
