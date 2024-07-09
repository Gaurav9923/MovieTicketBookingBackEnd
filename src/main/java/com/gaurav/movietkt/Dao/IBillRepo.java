package com.gaurav.movietkt.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.movietkt.model.Bill;

public interface IBillRepo extends JpaRepository<Bill, Integer> {

}
