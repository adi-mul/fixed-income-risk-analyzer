package com.example.fi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fi.entity.inflation_risk_rate;

public interface inflation_rate extends JpaRepository<inflation_risk_rate,Integer> {
}
