package com.atguigu.crowd.filter;

import com.atguigu.crowd.constant.AccessPassResources;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.vo.MemberLoginVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ldy
 * @version 1.0
 */
@Component
@Slf4j
public class CrowdAccessFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String servletPath = request.getServletPath();
        boolean containResult = AccessPassResources.PASS_RES_SET.contains(servletPath);
        //log.info("servletPath="+servletPath+",containResult="+containResult);
        if(containResult){
            return false;
        }
        // 5.判断当前请求是否为静态资源
        // 工具方法返回 true：说明当前请求是静态资源请求，取反为 false 表示放行不做登录检查
        // 工具方法返回 false：说明当前请求不是可以放行的特定请求也不是静态资源，取反为 true
        boolean staticResource = AccessPassResources.judgeCurrentServletPathWetherStaticResource(servletPath);
        log.info("如果是false则检查，staticResource="+staticResource+",servletPath="+servletPath);
        return !staticResource;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        MemberLoginVO loginMember = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        log.info("loginMember="+loginMember);
        if(loginMember == null){
            HttpServletResponse response = currentContext.getResponse();
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
            try {
                response.sendRedirect("/auth/member/to/login/page.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
