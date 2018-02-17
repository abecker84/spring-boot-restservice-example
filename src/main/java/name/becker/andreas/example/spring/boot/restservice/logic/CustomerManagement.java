package name.becker.andreas.example.spring.boot.restservice.logic;

import java.util.List;

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
 * Component interface of the customer management component.
 */
public interface CustomerManagement {

    /**
     * Creates a new customer with the given {@code customer} data. The technical id
     * of the created customer is returned.
     * 
     * @param customer
     *            The customer data of the customer to be created
     * @return The technical id of the created customer
     */
    public long createCustomer(final CustomerEto customer);

    /**
     * Returns a list of all customers.
     * 
     * @return list of all customers
     */
    public List<CustomerEto> readAllCustomers();

    /**
     * Retrieves the customer for the given {@code customerId}.
     * 
     * @param customerId
     *            The customer's technical id
     * @return the customer
     */
    public CustomerEto readCustomer(final long customerId);

    /**
     * Updates the customer corresponding to the given {@code customerId} with the
     * given {@code customerData}.
     * 
     * @param customerId
     *            The technical id of the customer to be updated
     * @param customer
     *            The new customer data
     * @return boolean flag, whether the customer was found and could be updated
     */
    public boolean updateCustomerData(final long customerId, final CustomerEto customerData);

    /**
     * Deletes the customer with the given {@code customerId}.
     * 
     * @param customerId
     *            The id of the customer to be deleted
     * @return boolean flag, whether the customer was found and could be deleted
     */
    public boolean deleteCustomer(final long customerId);

}
