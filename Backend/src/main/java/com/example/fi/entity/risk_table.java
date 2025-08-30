package com.example.fi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "risk_data_table")
public class risk_table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double cupon_rate;
    private double avg_rate;
    private double current_rate;
    private double real_rate_of_return;
    private double break_even_inflation_rate;
    private double inflation_risk_rate_premium;
    private double liquidity_risk_bid_ask_spread;
    private double liquidity_risk_turnover_ratio;
    private double yield_to_maturity;

}
