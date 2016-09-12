package edu.mum.ea.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task {
	@Id @GeneratedValue
	private int tid;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private Status status;
	private String resources;
	private List<OfferServices> offerServices;
	private List<User> teamMembers;
	@Column(columnDefinition="LONGBLOB")
	private byte[] pic;
	
	public Task(){
		
	}

	public Task(String name, String description, Status status, String resources, List<OfferServices> offerServices,
			List<User> teamMembers) {
		this.name = name;
		this.description = description;
		this.status = status;
		this.resources = resources;
		this.offerServices = offerServices;
		this.teamMembers = teamMembers;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public List<OfferServices> getOfferServices() {
		return offerServices;
	}

	public void setOfferServices(List<OfferServices> offerServices) {
		this.offerServices = offerServices;
	}

	public List<User> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<User> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	
	
}
