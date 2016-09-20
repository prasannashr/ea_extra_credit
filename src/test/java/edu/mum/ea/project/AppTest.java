package edu.mum.ea.project;

import java.io.IOException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	//static App mainApp = new App();

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */

	public AppTest(String testName) {
		super(testName);
	}

	
	/**
	 * @return the suite of tests being tested
	 * @throws IOException
	 */
	public static Test suite() throws IOException {
		System.out.println("tested....");
		
		// fill data
		//mainApp.fillDataBase();
		//execute queries and get data
		//mainApp.getData();
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * @throws IOException 
	 */
	public void testApp() throws IOException {
		System.out.println("test result");
			
		/*
		// 3. See information about projects and their beneficiaries
		assertEquals(mainApp.projects.size(), 2);

		// 5. List projects by status
		//createQuery("SELECT p " + "FROM Project p " + "WHERE p.status='NEW'")				
		assertEquals(mainApp.projectList.size(), 1);
		
		// 6. Look for projects that requires a particular type of
		// createQuery("SELECT p " + "FROM Project p " + "JOIN p.tasks t JOIN t.resources r " + "WHERE r.name='resource1'")
		assertEquals(mainApp.particularProjectList.size(), 1);
		
		// 7. Search projects by keywords and location
		// createQuery("SELECT p " + "FROM Project p " + "WHERE p.location LIKE '%120%'")
		assertEquals(mainApp.projectByLocation.size(), 1);
		
		// 8. List projects and tasks where a volunteer have offered services,
		// ordered by time of the task.
		// createQuery("SELECT p " + "FROM Project p " + "JOIN p.tasks t WHERE t.volunteers IS NOT NULL ORDER BY p.startDate")
		assertEquals(mainApp.projectWithVolunteerList.size(), 2);*/
	}
}
