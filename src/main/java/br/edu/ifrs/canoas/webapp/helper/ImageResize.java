package br.edu.ifrs.canoas.webapp.helper;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageResize {

    public final Dimension desirable = new Dimension(512, 288);

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


    public static String getBase64FromUploadImage(MultipartFile file, Dimension desirable, String imageType) throws IOException {
        BufferedImage buffer = getBuffer(file);
        buffer = Scalr.resize(buffer, desirable.width, desirable.height);
        buffer = Scalr.crop(buffer, 0, 0, desirable.width, desirable.height);
        return encode(buffer, imageType);
    }

    public static String getBase64FromUploadImage(MultipartFile file, Dimension desirable) throws IOException {
        return getBase64FromUploadImage(file, desirable, "jpg");
    }



    public static void main(String[] args) throws IOException {

        Dimension desirable = new Dimension(512, 288);
        File input = new File("/home/carina/Documentos/imagens_petlandia/3645-cachorro-que-nao-cresce-descubra-alguma-article_media_mobile-2.jpg");
        File output = new File("/home/carina/Documentos/imagens_petlandia/unnamed-crop.jpg");
        BufferedImage buffer = ImageIO.read(input);

        buffer = Scalr.resize(buffer, desirable.width, desirable.height);
        buffer = Scalr.crop(buffer, desirable.width, desirable.height);
        System.out.println(buffer.getWidth() + "x" + buffer.getHeight());
        ImageIO.write(buffer, "jpg", output);
    }
}
