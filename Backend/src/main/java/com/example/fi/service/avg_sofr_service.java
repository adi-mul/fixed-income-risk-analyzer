package com.example.fi.service;


import com.example.fi.entity.avf_sofr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.fi.repositories.avg_rate_repo;
import java.util.List;

@Service
public class avg_sofr_service {
    @Autowired
     avg_rate_repo repo;

    public List<avf_sofr> getAllsofr_data() {
       return repo.findAll();
    }
}
