package com.example.fi.service;

import com.example.fi.entity.float_bond_val;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class float_bond_val_service {


    public  double cal_float_bond_val(float_bond_val floatBond) {

        double faceValue = floatBond.getFaceValue();
        String settlementDate = floatBond.getSettlementDate();
        String maturityDate = floatBond.getMaturityDate();
        int paymentFrequency = floatBond.getPaymentFrequency();

        // Convert rates to decimals
        double adjustedReferenceRate = floatBond.getReferenceRate()/ 100.0;
        double adjustedSpread = floatBond.getSpread() / 100.0;
        double adjustedDiscountRate = floatBond.getDiscountRate() / 100.0;

        // Parse dates
        LocalDate settlement = LocalDate.parse(settlementDate);
        LocalDate maturity = LocalDate.parse(maturityDate);

        // Validate dates
        if (settlement.isAfter(maturity)) {
            throw new IllegalArgumentException("Settlement date must be before maturity date.");
        }

        // Calculate the total number of days to maturity
        long totalDaysToMaturity = ChronoUnit.DAYS.between(settlement, maturity);

        // Calculate the number of periods (coupon payments) remaining
        int totalPeriods = (int) (totalDaysToMaturity / (365.0 / paymentFrequency));

        // Calculate the periodic discount rate
        double periodDiscountRate = adjustedDiscountRate / paymentFrequency;

        // Calculate the periodic coupon payment
        double couponRate = adjustedReferenceRate + adjustedSpread;
        double couponPayment = (faceValue * couponRate) / paymentFrequency;

        // Initialize the bond price
        double bondPrice = 0.0;

        // Calculate the present value of coupon payments
        for (int t = 1; t <= totalPeriods; t++) {
            bondPrice += couponPayment / Math.pow(1 + periodDiscountRate, t);
        }

        // Add the present value of the face value (principal repayment at maturity)
        bondPrice += faceValue / Math.pow(1 + periodDiscountRate, totalPeriods);

        return bondPrice;

    }
}
