package com.model;

public class Client {

	private Integer id;
	private String firstname;
	private String lastname;
	private String number;
	private ClientStatus status;

	public Client() {

	}

	public Client(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public ClientStatus getStatus() {
		return status;
	}

	public void setStatus(ClientStatus status) {
		this.status = status;
	}

}
