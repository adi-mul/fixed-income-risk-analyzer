package com.example.fi.service;

import com.example.fi.entity.Bond;
import com.example.fi.entity.inflation_risk_rate;
import com.example.fi.entity.spread_analysis_data;
import com.example.fi.repositories.bond_repo;
import com.example.fi.repositories.inflation_rate;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.fi.repositories.treasury_spread_analysis;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class treasury_spread_ana {
    @Autowired
    treasury_spread_analysis  treasury;

    @Autowired
    bond_repo bondrepo;

    @Autowired
    inflation_rate infrate;

    private static double bondPrice(double faceValue, double marketPrice, double couponRate, int yearsToMaturity, int paymentFrequency, double ytm) {
        double price = 0;
        double couponPayment = (faceValue * couponRate) / paymentFrequency;

        for (int t = 1; t <= yearsToMaturity * paymentFrequency; t++) {
            price += couponPayment / Math.pow(1 + ytm, t);
        }

        price += faceValue / Math.pow(1 + ytm, yearsToMaturity * paymentFrequency);
        return price - marketPrice; // The function f(ytm) = bondPrice - marketPrice
    }

    private static double bondPriceDerivative(double faceValue, double couponRate, int yearsToMaturity, int paymentFrequency, double ytm) {
        double derivative = 0;
        double couponPayment = (faceValue * couponRate) / paymentFrequency;

        for (int t = 1; t <= yearsToMaturity * paymentFrequency; t++) {
            derivative += -t * couponPayment / Math.pow(1 + ytm, t + 1);
        }

        derivative += -yearsToMaturity * paymentFrequency * faceValue / Math.pow(1 + ytm, yearsToMaturity * paymentFrequency + 1);
        return derivative;
    }


    public static double calculateYTM(double faceValue, double marketPrice, double couponRate, int yearsToMaturity, int paymentFrequency) {
        double ytm = 0.05; // Initial guess (5%)
        double epsilon = 1e-6; // Convergence threshold
        int maxIterations = 1000;

        for (int i = 0; i < maxIterations; i++) {
            double fValue = bondPrice(faceValue, marketPrice, couponRate, yearsToMaturity, paymentFrequency, ytm);
            double fDerivative = bondPriceDerivative(faceValue, couponRate, yearsToMaturity, paymentFrequency, ytm);

            double newYTM = ytm - fValue / fDerivative;

            if (Math.abs(newYTM - ytm) < epsilon) {
                return newYTM * paymentFrequency; // Convert periodic YTM to annualized YTM
            }
            ytm = newYTM;
        }

        return -1; // Return -1 if it does not converge
    }




    public List<Double> treasury_op(int bond_Id){
        List<Double> spread_difference = new ArrayList<>();
        List<inflation_risk_rate> rate_infla = infrate.findAll();
        double inf_rate = rate_infla.get(rate_infla.size() - 1).getValue();

        List<spread_analysis_data> rate_infa = treasury.findAll();
        double first_year = rate_infa.get(rate_infa.size() - 1).getOne_year();
        double third_year = rate_infa.get(rate_infa.size() - 1).getThird_year();
        double fifth_year = rate_infa.get(rate_infa.size() - 1).getFifth_year();
        double seventh_year = rate_infa.get(rate_infa.size() - 1).getSeventh_year();
        double ten_year = rate_infa.get(rate_infa.size() - 1).getTen_year();
        double twenty_year = rate_infa.get(rate_infa.size() - 1).getTwenty_year();
        double thirty_year = rate_infa.get(rate_infa.size() - 1).getThirty_year();

        Bond risk_bond = bondrepo.getReferenceById(bond_Id);

        double faceValue = risk_bond.getFace_value();
        double marketPrice =risk_bond.getAsk_price();
        double couponRate = risk_bond.getCoupon_rate()/100;
        int yearsToMaturity= risk_bond.getYears() ;
        int paymentFrequency =  risk_bond.getCupon_freq();
        double ymt_val= calculateYTM(faceValue,marketPrice,couponRate,yearsToMaturity,paymentFrequency)*100;

        spread_difference.add(ymt_val);
        spread_difference.add(first_year);
        spread_difference.add(third_year);
        spread_difference.add(fifth_year);
        spread_difference.add(seventh_year);
        spread_difference.add(ten_year);

        spread_difference.add(twenty_year);
        spread_difference.add(thirty_year);

        return spread_difference;

    }



}
