package edu.mum.ea.project;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.Before;

import edu.mum.ea.project.model.Beneficiaries;
import edu.mum.ea.project.model.Project;
import edu.mum.ea.project.model.Resource;
import edu.mum.ea.project.model.Task;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
	private static final EntityManagerFactory emf;

	static {
		try {
			emf = Persistence.createEntityManagerFactory("cs544");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	
    public AppTest( String testName )
    {
        super( testName );
    }
    @Before
    public void prepare(){
    	System.out.println("before...");
    }

    /**
     * @return the suite of tests being tested
     * @throws IOException 
     */
    @SuppressWarnings("static-access")
	public static Test suite() throws IOException
    {
    	System.out.println("tested....");
    	App main = new App();
    	main.fillDataBase();
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        System.out.println("test result");
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
			
			assertTrue( true );
			
			//	5.	List projects by status
			@SuppressWarnings("unchecked")
			List<Project> projectList = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "WHERE p.status='NEW'").getResultList();
			
			//	6.	Look for projects that requires a particular type of resource/skill
			@SuppressWarnings("unchecked")
			List<Project> particularProjectList = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "JOIN p.tasks t "
												+ "JOIN t.resources r "
												+ "WHERE r.name='resource1'").getResultList();
			
			
			//	7.	Search projects by keywords and location
			@SuppressWarnings("unchecked")
			List<Project> projectByLocation = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "WHERE p.location LIKE '%100%'").getResultList();
			
			//	8.	List projects and tasks where a volunteer have offered services, ordered by time of the task.
			@SuppressWarnings("unchecked")
			List<Project> projectWithVolunteerList = em.createQuery("SELECT p "
												+ "FROM Project p "
												+ "JOIN p.tasks t "
												+ "WHERE t.volunteers IS NOT NULL ORDER BY p.startDate").getResultList();
			
			
			tx.commit();
		} catch (PersistenceException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}

    	
    }
}
