package com.example.fi.service;

import com.example.fi.entity.Bond;
import com.example.fi.entity.avf_sofr;
import com.example.fi.repositories.avg_rate_repo;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.fi.repositories.bond_repo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class bond_data_risk_serveice {

    @Autowired
    bond_repo bond_repo;

    public List<Bond> risk_table_get_data() {
        return bond_repo.findAll();
    }
}
