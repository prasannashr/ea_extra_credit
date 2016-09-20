package edu.mum.ea.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.mum.ea.project.model.Beneficiaries;
import edu.mum.ea.project.model.Project;
import edu.mum.ea.project.model.Resource;
import edu.mum.ea.project.model.Status;
import edu.mum.ea.project.model.Task;
import edu.mum.ea.project.model.User;
import edu.mum.ea.project.model.UserType;

public class AppHibernate {
	//private static Logger logger = Logger.getLogger(App.class);;

	private static final SessionFactory sessionFactory;
	static {
		try {
			Configuration configuration = new Configuration();
			
			// This step will read hibernate.cfg.xml
			sessionFactory = configuration.configure().buildSessionFactory(); 

		} catch (Throwable ex) {
			System.err.println(ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		Session session = null;
		Transaction tx = null;

		// fill the database
		fillDataBase();

		// Flights leaving USA capacity > 500
		try {
			
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
			//	3.	See information about projects and their beneficiaries
			
			@SuppressWarnings("unchecked")
			List<Project> projects = session.createQuery("FROM Project").list();
			for (Project project : projects) {
				System.out.println("3. "+project.getName()+", "+project.getLocation());
				for (Beneficiaries b : project.getBeneficiaries()) {
					System.out.println(b.getFirstName()+" "+b.getLastName());
				}
				
			}
			
			//	4.	List tasks for a project
			for (Project project : projects) {
				System.out.println("4. "+project.getName()+", "+project.getLocation());
				for (Task task : project.getTasks()) {
					System.out.println(task.getName()+" "+task.getDescription());
					for (Resource res : task.getResources()) {
						System.out.println(res.getName());
					}
				}
				
			}
			//	5.	List projects by status
			@SuppressWarnings("unchecked")
			List<Project> projectList = session.createQuery("SELECT p "
												+ "FROM Project p "
												+ "WHERE p.status='NEW'").list();
			for (Project project : projectList) {
				System.out.println("5. "+project.getName()+", "+project.getDescription()+", "+project.getLocation());
								
			}
			//	6.	Look for projects that requires a particular type of resource/skill
			@SuppressWarnings("unchecked")
			List<Project> particularProjectList = session.createQuery("SELECT p "
												+ "FROM Project p "
												+ "JOIN p.tasks t "
												+ "JOIN t.resources r "
												+ "WHERE r.name='resource1'").list();
			for (Project project : particularProjectList) {
				System.out.println("6. "+project.getName()+", "+project.getDescription()+", "+project.getLocation());
								
			}
			
			//	7.	Search projects by keywords and location
			@SuppressWarnings("unchecked")
			List<Project> projectByLocation = session.createQuery("SELECT p "
												+ "FROM Project p "
												+ "WHERE p.location LIKE '%100%'").list();
			for (Project project : projectByLocation) {
				System.out.println("7. "+project.getName()+", "+project.getDescription()+", "+project.getLocation());
								
			}
			//	8.	List projects and tasks where a volunteer have offered services, ordered by time of the task.
			@SuppressWarnings("unchecked")
			List<Project> projectWithVolunteerList = session.createQuery("SELECT p "
												+ "FROM Project p "
												+ "JOIN p.tasks t "
												+ "WHERE SIZE(t.volunteers)>0 ORDER BY p.startDate").list();
			
			for (Project project : projectWithVolunteerList) {
				System.out.println("8. "+project.getName()+", "+project.getDescription()+", "+project.getLocation()+" : "+project.getStartDate());
								
			}
			tx.commit();
		} catch (HibernateException e) {
			System.err.println(e);
			if (tx != null) tx.rollback();
		} finally {
			if (session != null) session.close();
		}
		
		if (!sessionFactory.isClosed()) {
			sessionFactory.close();
		}

	}

	public static void fillDataBase() {
		Session session = null;
		Transaction tx = null;
		
		try {			
			
			session = sessionFactory.openSession();			
			tx = session.beginTransaction();
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
				//Project project2 = new Project("Project2", "project2 description", new Date(), 
				//		new Date(2016, 10, 26), Status.NEW, beneficiaries, admin, "1200 5th st. CA,USA");
				/*Path p = FileSystems.getDefault().getPath("", "project1.png");
				byte [] fileData = Files.readAllBytes(p);
				project1.setPic(fileData);
				project2.setPic(fileData);*/
				
				Task task1 = new Task("Task1","Task 1 desc",Status.NEW);
				Task task2 = new Task("Task2","Task 2 desc",Status.IN_PROGRESS);
				
				List<Resource> resources = new ArrayList<Resource>();
				resources.add(new Resource("resource1"));
				resources.add(new Resource("resource2"));
				resources.add(new Resource("resource3"));
				
				task1.setResources(resources);
				task1.setProject(project1);
				
				//task2.setResources(resources);
				task2.setProject(project1);
				
			
			//	2.	Volunteers should be able to offer their services for tasks on projects.
				/*User volunteer1 = new User("Shyam",UserType.VOLUNTEER);
				User volunteer2 = new User("Hari",UserType.VOLUNTEER);
				User volunteer3 = new User("Arun",UserType.VOLUNTEER);
				task1.addVolunteer(volunteer1);
				task1.addVolunteer(volunteer2);
				task1.addVolunteer(volunteer3);
			
				task2.addVolunteer(volunteer2);
				task2.addVolunteer(volunteer3);*/
			
				//session.persist(admin);
				admin.setProjects(Arrays.asList(project1));
				project1.setTasks(Arrays.asList(task1,task2));
				session.persist(project1);
				//session.persist(task1);
				//session.persist(task2);
				//session.persist(project1);
				/*for (Beneficiaries beneficiar : beneficiaries) {
					session.persist(beneficiar);
				}
				session.persist(task1);
				//session.persist(task2);
				for (Resource resource : resources) {
					session.persist(resource);
				}
				for (Resource resource : resources2) {
					session.persist(resource);
				}
				session.persist(volunteer1);
				session.persist(volunteer2);
				session.persist(volunteer3);*/

			tx.commit();
			
		} catch (HibernateException e) {
			System.err.println(e);
			if (tx != null)  tx.rollback();
		} finally {
			if (session != null) session.close();
		}
		
		
	}

}