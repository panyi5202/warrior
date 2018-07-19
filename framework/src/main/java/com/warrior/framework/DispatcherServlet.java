package com.warrior.framework;

import com.warrior.framework.bean.Data;
import com.warrior.framework.bean.Handler;
import com.warrior.framework.bean.Param;
import com.warrior.framework.bean.View;
import com.warrior.framework.helper.BeanHelper;
import com.warrior.framework.helper.ConfigHelper;
import com.warrior.framework.helper.ControllerHelper;
import com.warrior.framework.util.ArrayUtil;
import com.warrior.framework.util.JsonUtil;
import com.warrior.framework.util.ReflectionUtil;
import com.warrior.framework.util.StreamUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器，处理http请求的核心处理类
 *
 * @author panyi on 18-7-18.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    /**
     * 系统默认编码
     */
    public final static String CHARACTER_ENCODING_DEFAULT = "UTF-8";
    public static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    public void init() throws ServletException {
        // 初始化相关 Helper 类
        HelperLoader.init();

        // 注册处理 jsp 的 servlet
        ServletRegistration jspServlet = getServletContext().getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认 servlet
        ServletRegistration defaultServlet = getServletContext().getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppStaticPath() + "*");

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // 获取请求方法和请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // 获取 Action 处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            // 处理get请求的参数
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            // 处理post请求的参数
            String body = URLDecoder.decode(StreamUtil.getString(req.getInputStream(), CHARACTER_ENCODING_DEFAULT), CHARACTER_ENCODING_DEFAULT);
            if (StringUtils.isNotBlank(body)) {
                String[] params = body.split("&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] kvs = param.split("=");
                        if (ArrayUtil.isNotEmpty(kvs) && kvs.length == 2) {
                            String paramName = kvs[0];
                            String paramValue = kvs[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);

            // 调用 Action 方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

            // 处理方法返回值
            if (result instanceof View) {
                // 返回jsp页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotBlank(path)) {
                    if (path.startsWith("/")) {
                        // 重定向
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        // 返回jsp页面
                        Map<String, Object> model = view.getModel();
                        model.forEach((k, v) -> {
                            req.setAttribute(k, v);
                        });
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }
                }
            }else if(result instanceof Data){
                // 返回json数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null){
                    resp.setContentType(CONTENT_TYPE_JSON);
                    resp.setCharacterEncoding(CHARACTER_ENCODING_DEFAULT);
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
