package com.example.fi.entity;

public class float_bond_val {

    private double faceValue ;       // Bond face value
    private double referenceRate ;      // Current reference rate (e.g., LIBOR at 3%)
    private double spread ;             // Fixed spread (1%)
    private double discountRate ;       // Discount rate (4%)
    private String maturityDate ; // Bond maturity date
    private  String settlementDate ; // Bond settlement date
    private int paymentFrequency ;


    public float_bond_val(double faceValue, double referenceRate, double spread, double discountRate, String maturityDate, String settlementDate, int paymentFrequency) {
        this.faceValue = faceValue;
        this.referenceRate = referenceRate;
        this.spread = spread;
        this.discountRate = discountRate;
        this.maturityDate = maturityDate;
        this.settlementDate = settlementDate;
        this.paymentFrequency = paymentFrequency;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public double getReferenceRate() {
        return referenceRate;
    }

    public void setReferenceRate(double referenceRate) {
        this.referenceRate = referenceRate;
    }

    public double getSpread() {
        return spread;
    }

    public void setSpread(double spread) {
        this.spread = spread;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public int getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(int paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }
}
