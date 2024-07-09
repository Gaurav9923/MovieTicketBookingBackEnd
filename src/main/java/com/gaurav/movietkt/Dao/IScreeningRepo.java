package com.gaurav.movietkt.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaurav.movietkt.model.Auditorium;
import com.gaurav.movietkt.model.Screening;

public interface IScreeningRepo extends JpaRepository<Screening, Integer> {
	
	  @Query("SELECT s FROM Screening s WHERE s.screeningId = :id")
	    Screening findByScreeningId2(@Param("id") Integer id);
	Optional<Screening> findByAuditorumFk(Auditorium aud);
	List<Screening> findByScreeningId(Integer screeningId);
	
	

}
