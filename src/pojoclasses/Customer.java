package pojoclasses;

import java.util.Date;

public class Customer {
	String name;
	String address;
	String password,email;
	long phno;
	int custid;
	Date openingdate;
	
	public Date getOpeningdate() {
		return openingdate;
	}
	public void setOpeningdate(Date openingdate) {
		this.openingdate = openingdate;
	}
	public int getCustid() {
		return custid;
	}
	public void setCustid(int custid) {
		this.custid = custid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhno() {
		return phno;
	}
	public void setPhno(long phno) {
		this.phno = phno;
	}
	@Override
	public String toString() {
		return " \n Customer name : " + name + "\n Address : " + address + "\n Password : " + password + "\n E-mail : " + email
				+ "\n Phone Number : " + phno + "\n Custome ID : " + custid + "\n Date of Account opening : "+openingdate;
	}
	
	

}
