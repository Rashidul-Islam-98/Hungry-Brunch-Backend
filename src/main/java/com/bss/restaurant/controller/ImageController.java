package com.bss.restaurant.controller;

import com.bss.restaurant.util.ImageUploader;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageUploader imageUploader;

    @GetMapping(value = "/{folder}/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void loadImage(
            @PathVariable String folder,
            @PathVariable String image,
            HttpServletResponse response
    ) throws IOException {
//        InputStream resource = imageUploader.getImage("images/", folder, image);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
        //todo: oct-stream
    }
}
