package name.becker.andreas.example.spring.boot.restservice;

import static org.junit.Assert.assertEquals;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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
 * Integration tests for the spring boot example ReST service. The spring boot
 * app is loaded, listening to a random http port by using the parameter
 * WebEnvironment.RANDOM_PORT of the SpringBootTest annotation. Subsequently,
 * the different http verbs and corresponding methods of the ReST service are
 * called within the test cases.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRestServiceExampleApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootRestServiceIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testReadCustomerSuccess() throws JSONException {
	HttpEntity<String> entity = new HttpEntity<>(null, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("/customers/4711"), GET, entity,
		String.class);

	String expectedResponseJson = "{\"id\":4711,\"surename\":\"Sandra\",\"lastname\":\"Sample\",\"email\":\"sandra.sample@somewhere.example\", \"dateOfBirth\":\"1980-04-24\"}";
	assertEquals("Wrong status code!", OK.value(), response.getStatusCodeValue());
	assertEquals(expectedResponseJson, response.getBody(), false);

    }

    @Test
    public void testReadCustomerFailureIdNotFound() throws JSONException {
	HttpEntity<String> entity = new HttpEntity<>(null, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("/customers/9999"), GET, entity,
		String.class);

	assertEquals("Wrong status code!", NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testCreateCustomerSuccess() {
	String requestJson = "{\"surename\":\"Goofy\",\"lastname\":\"Goodman\",\"email\":\"goofy.goodman@somewhere.example\", \"dateOfBirth\":\"1964-01-12\"}";
	headers.add("Content-Type", "application/json");
	HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("/customers"), PUT, entity, String.class);

	assertEquals("Wrong status code!", CREATED, response.getStatusCode());

	String resourceLocation = response.getHeaders().get(LOCATION).get(0);

	// GET the returned resource location:
	headers.clear();
	entity = new HttpEntity<>(null, headers);
	response = restTemplate.exchange(resourceLocation, GET, entity, String.class);

	assertEquals("Wrong status code!", OK, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomerSuccess() throws JSONException {

	// PUT changed data for customer with id = 4713:
	String requestJson = "{\"surename\":\"Goofy\",\"lastname\":\"Goodman\",\"email\":\"goofy.goodman@somewhere.example\", \"dateOfBirth\":\"1964-01-12\"}";
	headers.add("Content-Type", "application/json");
	HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("/customers/4713"), PUT, entity,
		String.class);

	assertEquals("Wrong status code!", OK, response.getStatusCode());

	// GET and check the modified data:
	String expectedResponseJson = "{\"id\":4713,\"surename\":\"Goofy\",\"lastname\":\"Goodman\",\"email\":\"goofy.goodman@somewhere.example\", \"dateOfBirth\":\"1964-01-12\"}";
	headers.clear();
	entity = new HttpEntity<>(null, headers);
	response = restTemplate.exchange(createUrl("/customers/4713"), GET, entity, String.class);

	assertEquals("Wrong status code!", OK, response.getStatusCode());
	assertEquals(expectedResponseJson, response.getBody(), false);
    }

    @Test
    public void testUpdateCustomerFailureIdNotFound() throws JSONException {

	// Try to PUT changed data for customer with wrong id = 6789:
	String requestJson = "{\"surename\":\"Goofy\",\"lastname\":\"Goodman\",\"email\":\"goofy.goodman@somewhere.example\", \"dateOfBirth\":\"1964-01-12\"}";
	headers.add("Content-Type", "application/json");
	HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("/customers/6789"), PUT, entity,
		String.class);

	// We should receive a 204 / No content!
	assertEquals("Wrong status code!", NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerSuccess() {

	// DELETE customer #4712:
	HttpEntity<String> entity = new HttpEntity<>(null, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("/customers/4712"), DELETE, entity,
		String.class);

	assertEquals("Wrong status code!", OK.value(), response.getStatusCodeValue());

	// Try to GET customer #4712 - Should result in 404!
	response = restTemplate.exchange(createUrl("/customers/4712"), GET, entity, String.class);

	assertEquals("Wrong status code!", NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testDeleteCustomerFailureIdNotFound() {

	// Try to DELETE customer with wrong id 6789:
	HttpEntity<String> entity = new HttpEntity<>(null, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("/customers/6789"), DELETE, entity,
		String.class);

	// We should receive a 204 / No content!
	assertEquals("Wrong status code!", NO_CONTENT.value(), response.getStatusCodeValue());

    }

    private String createUrl(String uri) {
	return "http://localhost:" + port + "/rest-example" + uri;
    }
}
