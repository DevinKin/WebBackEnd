package cn.devinkin.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class EncodedRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public EncodedRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        String method = request.getMethod();
        String value = super.getParameter(name);

        if ("get".equalsIgnoreCase(method)) {
            //get请求
            try {
                return new String(value.getBytes("iso8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if ("post".equalsIgnoreCase(method)) {
            try {
                request.setCharacterEncoding("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return value;
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String,String[]> map = new HashMap<String,String[]>(super.getParameterMap());
        if (map == null) return null;
        for (String name : map.keySet()) {
            String ename = name;
            try {
                ename = new String(name.getBytes("iso8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String[] values = map.get(name);
            for(int i = 0; i < values.length; i++) {
                try {
                    values[i] = new String(values[i].getBytes("iso8859-1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            map.put(ename, values);
        }
        return map;
    }
}
