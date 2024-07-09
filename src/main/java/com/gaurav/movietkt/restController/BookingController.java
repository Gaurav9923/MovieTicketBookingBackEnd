package com.gaurav.movietkt.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.movietkt.model.Reservation;
import com.gaurav.movietkt.model.ReservedSeats;
import com.gaurav.movietkt.model.Seats;
import com.gaurav.movietkt.model.User;
import com.gaurav.movietkt.service.IUserOperation;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins  = "*")
@RestController
@RequestMapping("api/book")
@Slf4j
public class BookingController {
	@Autowired
	IUserOperation userOperationService;
	
	//used to book seats
	@PostMapping("/selectSeat/{auditorium_id}")
    public ResponseEntity<?> getSeatNumberForBooking(@PathVariable("auditorium_id") Integer auditorium_id ,@RequestBody List<Integer> seatNumber) {
		///check given seat number already present in table or not 
		System.out.println(seatNumber+"||||"+auditorium_id);
		
		log.info("Inside BookingController.getSeatNumberForBooking()");
		
		
		List<Seats> seatInTables=userOperationService.selectSeatFromTable(auditorium_id);
		
		
		List<Integer>bookedAlready=new ArrayList();//add to this list if provided seat number is present in table and return it for user to know 
		
		log.info("BookingController.getSeatNumberForBooking()  bookedAlready list::"+bookedAlready.size());
		
		seatInTables.forEach(s->{if(seatNumber.contains(s.getSeatNumber())) {
										bookedAlready.add(s.getSeatNumber());
									}		
		
								});
		
		if(bookedAlready.isEmpty()) {
			Integer count=userOperationService.addSelectedSeatToTable(seatNumber, auditorium_id);
			return new ResponseEntity<>(HttpStatus.OK);
//			return new ResponseEntity<String>(count+" number of seat  bookings stored to seats table ",HttpStatus.OK);
			
		}else {
			String str="";
		  for(int i=0;i<bookedAlready.size();i++) {
			  str=str+" "+bookedAlready.get(i);
		  }
			return new ResponseEntity<>(HttpStatus.OK);
//			return new ResponseEntity<String>(
//					str+" are already booked select other" 
//					,HttpStatus.NOT_ACCEPTABLE);
		}
    	
    }
	
	@PostMapping("/bookedSeat/{auditorium_id}")
	public ResponseEntity<?> getBookedSeatNumber(@PathVariable("auditorium_id") Integer auditorium_id) {
		///check given seat number already present in table or not 
		log.info("Inside BookingController.getBookedSeatNumber()");
		
		
		List<ReservedSeats> seatInTables=userOperationService.selectBookedSeatFromReservedSeatTable(auditorium_id);
		
		
		List<Integer>bookedAlready=new ArrayList();//add to this list if provided seat number is present in table and return it for user to know 
		
		seatInTables.forEach(s->{
			bookedAlready.add(s.getSeatFk().getSeatNumber());
				
		
		});
		
		if(bookedAlready.isEmpty()) {
//			Integer count=userOperationService.addSelectedSeatToTable(seatNumber, auditorium_id);
			return new ResponseEntity<String>("no bookings  reserved table ",HttpStatus.NO_CONTENT);
			
		}else {
			
			return new ResponseEntity<List<Integer>>(
					bookedAlready
					,HttpStatus.OK);
		}
		
	}
	//confirm and proccessing done simultanoousy
	@PostMapping("/confirmSeats/{userId}/{auditoriumId}")
	public ResponseEntity<?> confirmSeatNumberForBooking(@RequestBody List<Integer>  seatNumber,@PathVariable("userId") Integer userId,@PathVariable("auditoriumId") Integer auditoriumId){
		log.info("Inside BookingController.confirmSeatNumberForBooking()");
		
		log.info("to confirm seats==> "+seatNumber +"and auditorium id "+ auditoriumId);
		  List<Integer> ans=userOperationService.addConfirmedSeatsToReservedSeatTable(seatNumber,userId,auditoriumId);
		log.info("confirmed seats==> "+seatNumber);
		  
		  if(ans.isEmpty() ) {
			  return new ResponseEntity<String>("nothing added to reserved table",HttpStatus.NOT_FOUND);
		  }else {
			  String str="";
			  for(int i=0;i<ans.size();i++) {
				  str=str+" "+ans.get(i);
			  }
			  Reservation res= processTicket(userId);
//			  return new ResponseEntity<String>(str+ ":: confirmed successfully and processed reservation is:"+res.getReservationId(),HttpStatus.OK);
			  return new ResponseEntity<>(res.getReservationId(),HttpStatus.OK);
			  
		  }
	}
	
//	@GetMapping("/processTicket/{uid}")
//	public ResponseEntity<?> processTicket(@PathVariable("uid") Integer uid){
//		System.out.println("received uid===> "+uid);
//		
//		User user=userOperationService.selectUserFromUserTable(uid);
//		
//		System.out.println("Controller=====> "+user);
//		userOperationService.addScreeningAndReservedSeatTOReservation(user);
//		List<Reservation> bookList=userOperationService.getUserBooking(uid);
//		
//		Optional<Reservation> bookListSorted=bookList.stream().sorted((r1,r2)->Integer.compare(r2.getReservationId()
//														, r1.getReservationId())
//								).findFirst();
//		
//		
//		
//		
//		return  new ResponseEntity<Reservation>(bookListSorted.get(),HttpStatus.OK);
//		
//	}
	
	@GetMapping("/reservationId/{uid}")
	public ResponseEntity<?> getResevationId(@PathVariable("uid") Integer uid){
		log.info("received uid===> "+uid);
		
		User user=userOperationService.selectUserFromUserTable(uid);
		
		
		List<Reservation> bookList=userOperationService.getUserBooking(uid);
		
		Optional<Reservation> bookListSorted=bookList.stream().sorted((r1,r2)->Integer.compare(r2.getReservationId()
														, r1.getReservationId())
								).findFirst();
		
		
		
		
		return  new ResponseEntity<Reservation>(bookListSorted.get(),HttpStatus.OK);
		
	}
	
	public Reservation  processTicket(Integer uid){
		
		
		User user=userOperationService.selectUserFromUserTable(uid);
		
		
		userOperationService.addScreeningAndReservedSeatTOReservation(user);
		List<Reservation> bookList=userOperationService.getUserBooking(uid);
		
		Optional<Reservation> bookListSorted=bookList.stream().sorted((r1,r2)->Integer.compare(r2.getReservationId()
														, r1.getReservationId())
								).findFirst();
		
		
		
		
			return bookListSorted.get();
	}
	
	
	
	
	
	@GetMapping("/printTicket/{rservationId}")
	public ResponseEntity<?> printTicket(@PathVariable("rservationId") Integer rservationId){
		log.info("Inside BookingController.printTicket()");
		log.info("BookingController.printTicket() :received rservationId===> "+rservationId);
		
		
		
		List<ReservedSeats> bookList=userOperationService.getReservationBooking(rservationId);
		
		System.out.println(bookList.size());
		

		
		
		
		return  new ResponseEntity<List<ReservedSeats>>(bookList,HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/cancelBooking/{reservationId}")
	public ResponseEntity<?> cancelBookingByReservationId(@PathVariable("reservationId") Integer reservationId){
		log.info("BookingController.cancelBookingByReservationId() ==>"+reservationId);
		
	  boolean result=	userOperationService.deleteReservedSeatById(reservationId);
	  if(result) {
	  return  new ResponseEntity<String>("NOT FOUND",HttpStatus.NOT_FOUND);
	  }else {
		  
		  return  new ResponseEntity<>(HttpStatus.OK);
	  }
		
		
	}
	
	

}
