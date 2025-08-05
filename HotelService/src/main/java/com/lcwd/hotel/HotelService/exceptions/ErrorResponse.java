package com.lcwd.hotel.HotelService.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String path;
    
    
}