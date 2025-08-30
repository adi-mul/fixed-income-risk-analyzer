package com.example.fi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "treasury_yield")

public class spread_analysis_data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private double first_month;
    private double second_month;
    private double third_month;
    private double fourth_month;
    private double sixth_month;
    private double one_year;
    private double second_year;
    private double third_year;
    private double fifth_year;
    private double seventh_year;
    private double ten_year;
    private double twenty_year;
    private double thirty_year;

}
