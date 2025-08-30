package com.example.fi.entity;

import java.util.Date;
import java.time.LocalDate;
import java.math.BigDecimal;

public class bond_val {

    private double faceValue;
    private double couponRate;
    private double yield ;
    private String maturityDate;
    private String settelmentDate;
    private int paymentFrequency;

    public bond_val(double faceValue, double couponRate, double yield, String maturityDate, String settelmentDate, int paymentFrequency) {
        this.faceValue = faceValue;
        this.couponRate = couponRate;
        this.yield = yield;
        this.maturityDate = maturityDate;
        this.settelmentDate = settelmentDate;
        this.paymentFrequency = paymentFrequency;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public double getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(double couponRate) {
        this.couponRate = couponRate;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getSettelmentDate() {
        return settelmentDate;
    }

    public void setSettelmentDate(String settelmentDate) {
        this.settelmentDate = settelmentDate;
    }

    public int getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(int paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }
}
