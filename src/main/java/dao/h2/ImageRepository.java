package dao.h2;

import dao.DaoException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class ImageRepository {

    private final String basePath = "/images/";

    public String add(String path) throws DaoException {
        File file = new File(path);
        String extension = getExtension(path);
        String newFileName = UUID.randomUUID().toString() + extension;
        String newFilePath = basePath + newFileName;
        File newFile = new File(newFilePath);
        try {
            Files.copy(file.toPath(), newFile.toPath());
        } catch (IOException e) {
            throw new DaoException("File could not be moved", e);
        }
        return newFileName;
    }


    public void delete(String name) throws DaoException {
        File file = new File(basePath + name);
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new DaoException("File could not be deleted", e);
        }
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
