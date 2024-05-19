package com.bss.restaurant.util;

import com.bss.restaurant.exception.RestaurantImageDeleteException;
import com.bss.restaurant.exception.RestaurantImageUploadException;
import com.cloudinary.Cloudinary;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import com.cloudinary.utils.ObjectUtils;

import java.io.*;
import java.util.Map;
@Slf4j
@Component
public class ImageUploader {
    @Autowired
    private Cloudinary cloudinary;
    public String uploadImage(String base64Image,String imageName, String folderName) {


        try {
            Map uploadResult = cloudinary.uploader().upload(base64Image,
                    ObjectUtils.asMap("public_id", "restaurant/"+folderName + "/" + imageName));

            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestaurantImageUploadException("Image upload failed");
        }
    }

    public void deleteImage(String folderName, String imageName) {
        try {
            String publicId = "restaurant/" + folderName + "/" + imageName;
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RestaurantImageDeleteException("Can't Delete Image from Cloudinary");
        }
    }
}
