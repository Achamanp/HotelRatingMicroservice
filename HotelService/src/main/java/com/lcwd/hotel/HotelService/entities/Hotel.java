package com.lcwd.hotel.HotelService.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="hotels")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)  
	private String id;
	private String name;
	private String location;
	private String about;
}
