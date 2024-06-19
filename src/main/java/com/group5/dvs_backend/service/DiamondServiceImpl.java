package com.group5.dvs_backend.service;

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
            String clarity, String cut, String symmetry, String polish, String fluorescence) {
        if (advanced) {
            return diamondRepository.findForTrueAdvanced(origin, carat, shape, color, clarity, cut, symmetry, polish,
                    fluorescence);
        } else {
            return diamondRepository.findForFalseAdvanced(origin, carat, shape, color, clarity);
        }
    }

}
