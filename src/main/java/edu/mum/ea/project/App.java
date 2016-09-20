package edu.mum.ea.project;

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
	public static List<Project> projects;
	public static List<Project> projectList;
	public static List<Project> particularProjectList;
	public static List<Project> projectByLocation;
	public static List<Project> projectWithVolunteerList;

	public static void main(String[] args) {

		// fill the database

		fillDataBase();
		//getData();

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public static void fillDataBase() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();

			User admin = new User("Prasanna", UserType.ADMIN);

			List<Beneficiaries> beneficiaries = new ArrayList<Beneficiaries>();
			beneficiaries.add(new Beneficiaries("Josh", "smith"));
			beneficiaries.add(new Beneficiaries("Frank", "Hanna"));

			Project project1 = new Project("Project1", "project1 description", new Date(), new Date(2016, 11, 16),
					Status.NEW, beneficiaries, admin, "1000 4th st. IA,USA");
			Project project2 = new Project("Project2", "project2 description", new Date(), new Date(2016, 10, 26),
					Status.OPEN, beneficiaries, admin, "1200 5th st. CA,USA");
			/*Path p = FileSystems.getDefault().getPath("", "project1.png");
			byte[] fileData = Files.readAllBytes(p);
			project1.setPic(fileData);
			project2.setPic(fileData);*/

			Task task1 = new Task("Task1", "Task 1 desc", Status.NEW);
			Task task2 = new Task("Task2", "Task 2 desc", Status.IN_PROGRESS);

			List<Resource> resources = new ArrayList<Resource>();
			resources.add(new Resource("resource1"));
			resources.add(new Resource("resource2"));
			resources.add(new Resource("resource3"));

			List<Resource> resources2 = new ArrayList<Resource>();
			resources2.add(new Resource("resource4"));
			resources2.add(new Resource("resource5"));
			resources2.add(new Resource("resource6"));

			task1.setResources(resources);
			task1.setProject(project1);

			task2.setResources(resources2);
			task2.setProject(project2);

			// 2. Volunteers should be able to offer their services for tasks on
			// projects.
			User volunteer1 = new User("Shyam", UserType.VOLUNTEER);
			User volunteer2 = new User("Hari", UserType.VOLUNTEER);
			User volunteer3 = new User("Arun", UserType.VOLUNTEER);
			task1.addVolunteer(volunteer1);
			task1.addVolunteer(volunteer2);
			task1.addVolunteer(volunteer3);

			task2.addVolunteer(volunteer2);
			task2.addVolunteer(volunteer3);

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
			/*
			em.persist(project2);
			em.persist(task2);
			for (Resource resource : resources2) {
				em.persist(resource);
			}*/
			
			// 3. See information about projects and their beneficiaries

			projects = em.createQuery("SELECT p " + "FROM Project p ").getResultList();
			for (Project project : projects) {
				System.out.println("3. "+project.getName() + ", " + project.getLocation());
				for (Beneficiaries b : project.getBeneficiaries()) {
					System.out.println(b.getFirstName() + " " + b.getLastName());
				}

			}

			// 4. List tasks for a project
			for (Project project : projects) {
				System.out.println("4. "+project.getName() + ", " + project.getLocation());
				for (Task task : project.getTasks()) {
					System.out.println(task.getName() + " " + task.getDescription());
					for (Resource res : task.getResources()) {
						System.out.println(res.getName());
					}
				}

			}
			// 5. List projects by status
			projectList = em.createQuery("SELECT p " + "FROM Project p " + "WHERE p.status='NEW'").getResultList();
			for (Project project : projectList) {
				System.out.println("5. "+project.getName() + ", " + project.getDescription() + ", " + project.getLocation());

			}
			// 6. Look for projects that requires a particular type of
			// resource/skill
			particularProjectList = em.createQuery("SELECT p " + "FROM Project p " + "JOIN p.tasks t "
					+ "JOIN t.resources r " + "WHERE r.name='resource1'").getResultList();
			for (Project project : particularProjectList) {
				System.out.println(
						"6. " + project.getName() + ", " + project.getDescription() + ", " + project.getLocation());

			}

			// 7. Search projects by keywords and location
			projectByLocation = em.createQuery("SELECT p FROM Project p WHERE p.location LIKE '%100%'")
					.getResultList();
			for (Project project : projectByLocation) {
				System.out.println(
						"7. " + project.getName() + ", " + project.getDescription() + ", " + project.getLocation());

			}
			// 8. List projects and tasks where a volunteer have offered
			// services, ordered by time of the task.
			projectWithVolunteerList = em.createQuery("SELECT p FROM Project p JOIN p.tasks t "
					+ "WHERE SIZE(t.volunteers)>0  ORDER BY p.startDate").getResultList();

			for (Project project : projectWithVolunteerList) {
				System.out.println("8. " + project.getName() + ", " + project.getDescription() + ", "
						+ project.getLocation() + " : " + project.getStartDate());

			}
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

	
	public static void getData() {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {

			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

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
