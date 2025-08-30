package com.example.fi.controller;

import com.example.fi.entity.Bond;
import com.example.fi.entity.float_bond_val;
import com.example.fi.entity.risk_table;
import com.example.fi.entity.spread_analysis_data;
import com.example.fi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
//@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/fi")
public class Bond_post_data {

    @Autowired
    Bond_service serv_bond;

    @Autowired
    risk_data_table risk_table_post;

    @Autowired
    get_risk_data_table get_data_risk_table;

    @Autowired
    yeild_analysis yeild;

    @Autowired
    spread_analysis spread;

    @Autowired
    yeild_to_call call_yield;

    @Autowired
    treasury_spread_ana treasury;
    @PostMapping("/post_Bond_data")
    public Bond post_bond_data(@RequestBody Bond bond_data){
        return serv_bond.post_Bond_data(bond_data);
    }

    @PostMapping("/risk_post_Bond_data/{bondId}")
    public List<Double> post_risk_table_data(@PathVariable int bondId){
        return risk_table_post.post_data_risk_table(bondId);
    }

    @GetMapping("/get_risk_bond_data")
    public  List<risk_table> get_risk_data(){
        return get_data_risk_table.get_risk_table_data();
    }

    @GetMapping("/yield_cal/{bondId}/{future_inflation_value}")
    public List<Double> yeild_cal(@PathVariable int bondId, @PathVariable double future_inflation_value){
        return  yeild.yeild_cal_fun(bondId,future_inflation_value);
    }

    @GetMapping("/spread/{bondId}/{spread_value}")
    public  List<Double> spread_ana(@PathVariable int bondId,@PathVariable double spread_value){
        return spread.get_spread_data(spread_value,bondId);
    }
    @GetMapping("/spread_bond/{bondId}")
    public  List<Double> spread_bond(@PathVariable int bondId){
     return treasury.treasury_op(bondId);
    }

    @GetMapping("/yield_to_call/{bondId}/{years_to_maturity}/{Call_price}/{call_years}/{future_infation_rate}")
    public  double yield_call_data(@PathVariable int bondId,@PathVariable int years_to_maturity,@PathVariable double Call_price,@PathVariable int call_years,@PathVariable double future_infation_rate){
        return call_yield.yeild_to_call_data( bondId, years_to_maturity, Call_price, call_years,future_infation_rate);
    }

}

