package com.gaurav.movietkt.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.movietkt.model.Auditorium;

public interface IAuditoriumRepo extends JpaRepository<Auditorium, Integer> {
//   public  List<Auditorium> findAllByAuditoriumId(Integer auditoriumId);
}
