package com.cg.onlinehotelmanagementsystem.service.impl;




import java.time.temporal.ChronoUnit;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.onlinehotelmanagementsystem.bean.BookingDetailsBean;
import com.cg.onlinehotelmanagementsystem.bean.CustomerDataBean;
import com.cg.onlinehotelmanagementsystem.bean.HotelDetailsBean;
import com.cg.onlinehotelmanagementsystem.bean.RoomDetailsBean;
import com.cg.onlinehotelmanagementsystem.dao.BookingDAO;
import com.cg.onlinehotelmanagementsystem.dao.IBookingDAO;
import com.cg.onlinehotelmanagementsystem.exception.ExceptionHandler;
import com.cg.onlinehotelmanagementsystem.service.IBookRoom;
import com.cg.onlinehotelmanagementsystem.utility.IExceptionClass;

@Service
public class BookRoomImpl implements IBookRoom {
	@Autowired
	IBookingDAO bookingDAO;
	
	public int bookRoom(RoomDetailsBean roomDetailsDTO, BookingDetailsBean bookingDetailsDTO) throws ExceptionHandler {
		
		
//		Validation validation = new Validation();
//		validation.dateCheckIn(bookingDetailsDTO.getCheckinbook());
//		validation.dateCheckout(bookingDetailsDTO.getCheckinbook(), bookingDetailsDTO.getCheckoutbook());
//		validation.isValidRoom(roomDetailsDTO);
		CustomerDataBean userDataDTO=new CustomerDataBean();
		userDataDTO.setCustomerID(1);
		HotelDetailsBean hotelDetailsDTO=new HotelDetailsBean();
		hotelDetailsDTO.setHotelid(123456);
		
		bookingDetailsDTO.setAmount(roomDetailsDTO.getPrice()* ChronoUnit.DAYS.between(bookingDetailsDTO.getCheckinbook(), bookingDetailsDTO.getCheckoutbook()));
		int numberOfColumn = bookingDAO.checkBooking(roomDetailsDTO.getRoomId(),bookingDetailsDTO);
		
		if (numberOfColumn==0) {
			
			if (roomDetailsDTO.getRoomType().equalsIgnoreCase("Ac")) {
				if (roomDetailsDTO.getNumberOfPersons() > 4)
					throw new ExceptionHandler(IExceptionClass.PERSON_EXCEED);
				
			}
			else if (roomDetailsDTO.getRoomType().equalsIgnoreCase("NonAc")) {
				
				if (roomDetailsDTO.getNumberOfPersons() > 3)
					throw new ExceptionHandler(IExceptionClass.PERSON_EXCEED);
				
			}
			else if (roomDetailsDTO.getRoomType().equalsIgnoreCase("kingsize")) {
				if (roomDetailsDTO.getNumberOfPersons() > 5)
					throw new ExceptionHandler(IExceptionClass.PERSON_EXCEED);
				
			}
			else if (roomDetailsDTO.getRoomType().equalsIgnoreCase("queensize")) {
				if (roomDetailsDTO.getNumberOfPersons() > 4)
					throw new ExceptionHandler(IExceptionClass.PERSON_EXCEED);
				
			}
			return bookingDAO.setBookingDetails(bookingDetailsDTO, roomDetailsDTO,hotelDetailsDTO,userDataDTO);
			}
		else {
			throw new ExceptionHandler(IExceptionClass.ROOM_BOOKED);
		}
			
		
			

		

	}

	public void setBookingDAO(BookingDAO bookingDAO) {
		this.bookingDAO = bookingDAO;
	}

	public void cancelBooking(int bookingId) throws ExceptionHandler {

		bookingDAO.searchIdForCancel(bookingId);
		

	}

}
