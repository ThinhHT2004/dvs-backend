package com.group5.dvs_backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public List<Map> uploadToCloudinary(MultipartFile file1, MultipartFile file2, MultipartFile file3,Long valuationReportId) {
        try {
            /*
             * Map chứa thông tin về file đã được tải lên. Các thông tin này
             * có thể bao gồm URL của file (url), kích thước của file (bytes),
             * định dạng của file (format), v.v
             */
            Map proportion = this.cloudinary.uploader().upload(file1.getBytes(), Map.of());
            Map characteristic = this.cloudinary.uploader().upload(file2.getBytes(), Map.of());
            Map iamge = this.cloudinary.uploader().upload(file3.getBytes(), Map.of());
            List<Map> maps = new ArrayList<>();
            maps.add(proportion);
            maps.add(characteristic);
            maps.add(iamge);
            // Lưu URL của ảnh xuống cơ sở dữ liệu
            String proportionUrl = (String) proportion.get("url");
            String characteristicUrl = (String) characteristic.get("url");
            String image = (String) iamge.get("url");
            ValuationReport valuationReport = valuationReportServiceImpl.findById(valuationReportId);
            valuationReport.setProportion(proportionUrl);
            valuationReport.setCharacteristic(characteristicUrl);
            valuationReport.setImage(image);
            valuationReportServiceImpl.save(valuationReport);

            return maps;
        } catch (IOException io) {
            throw new RuntimeException("Image upload fail");
        }
    }

}
