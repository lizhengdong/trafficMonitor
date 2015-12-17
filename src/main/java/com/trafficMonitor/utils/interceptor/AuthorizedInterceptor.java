package com.trafficMonitor.utils.interceptor;

import com.trafficMonitor.model.SystemUsers;
import com.trafficMonitor.service.LoginService;
import com.trafficMonitor.utils.cookie.CookieTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lizhengdong on 12/15/15.
 * Author: lizhengdong
 * MailBox: 837718866@qq.com
 * Update by lizhengdong on 12/15/15.
 * Description: AuthorizedInterceptor
 */
public class AuthorizedInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        //登录请求不拦截
        if (uri.indexOf("login") != -1) {
            return true;
        }
        //设置不拦截的对象
        //对登录本身的页面以及业务不拦截
        String[] noFilters = new String[]{"loginUser", "register", "registerFail", "registerSuccess", "index"};
        boolean beFilter = true;
        for (String s : noFilters) {
            if (uri.indexOf(s) != -1) {
                beFilter = false;
                break;
            }
        }
        if (beFilter == true) {
            //除了不拦截对象以外
            String path = httpServletRequest.getContextPath();
            String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + path + "/";
            SystemUsers systemUsers = (SystemUsers) httpServletRequest.getSession().getAttribute("systemUsers");
            if (systemUsers == null) {
                //未登录
                httpServletResponse.sendRedirect(basePath + "login");
                return false;
            } else {
                //已经登录，判断是否在登录勾选了记住密码
                Cookie cookieLoginName = CookieTool.getCookieByName(httpServletRequest, "loginName");
                Cookie cookieLoginPwd = CookieTool.getCookieByName(httpServletRequest, "loginPwd");
                if (cookieLoginName != null && cookieLoginPwd != null && cookieLoginName.getValue() != null && cookieLoginPwd.getValue() != null) {
                    String loginName = cookieLoginName.getValue();
                    String loginPwd = cookieLoginPwd.getValue();
                    //检查到客户端保存了用户密码，进行该账户的验证
                    if (!loginService.login(loginName, loginPwd)) {
                        //未验证通过
                        CookieTool.addCookie(httpServletResponse, "loginName", null, 0);
                        CookieTool.addCookie(httpServletResponse, "loginPwd", null, 0);
                        try {
                            httpServletResponse.sendRedirect(basePath + "/login");
                            return false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        httpServletRequest.getSession().setAttribute("errorInfo", "请登录！");
                    }
                }
            }
        }
        return true;
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
