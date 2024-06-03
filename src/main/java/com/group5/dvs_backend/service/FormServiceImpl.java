package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Form;
import com.group5.dvs_backend.repository.FormRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FormServiceImpl implements FormService{

    private FormRepository formRepository;
    @Override
    public Form createForm(Form form) {
        return formRepository.save(form);
    }
}
