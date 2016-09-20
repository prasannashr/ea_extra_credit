package edu.mum.ea.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id @GeneratedValue
	private int userId;
	
	private String username;
	
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	@OneToMany(mappedBy="createdBy",cascade={CascadeType.ALL})	
	private List<Project> projects;
	
		
	public User(){
		
	}

	public User(String username, UserType type) {
		super();
		this.username = username;
		this.type = type;
	}

	
	public List<Project> getProjects() {
		return projects;
	}

	public UserType getType() {
		return type;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
