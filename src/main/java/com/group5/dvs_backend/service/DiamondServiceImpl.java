package com.group5.dvs_backend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group5.dvs_backend.entity.Diamond;
import com.group5.dvs_backend.repository.DiamondRepository;

import lombok.*;

@Service
@AllArgsConstructor
public class DiamondServiceImpl implements DiamondService {

    @Autowired
    private final DiamondRepository diamondRepository;

    @Override
    public List<Diamond> findDiamonds(boolean advanced, String origin, double carat, String shape, String color,
            String clarity, String cut, String symmetry, String polish, String fluorescence) throws ParseException {
        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        instant = instant.atZone(ZoneId.of("Asia/Bangkok")).toInstant();

        Date date = Date.from(instant);


        if (advanced) {
            return diamondRepository.findForTrueAdvanced(origin.trim(), carat, shape.trim(), color.trim(), clarity.trim(), cut.trim(), symmetry.trim(), polish.trim(),
                    fluorescence.trim(), new Date());
        } else {
            return diamondRepository.findForFalseAdvanced(origin.trim(), carat, shape.trim(), color.trim(), clarity.trim(), new Date());
        }
    }

    @Override
    public List<Diamond> findDiamondsWithDay(boolean advanced, String origin, double carat, String shape, String color, String clarity, String cut, String symmetry, String polish, String fluorescence, int day) {

        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(day);
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        instant = instant.atZone(ZoneId.of("Asia/Bangkok")).toInstant();
        Date date = Date.from(instant);

        System.out.println(date);
        if (advanced) {
            return diamondRepository.findForTrueAdvanced(origin.trim(), carat, shape.trim(), color.trim(), clarity.trim(), cut.trim(), symmetry.trim(), polish.trim(),
                    fluorescence.trim(), date);
        } else {
            return diamondRepository.findForFalseAdvanced(origin.trim(), carat, shape.trim(), color.trim(), clarity.trim(), date);
        }
    }

    @Override
    public List<Diamond> findAll() {
        return diamondRepository.findAll();
    }
}
