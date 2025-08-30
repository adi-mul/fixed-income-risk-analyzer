package com.example.fi.service;

import com.example.fi.entity.Bond;
import com.example.fi.entity.inflation_risk_rate;
import com.example.fi.repositories.bond_repo;
import com.example.fi.repositories.inflation_rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class yeild_analysis {

    @Autowired
    bond_repo bondrepo;

    @Autowired
    inflation_rate infrate;

    public double calculateRealYield(double nominalYield, double inflationRate) {
        return ((1 + nominalYield) / (1 + inflationRate)) - 1;
    }

    public List<Double> yeild_cal_fun(int bond_Id,double future_infation_rate) {

        List<Double> future_inflation_rate=new ArrayList<Double>();
        List<String> inflation_Risk_op = new ArrayList<>();

        Bond risk_bond = bondrepo.getReferenceById(bond_Id);
        List<inflation_risk_rate> rate_infa = infrate.findAll();
        double inf_rate = rate_infa.get(rate_infa.size() - 1).getValue();

        double anual_rate=(risk_bond.getCoupon_rate()/100) * risk_bond.getFace_value();

        double anual_rate_adj_freq=anual_rate/risk_bond.getCupon_freq();
        double nominal_yield = (anual_rate_adj_freq / risk_bond.getFace_value())*100;

        double cal_val =0;
            for(int i=0;i<6;i++){

//                cal_val=cal_val+inf_rate%future_infation_rate;
                cal_val=cal_val+future_infation_rate;
                future_inflation_rate.add(calculateRealYield(nominal_yield,cal_val));
            }
            return future_inflation_rate;
    }

    }
