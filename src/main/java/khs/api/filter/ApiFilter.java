package khs.api.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.IOUtils;


public class ApiFilter implements Filter {

	private static void loadRouteMap() {
		InputStream apiRouteInputStream = null;
		try {
			apiRouteInputStream = ApiFilter.class.getResource("/route.map").openStream();
			final String s = IOUtils.toString(apiRouteInputStream);
			String lines[] = s.split("\\r?\\n");
			for (int i = 0; i < lines.length; i++) {

				String[] a = lines[i].split("->");
				if (a.length == 2) {
					String route = a[0];
					String service = a[1].trim();
					routeMap.put(route, service);
				}
			}

			System.out.println(routeMap);

		} catch (final Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (apiRouteInputStream != null) {
				IOUtils.closeQuietly(apiRouteInputStream);
			}
		}
	}
	
	
	
	
	private static Map<String, String> routeMap;
	private ServiceClient serviceClient;

	public ApiFilter(Map<String, String> routeMap, ServiceClient serviceClient) {
		this.routeMap = routeMap;
		this.serviceClient = serviceClient;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		final HttpServletRequest request = (HttpServletRequest) servletRequest;


		String api = findRoute(request.getRequestURI());

		if (request.getMethod().equalsIgnoreCase("POST")) {


			request.setAttribute("api", api);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/sherpa/router/post");
			dispatcher.forward(request, response);
			return;


		}


		if (request.getMethod().equalsIgnoreCase("PUT")) {


			request.setAttribute("api", api);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/sherpa/router/put");
			dispatcher.forward(request, response);
			return;


		}

		if (request.getMethod().equalsIgnoreCase("DELETE")) {


			request.setAttribute("api", api);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/sherpa/router/delete");
			dispatcher.forward(request, response);
			return;
		}

		if (request.getMethod().equalsIgnoreCase("GET")) {
			request.setAttribute("api", api);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/sherpa/router/get");
			dispatcher.forward(request, response);
			return;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private String findRoute(String api) {
		String urlroute = null;
		for (String key : routeMap.keySet()) {
			Pattern p = Pattern.compile(key);
			Matcher m = p.matcher(api);
			if (m.find()) {
				String service = routeMap.get(key);
				urlroute = serviceClient.discoverAddress(service) + api;
			}
		}
		return urlroute;
	}

}
