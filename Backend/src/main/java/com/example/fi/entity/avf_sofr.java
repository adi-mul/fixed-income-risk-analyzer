package com.example.fi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "avgrate")

public class  avf_sofr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String date;

    private String rate_type;

    private Double avg_30;

    private Double avg_90;

    private Double avg_180;

    private String sofr_index;

}
