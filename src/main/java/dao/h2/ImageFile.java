package dao.h2;

import dao.DaoException;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImageFile {

    private final String imageFolder = "/images/";

    public String add(String imageName) throws DaoException {
        File file = new File(imageName);
        String extension = getExtension(imageName);
        String newFileName = UUID.randomUUID().toString() + extension;
        try {
            String absolutePath = new File(".").getCanonicalPath();
            String newFilePath = absolutePath + imageFolder + newFileName;
            File newFile = new File(newFilePath);
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new DaoException("File could not be moved", e);
        }
        return newFileName;
    }


    public void delete(String name) throws DaoException {
        try {
            String absolutePath = new File(".").getCanonicalPath();
            String deleteFilePath = absolutePath + imageFolder + name;
            File file = new File(deleteFilePath);
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new DaoException("File could not be deleted", e);
        }
    }

    public Image get(String imageName){
        String absolutePath = null;
        try {
            absolutePath = new File(".").getCanonicalPath();
            String imageFilePath = absolutePath + imageFolder + imageName;
            File file = new File(imageFilePath);
            return new Image(file.toURI().toString());
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;

    }


    private String getExtension(String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        return "." + extension;
    }

}
