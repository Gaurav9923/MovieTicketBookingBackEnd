package com.gaurav.movietkt.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.movietkt.model.Theater;

public interface ITheaterRepo extends JpaRepository<Theater, Integer> {

}
