package khs.api.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;



@Controller
@RequestMapping(value="/")
public class ApiRouterController {


	@RequestMapping(value = "/router/post", method = RequestMethod.POST)
	@ResponseBody
	public String doPost(HttpServletRequest request) throws IOException {
		String api = (String) request.getAttribute("api");

		// TODO: should we pass individual headers or pass all of the headers
		// over?
		Map<String, String> headers = new HashMap<String, String>();
		addHeaderIfExists(headers, request, "userid");
		addHeaderIfExists(headers, request, "token");

		String requestBody = extractRequestBody(request);
	
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
		
		HttpEntity<String> entity = new HttpEntity<String>(extractRequestBody(request), httpheaders);
		ResponseEntity<String> result = restTemplate.exchange(api, HttpMethod.POST, entity, String.class);
		
		return result.getBody();

	}


	private String extractRequestBody(HttpServletRequest request) throws IOException {
		InputStream s = request.getInputStream();
		int bite;
		StringBuffer buf = new StringBuffer();
		while ((bite = s.read()) >= 0) {
			buf.append((char) bite);

		}
		return buf.toString();
	}

	@RequestMapping(value = "/router/put", method = RequestMethod.POST)
	@ResponseBody
	public String doPut(HttpServletRequest request) throws IOException {
		String api = (String) request.getAttribute("api");

		String requestBody = extractRequestBody(request);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
		
		HttpEntity<String> entity = new HttpEntity<String>(extractRequestBody(request), httpheaders);
		ResponseEntity<String> result = restTemplate.exchange(api, HttpMethod.PUT, entity, String.class);
		
		return result.getBody();
	}

	@RequestMapping(value = "/router/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean doDelete(HttpServletRequest request) throws IOException {
		String api = (String) request.getAttribute("api");


		String requestBody = extractRequestBody(request);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
		
		HttpEntity<String> entity = new HttpEntity<String>(extractRequestBody(request), httpheaders);
		ResponseEntity<String> result = restTemplate.exchange(api, HttpMethod.DELETE, entity, String.class);
		
		return true;
	}

	
	@RequestMapping(value = "/router/get", method = RequestMethod.GET)
	@ResponseBody
	public String doGet(HttpServletRequest request) throws IOException {
		String api = (String) request.getAttribute("api");

		String requestBody = extractRequestBody(request);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
		
		HttpEntity<String> entity = new HttpEntity<String>(extractRequestBody(request), httpheaders);
		ResponseEntity<String> result = restTemplate.exchange(api, HttpMethod.GET, entity, String.class);
		
		return result.getBody();

	}

	private void addHeaderIfExists(Map<String, String> headers, HttpServletRequest request, String key) {
		if (request.getHeader(key) != null) {
			headers.put(key, request.getHeader(key));
		}
	}

	


}
