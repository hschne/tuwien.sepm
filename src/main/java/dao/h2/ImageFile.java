package dao.h2;

import dao.DaoException;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImageFile {

    private final Logger logger = LogManager.getLogger(ImageFile.class);

    private final String imageFolder = "/images/";

    private String absoluteImageFolderPath;

    public ImageFile() {
        try {
            absoluteImageFolderPath = new File(".").getCanonicalPath() + imageFolder;
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String add(String imageName) throws DaoException {
        if (existsInImageFolder(imageName)) {
            return imageName;
        } else {
            return copyFile(imageName);
        }
    }

    public void delete(String name) throws DaoException {
        try {
            String deleteFilePath = absoluteImageFolderPath + name;
            File file = new File(deleteFilePath);
            Files.delete(file.toPath());
        } catch (IOException e) {
            logger.error(e);
            throw new DaoException("File could not be deleted", e);
        }
    }

    public Image get(String imageName) {
        String imageFilePath = absoluteImageFolderPath + imageName;
        File file = new File(imageFilePath);
        return new Image(file.toURI().toString());
    }

    @NotNull
    private String copyFile(String imageName) throws DaoException {
        File existingFile = new File(imageName);
        String extension = getExtension(imageName);
        String newFileName = UUID.randomUUID().toString() + extension;
        try {
            File newFile = new File(absoluteImageFolderPath + newFileName);
            Files.copy(existingFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error(e);
            throw new DaoException("File could not be moved", e);
        }
        return newFileName;
    }

    private boolean existsInImageFolder(String imageName) {
        File file = new File(absoluteImageFolderPath + imageName);
        return file.exists();
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
