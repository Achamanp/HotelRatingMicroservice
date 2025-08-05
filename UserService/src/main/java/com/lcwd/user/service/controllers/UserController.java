package com.lcwd.user.service.controllers;

import com.lcwd.user.service.config.JwtHelper;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.payload.AuthResponse;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.ws.rs.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.lcwd.user.service.payload.Credentials;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.securityService.CustomUserDetail;
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private JwtHelper jwtHelper;

    //create
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    int retryCount = 1;


    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        logger.info("Get Single User Handler: UserController");
        logger.info("Retry count: {}", retryCount);
        retryCount++;

        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fall back  method for circuitbreaker


    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
//        logger.info("Fallback is executed because service is down : ", ex.getMessage());

        ex.printStackTrace();

        User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This user is created dummy because some service is down").userId("141234").build();
        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }


    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Credentials credentials) {
       try {
            Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
            );
            
            if (authentication.isAuthenticated()) {
                // Try to find user by email first, then by username
                Optional<User> userOptional = this.userRepository.findByEmail(credentials.getEmail());
                
                if (userOptional.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                
                User user = userOptional.get();
                GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
                CustomUserDetail userDetailsImpl = new CustomUserDetail(
                    user.getUserId(), 
                    user.getName(), 
                    user.getEmail(), // Use actual email instead of credentials.getUsername()
                    user.getPassword(), 
                    List.of(authority)
                );
                
                String token = jwtHelper.generateToken(userDetailsImpl);
                AuthResponse authResponse = new AuthResponse();
                authResponse.setExpirationTime(jwtHelper.getExpiration(token));
                authResponse.setJwt(token);
                authResponse.setMessage("Login Successfully");
                
                return new ResponseEntity<>(authResponse, HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Invalid username or password");
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
        
        // This should never be reached, but just in case
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    
    @GetMapping("/home")
    public String home() {
    	return "this is home page";
    }
    
}
