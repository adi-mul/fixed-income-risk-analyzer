package com.example.fi.service;
import  com.example.fi.entity.bond_val;
import org.springframework.stereotype.Service;

import  java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class bond_val_service {

//BOND PRICE CALCULATION SERVICE

    public  double cal_bond_value(bond_val bond) {

         double faceValue =bond.getFaceValue();
         double couponRate=bond.getCouponRate()/100;
        double yield=bond.getYield()/100 ;
        String settlementDate = bond.getSettelmentDate();
        String maturityDate = bond.getMaturityDate();
        int paymentFrequency=bond.getPaymentFrequency();

//        System.out.print(faceValue);
//        System.out.print(couponRate);
//        System.out.print(yield);
//        System.out.print(settlementDate);
//        System.out.print(maturityDate);
//        System.out.print(paymentFrequency);
        // Parse the maturity and settlement dates
        LocalDate maturity = LocalDate.parse(maturityDate);
        LocalDate settlement = LocalDate.parse(settlementDate);

        // Calculate the total number of days between settlement and maturity
        long totalDaysToMaturity = ChronoUnit.DAYS.between(settlement, maturity);

        // Calculate the number of periods (coupons) to maturity
        int totalPeriods = (int) (totalDaysToMaturity / (365.0 / paymentFrequency));

        // Adjust yield and coupon payment for the payment frequency
        double periodYield = yield / paymentFrequency;
        double couponPayment = (faceValue * couponRate) / paymentFrequency;

        double price = 0.0;

        // Calculate the present value of coupon payments
        for (int t = 1; t <= totalPeriods; t++) {
            price += couponPayment / Math.pow(1 + periodYield, t);
        }

        // Add the present value of the face value
        price += faceValue / Math.pow(1 + periodYield, totalPeriods);

        return price;
    }
}
