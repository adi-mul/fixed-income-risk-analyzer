package com.example.fi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fi.entity.avf_sofr;
import org.springframework.stereotype.Repository;


public interface avg_rate_repo extends JpaRepository<avf_sofr,Integer> {
}
