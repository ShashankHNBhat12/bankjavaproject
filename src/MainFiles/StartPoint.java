package MainFiles;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import daofiles.*;
import pojoclasses.*;
import validation.*;

public class StartPoint {
	
	public static void signup() throws Exception
	{
		try {
		Scanner sc=new Scanner(System.in);
		Customer_dao ob=new Customer_dao();
		Customer ob1=new Customer();
		Bank_dao ob2=new Bank_dao();
		Bank ob3=new Bank();
		
		
		System.out.println("Enter your Name");
		String name=sc.nextLine();
		ob1.setName(name);
		boolean checkname=validation.namevalidation.validatename(ob1);
		if(checkname)
		{
			System.out.println("valid name....");
		
		
		System.out.println("Enter your email");
		String email=sc.nextLine();
		ob1.setEmail(email);
		boolean checkemail=validation.emailvalidation.validateemail(ob1);
		if(checkemail)
		{
			System.out.println("valid email....");
		
		System.out.println("Enter your address(city,state)");
		String addr=sc.nextLine();
		ob1.setAddress(addr);
		
		System.out.println("Enter your phno");
		long ph=sc.nextLong();
		ob1.setPhno(ph);
		boolean checkphno=validation.phonenumbervalidation.validatephno(ob1);
		if(checkphno)
		{
			System.out.println("valid phone number....");
		
		sc.nextLine();
		System.out.println("Enter your password (min length must be 4 and maximum must be 10)");
		String pw=sc.nextLine();
		System.out.println("Re-enter your password ");
		String pwcopy=sc.nextLine();
		ob1.setPassword(pw);
		boolean checkpassword=validation.passwordvalidation.validatepassword(ob1);
		if(!pw.equals(pwcopy))
		{
			checkpassword=false;
		}
		if(checkpassword)
		{
			System.out.println("valid password....");
		
		boolean check=true;
		do {
		System.out.println("select your account type \n 1.Saving \n 2.Salary");
		int accChoice=sc.nextInt();
		if(accChoice==1 )
		{
			ob3.setAcctype("saving");
			ob3.setAmount(0);
			check=false;
		}
		else if(accChoice==2)
		{
			ob3.setAcctype("salary");
			ob3.setAmount(0);
			check=false;
		}
		else
		{
			check=true;
			
		}
		}while(check);
		
	System.out.println("creating account......\n");
		
		ob.Customer_insert(ob1);
		
	
		
		ob2.createaccount(ob1,ob3);
		System.out.println(" \n ********************************************************  \n Your Customer id : "+ob1.getCustid()+"\n \n");
		System.out.println(" Your Account number : "+ob3.getAcccno()+"\n ******************************************************** \n");
		System.out.println("\n\n Please login using customer id and password ");
		}
		else
		{
			System.out.println("invalid password or re entered password do not match ...");
		}
		}
		else
		{
			System.out.println("invalid phone number....");
		}
		}
		else
		{
			System.out.println("invalid email....");
		}
		}
		else
		{
			System.out.println("invalid name....");
		}

	
	
	
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
	
	}
	
	public static void login()
	{
		try {
		Scanner s=new  Scanner(System.in);
		System.out.println("Enter your customer id ");
		Customer ob=new Customer();
		Customer_dao obj=new Customer_dao();
		int id=s.nextInt();
		s.nextLine();
		ob.setCustid(id);
		System.out.println("Enter password ");
		String pw=s.nextLine();
		ob.setPassword(pw);
		obj.Customer_login(ob);
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
	}
	public static void admin_login()
	{
		try {
		Scanner s=new  Scanner(System.in);
		System.out.println("Enter your admin id ");
		Admin ob=new Admin();
		Admin_dao obj=new Admin_dao();
		int id=s.nextInt();
		s.nextLine();
		ob.setId(id);
		System.out.println("Enter password ");
		String pw=s.nextLine();
		ob.setPassword(pw);
		obj.Admin_login(ob);
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
		
	}
	public static void forgot_password()
	{
		try {
		Scanner s=new  Scanner(System.in);
		ArrayList<Object> obj=new ArrayList();
		System.out.println("Enter the custid");
		int id=s.nextInt();
		System.out.println("Enter your mobile number ");
		long ph=s.nextLong();
		obj.add(0, id);
		obj.add(1, ph);
		(new Customer_dao()).forgotpassword(obj);
		}
		catch(InputMismatchException e)
		{
			System.out.println("INVALID INPUT");
		}
		
	}
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		int choice;
		boolean check=true;
		Entry:
			
		try
		{
			
			do
			{
				System.out.println(" \n                  WELCOME TO YOURS BANK \n\n -----------------------------------------------------");
		System.out.println("Please select any");
		System.out.println(" 1.Login \n 2.Signup \n 3.Admin Login \n 4.Forgot password \n 5.Exit");
		choice=sc.nextInt();
		
		
		switch(choice)
		{
		case 1: login();
				
				continue;
		case 2: signup();
			
				continue;
		case 3:
			admin_login();
			break;
		case 4:
			forgot_password();
			break;
			
		case 5:
			check=false;
			break;
			
		default:
			System.out.println("Please select approprite option");
			continue;
		}
			}while(check);
			
			}
		
			catch(InputMismatchException e)
			{
				System.out.println("INVALID INPUT....Your session is expired.....please try again");
			}
		System.out.println("Thank you for Banking with us :)");
		

	}
}
