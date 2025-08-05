package com.lcwd.hotel.HotelService.services;

import java.util.List;

import com.lcwd.hotel.HotelService.entities.Hotel;

public interface HotelService {
	
	public Hotel createHotel(Hotel hotel);
	public List<Hotel> getAllHotel();
	public Hotel getHotel(String id);
	public Hotel updateHotel(String hid, Hotel hotel);
	public void deleteHotel(String hid);

}