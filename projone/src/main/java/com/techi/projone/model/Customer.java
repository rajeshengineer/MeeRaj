package com.techi.projone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	@Column(name = "CUST_ID")
	private long id;
	@Column(name = "LAST_NAME")
	private String lname;
	@Column(name = "FIRST_NAME")
	private String fname;
	@Column(name = "PHONE_NUM")
	private String pnum;
	@Column(name = "ADDRS")
	private String addrs;

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getPnum() {
		return pnum;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

	public String getAddrs() {
		return addrs;
	}

	public void setAddrs(String addrs) {
		this.addrs = addrs;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", lname=" + lname + ", fname=" + fname + ", pnum=" + pnum + ", addrs=" + addrs
				+ "]";
	}

}
