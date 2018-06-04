package cn.devinkin.case4.web.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//1.强转
		final HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;

		//创建代理对象
		HttpServletRequest requestProxy=(HttpServletRequest) Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if("getParameter".equals(method.getName())){
					//获取请求方式
					String m = request.getMethod();
					System.out.println(m);
					if("get".equalsIgnoreCase(m)){
						String s = (String) method.invoke(request, args);//相当于  request.getParameter(args);
						return new String(s.getBytes("iso8859-1"),"utf-8");
					}else if("post".equalsIgnoreCase(m)){
						request.setCharacterEncoding("utf-8");
						System.out.println("post");
						return method.invoke(request, args);
					}
				}
				
				//不需要加强的方法
				return method.invoke(request, args);
			}
		});
		
		//2.放行
		chain.doFilter(requestProxy, response);

	}

	@Override
	public void destroy() {

	}

}
