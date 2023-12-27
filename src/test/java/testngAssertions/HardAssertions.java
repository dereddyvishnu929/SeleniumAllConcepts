package testngAssertions;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HardAssertions {
	
	@Test
	void test()
	{
	
		/*
	int x=10;
	int y=20;
	
	    //Assert.assertTrue(true); //pass
	    //Assert.assertFalse(false); //pass
	      Assert.assertTrue(false); //fail
	
		int a=10;
		int b=20;
		
		//Assert.assertEquals(a>b,true,"a is not greater than b");
		 
		 
		 */
		
		
		String s1="xyz";
		String s2="xyz1";
		
		//Assert.assertEquals(s1,s2,"Strings are not equal "); //pass
		//Assert.assertNotEquals(s1, s2);  //fails
		
		
		if(true)
		{
			Assert.assertTrue(true); //pass
		}
		else
		{
			Assert.assertFalse(false);
			//Assert.fail(); //fails
		}
		
		
	
	
	
	}
	

}
