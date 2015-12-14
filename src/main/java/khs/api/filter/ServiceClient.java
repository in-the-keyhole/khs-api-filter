package khs.api.filter;

import java.util.Arrays;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


public class ServiceClient {

	@Autowired
	private EurekaRegistry autowired;

	private static EurekaRegistry registry;

	@PostConstruct
	public void init() {
        // set static property, so static utility method can be invoked....
		registry = autowired;

	}
	
	public static String discoverAddress(String service) {
		return  registry.discoverAddress(service, false);
	}
	

	

}
