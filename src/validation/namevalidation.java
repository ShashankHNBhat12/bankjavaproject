package validation;

import pojoclasses.Customer;

public class namevalidation {
	public static boolean validatename(Customer ob)
	{
		//System.out.println(ob.getName()+" is "+ ob.getName().matches( "[A-Z][a-z]" ));
		
		return ob.getName().matches( "^[a-zA-Z_ ]*$" );
	}

}
