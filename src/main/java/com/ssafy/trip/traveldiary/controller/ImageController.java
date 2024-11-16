package com.ssafy.trip.traveldiary.controller;

import java.io.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class ImageController {
    private static final String IMAGE_DIR = "src/main/resources/static/uploads/";
    private static final String IMAGE_BASE_URL = "http://localhost:8080/uploads/";
    @GetMapping("/api/image-url/{id}")
    public ResponseEntity<String> getImageUrl(@PathVariable String id) {
        try{
            String[] extensions = {".jpg", ".jpeg"};
            for (String ext : extensions) {
                File imageFile = new File(IMAGE_DIR + File.separator +  id + ext);
                if (imageFile.exists()) {
                    String imageUrl = IMAGE_BASE_URL + id + ext;
                    return ResponseEntity.ok(imageUrl);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image request");
        }
    }
}
