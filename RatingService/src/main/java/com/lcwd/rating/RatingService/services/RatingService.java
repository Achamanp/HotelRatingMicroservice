package com.lcwd.rating.RatingService.services;

import java.util.List;

import com.lcwd.rating.RatingService.entities.Rating;


public interface RatingService {
	public Rating createRating(Rating rating);
	public List<Rating> getAllRating();
	public List<Rating> getRatingByUserId(String userId);
	public List<Rating> getRatingByHotelId(String hotelId);
	
	public Rating getRatingById(String id);
	
	public void deleteRating(String id);
	
	public Rating updateRating(Rating rating);
}