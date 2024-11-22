package com.ssafy.trip.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Image 파일을 관리하는 Util입니다.
 */
@Component
public class ImageUtils {
    private static final String IMAGE_DIR = System.getProperty("user.dir") + "/uploads";
    private static final String IMAGE_BASE_URL = "http://localhost:8080/uploads/";


    /**
     * 서버에 저장된 이미지에 접근할 수 있는 url을 반환합니다.
     * .jpg, .jpeg, .png Image 확장자를 지원합니다
     *
     * @param filename image 파일이름
     * @return image 파일 접근 url
     */
    public static String getImageUrl(String filename) {
        try {
            String[] extensions = {".jpg", ".jpeg", ".png"};
            for (String ext : extensions) {
                File imageFile = new File(IMAGE_DIR + File.separator + filename + ext);
                if (imageFile.exists()) {
                    return IMAGE_BASE_URL + filename + ext;
                }
            }

        } catch (Exception e) {
            return "Error processing image request";
        }
        return "Error processing image request";
    }

    /**
     * 파일을 저장합니다.
     *
     * @param file MultipartFile type
     */
    public static String upload(MultipartFile file) {
        String uniqueFileName = UUID.randomUUID().toString();
        if (file.isEmpty()) {
            try {
                throw new FileNotFoundException("파일을 찾지 못했습니다");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            File uploadDir = new File(IMAGE_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = IMAGE_DIR + File.separator + uniqueFileName;
            file.transferTo(new File(filePath));
            return uniqueFileName;
        } catch (IOException e) {
            try {
                throw new FileNotFoundException("파일을 찾지 못했습니다.");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
