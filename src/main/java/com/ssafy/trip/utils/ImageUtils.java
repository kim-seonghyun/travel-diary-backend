package com.ssafy.trip.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.fileupload.FileUploadException;
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
    public static void upload(MultipartFile file) {
        if (file.isEmpty()) {
            return;
        }
        try {
            File uploadDir = new File(IMAGE_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = IMAGE_DIR + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            try {
                throw new FileUploadException("파일 업로드 중 문제가 발생했습니다");
            } catch (FileUploadException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
