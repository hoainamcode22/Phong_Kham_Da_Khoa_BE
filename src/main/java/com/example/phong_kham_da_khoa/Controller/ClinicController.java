package com.example.phong_kham_da_khoa.Controller;

import com.example.phong_kham_da_khoa.Model.Clinic;
import com.example.phong_kham_da_khoa.Repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    @Autowired
    private ClinicRepository clinicRepository;

    // GET: /api/clinics
    @GetMapping
    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    // POST: /api/clinics
    @PostMapping
    public Clinic createClinic(@RequestBody Clinic clinic) {
        return clinicRepository.save(clinic);
    }
}
