package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String email;
	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", email=" + email + "]";
	}
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Member( String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
