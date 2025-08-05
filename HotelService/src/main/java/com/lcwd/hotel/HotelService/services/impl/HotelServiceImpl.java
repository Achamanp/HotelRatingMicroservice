package com.lcwd.hotel.HotelService.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.hotel.HotelService.entities.Hotel;
import com.lcwd.hotel.HotelService.exceptions.ResourceNotFoundException;
import com.lcwd.hotel.HotelService.repositories.HotelRepository;
import com.lcwd.hotel.HotelService.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id).get();
    }

    @Override
    public Hotel updateHotel(String hid, Hotel hotel) {
        Hotel existing = hotelRepository.findById(hid)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hid));

        existing.setName(hotel.getName());
        existing.setLocation(hotel.getLocation());
        existing.setAbout(hotel.getAbout());

        return hotelRepository.save(existing);
    }

    @Override
    public void deleteHotel(String hid) {
        Hotel existing = hotelRepository.findById(hid)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hid));

        hotelRepository.delete(existing);
    }	
}