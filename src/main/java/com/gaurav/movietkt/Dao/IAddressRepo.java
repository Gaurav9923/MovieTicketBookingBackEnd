package com.gaurav.movietkt.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.movietkt.model.Address;

public interface IAddressRepo extends JpaRepository<Address, Integer> {

}
