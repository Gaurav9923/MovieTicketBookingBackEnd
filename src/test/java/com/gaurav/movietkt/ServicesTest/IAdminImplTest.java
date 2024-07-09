package com.gaurav.movietkt.ServicesTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaurav.movietkt.Dao.IAuditoriumRepo;
import com.gaurav.movietkt.Dao.IMovieRepo;
import com.gaurav.movietkt.Dao.IScreeningRepo;
import com.gaurav.movietkt.model.Auditorium;
import com.gaurav.movietkt.model.Movies;
import com.gaurav.movietkt.model.Screening;
import com.gaurav.movietkt.service.IAdminImpl;


@ExtendWith(MockitoExtension.class)
public class IAdminImplTest {

    @InjectMocks
    IAdminImpl iAdmin;

    @Mock
    IMovieRepo movieRepo; 
    
    
    @Mock
    IAuditoriumRepo auditoriumRepo;
    
    @Mock
    IScreeningRepo screeningRepo;



    @Test
    public void testAddMovie() {
    	
        Movies movie = Movies.builder()
        				.movieId(1)
        				.movieName("Test")
        				.description("test")
        				.genre("test")
        				.build();
        		
        		
        

        when(movieRepo.save(any(Movies.class))).thenReturn(movie);

        String result = iAdmin.addMovie(movie);

       Assertions.assertThat(result).isNotNull();
    }
    
    @Test
    public void testGetMovieWithId() {
    	 Movies movie = Movies.builder()
 				.movieId(1)
 				.movieName("Test")
 				.description("test")
 				.genre("test")
 				.build();
 		

        when(movieRepo.findById(anyInt())).thenReturn(Optional.of(movie));

        Movies result = iAdmin.getMovieWhithId(1);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void testGetAllMovies() {
    	 Movies movie1 = Movies.builder()
  				.movieId(1)
  				.movieName("Test1")
  				.description("test")
  				.genre("test")
  				.build();
    	 
    	 Movies movie2 = Movies.builder()
  				.movieId(2)
  				.movieName("Test2")
  				.description("test")
  				.genre("test")
  				.build();
        when(movieRepo.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        List<Movies> result = iAdmin.getAllMovie();

        Assertions.assertThat(result).isNotNull();
    }
    
    
    
    @Test
    public void testAddAuditorium() {
    
    	Auditorium auditorium=Auditorium.builder()
    							.auditoriumId(1)
    							.auditoriumName("auditoriumTest")
    							.build();
    	
    	when(auditoriumRepo.save(any(Auditorium.class))).thenReturn(auditorium);
    	
    	Auditorium result =auditoriumRepo.save(auditorium);
    	
    	
    	Assertions.assertThat(result).isNotNull();
    	
    	
    }
    
    @Test
    public void testGetAllAuditorium() {
    	
    	Auditorium auditorium1=Auditorium.builder()
    			.auditoriumId(1)
    			.auditoriumName("auditoriumTest1")
    			.build();
    	
    	Auditorium auditorium2=Auditorium.builder()
    			.auditoriumId(1)
    			.auditoriumName("auditoriumTest2")
    			.build();
    	
    	when(auditoriumRepo.findAll()).thenReturn(Arrays.asList(auditorium1,auditorium2));
    	
    	List<Auditorium> result =auditoriumRepo.findAll();
    	
    	
    	Assertions.assertThat(result).isNotNull();
    	
    	
    }
    
    
    @Test
    public void TestAddScreeining() {
    	Screening screening=Screening.builder()
    			.screeningId(1)
    			.build();
    	
    	when(screeningRepo.save(any(Screening.class))).thenReturn(screening);
    	
    	Screening result=screeningRepo.save(screening);
    	Assertions.assertThat(result).isNotNull();
    	
    	
    	
    }
    
    @Test
    public void TestGetScreeiningWithScreeningId() {
    	Screening screening=Screening.builder()
    			.screeningId(1)
    			.build();
    	
    	when(screeningRepo.findById(anyInt())).thenReturn(Optional.of(screening));
    	Optional<Screening> result=screeningRepo.findById(1);
    	Assertions.assertThat(result.get()).isNotNull();
    }
    
    
    
    
    
    
    
}
