package name.becker.andreas.example.spring.boot.restservice.logic;

import java.time.LocalDate;

/*
 * Copyright 2018, Andreas Becker <andreas AT becker DOT name>
 * 
 * This file is part of The Spring Boot REST Service example.
 * 
 * The Spring Boot REST Service example is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * The Spring Boot REST Service example is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * General Public License along with The Spring Boot REST Service example. If
 * not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Entity transfer object which represents a customer.
 */
public class CustomerEto {

    private long id;
    private String surename;
    private String lastname;
    private String email;
    private LocalDate dateOfBirth;

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

    public LocalDate getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

}
