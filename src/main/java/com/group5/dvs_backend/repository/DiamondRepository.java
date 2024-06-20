package com.group5.dvs_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group5.dvs_backend.entity.Diamond;

@Repository
public interface DiamondRepository extends JpaRepository<Diamond, Integer> {
    @Query("SELECT d FROM Diamond d WHERE d.origin = :origin AND (d.caratWeight > (:carat - 0.05) AND d.caratWeight < (:carat + 0.05) )AND d.shape = :shape AND d.color = :color AND d.clarity = :clarity")
    List<Diamond> findForFalseAdvanced(String origin, double carat, String shape, String color, String clarity);

    @Query("SELECT d FROM Diamond d WHERE d.origin = :origin AND (d.caratWeight > (:carat - 0.05) AND d.caratWeight < (:carat + 0.05) )AND d.shape = :shape AND d.color = :color AND d.clarity = :clarity AND d.cut = :cut AND d.symmetry = :symmetry AND d.polish = :polish AND d.fluorescence = :fluorescence")
    List<Diamond> findForTrueAdvanced(String origin, double carat, String shape, String color, String clarity,
            String cut,
            String symmetry, String polish, String fluorescence);
}
