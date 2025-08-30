package com.example.fi.service;

import com.example.fi.entity.Bond;
import com.example.fi.entity.risk_table;
import com.example.fi.repositories.bond_repo;
import com.example.fi.repositories.risk_table_data;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.fi.repositories.bond_repo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class yeild_to_call {

    List<Double> future_inflation_rate = new ArrayList<>();
    @Autowired
    risk_table_data risk_table_data_var;

    @Autowired
    bond_repo bondrepo;

    public static double calculateBondPrice(double faceValue, double couponRate, double yieldToMaturity, int yearsToMaturity, int periodsPerYear) {
        double couponPayment = (couponRate * faceValue) / periodsPerYear;
        double r = yieldToMaturity / periodsPerYear;
        int totalPeriods = yearsToMaturity * periodsPerYear;
        double price = 0;

        // Present value of coupon payments
        for (int t = 1; t <= totalPeriods; t++) {
            price += couponPayment / Math.pow(1 + r, t);
        }

        // Present value of face value
        price += faceValue / Math.pow(1 + r, totalPeriods);

        System.out.print(price);
        return price;


    }

    public static double calculateYTC(double couponPayment, double callPrice, double currentPrice, int yearsToCall) {
        return (couponPayment + (callPrice - currentPrice) / yearsToCall) / ((callPrice + currentPrice) / 2);
    }


    public double calculateRealYield(double nominalYield, double inflationRate) {
        return ((1 + nominalYield) / (1 + inflationRate)) - 1;
    }


    public Double yeild_to_call_data(int bond_Id,int years_to_maturity,double Call_price,int call_years, double future_infation_rate){
        Bond risk_bond = bondrepo.getReferenceById(bond_Id);
        risk_table ris_data= risk_table_data_var.getReferenceById(bond_Id);
        double faceValue = risk_bond.getFace_value();

        double couponRate = risk_bond.getCoupon_rate()/100;
        int yearsToMaturity= risk_bond.getYears() ;
        int paymentFrequency =  risk_bond.getCupon_freq();
        double data_yield_to_maturity= ris_data.getYield_to_maturity();
       double current_price= calculateBondPrice(faceValue,couponRate,data_yield_to_maturity,years_to_maturity,paymentFrequency);

        double yield_to_call =calculateYTC(couponRate,Call_price,current_price,call_years)*100;

        return yield_to_call;
    }
}
