package cn.itcast.export.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class MyStruts2Filter extends StrutsPrepareAndExecuteFilter {

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if(request.getRequestURI().contains("/ws")){
			chain.doFilter(request, response);//放行
		}else{
			super.doFilter(request, response, chain);
		}
		
	}

	
}
