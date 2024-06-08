package com.group5.dvs_backend.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    // private final ImageRepository imageRepository;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    // public CloudinaryService(Cloudinary cloudinary, ImageRepository
    // imageRepository) {
    // this.cloudinary = cloudinary;
    // this.imageRepository = imageRepository;
    // }

    public Map upload(MultipartFile file) {
        try {
            /*
             * Map chứa thông tin về file đã được tải lên. Các thông tin này
             * có thể bao gồm URL của file (url), kích thước của file (bytes),
             * định dạng của file (format), v.v
             */
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());

            // Lưu URL của ảnh xuống cơ sở dữ liệu
            // String imageUrl = (String) data.get("url");
            // Image image = new Image();
            // image.setUrl(imageUrl);
            // imageRepository.save(image);

            return data;
        } catch (IOException io) {
            throw new RuntimeException("Image upload fail");
        }
    }

}
