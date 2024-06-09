package com.group5.dvs_backend.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.group5.dvs_backend.entity.ValuationReport;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final ValuationReportServiceImpl valuationReportServiceImpl;

    public CloudinaryService(Cloudinary cloudinary, ValuationReportServiceImpl valuationReportServiceImpl) {
        this.cloudinary = cloudinary;
        this.valuationReportServiceImpl = valuationReportServiceImpl;
    }

    public Map uploadToCloudinary(MultipartFile file, Long valuationReportId) {
        try {
            /*
             * Map chứa thông tin về file đã được tải lên. Các thông tin này
             * có thể bao gồm URL của file (url), kích thước của file (bytes),
             * định dạng của file (format), v.v
             */
            Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());

            // Lưu URL của ảnh xuống cơ sở dữ liệu
            String imageUrl = (String) data.get("url");
            ValuationReport valuationReport = valuationReportServiceImpl.findById(valuationReportId);
            valuationReport.setProportion(imageUrl);
            valuationReportServiceImpl.save(valuationReport);

            return data;
        } catch (IOException io) {
            throw new RuntimeException("Image upload fail");
        }
    }

}
