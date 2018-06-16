package cn.devinkin.web.filter;

import cn.devinkin.contant.Constant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@WebFilter(filterName = "EncodingFilter2", urlPatterns = "/*")
public class EncodingFilter2 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         * 1. 强转
         * 2. 创建代理对象
         * 3. 重写方法
         * 4. 方形
         */
        final HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest requestProxy = (HttpServletRequest) Proxy.newProxyInstance(HttpServletRequest.class.getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                //重写getParameterMap方法
                if (Constant.GETPARAMETERMAP.equals(methodName)) {
                    /**
                     * 1. 获取请求方式
                     * 2. 若为get请求，使用new String，返回对应字符串
                     * 3. 若为post请求，使用setCharacter方法，返回值为method.invoke()
                     */
                    String postMethod = request.getMethod();
                    if (Constant.METHOD_GET.equalsIgnoreCase(postMethod)) {
                        Map<String, String[]> map = (Map<String, String[]>) method.invoke(request, args);
                        if (map == null)
                            return null;
                        for (String key : map.keySet()) {
                            String[] values = map.get(key);
                            for (int i = 0; i < values.length; i++) {
                                values[i] = new String(values[i].getBytes("iso8859-1"), "utf-8");
                            }
                        }
                        return map;
                    } else if (Constant.METHOD_POST.equalsIgnoreCase(postMethod)) {
                        request.setCharacterEncoding("utf-8");
                        return method.invoke(request, args);
                    }

                    //重写getParameterValues方法
                } else if (Constant.GETPARAMETERVALUES.equalsIgnoreCase(methodName)) {
                    /**
                     * 1. 检验name是否为空
                     *  1. 空则返回null
                     * 2. 检验通过name查找的values是否为空
                     *  1. 如果为空，返回null
                     * 3. 判断get还是post，修改编码
                     */
                    String name = (String) args[0];
                    if (name == null || name.trim().length() == 0) {
                        return null;
                    }
                    String postMethod = request.getMethod();
                    if (Constant.METHOD_GET.equalsIgnoreCase(postMethod)) {
                        String[] values = (String[]) method.invoke(request, args);
                        if (values.length == 0)
                            return null;
                        for (int i = 0; i < values.length; i++) {
                            values[i] = new String(values[i].getBytes("iso8859-1"), "utf-8");
                        }
                        return values;
                    } else if (Constant.METHOD_POST.equalsIgnoreCase(postMethod)) {
                        request.setCharacterEncoding("utf-8");
                        return method.invoke(request, args);
                    }

                    //重写getParameter方法
                } else if (Constant.GETPARAMETER.equals(methodName)) {
                    /**
                     * 1. 检验name是否为空
                     *  1. 空则返回null
                     * 2. 通过name查找value，判断value是否为空
                     *  1. 为空返回null
                     * 3. 判断get还是post，修改编码
                     */
                    String name = (String) args[0];
                    if (name == null || name.trim().length() == 0)
                        return null;
                    String postMethod = request.getMethod();
                    if (Constant.METHOD_GET.equalsIgnoreCase(postMethod)) {
                        String value = (String) method.invoke(request, args);
                        if (value == null || value.trim().length() == 0)
                            return null;
                        return new String(value.getBytes("iso8859-1"), "utf-8");
                    } else if (Constant.METHOD_POST.equalsIgnoreCase(postMethod)) {
                        request.setCharacterEncoding("utf-8");
                        return method.invoke(request, args);
                    }
                }
                //其余不需要增强的方法调用原来的方法即可
                return method.invoke(request, args);
            }
        });
        chain.doFilter(requestProxy, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
