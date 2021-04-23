package daofiles;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dbutilities.DbConnectionFactory;
import pojoclasses.Customer;
import java.sql.*;
import daofiles.*;

public class Customer_dao {
	Connection con=null;
	
	
	PreparedStatement pst=null;
	
	private  Connection getDBConnection1() throws Exception
	{
		con=DbConnectionFactory.getdbconnection();
		return con;
	}
	
	
	public void Customer_insert(pojoclasses.Customer ob)
	{
		
		try
		{
			
			con=getDBConnection1();
			
			String query1="insert into customer values(autoid.nextval,?,?,?,?,?,?)";
			pst=con.prepareStatement(query1);
			pst.setString(1, ob.getName());
			pst.setString(2, ob.getAddress());
			pst.setString(3, ob.getEmail());
			pst.setLong(4, ob.getPhno());
			pst.setString(5, ob.getPassword());
			pst.setObject(6, LocalDate.now());
			
			
			int rows=pst.executeUpdate();
			//System.out.println(" Done :)");
			
			System.out.println("your account is successfully created \n ");
			//select cid from customer order by cid desc;
			
			java.sql.Statement stmt=con.createStatement();
			ResultSet rs1=((java.sql.Statement) stmt).executeQuery("select * from customer order by cid desc");
			rs1.next();
			ob.setCustid(rs1.getInt(1));
			
			rs1.close();
			stmt.close();
			
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(con!=null)
					con.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}

	public void Customer_login(pojoclasses.Customer ob)
	{
		
		try
		{
			
			con=getDBConnection1();
			
			String querry="select * from customer where cid="+ob.getCustid()+"and password='"+ob.getPassword()+"'";
			java.sql.Statement stmt=con.createStatement();
			ResultSet rs=((java.sql.Statement) stmt).executeQuery(querry);
			if(!rs.next())
			{
				System.out.println("Customer id  not found or Entered password is wrong!!");
			}
			else
			{
				System.out.println("successfully logged in");
				login_page(ob);
				
			}
			
			
			rs.close();
			stmt.close();
			
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(con!=null)
					con.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}
	
	public  void login_page(pojoclasses.Customer ob)
	{
		try {
			Scanner sc=new Scanner(System.in);
			boolean choice=true;
			do {
				System.out.println(" \n                  Welcome Customer   \n\n -----------------------------------------------------");
			System.out.println("Please select any one of the following \n 1.Profile \n 2.Update Profile \n 3.View Bank details \n"
					+ " 4.Debit Money \n 5.Credit Money \n 6.View Transaction History \n 7.Return to Main Menu");
			int option=sc.nextInt();
			switch(option)
			{
			case 1: 
				view_profile(ob);
				break;
			case 2:
				update_profile(ob);
				break;
			case 3:
				//follow singleton
				Bank_dao a=new Bank_dao();
				a.get_bankdetails(ob);
				break;
			case 4:
				//follow singleton
				Bank_dao c=new Bank_dao();
				c.user_debit(ob);
				break;
			case 5:
				//follow singleton
				Bank_dao b=new Bank_dao();
				b.user_credit(ob);
				break;
			case 6:
				view_transaction(ob);
				break;
			case 7:
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
			System.out.println("PLEASE ENTER VALID INPUT....Logged out");
		}
	}

	public void view_profile(pojoclasses.Customer ob)
	{
		
		try
		{
			
			con=getDBConnection1();
			
			String querry="select * from customer where cid="+ob.getCustid()+"and password='"+ob.getPassword()+"'";
			java.sql.Statement stmt=con.createStatement();
			ResultSet rs=((java.sql.Statement) stmt).executeQuery(querry);
			if(!rs.next())
			{
				System.out.println("Details not found!!");
			}
			else
			{
				ob.setAddress(rs.getString(3));
				ob.setEmail(rs.getString(4));
				ob.setName(rs.getString(2));
				ob.setPhno(rs.getLong(5));
				ob.setOpeningdate((rs.getDate(7)));
				System.out.println(ob);
			}
			
			
			rs.close();
			stmt.close();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(con!=null)
					con.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}

	public void update_profile(pojoclasses.Customer ob)
	{
		
		
		try
		{
			int rows=0;
			con=getDBConnection1();
			java.sql.Statement stmt=con.createStatement();
			Scanner sc=new Scanner(System.in);
			String querry=null;
			boolean check=true;
			
			System.out.println("\nSelect what you want to update \n 1. Address \n 2. E-mail \n 3. Phone \n 4. Password \n 5. Return ");
			int choice=sc.nextInt();
			sc.nextLine();
			if(choice==1)
			{
				System.out.println("Enter new Address");
				String address=sc.nextLine();
				ob.setAddress(address);
				querry="update customer set address='"+ob.getAddress()+"' where cid="+ob.getCustid();
				
				rows=stmt.executeUpdate(querry);
				System.out.println("address updated successfully");
				
				}
			else if (choice==2)
			{
				System.out.println("Enter new E-mail");
				String email=sc.nextLine();
				ob.setEmail(email);
				boolean checkemail=validation.emailvalidation.validateemail(ob);
				if(checkemail) {
				querry="update customer set email='"+ob.getEmail()+"' where cid="+ob.getCustid();
				rows=stmt.executeUpdate(querry);
				System.out.println("email updated successfully");
				
				}
				else {
					System.out.println("please enter valid email");
				}
				
				}
			else if(choice==3)
			{
				System.out.println("Enter new Phone Number");
				long phno=sc.nextLong();
				ob.setPhno(phno);
				boolean checkphno=validation.phonenumbervalidation.validatephno(ob);
				if(checkphno)
				{
				querry="update customer set phno="+ob.getPhno()+" where cid="+ob.getCustid();
				rows=stmt.executeUpdate(querry);
				System.out.println("Phone number updated successfully");
				
				}
				
				else
				{
					System.out.println("please enter valid phonenumber");
				}
				}
			else if(choice==4)
			{
				System.out.println("Enter new Password");
				String password=sc.nextLine();
				System.out.println("Re-enter your password ");
				String pwcopy=sc.nextLine();
				ob.setPassword(password);
				boolean checkpassword=validation.passwordvalidation.validatepassword(ob);
				if(!password.equals(pwcopy))
				{
					checkpassword=false;
				}
				if(checkpassword) {
				querry="update customer set password='"+ob.getPassword()+"' where cid="+ob.getCustid();
				rows=stmt.executeUpdate(querry);
				System.out.println("password updated successfully");
				}
				else
				{
					System.out.println("invalid password or re entered password do not match ...");
				}
				}
			else if(choice==5)
			{

			}
			else
			{
				System.out.println("please enter valid choice ");
			}
			
			
			
			if(rows>0)
			{
				System.out.println("Update is Successful :)");
			}
			else
			{
				System.out.println("Update is Unsuccessful");
			}
			
			
			
			
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
				
				
					if(con!=null)
					con.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}
	
	public void view_transaction(pojoclasses.Customer ob)
	{
		
		try
		{
			
			con=getDBConnection1();
			
			String querry7="select * from Transaction where from_id="+ob.getCustid();
			java.sql.Statement stmt7=con.createStatement();
			ResultSet rs7=((java.sql.Statement) stmt7).executeQuery(querry7);
			System.out.println("Trasaction done by you : \n \n DATE \t\t AMOUNT \n ");
			while(rs7.next())
			{
				System.out.print(rs7.getDate(4)+"\t");
				System.out.print(rs7.getLong(5)+" Rs");
				
				System.out.println();
			}
			
			rs7.close();
			stmt7.close();
			
			String querry8=" select a.transactiondate,a.amount from Transaction a,Admin b where a.from_id=b.id and a.to_id="+ob.getCustid();
			java.sql.Statement stmt8=con.createStatement();
			ResultSet rs8=((java.sql.Statement) stmt8).executeQuery(querry8);
			System.out.println("\n Trasaction done by Admin \n \n DATE \t\t AMOUNT \n");
			while(rs8.next())
			{
				System.out.print(rs8.getDate(1)+"\t");
				System.out.print(rs8.getLong(2)+" Rs");
				System.out.println();
				
			}
			
			rs8.close();
			stmt8.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(con!=null)
					con.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}
	
	public void forgotpassword(ArrayList<Object> ob)
	{
		
		try
		{
			
			con=getDBConnection1();
			
			String querry="select * from customer where cid="+ob.get(0)+"and phno="+ob.get(1);
			java.sql.Statement stmt=con.createStatement();
			ResultSet rs=((java.sql.Statement) stmt).executeQuery(querry);
			if(!rs.next())
			{
				System.out.println("Details not found!!");
			}
			else
			{
				ob.add(2, rs.getString(6));
				System.out.println("your account password is "+ob.get(2));
				
			}
			
			
			rs.close();
			stmt.close();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				
				
					if(con!=null)
					con.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}
	
	
	


}
