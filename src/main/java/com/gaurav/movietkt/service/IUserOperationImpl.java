package com.gaurav.movietkt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.gaurav.movietkt.Dao.IAuditoriumRepo;
import com.gaurav.movietkt.Dao.IReservationRepo;
import com.gaurav.movietkt.Dao.IReservedSeatsRepo;
import com.gaurav.movietkt.Dao.IScreeningRepo;
import com.gaurav.movietkt.Dao.ISeatsRepo;
import com.gaurav.movietkt.Dao.IUserRepo;
import com.gaurav.movietkt.Exception.UserException.NotFoundException;
import com.gaurav.movietkt.model.Auditorium;
import com.gaurav.movietkt.model.Reservation;
import com.gaurav.movietkt.model.ReservedSeats;
import com.gaurav.movietkt.model.Screening;
import com.gaurav.movietkt.model.Seats;
import com.gaurav.movietkt.model.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IUserOperationImpl implements IUserOperation {
	public static Integer TOTAL_SEATS_CAPACITY;
	public static Integer TOTAL_SEATS_AVAILABLE;
	public static Integer TOTAL_SEATS_BOOKED;

	@Autowired
	IScreeningRepo screeningRepo;

	@Autowired
	ISeatsRepo seatRepo;
	


	@Autowired
	IReservedSeatsRepo reservedSeatRepo;

	@Autowired
	IAuditoriumRepo auditoriumRepo;
	
	@Autowired
	IUserRepo userRepo;

	@Autowired
	IReservationRepo reservationRepo;
	
	@Override
	public  Map<String,Integer> getCurrentAvalibility(Integer audId) {
		log.info("Inside IUserOperationImpl.getCurrentAvalibility()");
		
		Map<String,Integer> availMap=new HashMap<>();
		checkSeatAvailability(audId);
    	
    	availMap.put("available", TOTAL_SEATS_AVAILABLE);
    	availMap.put("Booked", TOTAL_SEATS_BOOKED);
    	availMap.put("Capacity", TOTAL_SEATS_CAPACITY);
    	
    	System.out.println("IUserOperationImpl.getCurrentAvalibility():availMap-->"+availMap);
    	
    	return availMap;
	}
	
	
	
	public void checkSeatAvailability(Integer auditorium_id) {
//		if(seatRepo.findByAuditoriumId(auditorium_id).isEmpty())
//            throw new NotFoundException("Seat Find by Auditorium Id Not Found ");
		List<Seats> lis = seatRepo.findByAuditoriumId(auditorium_id);

		TOTAL_SEATS_CAPACITY = auditoriumRepo.findById(auditorium_id).get().getNumber_of_seat();// it will take capacity
																								// of auditorium from
																								// auditorium table
		TOTAL_SEATS_BOOKED = seatRepo.findByAuditoriumId(auditorium_id).size();// from seat repo custom method created
																				// to get this info
		TOTAL_SEATS_AVAILABLE = TOTAL_SEATS_CAPACITY - TOTAL_SEATS_BOOKED;
		System.out.println("==============================================");

		System.out.println("TOTAL_SEATS_CAPACITY==>" + TOTAL_SEATS_CAPACITY);
		System.out.println("TOTAL_SEATS_BOOKED==>" + TOTAL_SEATS_BOOKED);
		System.out.println("TOTAL_SEATS_AVAILABLE==>" + TOTAL_SEATS_AVAILABLE);

		System.out.println("==============================================");
	}
	
	@Override
	public Screening selectScreeningFromScreeningTable(Auditorium aud) {
		// TODO Auto-generated method stub
		
		log.info("Inside  IUserOperationImpl.selectScreeningFromScreeningTable()");
//		System.out.println("to get screen obj :: ===>"+aud);
		
		if(screeningRepo.findByAuditorumFk(aud).isEmpty())
            throw new NotFoundException("Screening Find by Auditorium Id Not Found ");
		Optional<Screening>op=screeningRepo.findByAuditorumFk(aud);
		System.out.println(op.get());
		return op.get();
	}

	@Override
	public List<Seats> selectSeatFromTable(Integer auditorium_id) {
		
		log.info("Inside IUserOperationImpl.selectSeatFromTable()");
		checkSeatAvailability(auditorium_id);
		
		if(seatRepo.findByAuditoriumId(auditorium_id)==null)
            throw new NotFoundException("SeatRepo Find by Auditorium Id Not Found ");
		return seatRepo.findByAuditoriumId(auditorium_id);

	}
	@Override
	public List<ReservedSeats> selectBookedSeatFromReservedSeatTable(Integer auditorium_id) {
		
		log.info("Inside IUserOperationImpl.selectBookedSeatFromReservedSeatTable()");
		if(reservedSeatRepo.findByAuditoriumId(auditorium_id)==null)
			throw new NotFoundException("findByAuditoriumId Find by Auditorium Id Not Found ");
		return reservedSeatRepo.findByAuditoriumId(auditorium_id);
		
	}

	// it will add all seats number provided by json to given auditorium and
	// update/check availibility
	@Override
	public Integer addSelectedSeatToTable(List<Integer> seatNumberList, Integer auditoriumId) {
		log.info("Inside IUserOperationImpl.addSelectedSeatToTable()");
		
		Integer counter = 0;
		Integer not_booked_counter = 0;
		
		if(auditoriumRepo.findById(auditoriumId).isEmpty())
            throw new NotFoundException("Auditorium Find by Auditorium Id Not Found ");
		Optional<Auditorium> aud = auditoriumRepo.findById(auditoriumId);
		for (int i = 0; i < seatNumberList.size(); i++) {
			if (TOTAL_SEATS_AVAILABLE > 0) {

				checkSeatAvailability(auditoriumId); // it will check and update seats availibility

				Seats seat = new Seats(); // to add seat obj in seat table ,it linked with auditorium obj
				seat.setAuditoriumFk(aud.get());

				seat.setSeatNumber(seatNumberList.get(i));
				seatRepo.save(seat);
				counter++;

			} else {
				not_booked_counter++;
			}

		}
		if (counter > 0) {
			return counter;
		} else {
			return not_booked_counter;
		}

	}

	@Override
	public Seats selectSeatsFromSeatTable(Integer seatNumber) {
		// TODO Auto-generated method stub
		
		log.info("Inside IUserOperationImpl.selectSeatsFromSeatTable()");
		
		if(seatRepo.findBySeatNumber(seatNumber).isEmpty())
            throw new NotFoundException("SeatRepo Find by Seat Number Not Found ");
		Optional<Seats>op=seatRepo.findBySeatNumber(seatNumber);
		return op.get();
	}
	@Override
	public Seats selectSeatsFromSeatTableBySeatNumberAndAuditoriumId(Integer seatNumber,Integer auditoriumId) {
		// TODO Auto-generated method stub
		
		log.info("Inside IUserOperationImpl.selectSeatsFromSeatTableBySeatNumberAndAuditoriumId()");
			if(seatRepo.findBySeatNumberAndAuditoriumFk(seatNumber,auditoriumId).isEmpty())
	            throw new NotFoundException("SeatRepo Find by Seat Number and Auditorium id Not Found ");
		Optional<Seats>op=seatRepo.findBySeatNumberAndAuditoriumFk(seatNumber,auditoriumId);
		return op.get();
	}

	
	
	public List<Integer> addConfirmedSeatsToReservedSeatTable(List<Integer> seatNumber,Integer userId,Integer auditoriumId){
		log.info("Inside IUserOperationImpl.addConfirmedSeatsToReservedSeatTable()");
		List<Integer> list = new ArrayList<>();    //used to return confirmed seat number
		User user=selectUserFromUserTable(userId);
		System.out.println("confirming process:: " +seatNumber);
		seatNumber.forEach(s->{                     //for each seat number it will get seat object from seat table 
			Seats seat=selectSeatsFromSeatTableBySeatNumberAndAuditoriumId(s,auditoriumId);
			System.out.println(s+" ::seats object==> "+seat);
			Auditorium aud=seat.getAuditoriumFk();  
			System.out.println(s+" ::auditorium object==> "+aud);
			Screening screening=selectScreeningFromScreeningTable(aud);
			System.out.println(s+" ::screeinign object==> "+screening);
			
			
			ReservedSeats resSeat=new ReservedSeats();
			resSeat.setScreeningFk(screening);
			resSeat.setSeatFk(seat);
			resSeat.setUserFk(user);
			
			
			if(reservedSeatRepo.save(resSeat).getSeatFk().getSeatNumber()==null)
	            throw new NotFoundException("ReservedSeat not saved ");
			Integer ans=reservedSeatRepo.save(resSeat).getSeatFk().getSeatNumber();
			list.add(ans);
			
			
				
				
		});
		return list;
	}

	@Override
	public User selectUserFromUserTable(Integer userId) {
		log.info("Inside IUserOperationImpl.selectUserFromUserTable()");
		if(userRepo.findById(userId).isEmpty())
            throw new NotFoundException("User  not Exist");
		Optional<User>op= userRepo.findById(userId);
		return op.get();
	}

	@Override
	public void addScreeningAndReservedSeatTOReservation(User user) {
//        User user=selectUserFromUserTable(userId);
		log.info("Inside IUserOperationImpl.addScreeningAndReservedSeatTOReservation()");
        Reservation reservation=new Reservation();
        reservation.setUserFk(user);
        
       
        
        
        List<ReservedSeats> reserveList=reservedSeatRepo.findByUserFk(user);
        
        for (ReservedSeats reservedSeats : reserveList) {
			reservedSeats.setReservationFk(reservation);
		}
       
        reservation.setReservedSeatIdFk(reserveList);
        
        
        	reservationRepo.save(reservation);
        
        
   
		System.out.println("IUserOperationImpl.addScreeningAndReservedSeatTOReservation()4");
	}

	@Override
	public List<Reservation> getUserBooking(Integer userId) {
		log.info("Inside IUserOperationImpl.getUserBooking()");
		
		    User user=selectUserFromUserTable(userId);
		    System.out.println(user);
		    
		    if(reservationRepo.findByUserFk(user).isEmpty())
	            throw new NotFoundException("Reservation  not Exists");
		List<Reservation>lis= reservationRepo.findByUserFk(user);
		
		
		return lis;
	}

	@Override
	public List<ReservedSeats> getReservedSeats(ReservedSeats reservedSeatsFk) {
		log.info("Inside IUserOperationImpl.getReservedSeats()");
		 if(reservedSeatRepo.findByReservationFk(reservedSeatsFk).isEmpty())
	            throw new NotFoundException("Reserved Seat not Exists");
		return reservedSeatRepo.findByReservationFk(reservedSeatsFk);
	}

	@Override
	public List<ReservedSeats> getReservationBooking(Integer rservationId) {
		// TODO Auto-generated method stub
		log.info("Inside IUserOperationImpl.getReservationBooking()");
		 if(reservedSeatRepo.findByReservationFkId(rservationId).isEmpty())
	            throw new NotFoundException("Reserved Seat not Exists");
		return reservedSeatRepo.findByReservationFkId(rservationId);
	}

	@Override
	public User updateUserInfo(User user) {
		// TODO Auto-generated method stub
		log.info("Inside IUserOperationImpl.updateUserInfo()");
		System.out.println("IUserOperationImpl.updateUserInfo()");
		return userRepo.save(user);
	}



	@Override
	public boolean deleteReservedSeatById(Integer reserveSeatId) {

		
		Optional<ReservedSeats>reservedSeatObj= reservedSeatRepo.findById(reserveSeatId);
//		Reservation reservationObj=reservedSeatObj.get().getReservationFk();
//		Seats seatObj=reservedSeatObj.get().getSeatFk();
		
		reservedSeatObj.get().setUserFk(null);
		reservedSeatObj.get().setScreeningFk(null); 
//		reservedSeatObj.get().getReservationFk().setUserFk(null);
		reservedSeatObj.get().getSeatFk().setAuditoriumFk(null);
		
		ReservedSeats resSeatTemp= reservedSeatRepo.save(reservedSeatObj.get());
		
		reservedSeatRepo.delete(resSeatTemp);
		
		boolean result=reservedSeatRepo.exists(Example.of(resSeatTemp));
		
		return result;
		


		
		
		
		

		
		
		
	}
	
	
	
	
	
	
	
	

}
