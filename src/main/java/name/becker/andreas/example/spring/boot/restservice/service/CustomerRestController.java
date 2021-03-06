package name.becker.andreas.example.spring.boot.restservice.service;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import name.becker.andreas.example.spring.boot.restservice.logic.CustomerEto;
import name.becker.andreas.example.spring.boot.restservice.logic.CustomerManagement;

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
 * REST-Controller for the customer example REST-service. Customers can be
 * created, read, updated and deleted via the annotated methods that are mapped
 * to the corresponding http verbs that grant access to the customer resource.
 */
@RestController
@RequestMapping("/rest-example")
public class CustomerRestController {

    @Autowired
    CustomerManagement customerManagement;

    @PutMapping("/customers")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerEto customer) {
	if (customer == null) {
	    return ResponseEntity.noContent().build();
	}

	long customerId = customerManagement.createCustomer(customer);

	UriComponents ucb = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + customerId).build();

	return ResponseEntity.created(ucb.toUri()).build();
    }

    @GetMapping("/customers")
    public List<CustomerEto> readAllCustomers() {
	return customerManagement.readAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerEto> readCustomer(@PathVariable Long customerId) {
	CustomerEto customer = customerManagement.readCustomer(customerId);

	if (customer != null) {
	    return new ResponseEntity<>(customer, HttpStatus.OK);
	} else {
	    return notFound().build();
	}
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerEto customerData) {
	boolean updated = customerManagement.updateCustomerData(customerId, customerData);

	if (updated) {
	    return ok().build();
	} else {
	    return noContent().build();
	}
    }

    @DeleteMapping("customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
	boolean deleted = customerManagement.deleteCustomer(customerId);

	if (deleted) {
	    return ok().build();
	} else {
	    return noContent().build();
	}
    }
}
