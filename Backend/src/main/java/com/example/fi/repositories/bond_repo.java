package com.example.fi.repositories;

import com.example.fi.entity.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface bond_repo extends JpaRepository<Bond,Integer> {
}
