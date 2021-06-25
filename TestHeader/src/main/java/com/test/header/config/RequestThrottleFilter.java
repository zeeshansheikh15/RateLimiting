package com.test.header.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
public class RequestThrottleFilter implements Filter {

//	private String[] urisArray = { "/std" };

	private int MAX_REQUESTS_PER_SECOND = 2; // or whatever you want it to be

	private LoadingCache<String, Integer> requestCountsPerIpAddress;

	private Map<String,Integer> uris = new HashMap<>();
	

	public RequestThrottleFilter() {
		super();
		System.out.println("constructor called");
		
//		if (uris.contains(req.getRequestURI())) {
//			requestCountsPerIpAddress = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
//					.build(new CacheLoader<String, Integer>() {
//						public Integer load(String key) {
//							return 0;
//						}
//					});
//		} else {
			requestCountsPerIpAddress = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES)
					.build(new CacheLoader<String, Integer>() {
						public Integer load(String key) {
							return 0;
						}
					});
//		}

			this.uris.put("/std", 5);
			this.uris.put("/list", 10);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

//        String clientIpAddress = getClientIP(httpServletRequest);
//        use for IP-Address
//        if(isMaximumRequestsPerSecondExceeded(clientIpAddress)){
//          httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//          httpServletResponse.getWriter().write("Too many requests");
//          return;
//         }

//      	Token based rate limiter

		String token = parseJwt(httpServletRequest);
		if (isMaximumRequestsPerSecondExceeded(token+httpServletRequest.getRequestURI(),httpServletRequest.getRequestURI())) {
//            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//            httpServletResponse.getWriter().write("Too many requests");
			httpServletResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too Many Requests");
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	private boolean isMaximumRequestsPerSecondExceeded(String key,String endPoint) {
		int requests = 0;
		try {
			requests = requestCountsPerIpAddress.get(key);
			System.out.println("key :"+key+" ::: "+requests);
			if(uris.containsKey(endPoint)) {
				if (requests > uris.get(endPoint)) {
					requestCountsPerIpAddress.put(key, requests);
					return true;
				}
			} else {
				if (requests > MAX_REQUESTS_PER_SECOND) {
					requestCountsPerIpAddress.put(key, requests);
					return true;
				}
			}
				

		} catch (ExecutionException e) {
			requests = 0;
		}
		requests++;
		requestCountsPerIpAddress.put(key, requests);
		return false;
	}

	public String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");
		System.out.println("xfHeader : " + xfHeader);
		if (xfHeader == null) {
			System.out.println("request.getRemoteAddr() " + request.getRemoteAddr());
			return request.getRemoteAddr();
		}
		System.out.println("without IP");
		return xfHeader.split(",")[0]; // voor als ie achter een proxy zit
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}

		return null;
	}

	@Override
	public void destroy() {

	}

}
