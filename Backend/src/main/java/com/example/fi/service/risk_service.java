package com.example.fi.service;
import com.example.fi.entity.inflation_risk_rate;
import com.example.fi.entity.risk_table;
import com.example.fi.entity.sofr_risk_data;
import com.example.fi.repositories.bond_repo;
import com.example.fi.entity.Bond;
import com.example.fi.repositories.inflation_rate;
import com.example.fi.repositories.risk_table_data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.example.fi.repositories.sofr_reinvest_risk_data;

import java.util.ArrayList;
import java.util.List;

@Service
public class risk_service {

    @Autowired
     bond_repo bondrepo;

    @Autowired
    inflation_rate infrate;

    @Autowired
    risk_table_data risk_table_data_var;
    @Autowired
    sofr_reinvest_risk_data sofr_reinvest_risk_data;
    List<String> risk= new ArrayList<String>();

    public List<String> risk_cal(int bond_Id) {

//        List<String> risk= new ArrayList<String>();
        risk.clear();
        Bond risk_bond = bondrepo.getReferenceById(bond_Id);

        calDefaultRisk(risk_bond);
        calReinvestmentRisk(bond_Id);
        infationRisk(bond_Id);
        LiquidityRisk(bond_Id);
        ytm(bond_Id);
        return risk;
      //  return calDefaultRisk(risk_bond) + calReinvestmentRisk(bond_Id) +infationRisk(bond_Id);
    }
///// DEFAULT RISK /////
    public void calDefaultRisk(Bond risk_bond){
        String riskCatergory = risk_bond.getCredit_rating();

        String mitigation ;

        if (riskCatergory.equals("AAA+")||riskCatergory.equals("AAA")||riskCatergory.equals("AAA-")) {
            mitigation ="Default Risk is low , Issuer has extremely strong capacity to meet its financial commitments " ;
        } else if (riskCatergory.equals("AA+")||riskCatergory.equals("AA")||riskCatergory.equals("AA-")) {
            mitigation="Default Risk is low , Issuer has very strong capacity to meet its financial commitments";
        }
        else if (riskCatergory.equals("A+")||riskCatergory.equals("A")||riskCatergory.equals("A-")) {
            mitigation="Default Risk is medium , Issuer has strong capacity to meet its financial commitments but is somewhat more susceptible to the adverse effects of changes in circumstances and economic conditions than issuer is in higher-rated categories.";
        }
        else if (riskCatergory.equals("BBB+")||riskCatergory.equals("BBB")||riskCatergory.equals("BBB-")) {
            mitigation="Default Risk is medium ,Issuer has adequate capacity to meet its financial commitments. However, adverse economic conditions or changing circumstances are more likely to lead to a weakened capacity of the issue to meet its financial commitments.";
        }
        else if (riskCatergory.equals("BB+")||riskCatergory.equals("BB")||riskCatergory.equals("BB-")) {
            mitigation="Default Risk is high,Issuer is less vulnerable in the near term than other lower-rated . However, it faces major ongoing uncertainties and exposure to adverse business, financial, or economic conditions which could lead to the issuer inadequate capacity to meet its financial commitments.";
        }
        else if (riskCatergory.equals("B+")||riskCatergory.equals("B")||riskCatergory.equals("B-")) {
            mitigation=" Default Risk is high,Issuer is more vulnerable than the issuer rated 'BB', but the issuer currently may have the capacity to meet its financial commitments. Adverse business, financial, or economic conditions will likely impair the issuer capacity or willingness to meet its financial commitments.";
        }
        else if (riskCatergory.equals("CCC+")||riskCatergory.equals("CCC")||riskCatergory.equals("CCC-")) {
            mitigation="Default Risk is highest ,Issuer is currently vulnerable, and is dependent upon favourable business, financial, and economic conditions to meet its financial commitments.";
        }
        else if (riskCatergory.equals("CC+")||riskCatergory.equals("CC")||riskCatergory.equals("CC-")) {
            mitigation=" Default Risk is highest ,Issuer is currently highly vulnerable.";
        }
        else if (riskCatergory.equals("C+")||riskCatergory.equals("C")||riskCatergory.equals("C-")) {
            mitigation="Avoid investment, Issuer is currently highly vulnerable to nonpayment. May be used where a bankruptcy petition has been filed.";
        }
        else if (riskCatergory.equals("D")) {
            mitigation="Issuer has failed to pay one or more of its financial obligations";
        }
        else {
            mitigation="Please check the provided credit rating ,data is Inaccurate";
        }

         risk.add(mitigation);
    }
////REINVESTMENT RISK /////
    public  void calReinvestmentRisk(int bond_Id){

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
//        String riskCategory = riskValue > 1.0 ? "High" : "Low";

//        risk.add("riskCategory : "+riskCategory);
        risk.add("coupon rate : " + couponRate);
        risk.add("avg rate : "+averageRate);
        risk.add("current rate : "+currentRate);



    //return riskCategory+"coupon rate :" + couponRate +"avg rate :"+averageRate+"current rate :"+currentRate;
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

    public void infationRisk(int bond_Id) {
        List<String> inflation_Risk_op = new ArrayList<>();

        Bond risk_bond = bondrepo.getReferenceById(bond_Id);
        List<inflation_risk_rate> rate_infa = infrate.findAll();

        double inf_rate = rate_infa.get(rate_infa.size() - 1).getValue();
      // double nominal_yield = ((risk_bond.getCoupon_rate() * risk_bond.getCupon_freq()) % risk_bond.getFace_value()) / risk_bond.getFace_value();
        double anual_rate=(risk_bond.getCoupon_rate()/100) * risk_bond.getFace_value();
        System.out.print("   "+risk_bond.getCupon_freq());
        double anual_rate_adj_freq=anual_rate/risk_bond.getCupon_freq();
        double nominal_yield = (anual_rate_adj_freq / risk_bond.getFace_value())*100;
        System.out.print(anual_rate+"   "+anual_rate_adj_freq+"   "+nominal_yield);

        double real_yeild = calculateRealYield(nominal_yield, inf_rate);
        double real_rate_of_return = calculateRealRateOfReturn(nominal_yield, inf_rate);

        double break_even_inflation_rate = calculateBreakEvenInflationRate(nominal_yield, real_yeild);

        double inflation_ris_premimum = calculateInflationRiskPremium(nominal_yield, real_yeild, inf_rate);

        String output=" ";

        String op_real_rate_of_return =" ";

        String op_inflation_ris_premium= " ";

        String op_break_even_inflation_risk=" ";
        if(real_rate_of_return<0){
            op_real_rate_of_return="Real rate of return : "+real_rate_of_return+" ,Real rate of return is negative,this indicate inflation is exceeding the investment gains";
        }
        else {
            op_real_rate_of_return="Real rate of return : "+real_rate_of_return;
        }

        if(inflation_ris_premimum<0){
            op_inflation_ris_premium =" Inflation risk premium : "+ inflation_ris_premimum+" ,Inflation risk premium is negative , this indicate purchasing power of the principal will decrease over time.";
        }
        else {
            op_inflation_ris_premium=" Inflation risk premium : "+inflation_ris_premimum;
        }
        if(break_even_inflation_rate<0){
            op_break_even_inflation_risk ="Break even inflation rate : "+break_even_inflation_rate +", Break even inflation rate is negative , this indicate investors believe the economy may experience deflation in near future";
        }
        else {
            op_break_even_inflation_risk="Break even inflation rate :"+break_even_inflation_rate;
        }
        risk.add(op_real_rate_of_return);
        risk.add(op_break_even_inflation_risk);
        risk.add(op_inflation_ris_premium);
        //return op_real_rate_of_return + op_break_even_inflation_risk + op_inflation_ris_premium;
    }

    ////Liquidity Risk///

    public void LiquidityRisk(int bond_Id){
        Bond risk_bond = bondrepo.getReferenceById(bond_Id);


        double mid_price = (risk_bond.getAsk_price()+risk_bond.getBid_price())/2;

        double liquidity_risk_bid_ask_spread = ((risk_bond.getAsk_price()- risk_bond.getBid_price())/mid_price)*100;

        double liquidity_risk_turnover_ratio =(1-(risk_bond.getBond_volume()/risk_bond.getTotal_outstanding()))*100;

        risk.add("liquidity_risk_bid_ask_spread : "+liquidity_risk_bid_ask_spread);
        risk.add("liquidity_risk_turnover_ratio : " +liquidity_risk_turnover_ratio);
        //return "liquidity_risk_bid_ask_spread : "+liquidity_risk_bid_ask_spread +"   liquidity_risk_turnover_ratio : " +liquidity_risk_turnover_ratio;
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

    public  void ytm(int bond_Id){

        Bond risk_bond = bondrepo.getReferenceById(bond_Id);

        double faceValue = risk_bond.getFace_value();
        double marketPrice =risk_bond.getAsk_price();
        double couponRate = risk_bond.getCoupon_rate()/100;
        int yearsToMaturity= risk_bond.getYears() ;
        int paymentFrequency =  risk_bond.getCupon_freq();
       risk.add("Yield to Maturity" + calculateYTM(faceValue,marketPrice,couponRate,yearsToMaturity,paymentFrequency)*100);

        //risk.add("Yield to Maturity"+calculateYTM(1000,950,0.05,10,2)*100);
    }

}





