package com.gaurav.movietkt.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.movietkt.model.Movies;

public interface IMovieRepo extends JpaRepository<Movies, Integer> {

}
