package daofiles;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import dbutilities.DbConnectionFactory;
import pojoclasses.*;

public class Bank_dao {
	Connection conn=null;
	
	
	PreparedStatement pst=null;
	
	private Connection getDBConnection2() throws Exception
	{
		conn=DbConnectionFactory.getdbconnection();
		return conn;
	}
	
	public void createaccount(Customer ob1,Bank ob2)
	{
		try
		{
			
			conn=getDBConnection2();
			
			String query1="insert into bank values(autoacc.nextval,?,?,?)";
			pst=conn.prepareStatement(query1);
			pst.setInt(1,ob1.getCustid() );
			pst.setString(2,ob2.getAcctype() );
			pst.setInt(3, ob2.getAmount());
		
			int rows=pst.executeUpdate();
			
			System.out.println(ob2.getAcctype()+ " Account is created successfully..... \n");
			
			java.sql.Statement stmt=conn.createStatement();
			ResultSet rs1=((java.sql.Statement) stmt).executeQuery("select * from bank order by accno desc");
			rs1.next();
			ob2.setAcccno(rs1.getInt(1));
			//ob.setCustid(rs1.getInt(1));
			
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
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
	}
	
	public void get_bankdetails(pojoclasses.Customer ob)
	{
		
		try
		{
			
			conn=getDBConnection2();
			
			String querry="select * from bank where custid="+ob.getCustid();
			java.sql.Statement stmt=conn.createStatement();
			ResultSet rs=((java.sql.Statement) stmt).executeQuery(querry);
			if(!rs.next())
			{
				System.out.println("Details not found!!");
			}
			else
			{
				System.out.println("Account Number :"+rs.getInt(1));
				System.out.println("Account Type :"+rs.getString(3));
				System.out.println("Balance :"+rs.getLong(4));
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
				
				
					if(conn!=null)
					conn.close();
			}
			catch(Exception e)
			{
				System.out.println("Finally Block::"+e);
			}
		}
		
	}
	
	public void user_credit(pojoclasses.Customer ob)
	{
		Scanner sc=new Scanner(System.in);
		int amount=0;
		try {
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
			
			conn=getDBConnection2();
			
			String query1="{call transaction_insert(autotid.nextval,?,?,?,?)}";
			pst=conn.prepareStatement(query1);
			pst.setLong(1, ob.getCustid());
			pst.setLong(2, ob.getCustid());
			pst.setObject(3, LocalDate.now());
			pst.setLong(4, amount);
			
			
			
			int rows=pst.executeUpdate();
			System.out.println(" Amount added successfully ");
			
		pst.close();//addedwithoutcompile
			
			
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
	
	public void user_debit(pojoclasses.Customer ob)
	{
		int amount=0;
		Scanner sc=new Scanner(System.in);
		try {
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
			
			Bank tempobj=new Bank();
			
			conn=getDBConnection2();
			//checking minimum balance
			
			String querry4="select * from bank where custid="+ob.getCustid();
			
			java.sql.Statement stmt4=conn.createStatement();
			ResultSet rs4=((java.sql.Statement) stmt4).executeQuery(querry4);
			
			
			if(!rs4.next())
			{
				System.out.println("-------------------------------");
			}
			else
			{
				tempobj.setAmount(rs4.getInt(4));
				//System.out.println("your account balance "+tempobj.getAmount());
			}
			
			//2
			
			if(amount<(tempobj.getAmount()))
			{
				amount=amount-(amount*2);
			String query1="{call transaction_insert(autotid.nextval,?,?,?,?)}";
			pst=conn.prepareStatement(query1);
			pst.setLong(1, ob.getCustid());
			pst.setLong(2, ob.getCustid());
			pst.setObject(3, LocalDate.now());
			pst.setLong(4, amount);
			
			
			
			int rows=pst.executeUpdate();
			if(rows>0)
			{
			System.out.println(" Amount debited successfully \n ---------------------------");
			}
			}
			else
			{
				System.out.println("Your account balance is low");
			}
			rs4.close();
			stmt4.close();
		
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
		System.out.println("please enter valid amount..");
	}
	
	
	}
}
