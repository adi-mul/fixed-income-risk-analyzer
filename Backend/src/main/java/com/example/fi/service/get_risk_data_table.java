package com.example.fi.service;

import com.example.fi.repositories.risk_table_data;
import com.example.fi.entity.risk_table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class get_risk_data_table {

    @Autowired
    risk_table_data repo;

    public List<risk_table> get_risk_table_data() {
        return repo.findAll();
    }
}
