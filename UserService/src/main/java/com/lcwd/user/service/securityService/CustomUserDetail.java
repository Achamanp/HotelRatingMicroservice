package com.lcwd.user.service.securityService;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lcwd.user.service.entities.User;

public class CustomUserDetail implements UserDetails{

	private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String email;
    
    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    public CustomUserDetail(String id, String username , String email, String password,  Collection<? extends GrantedAuthority> authorities) {
    	this.id = id;
    	this.username = username;
    	this.email = email;
    	this.password = password;
    	this.authorities = authorities;
    }
    public static CustomUserDetail build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
        return new CustomUserDetail(
            user.getUserId(),
            user.getEmail(), // ← Use email instead of username
            user.getEmail(), 
            user.getPassword(), 
            List.of(authority)
        );
    }

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}
	  public String getId() {
	        return id;
	    }

	    public String getEmail() {
	        return email;
	    }


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
