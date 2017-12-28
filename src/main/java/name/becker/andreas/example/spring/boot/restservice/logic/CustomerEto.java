package name.becker.andreas.example.spring.boot.restservice.logic;

/**
 * Entity transfer object which represents a customer.
 */
public class CustomerEto {

    private long id;
    private String surename;
    private String lastname;
    private String email;

    public String getSurename() {
	return surename;
    }

    public void setSurename(String surename) {
	this.surename = surename;
    }

    public String getLastname() {
	return lastname;
    }

    public void setLastname(String lastname) {
	this.lastname = lastname;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

}
