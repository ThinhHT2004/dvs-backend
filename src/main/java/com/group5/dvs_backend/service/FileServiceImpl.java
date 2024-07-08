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
                    .price(Float.parseFloat(diamondContent[0]))
                    .shape(diamondContent[1])
                    .caratWeight(Float.parseFloat(diamondContent[2]))
                    .color(diamondContent[3])
                    .clarity(diamondContent[4])
                    .cut(diamondContent[5])
                    .fluorescence(diamondContent[6])
                    .polish(diamondContent[7])
                    .symmetry(diamondContent[8])
                    .measurement(diamondContent[9])
                    .origin(diamondContent[10])
                    .source(diamondContent[11])
                    .image(diamondContent[12])
                    .date(format.parse(diamondContent[13]))
                    .build();
            Diamond savedDiamond = diamondRepository.save(diamond);
            System.out.println(savedDiamond);

        }

        return text;
    }
}
