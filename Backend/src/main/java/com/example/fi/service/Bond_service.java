package com.example.fi.service;


import com.example.fi.entity.Bond;
import com.example.fi.repositories.bond_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bond_service {

    @Autowired
bond_repo Bond;


       public Bond post_Bond_data(Bond bond){

          return Bond.save(bond);
       }

}
