package validation;

import pojoclasses.Customer;

public class phonenumbervalidation {
	public static boolean validatephno(Customer ob)
	{
		String temp=ob.getPhno()+"";
		return temp.matches("^[6789]\\d{9}$");
	}

}
