/**
 * 
 */
package name.becker.andreas.example.spring.boot.restservice.logic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import name.becker.andreas.example.spring.boot.restservice.logic.CustomerEto;

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
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerManagementImplTest {

	@Autowired
	private CustomerManagementImpl testObject;

	/**
	 * Success test case for creating and reading a customer via the test object.
	 * All attribute values of the returned transfer object have to match the values
	 * that were originally passed to the createCustomer()-method.
	 */
	@Test
	public void testSuccess_loadCustomer_allValuesCorrect() {

		CustomerEto customer = new CustomerEto();

		customer.setSurename("Trudy");
		customer.setLastname("Test");
		customer.setDateOfBirth(LocalDate.parse("1964-11-10"));
		customer.setEmail("trudy.test@testmail.example");
		long customerId = testObject.createCustomer(customer);

		CustomerEto loadedCustomer = testObject.readCustomer(customerId);

		assertEquals(customer.getSurename(), loadedCustomer.getSurename());
		assertEquals(customer.getLastname(), loadedCustomer.getLastname());
		assertEquals(customer.getDateOfBirth(), loadedCustomer.getDateOfBirth());
		assertEquals(customer.getEmail(), loadedCustomer.getEmail());

	}

	/**
	 * Success test case for reading a customer with a non-existing customer id. The
	 * test object should not return a customer object.
	 */
	@Test
	public void testSuccess_readCustomer_customerDoesNotExist() {

		long wrongCustomerId = 471147124713L;
		CustomerEto customer = testObject.readCustomer(wrongCustomerId);
		assertNull(customer);

	}
}
