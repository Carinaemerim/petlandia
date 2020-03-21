package br.edu.ifrs.canoas.webapp.helper;

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

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    public static BufferedImage resize(BufferedImage originalImage, Dimension target) {

        BufferedImage resizedImage = new BufferedImage(target.width,
                target.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, target.width, target.height, null);
        g.dispose();
        return resizedImage;
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


    public static String getBase64FromUploadImage(MultipartFile file, Dimension desirable, String imageType) throws IOException {

        BufferedImage buffer = ImageResize.getBuffer(file);
        Dimension current = new Dimension(buffer.getWidth(), buffer.getHeight());
        Dimension target = ImageResize.getScaledDimension(current, desirable);
        buffer = ImageResize.resize(buffer,target);

        return ImageResize.encode(buffer, imageType);
    }

    public static String getBase64FromUploadImage(MultipartFile file, Dimension desirable) throws IOException {
        return getBase64FromUploadImage(file, desirable, "jpg");
    }
}
