package validation;

import pojoclasses.Customer;

public class emailvalidation {
	public static boolean validateemail(Customer ob)
	{
		return ob.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
	}
	

}
