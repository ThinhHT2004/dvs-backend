package com.group5.dvs_backend.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group5.dvs_backend.entity.Diamond;
import com.group5.dvs_backend.service.DiamondService;

@RestController
@RequestMapping("/api/diamond")
@CrossOrigin
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    // @GetMapping("/search/{advanced}/{origin}/{carat}/{shape}/{color}/{clarity}/{cut}/{symmetry}/{polish}/{fluorescence}")
    // public ResponseEntity<List<Diamond>> searchDiamonds(@PathVariable("advanced")
    // boolean advanced,
    // @PathVariable("origin") String origin, @PathVariable("carat") double carat,
    // @PathVariable("shape") String shape, @PathVariable("color") String color,
    // @PathVariable("clarity") String clarity, @PathVariable("cut") String cut,
    // @PathVariable("symmetry") String symmetry, @PathVariable("polish") String
    // polish,
    // @PathVariable("fluorescence") String fluorescence) {
    // List<Diamond> diamonds = diamondService.findDiamonds(advanced, origin, carat,
    // shape, color, clarity, cut,
    // symmetry, polish, fluorescence);
    // return ResponseEntity.ok(diamonds);
    // }

    @GetMapping("/search/{advanced}")
    public ResponseEntity<List<Diamond>> searchDiamonds(@PathVariable("advanced") boolean advanced,
                                                        @RequestParam("origin") String origin, @RequestParam("carat") double carat,
                                                        @RequestParam("shape") String shape, @RequestParam("color") String color,
                                                        @RequestParam("clarity") String clarity, @RequestParam("cut") String cut,
                                                        @RequestParam("symmetry") String symmetry, @RequestParam("polish") String polish,
                                                        @RequestParam("fluorescence") String fluorescence) throws ParseException {
        List<Diamond> diamonds = diamondService.findDiamonds(advanced, origin, carat,
                shape, color, clarity, cut, symmetry, polish, fluorescence);
        return ResponseEntity.ok(diamonds);
    }

    @GetMapping("/search/{advanced}/{day}")
    public ResponseEntity<List<Diamond>> searchDiamondsWithDate(@PathVariable("advanced") boolean advanced,
                                                        @RequestParam("origin") String origin, @RequestParam("carat") double carat,
                                                        @RequestParam("shape") String shape, @RequestParam("color") String color,
                                                        @RequestParam("clarity") String clarity, @RequestParam("cut") String cut,
                                                        @RequestParam("symmetry") String symmetry, @RequestParam("polish") String polish,
                                                        @RequestParam("fluorescence") String fluorescence,
                                                        @PathVariable("day") int day) {
        List<Diamond> diamonds = diamondService.findDiamondsWithDay(advanced, origin, carat,
                shape, color, clarity, cut, symmetry, polish, fluorescence, day);
        return ResponseEntity.ok(diamonds);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Diamond>> getAll(){
        return ResponseEntity.ok(diamondService.findAll());
    }
}