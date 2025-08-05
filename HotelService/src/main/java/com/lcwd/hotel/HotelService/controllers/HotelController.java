package com.lcwd.hotel.HotelService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.hotel.HotelService.entities.Hotel;

import com.lcwd.hotel.HotelService.services.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel created = hotelService.createHotel(hotel);
        
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
    	String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	System.out.println(name);
        return ResponseEntity.ok(hotelService.getAllHotel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String id) {
        return ResponseEntity.ok(hotelService.getHotel(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String id, @RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.updateHotel(id, hotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}