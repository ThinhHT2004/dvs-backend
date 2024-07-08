package com.group5.dvs_backend.service;

import java.text.ParseException;
import java.util.List;

import com.group5.dvs_backend.entity.Diamond;

public interface DiamondService {
    public List<Diamond> findDiamonds(boolean advanced, String origin, double carat, String shape, String color,
            String clarity, String cut, String symmetry, String polish, String fluorescence) throws ParseException;
    public List<Diamond> findDiamondsWithDay(boolean advanced, String origin, double carat, String shape, String color,
                                      String clarity, String cut, String symmetry, String polish, String fluorescence, int day);
}
