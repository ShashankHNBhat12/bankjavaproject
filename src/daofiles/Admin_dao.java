package daofiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import dbutilities.DbConnectionFactory;
import pojoclasses.*;

public class Admin_dao {
	
Connection conn=null;
	
	
	PreparedStatement pst=null;
	
	private Connection getDBConnection3() throws Exception
	{
		conn=DbConnectionFactory.getdbconnection();
		return conn;
	}
	
	public void Admin_login(pojoclasses.Admin ob)
	{
		
		try
		{
			
			conn=getDBConnection3();
			
			String querry="select * from Admin where id="+ob.getId()+"and password='"+ob.getPassword()+"'";
			java.sql.Statement stmt=conn.createStatement();
			ResultSet rs=((java.sql.Statement) stmt).executeQuery(querry);
			if(!rs.next())
			{
				System.out.println("Customer id or Password not found!!");
			}
			else
			{
				System.out.println("successfully logged in");
				admin_login_page(ob);
				
			}
			
			
			rs.close();
			stmt.close();
			
			
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}
	
	public  void admin_login_page(pojoclasses.Admin ob)
	{
		try {
			
			Scanner sc=new Scanner(System.in);
			boolean choice=true;
			do {
				System.out.println(" \n                  Welcome Admin \n\n -----------------------------------------------------");
			System.out.println("Please select any one of the following \n 1. Credit Money  \n 2. Debit Money \n"
					+ " 3. View Customer Transaction  \n 4. View Transaction History \n 5. View Admin Transaction \n 6. Return to Main Menu");
			int option=sc.nextInt();
			switch(option)
			{
			case 1: 
				admin_credit(ob);
				break;
			case 2:
				admin_debit(ob);
				break;
			case 3:
				view_customer_transaction();
				break;
			case 4:
				view_all_transaction();
				break;
			case 5:
				view_admin_transaction(ob);
				break;
			case 6:
				System.out.println("Logged out");
				choice=false;
				break;
			default:
				break;
			
			}
			}while(choice);
			
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
	}
	
	public void admin_credit(pojoclasses.Admin ob)
	{
		Scanner sc=new Scanner(System.in);
		int amount=-1;
		try
		{
		System.out.println("Enter the Amount ");
		amount=sc.nextInt();
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
		
		if(amount>0)
		{
		try
		{
			conn=getDBConnection3();
			boolean check=false;
			Bank tempobj1=new Bank();
			Customer tempobj2=new Customer();
			System.out.println("Enter the account number");
			int accno=sc.nextInt();
			tempobj1.setAcccno(accno);
			//validating account
			String querry2="select * from Bank where accno="+tempobj1.getAcccno();
			java.sql.Statement stmt2=conn.createStatement();
			ResultSet rs2=((java.sql.Statement) stmt2).executeQuery(querry2);
			if(!rs2.next())
			{
				System.out.println("Account not found!!");
			}
			else
			{
				System.out.println("Account found...");
				tempobj2.setCustid(rs2.getInt(2));
				check=true;
			}
			rs2.close();
			stmt2.close();
			
			
			if(check)
			{
			String query1="{call transaction_insert(autotid.nextval,?,?,?,?)}";
			pst=conn.prepareStatement(query1);
			pst.setLong(1, ob.getId());
			pst.setLong(2, tempobj2.getCustid());
			pst.setObject(3, LocalDate.now());
			pst.setLong(4, amount);
			
			
			
			int rows=pst.executeUpdate();
			System.out.println(" Amount added successfully ");
			
		pst.close();//addedwithoutcompile
			}
			
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		}
		else
		{
			System.out.println("Please enter valid amount!!!");
		}
	}
	
	public void admin_debit(pojoclasses.Admin ob)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Amount ");
		int amount=0;
		try {
		amount=sc.nextInt();}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
		if(amount>0)
		{
		try
		{
			conn=getDBConnection3();
			boolean check=false;
			Bank tempobj1=new Bank();
			Customer tempobj2=new Customer();
			System.out.println("Enter the account number");
			int accno=sc.nextInt();
			tempobj1.setAcccno(accno);
			//validating account
			String querry2="select * from Bank where accno="+tempobj1.getAcccno();
			java.sql.Statement stmt2=conn.createStatement();
			ResultSet rs2=((java.sql.Statement) stmt2).executeQuery(querry2);
			if(!rs2.next())
			{
				System.out.println("Account not found!!");
			}
			else
			{
				System.out.println("Account found...");
				tempobj2.setCustid(rs2.getInt(2));
				tempobj1.setAmount(rs2.getInt(4));
				check=true;
			}
			rs2.close();
			stmt2.close();
			
			
			if(check && amount<(tempobj1.getAmount()))
			{
				amount=amount-(amount*2);
			String query1="{call transaction_insert(autotid.nextval,?,?,?,?)}";
			pst=conn.prepareStatement(query1);
			pst.setLong(1, ob.getId());
			pst.setLong(2, tempobj2.getCustid());
			pst.setObject(3, LocalDate.now());
			pst.setLong(4, amount);
			
			
			
			int rows=pst.executeUpdate();
			System.out.println(" Amount debited successfully ");
			
		pst.close();//addedwithoutcompile
			}
			else
			{
				if(check && amount>(tempobj1.getAmount())) {
				System.out.println("Acoount Balance is low");}
			}
			
		}
		catch(InputMismatchException d)
		{
			System.out.println("PLEASE ENTER VALID INPUT....");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		}
		else
		{
			System.out.println("Please enter valid amount!!!");
		}
	}
	
	public void view_customer_transaction()
	{
		
		try
		{
			Scanner sc=new Scanner(System.in);
			conn=getDBConnection3();
			
			boolean check=false;
			Bank tempobj1=new Bank();
			Customer tempobj2=new Customer();
			System.out.println("Enter the account number");
			int accno=0;
			try {
			accno=sc.nextInt();}
			catch(InputMismatchException d)
			{
				System.out.println("PLEASE ENTER VALID INPUT....");
			}
			tempobj1.setAcccno(accno);
			//validating account
			String querry2="select * from Bank where accno="+tempobj1.getAcccno();
			java.sql.Statement stmt2=conn.createStatement();
			ResultSet rs2=((java.sql.Statement) stmt2).executeQuery(querry2);
			if(!rs2.next())
			{
				System.out.println("Account not found!!");
			}
			else
			{
				System.out.println("Account found...");
				tempobj2.setCustid(rs2.getInt(2));
				tempobj1.setAmount(rs2.getInt(4));
				check=true;
			}
			rs2.close();
			stmt2.close();
			
			
			if(check)
			{
			String querry7="select * from Transaction where from_id="+tempobj2.getCustid();
			java.sql.Statement stmt7=conn.createStatement();
			ResultSet rs7=((java.sql.Statement) stmt7).executeQuery(querry7);
			System.out.println("Trasaction done by customer are \n \n DATE \t\t AMOUNT \n");
			while(rs7.next())
			{
				System.out.print(rs7.getDate(4)+"\t");
				System.out.print(rs7.getLong(5)+" Rs");
				
				System.out.println();
			}
			
			rs7.close();
			stmt7.close();
			
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}
	
	public void view_all_transaction()
	{
		
		try
		{
			
			conn=getDBConnection3();
			
			String querry9="select * from Transaction ";
			java.sql.Statement stmt9=conn.createStatement();
			ResultSet rs9=((java.sql.Statement) stmt9).executeQuery(querry9);
			System.out.println("\nAll Trasaction history ::\n \nFROM_ID  TO_ID      DATE       AMOUNT \n");
			while(rs9.next())
			{
				System.out.print(rs9.getLong(2)+"\t");
				System.out.print(rs9.getLong(3)+"\t");
				System.out.print(rs9.getDate(4)+"\t");
				System.out.print(rs9.getLong(5)+" Rs");
				
				System.out.println();
			}
			
			rs9.close();
			stmt9.close();
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
}
	
	public void view_admin_transaction(pojoclasses.Admin ob)
	{
		
		try
		{
			
			conn=getDBConnection3();
			
			String querry9="select * from Transaction where from_id="+ob.getId();
			java.sql.Statement stmt9=conn.createStatement();
			ResultSet rs9=((java.sql.Statement) stmt9).executeQuery(querry9);
			System.out.println("\n Your Trasaction history ::\n\nTO_ID     DATE           AMOUNT \n");
			while(rs9.next())
			{
				//System.out.print(rs9.getLong(2)+"\t");
				System.out.print(rs9.getLong(3)+"\t");
				System.out.print(rs9.getDate(4)+"\t");
				System.out.print(rs9.getLong(5)+" Rs");
				
				System.out.println();
			}
			
			rs9.close();
			stmt9.close();
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
}
}
