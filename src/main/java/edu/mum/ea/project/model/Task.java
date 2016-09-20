package edu.mum.ea.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Task {
	
	@Id @GeneratedValue
	private int tid;
	
	private String name;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Task_Resources",
			joinColumns=@JoinColumn(name="task_id"),
			inverseJoinColumns=@JoinColumn(name="resource_id")
	)
	private List<Resource> resources;
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Task_Volunteers",
		joinColumns=@JoinColumn(name="task_id"),
		inverseJoinColumns=@JoinColumn(name="user_id")
	)
	private List<User> volunteers;
	
	@ManyToOne
	@JoinColumn(name="projectId")
	private Project project;
	
	public Task(){
		
	}

	public Task(String name, String description, Status status) {
		//volunteers = new ArrayList<User>();
		this.name = name;
		this.description = description;
		this.status = status;
	}
	
	public void addVolunteer(User volunteer){
		//volunteers.add(volunteer);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public Project getProject() {
		return project;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public Status getStatus() {
		return status;
	}

	public List<User> getTeamMembers() {
		return volunteers;
	}
	public List<User> getVolunteers() {
		return volunteers;
	}


	public int getTid() {
		return tid;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	public void setTid(int tid) {
		this.tid = tid;
	}

}
