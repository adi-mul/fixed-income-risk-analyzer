package com.example.fi.repositories;

import com.example.fi.entity.sofr_risk_data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface sofr_reinvest_risk_data extends JpaRepository<sofr_risk_data,Integer> {
}
