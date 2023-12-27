package testngAssertions;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class HardAssertionVsSoftAssertion {
	
	//@Test
	void Hard_Assertion()
	{
		System.out.println("testing1...");
		System.out.println("testing2...");
		System.out.println("testing3...");
		System.out.println("testing4...");
		
		//Assert.assertEquals(1, 1); //pass
		Assert.assertEquals(1,2); // if Hard assert fails execution will stop and will not execute next steps
		
		System.out.println("Hard Assertion got completed");
	}
	
	@Test
	void Soft_Assertion()
	{
		System.out.println("testing1...");
		System.out.println("testing2...");
		System.out.println("testing3...");
		System.out.println("testing4...");
		
		SoftAssert sa=new SoftAssert();
		sa.assertEquals(1,1); //pass
		
		sa.assertEquals(1,2); //If Soft Assert fails still it will continue next statements
		
		System.out.println("Soft Assertion got completed");
	}
	
	

}
