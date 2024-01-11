package com.bss.restaurant.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResourceAccessException;

import java.io.*;
import java.nio.file.*;
import java.util.Base64;
@Slf4j
@Component
public class ImageUploader {
    public String uploadImage(String base64Image,String imageName, String folderName) {

        String[] parts = base64Image.split(",");
        String base64Data = parts[1];


        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);


        String directoryPath = "images"+File.separator+folderName;


        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }


        String fileName = directoryPath + File.separator + imageName;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            FileCopyUtils.copy(decodedBytes, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceAccessException("Can't Upload Image");
        }

        return "Image Uploaded Successfully";
    }

    public InputStream getImage(String path, String folder, String image) throws FileNotFoundException {
        String fullPath = path+File.separator+folder+File.separator+image;
        InputStream inputFile = new FileInputStream(fullPath);
        return inputFile;
    }

    public void deleteImage(String folderName, String imageName) throws IOException {
        Path directoryPath = Paths.get("images",folderName);
        Path filePath = directoryPath.resolve(imageName);// classPath:

        try {
            Files.deleteIfExists(filePath);
            log.info("Image Deleted Successfully");
        } catch (IOException e) {
            throw new IOException("Can't Delete Image"+e.getMessage());
        }
    }
}
