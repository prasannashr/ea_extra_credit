package edu.mum.ea.project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Project {
	@Id @GeneratedValue
	private int pid;
	private String name;
	private String description;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Enumerated(EnumType.STRING)
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(name="project_Beneficiaries",
		joinColumns=@JoinColumn(name="p_id"),
		inverseJoinColumns=@JoinColumn(name="beneficiaries_id")
	)
	private List<Beneficiaries> beneficiaries;
	
	@Column(columnDefinition="LONGBLOB")
	private byte[] pic;
	
	@ManyToOne(cascade={CascadeType.ALL})
	private User createdBy;
	
	private String location;
	
	@OneToMany(mappedBy="project",cascade={CascadeType.ALL})
	private List<Task> tasks;

	public Project(){
		
	}
	public Project(String name, String description, Date startDate, Date endDate, Status status,
			List<Beneficiaries> beneficiaries, User createdBy, String location) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.beneficiaries = beneficiaries;
		this.createdBy = createdBy;
		this.location = location;
	}
	
	public void addTask(Task task){
		task.setProject(this);
		tasks.add(task);
	}
	
	public List<Beneficiaries> getBeneficiaries() {
		return beneficiaries;
	}
	
	
	public User getCreatedBy() {
		return createdBy;
	}


	public String getDescription() {
		return description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public byte[] getPic() {
		return pic;
	}

	public int getPid() {
		return pid;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Status getStatus() {
		return status;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setBeneficiaries(List<Beneficiaries> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
