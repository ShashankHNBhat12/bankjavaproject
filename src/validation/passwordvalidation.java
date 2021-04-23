package validation;

import pojoclasses.Customer;

public class passwordvalidation {
	public static boolean validatepassword(Customer ob)
	{
		return ob.getPassword().matches("^\\w{4,10}$");
	}

}
