package name.becker.andreas.example.spring.boot.restservice;

import static org.junit.Assert.assertEquals;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
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
	ResponseEntity<String> response = restTemplate.exchange(createUrl("customers/4711"), GET, entity, String.class);

	String expectedResponseJson = "{\"id\":4711,\"surename\":\"Sandra\",\"lastname\":\"Sample\",\"email\":\"sandra.sample@somewhere.example\"}";
	assertEquals("Wrong status code!", OK.value(), response.getStatusCodeValue());
	assertEquals(expectedResponseJson, response.getBody(), false);

    }

    @Test
    public void testReadCustomerFailureIdNotFound() throws JSONException {
	HttpEntity<String> entity = new HttpEntity<>(null, headers);
	ResponseEntity<String> response = restTemplate.exchange(createUrl("customers/9999"), GET, entity, String.class);

	assertEquals("Wrong status code!", NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testCreateCustomerSuccess() {
	String requestJson = "{\"surename\":\"Goofy\",\"lastname\":\"Goodman\",\"email\":\"goofy.goodman@somewhere.example\"}";
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

    // TODO:
    // testUpdateCustomerSuccess()
    // testUpdateCustomerFailureIdNotFound()
    // testDeleteCustomerSuccess()
    // testDeleteCustomerFailureIdNotFound()

    private String createUrl(String uri) {
	return "http://localhost:" + port + uri;
    }
}
