/**
 * 
 */
package name.becker.andreas.example.spring.boot.restservice.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import name.becker.andreas.example.spring.boot.restservice.logic.CustomerEto;
import name.becker.andreas.example.spring.boot.restservice.logic.CustomerManagement;

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
	customer.setEmail("sandra.sample@somewhere.example");

	allCustomers.put(customer.getId(), customer);

	customer = new CustomerEto();

	customer.setId(4712L);
	customer.setLastname("Alright");
	customer.setSurename("Andy");
	customer.setEmail("andy.alright@alright-software.example");

	allCustomers.put(customer.getId(), customer);

	customer = new CustomerEto();

	customer.setId(4713L);
	customer.setLastname("Barnes");
	customer.setSurename("Barney");
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
