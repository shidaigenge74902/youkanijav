package com.bayside.app.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bayside.app.opinion.war.myuser.model.ManagerUser;

public class CheckLoginFilter extends OncePerRequestFilter {
	private static final Logger log = Logger.getLogger(CheckLoginFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		  HttpServletRequest httpRequest = (HttpServletRequest) request;
	        HttpServletResponse httpResponse = (HttpServletResponse) response;
	        
		System.out.println("当前sessionid"+request.getSession().getId());
		System.out.println("当前请求地址："+request.getRequestURI());
		String requesturi =  request.getRequestURI();
		ManagerUser user = (ManagerUser)request.getSession().getAttribute("managerUser");
		// TODO Auto-generated method stub insertUserType  selectStanderPower sendTelCheck
		if ("/app-opinion-manager/selectInfoByPa".equals(requesturi)||"/app-opinion-manager/updateManagerUserPassword".equals(requesturi) ||request.getSession().getAttribute("managerid") != null 
				||"/app-opinion-manager/sendTelCheck".equals(requesturi)||"/app-opinion-manager/selectManagerByPhone".equals(requesturi)||"/app-opinion-manager/getSiteScreen".equals(requesturi)||
				"/app-opinion-manager/updateSiteScreen".equals(requesturi)||"/app-opinion-manager/deploymentController/downloadFile".equals(requesturi)
				||"/app-opinion-manager/solrMatch".equals(requesturi)
				) {
			System.out.println("允许请求："+request.getRequestURI());
			filterChain.doFilter(request, response);
			String userid= "";
			if(user!=null){
				userid = user.getId();
			}
			String ip = "";
			try {
				ip = IpUtil.getIpAddr(httpRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.info("为获取到客户端ip地址");
				log.error("为获取到客户端ip地址"+e.getMessage(),e);
			}
			String requestAddress = request.getRequestURI();
			int status = response.getStatus();
			boolean successed = false;
			if(status==200){
				successed = true;	
			}
			log.info("userid:"+userid+",loginip:"+ip+",requestAddress:"+requestAddress+",successed:"+successed);
		} else {
			System.out.println(request.getRequestURI());
			System.out.println(request.getSession().getId()+"未登录 请求转到登录页面");
				response.sendError(401);
				/*if(user == null){
	                String contextPath = httpRequest.getContextPath();
	                String redirect =  contextPath ;
	                //ajax session 过期处理
	                //1:判断是否是ajax请求
	                if (httpRequest.getHeader("x-requested-with") != null 
	                        && "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("x-requested-with"))) {   
	                    //向http头添加 状态 sessionstatus
	                    httpResponse.setHeader("sessionstatus","timeout");
	                    httpResponse.setStatus(403);
	                    //向http头添加登录的url
	                    httpResponse.addHeader("loginPath", contextPath);
	                    filterChain.doFilter(request, response);
	                   // log.debug("ajax request");
	                    return ;
	                }
	                httpResponse.sendRedirect(redirect);
	                return;
	            }else{
	            	filterChain.doFilter(request, response);
	            }*/
			// response.sendRedirect("/app-opinion-web/user/login.html");
			// request.getRequestDispatcher("/user/login.html").forward(request,
			// response);
			return;
		}
//		filterChain.doFilter(request, response);

	}

}
