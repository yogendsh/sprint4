package com.cg.onlinehotelmanagementsystem.dao;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.onlinehotelmanagementsystem.bean.BookingDetailsBean;
import com.cg.onlinehotelmanagementsystem.bean.CustomerDataBean;
import com.cg.onlinehotelmanagementsystem.bean.HotelDetailsBean;
import com.cg.onlinehotelmanagementsystem.bean.RoomDetailsBean;
import com.cg.onlinehotelmanagementsystem.exception.ExceptionHandler;
import com.cg.onlinehotelmanagementsystem.utility.IExceptionClass;
@Repository
@Transactional
public class BookingDAO implements IBookingDAO {
@PersistenceContext
EntityManager entityManager;
	
	public int checkBooking(int roomId, BookingDetailsBean bookingDetailsDTO) throws ExceptionHandler {

		
		TypedQuery<BookingDetailsBean> query = entityManager.createQuery(
				"SELECT bookObject from BookingDetailsBean bookObject,RoomDetailsBean roomObject where roomObject.roomId=:roomid and (bookObject.checkinbook BETWEEN TO_DATE(:checkinbook,'dd-MM-yyyy') AND TO_DATE(:checkoutbook,'dd-MM-yyyy')) OR (bookObject.checkoutbook BETWEEN TO_DATE(:checkinbook,'dd-MM-yyyy') AND TO_DATE(:checkoutbook,'dd-MM-yyyy'))",
				BookingDetailsBean.class);
		query.setParameter("roomid", roomId);
		query.setParameter("checkinbook", Date.valueOf(bookingDetailsDTO.getCheckinbook()));
		
		query.setParameter("checkoutbook", Date.valueOf(bookingDetailsDTO.getCheckoutbook()));
		
		query.setParameter("checkinbook", Date.valueOf(bookingDetailsDTO.getCheckinbook()));
		
		query.setParameter("checkoutbook", Date.valueOf(bookingDetailsDTO.getCheckoutbook()));
		if (query.getResultList().size() == 0) {
			return 0;
			
		} else
			return 1;
		

	}

	public int setBookingDetails(BookingDetailsBean bookingDetailsDTO, RoomDetailsBean roomDetailsDTO,HotelDetailsBean hotelDetailsDTO,CustomerDataBean userDataDTO)
	 {
	

		bookingDetailsDTO.setRoomDetailsDTO(roomDetailsDTO);
		bookingDetailsDTO.setHotelDetailsDTO(hotelDetailsDTO);
		bookingDetailsDTO.setUserDataDTO(userDataDTO);

		entityManager.persist(bookingDetailsDTO);
	
		return bookingDetailsDTO.getBookingid();
	}

	public void searchIdForCancel(int bookingId) throws ExceptionHandler {
		
		BookingDetailsBean bookingDetailsDTO = entityManager.find(BookingDetailsBean.class, bookingId);
		if (bookingDetailsDTO != null) {
		
			entityManager.remove(bookingDetailsDTO);
		
		} else
			throw new ExceptionHandler(IExceptionClass.CANCEL_BOOKING);
	

	}



}
