package edu.mum.ea.project;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import edu.mum.ea.project.model.Beneficiaries;
import edu.mum.ea.project.model.Project;
import edu.mum.ea.project.model.Resource;
import edu.mum.ea.project.model.Status;
import edu.mum.ea.project.model.Task;
import edu.mum.ea.project.model.User;
import edu.mum.ea.project.model.UserType;

public class App {
	private static Logger logger = Logger.getLogger(App.class);;

	private static final EntityManagerFactory emf;

	static {
		try {
			emf = Persistence.createEntityManagerFactory("cs544");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		EntityManager em = null;
		EntityTransaction tx = null;

		// fill the database
		

		// Flights leaving USA capacity > 500
		try {
			//fillDataBase();
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			//	3.	See information about projects and their beneficiaries
			
			@SuppressWarnings("unchecked")
			List<Project> projects = em.createQuery("SELECT p "
												+ "FROM Project p ").getResultList();
			for (Project project : projects) {
				System.out.println(project.getName()+", "+project.getLocation());
				for (Beneficiaries b : project.getBeneficiaries()) {
					System.out.println(b.getFirstName()+" "+b.getLastName());
				}
				
			}
			
			//	4.	List tasks for a project
			for (Project project : projects) {
				System.out.println(project.getName()+", "+project.getLocation());
				for (Task task : project.getTasks()) {
					System.out.println(task.getName()+" "+task.getDescription());
					for (Resource res : task.getResources()) {
						System.out.println(res.getName());
					}
				}
				
			}
			//	5.	List projects by status
			@SuppressWarnings("unchecked")
			List<Project> projectList = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "WHERE p.status='NEW'").getResultList();
			for (Project project : projectList) {
				System.out.println(project.getName()+", "+project.getDescription()+", "+project.getLocation());
								
			}
			//	6.	Look for projects that requires a particular type of resource/skill
			@SuppressWarnings("unchecked")
			List<Project> particularProjectList = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "JOIN p.tasks t "
												+ "JOIN t.resources r "
												+ "WHERE r.name='resource1'").getResultList();
			for (Project project : particularProjectList) {
				System.out.println("6. "+project.getName()+", "+project.getDescription()+", "+project.getLocation());
								
			}
			
			//	7.	Search projects by keywords and location
			@SuppressWarnings("unchecked")
			List<Project> projectByLocation = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "WHERE p.location LIKE '%100%'").getResultList();
			for (Project project : projectByLocation) {
				System.out.println("7. "+project.getName()+", "+project.getDescription()+", "+project.getLocation());
								
			}
			//	8.	List projects and tasks where a volunteer have offered services, ordered by time of the task.
			@SuppressWarnings("unchecked")
			List<Project> projectWithVolunteerList = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "JOIN p.tasks t "
												+ "WHERE t.volunteers IS NOT NULL ORDER BY p.startDate").getResultList();
			
			for (Project project : projectWithVolunteerList) {
				System.out.println("8. "+project.getName()+", "+project.getDescription()+", "+project.getLocation()+" : "+project.getStartDate());
								
			}
			tx.commit();
		} catch (PersistenceException e) {
			if (tx != null) {
				logger.error("Rolling back:", e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	public static void fillDataBase() throws IOException {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {

			
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			/*
			 * 
			 * 	1.	Administrators should be able to create a project with a description, expected start and end dates, 
			 * 		tasks required to be done (with a timeframe to be completed), update the status of the tasks and projects, 
			 * 		indicate what type of resources are required for each task. It may also have a list of beneficiaries. 
			 * 		Should be able to load pictures to enhance the descriptions, tasks and beneficiaries information.
			 */
				User admin = new User("Prasanna",UserType.ADMIN);
				
				List<Beneficiaries> beneficiaries = new ArrayList<Beneficiaries>();
				beneficiaries.add(new Beneficiaries("Josh","smith"));
				beneficiaries.add(new Beneficiaries("Frank","Hanna"));
				
				Project project1 = new Project("Project1", "project1 description", new Date(), 
						new Date(2016, 11, 16), Status.NEW, beneficiaries, admin, "1000 4th st. IA,USA");
				Path p = FileSystems.getDefault().getPath("", "project1.png");
				byte [] fileData = Files.readAllBytes(p);
				project1.setPic(fileData);
				
				Task task1 = new Task("Task1","Task 1 desc",Status.NEW);
				
				List<Resource> resources = new ArrayList<Resource>();
				resources.add(new Resource("resource1"));
				resources.add(new Resource("resource2"));
				resources.add(new Resource("resource3"));
				
				task1.setResources(resources);
				task1.setProject(project1);
				
			
			//	2.	Volunteers should be able to offer their services for tasks on projects.
				User volunteer1 = new User("Shyam",UserType.VOLUNTEER);
				User volunteer2 = new User("Hari",UserType.VOLUNTEER);
				User volunteer3 = new User("Arun",UserType.VOLUNTEER);
				task1.addVolunteer(volunteer1);
				task1.addVolunteer(volunteer2);
				task1.addVolunteer(volunteer3);
			
			
				em.persist(admin);
				em.persist(project1);
				for (Beneficiaries beneficiar : beneficiaries) {
					em.persist(beneficiar);
				}
				em.persist(task1);
				for (Resource resource : resources) {
					em.persist(resource);
				}
				em.persist(volunteer1);
				em.persist(volunteer2);
				em.persist(volunteer3);

			tx.commit();
			
		} catch (PersistenceException e) {
			if (tx != null) {
				logger.error("Rolling back", e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
