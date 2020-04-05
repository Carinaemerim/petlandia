package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.forms.Cropper;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageResize {
    public static BufferedImage getBuffer(MultipartFile file) {

        if (file.isEmpty()) {
            return null;
        }

        try {
            return ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            return null;
        }
    }

    public static String encode(BufferedImage image, String type) throws IOException {

        String imageString;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes);
        } finally {
            bos.close();
        }
        return imageString;
    }

    public static BufferedImage decode(String imageString) throws IOException {

        BufferedImage image;
        ByteArrayInputStream bis = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(imageString);
            bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
        } finally {
            if (bis != null) {
                bis.close();
            }
        }

        return image;
    }


    public static BufferedImage rotate(BufferedImage buffer, int angle) {
        Scalr.Rotation rotation = null;

        if (angle == 90) {
            rotation = Scalr.Rotation.CW_90;
        }

        if (angle == 180) {
            rotation = Scalr.Rotation.CW_180;
        }

        if (angle == 270) {
            rotation = Scalr.Rotation.CW_270;
        }

        if (rotation == null) {
            return buffer;
        }

        return Scalr.rotate(buffer, rotation);
    }


    public static String getBase64FromUploadImage(Cropper cropper, Dimension target, String imageType) throws IOException {
        BufferedImage buffer = getBuffer(cropper.getImage());

        buffer = rotate(buffer, (int) cropper.getRotate());
        buffer = Scalr.crop(buffer, (int) cropper.getX(), (int) cropper.getY(), (int) cropper.getWidth(), (int) cropper.getHeight());
        buffer = Scalr.resize(buffer, (int) target.getWidth(), (int) target.getHeight());
        return encode(buffer, imageType);
    }

    public static String getBase64FromUploadImage(Cropper cropper, Dimension target) throws IOException {
        return getBase64FromUploadImage(cropper, target, "jpg");
    }
}
