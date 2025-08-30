package com.example.fi.controller;


import com.example.fi.entity.Bond;
import com.example.fi.entity.inflation_risk_rate;
import com.example.fi.entity.risk_table;
import com.example.fi.repositories.inflation_rate;
import com.example.fi.service.bond_data_risk_serveice;
import com.example.fi.service.inflation_risk_data;
import com.example.fi.service.risk_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/fi")
public class risk_controller {

    @Autowired
    risk_service risk_ser;

    @Autowired
    bond_data_risk_serveice get_bond_data;

    @Autowired
    inflation_risk_data rate_infa;

    @GetMapping("/risk_aasessment/{bondId}")
    public List<String> riskdetect(@PathVariable int bondId){
//        List<String> risks = new ArrayList<>();
//        risks.add(risk_ser.risk_cal(bondId));
        System.out.print(bondId);
        return risk_ser.risk_cal(bondId);
    }

    @GetMapping("/risk_table")
    public List<Bond> risk_table_get(){
        return get_bond_data.risk_table_get_data();
    }

    @GetMapping("/get_inflation_data")
    public List<inflation_risk_rate>get_risk_data(){
        return rate_infa.risk_get_data();
    }



}
