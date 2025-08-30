package com.example.fi.controller;

import com.example.fi.entity.bond_val;
import com.example.fi.entity.float_bond_val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.fi.service.avg_sofr_service;
import com.example.fi.entity.avf_sofr;
import com.example.fi.service.bond_val_service;
import com.example.fi.service.float_bond_val_service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fi")
public class avg_sofr {

    @Autowired
    avg_sofr_service avg_out;

    @Autowired
    bond_val_service bond_new_val;

    @Autowired
    float_bond_val_service float_bond_new_val;

    @GetMapping("/avg_sofr")
    public List<avf_sofr> riskdetect(){
       return avg_out.getAllsofr_data();
    }

    @PostMapping("/bond_val")
    public  double bond_val(@RequestBody bond_val bonds){

        return bond_new_val.cal_bond_value(bonds);
    }

    @PostMapping("/float_bond_val")
    public  double float_bond_val(@RequestBody float_bond_val float_bond){
        System.out.print(float_bond.getSpread());
        System.out.print(float_bond.getMaturityDate());
        System.out.print(float_bond.getDiscountRate());
        System.out.print(float_bond.getFaceValue());
        System.out.print(float_bond.getPaymentFrequency());
        System.out.print(float_bond.getReferenceRate());
        System.out.print(float_bond.getSettlementDate());
        return float_bond_new_val.cal_float_bond_val(float_bond);
    }
}
