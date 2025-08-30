package com.example.fi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bond_table")
public class Bond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bond_name;
    private String issuer;
    private double face_value;
    private double coupon_rate;
    private String maturity_date;
    private String currency;
    private String credit_rating;
    private int cupon_freq;
    private double bid_price;
    private double ask_price;
    private double bond_volume;
    private double total_outstanding;
    private int years;

}
