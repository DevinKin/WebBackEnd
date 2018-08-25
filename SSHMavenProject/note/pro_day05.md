# Struts2异常处理框架
## 操作步骤
1. 自定义异常处理类
2. 在struts.xml中进行全局异常的配置
3. 在page/error.jsp页面加入异常图片

### 异常处理类
```java
package cn.devinkin.jk.exception;

public class SysException extends Exception{
    private String message;

    public SysException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
```

### 全局异常的配置
```xml
        <global-results>
            <result name="error">/WEB-INF/page/error.jsp</result>
        </global-results>
        <!-- 全局的异常处理 -->
        <global-exception-mappings>
            <exception-mapping exception="error" result="cn.devinkin.jk.exception.SysException"></exception-mapping>
            <exception-mapping exception="error" result="java.lang. Exception"></exception-mapping>
        </global-exception-mappings>

```

