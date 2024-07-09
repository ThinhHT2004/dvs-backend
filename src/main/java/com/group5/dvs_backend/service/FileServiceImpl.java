package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Diamond;
import com.group5.dvs_backend.repository.DiamondRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService{

    private DiamondRepository diamondRepository;
    @Override
    public String readFile(MultipartFile file) throws IOException, ParseException {
        String text = new String(file.getBytes());
        String[] currentContent = text.split("\n");
        String[] content = new String[currentContent.length-1];
        System.arraycopy(currentContent, 0, content, 0, content.length);
        for (String str : content) {
            String[] diamondContent = str.trim().split(",");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Diamond diamond = Diamond
                    .builder()
                    .price(Float.parseFloat(diamondContent[0].trim()))
                    .shape(diamondContent[1].trim())
                    .caratWeight(Float.parseFloat(diamondContent[2].trim()))
                    .color(diamondContent[3].trim())
                    .clarity(diamondContent[4].trim())
                    .cut(diamondContent[5].trim())
                    .fluorescence(diamondContent[6].trim())
                    .polish(diamondContent[7].trim())
                    .symmetry(diamondContent[8].trim())
                    .measurement(diamondContent[9].trim())
                    .origin(diamondContent[10].trim())
                    .source(diamondContent[11].trim())
                    .image(diamondContent[12].trim())
                    .date(format.parse(diamondContent[13].trim()))
                    .build();
            System.out.println(diamond);
            Diamond savedDiamond = diamondRepository.save(diamond);
            System.out.println(savedDiamond);
        }

        return text;
    }
}
