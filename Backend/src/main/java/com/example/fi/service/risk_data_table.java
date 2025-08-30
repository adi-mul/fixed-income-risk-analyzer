package com.example.fi.service;

import com.example.fi.entity.Bond;
import com.example.fi.entity.inflation_risk_rate;
import com.example.fi.entity.risk_table;
import com.example.fi.entity.sofr_risk_data;
import com.example.fi.repositories.bond_repo;
import com.example.fi.repositories.inflation_rate;
import com.example.fi.repositories.risk_table_data;
import com.example.fi.repositories.sofr_reinvest_risk_data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class risk_data_table {

    @Autowired
    bond_repo bondrepo;

    @Autowired
    inflation_rate infrate;

    @Autowired
    sofr_reinvest_risk_data sofr_reinvest_risk_data;

    @Autowired
    risk_table_data risk_table_data_var;

    List<Double> risk= new ArrayList<>();


////MAIN FUNCTION /////////
    public List<Double> post_data_risk_table(int bond_Id){
        Bond risk_bond = bondrepo.getReferenceById(bond_Id);
        risk_table risk_post_data_var = new risk_table();

        calReinvestmentRisk(bond_Id,risk_post_data_var);
        infationRisk(bond_Id,risk_post_data_var);
        LiquidityRisk(bond_Id,risk_post_data_var);
        ytm(bond_Id,risk_post_data_var);


        risk_table_data_var.save(risk_post_data_var);

        return risk;
    }



    ////REINVESTMENT RISK /////
    public  void calReinvestmentRisk(int bond_Id,risk_table risk_post_data_var){

        List<sofr_risk_data> historicalRates =  sofr_reinvest_risk_data.findAll();

        double currentRate = historicalRates.get(historicalRates.size() - 1).getRate() * 100;
        double averageRate = historicalRates.stream()
                .mapToDouble(sofr_risk_data::getRate)
                .average()
                .orElse(0.0);

        Bond risk_bond = bondrepo.getReferenceById(bond_Id);

        double couponRate = risk_bond.getCoupon_rate();
        double riskValue = Math.max(0, couponRate - averageRate);

        // Assess risk category
        String riskCategory = riskValue > 1.0 ? "High" : "Low";



        risk.add(couponRate);
        risk.add(averageRate);
        risk.add(currentRate);
        risk_post_data_var.setCupon_rate(couponRate);
        risk_post_data_var.setAvg_rate(averageRate);
        risk_post_data_var.setCurrent_rate(currentRate);


    }
///// INVESTMENT RISK /////

    public double calculateRealYield(double nominalYield, double inflationRate) {
        return ((1 + nominalYield) / (1 + inflationRate)) - 1;
    }
    public double calculateRealRateOfReturn(double nominalRate, double inflationRate) {
        return ((1 + nominalRate) / (1 + inflationRate)) - 1;
    }

    public double calculateBreakEvenInflationRate(double nominalRate, double realYield) {
        return nominalRate - realYield;
    }

    public double calculateInflationRiskPremium(double nominalRate, double realYield, double expectedInflationRate) {
        return nominalRate - realYield - expectedInflationRate;
    }
///INFLATION RISK/////

    public void infationRisk(int bond_Id, risk_table risk_post_data_var) {
        List<String> inflation_Risk_op = new ArrayList<>();

        Bond risk_bond = bondrepo.getReferenceById(bond_Id);
        List<inflation_risk_rate> rate_infa = infrate.findAll();

        double inf_rate = rate_infa.get(rate_infa.size() - 1).getValue();
        double nominal_yield = ((risk_bond.getCoupon_rate() * risk_bond.getCupon_freq()) % risk_bond.getFace_value()) / risk_bond.getFace_value();

        double real_yeild = calculateRealYield(nominal_yield, inf_rate);
        double real_rate_of_return = calculateRealRateOfReturn(nominal_yield, inf_rate);

        double break_even_inflation_rate = calculateBreakEvenInflationRate(nominal_yield, real_yeild);

        double inflation_ris_premium = calculateInflationRiskPremium(nominal_yield, real_yeild, inf_rate);



        risk.add(real_rate_of_return);
        risk.add(break_even_inflation_rate);
        risk.add(inflation_ris_premium);
        risk_post_data_var.setReal_rate_of_return(real_rate_of_return);
        risk_post_data_var.setBreak_even_inflation_rate(break_even_inflation_rate);
        risk_post_data_var.setInflation_risk_rate_premium(inflation_ris_premium);

    }

    public void LiquidityRisk(int bond_Id,risk_table risk_post_data_var ){
        Bond risk_bond = bondrepo.getReferenceById(bond_Id);


        double mid_price = (risk_bond.getAsk_price()+risk_bond.getBid_price())/2;

        double liquidity_risk_bid_ask_spread = ((risk_bond.getAsk_price()- risk_bond.getBid_price())/mid_price)*100;

        double liquidity_risk_turnover_ratio =(1-(risk_bond.getBond_volume()/risk_bond.getTotal_outstanding()))*100;



        risk.add(liquidity_risk_bid_ask_spread);
        risk.add(liquidity_risk_turnover_ratio);

        risk_post_data_var.setLiquidity_risk_bid_ask_spread(liquidity_risk_bid_ask_spread);
        risk_post_data_var.setLiquidity_risk_turnover_ratio(liquidity_risk_turnover_ratio);

    }


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
    public  void ytm(int bond_Id,risk_table risk_post_data_var){

        Bond risk_bond = bondrepo.getReferenceById(bond_Id);

        double faceValue = risk_bond.getFace_value();
        double marketPrice =risk_bond.getAsk_price();
        double couponRate = risk_bond.getCoupon_rate()/100;
        int yearsToMaturity= risk_bond.getYears() ;
        int paymentFrequency =  risk_bond.getCupon_freq();
       double ymt_value= calculateYTM(faceValue,marketPrice,couponRate,yearsToMaturity,paymentFrequency)*100;

       risk_post_data_var.setYield_to_maturity(ymt_value);

        //risk.add("Yield to Maturity"+calculateYTM(1000,950,0.05,10,2)*100);
    }

}
