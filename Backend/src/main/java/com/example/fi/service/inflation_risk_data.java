package com.example.fi.service;

import com.example.fi.entity.inflation_risk_rate;
import com.example.fi.entity.risk_table;
import com.example.fi.repositories.inflation_rate;
import com.example.fi.repositories.risk_table_data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class inflation_risk_data {

    @Autowired
    inflation_rate infa_rate;

    public List<inflation_risk_rate> risk_get_data(){
        List<inflation_risk_rate> a = new ArrayList<>();

      return  a= infa_rate.findAll();
    }


}
