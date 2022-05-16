```


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	@Bean
	public FilterRegistrationBean<Filter> corsFilter (){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
		filterRegistrationBean.setFilter(new OncePerRequestFilter() {

			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				String origin = request.getHeader(HttpHeaders.ORIGIN);

				if (StringUtils.hasText(origin)) {

					response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);

					String requestHeaders = request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
					if (StringUtils.hasText(requestHeaders)) {
						response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders);
					}

					// If the browser version is too low, "*" it may not be supported.
					response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

					response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

					response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");

					if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
						response.setContentType(MediaType.TEXT_HTML_VALUE);
						response.setCharacterEncoding("utf-8");
						response.setContentLength(0);
						response.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800");
						return;
					}
				}
				filterChain.doFilter(request, response);
			}
			
		});
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setOrder(Integer.MIN_VALUE);
		return filterRegistrationBean;
	}
}
```
