package com.gaurav.movietkt.service;

import java.util.List;
import java.util.Map;

import com.gaurav.movietkt.model.Auditorium;
import com.gaurav.movietkt.model.Reservation;
import com.gaurav.movietkt.model.ReservedSeats;
import com.gaurav.movietkt.model.Screening;
import com.gaurav.movietkt.model.Seats;
import com.gaurav.movietkt.model.User;

public interface IUserOperation {
		public List<Seats> selectSeatFromTable(Integer auditorium_id);
		public Integer addSelectedSeatToTable(List<Integer> seatNumberList,Integer auditoriumId);;
		public Seats selectSeatsFromSeatTable(Integer seatId);
		public Screening selectScreeningFromScreeningTable(Auditorium aud);
		public User selectUserFromUserTable(Integer userId);
		public List<Integer> addConfirmedSeatsToReservedSeatTable(List<Integer> seatNumber,Integer userId, Integer auditoriumId);
		
		public void addScreeningAndReservedSeatTOReservation(User user);
		public List<Reservation> getUserBooking(Integer userId);
		
		public List<ReservedSeats> getReservedSeats(ReservedSeats reservedSeatsFk);
		public List<ReservedSeats> getReservationBooking(Integer rservationId);
		public Seats selectSeatsFromSeatTableBySeatNumberAndAuditoriumId(Integer seatNumber, Integer auditoriumId);
		public List<ReservedSeats> selectBookedSeatFromReservedSeatTable(Integer auditorium_id);
		
		public  Map<String,Integer> getCurrentAvalibility(Integer audId);
		public User updateUserInfo(User user);
		
		public boolean deleteReservedSeatById(Integer reserveSeatId);
		
		
		
		
}
