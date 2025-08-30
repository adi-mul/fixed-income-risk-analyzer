package com.example.fi.repositories;

import com.example.fi.entity.spread_analysis_data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface treasury_spread_analysis extends JpaRepository< spread_analysis_data,Integer> {
}
