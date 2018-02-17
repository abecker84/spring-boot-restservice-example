/**
 * 
 */
package name.becker.andreas.example.spring.boot.restservice.logic.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

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
 * Default implementation of the customer management service.
 */
@Service
public class CustomerManagementImpl implements CustomerManagement {

    private static final long MAX_ID_VALUE = 999999999L;

    private Map<Long, CustomerEto> allCustomers;

    @PostConstruct
    public void init() {

	allCustomers = new HashMap<>();

	CustomerEto customer = new CustomerEto();

	customer.setId(4711L);
	customer.setLastname("Sample");
	customer.setSurename("Sandra");
	customer.setDateOfBirth(LocalDate.parse("1980-04-24"));
	customer.setEmail("sandra.sample@somewhere.example");

	allCustomers.put(customer.getId(), customer);

	customer = new CustomerEto();

	customer.setId(4712L);
	customer.setLastname("Alright");
	customer.setSurename("Andy");
	customer.setDateOfBirth(LocalDate.parse("1974-08-16"));
	customer.setEmail("andy.alright@alright-software.example");

	allCustomers.put(customer.getId(), customer);

	customer = new CustomerEto();

	customer.setId(4713L);
	customer.setLastname("Barnes");
	customer.setSurename("Barney");
	customer.setDateOfBirth(LocalDate.parse("1955-02-12"));
	customer.setEmail("barney.barnes@barnes-food.example");

	allCustomers.put(customer.getId(), customer);
    }

    @Override
    public long createCustomer(final CustomerEto customer) {
	final long customerId = ThreadLocalRandom.current().nextLong(MAX_ID_VALUE);
	customer.setId(customerId);
	allCustomers.put(customerId, customer);
	return customerId;
    }

    @Override
    public List<CustomerEto> readAllCustomers() {
	return new ArrayList<CustomerEto>(allCustomers.values());
    }

    @Override
    public CustomerEto readCustomer(final long customerId) {
	return allCustomers.get(customerId);
    }

    @Override
    public boolean updateCustomerData(final long customerId, final CustomerEto customerData) {

	boolean deleted = deleteCustomer(customerId);

	if (!deleted) {
	    return false;
	}

	customerData.setId(customerId);
	allCustomers.put(customerId, customerData);
	return true;

    }

    @Override
    public boolean deleteCustomer(final long customerId) {
	if (allCustomers.containsKey(customerId)) {
	    allCustomers.remove(customerId);
	    return true;
	} else {
	    return false;
	}
    }

}
