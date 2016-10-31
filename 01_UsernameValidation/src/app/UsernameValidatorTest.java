package app;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.RexLoginValidator;

public class UsernameValidatorTest {
	private RexLoginValidator usernameValidator;

	@BeforeClass
	public void initData() {
		usernameValidator = new RexLoginValidator();
	}

	@DataProvider
	public Object[][] ValidUsernameProvider() {
		return new Object[][] { { new String[] { "mkyong34", "mkyong_2002", "mkyong-2002", "MK3-4_yong","0123etst" } } };
	}

	@DataProvider
	public Object[][] InvalidUsernameProvider() {
		return new Object[][] { { new String[] { "mk", "mk@yong", "mkyong123456789_-" } } };
	}

	@Test(dataProvider = "ValidUsernameProvider")
	public void ValidUsernameTest(String[] Username) {

		for (String temp : Username) {
			boolean valid = usernameValidator.validate(temp);
			System.out.println("Username is valid : " + temp + " , " + valid);
			Assert.assertEquals(true, valid);
		}

	}

	@Test(dataProvider = "InvalidUsernameProvider", dependsOnMethods = "ValidUsernameTest")
	public void InValidUsernameTest(String[] Username) {

		for (String temp : Username) {
			boolean valid = usernameValidator.validate(temp);
			System.out.println("username is valid : " + temp + " , " + valid);
			Assert.assertEquals(false, valid);
		}

	}
}
