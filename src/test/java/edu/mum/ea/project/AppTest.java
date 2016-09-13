package edu.mum.ea.project;

import java.io.IOException;

import org.junit.Before;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
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
    	assertTrue( true );
    }
}
