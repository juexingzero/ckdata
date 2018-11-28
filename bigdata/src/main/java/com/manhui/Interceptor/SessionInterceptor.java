package com.manhui.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.manhui.cacheManage.CacheManager;
import com.manhui.model.BaseUser;

/**
 * @ClassName: SessionInterceptor
 * @description: 添加拦截器
 * @author:	HeJiayan
 * @date Create in 下午5:28:30 2017年11月1日
 * @version: v1.0.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class SessionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//登陆登出不做拦截
		System.out.println(request.getRequestURI()+"******************************");
		if(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/login.action") 
				|| request.getRequestURI().equals("/get_day_all_log") || request.getRequestURI().equals("/admin/login") || request.getRequestURI().equals("/admin/login.action") 
				|| request.getRequestURI().equals("/admin/logout") || request.getRequestURI().equals("/admin/logout.action") 
				|| request.getRequestURI().equals("/dashboard.html") || request.getRequestURI().equals("/unreadMessageCount")
				|| request.getRequestURI().equals("/get_day_all_log") || request.getRequestURI().equals("/mobile_getdata")
				|| request.getRequestURI().equals("/mobile_login") || request.getRequestURI().equals("/mobile_company_show")
				|| request.getRequestURI().equals("/mobile_company_detal") || request.getRequestURI().equals("/mobile_getRegion")
				|| request.getRequestURI().equals("/mobile_company_update") || request.getRequestURI().equals("/mobile_company_save")
				|| request.getRequestURI().equals("/mobile_company_shop_delete") || request.getRequestURI().equals("/mobile_article_show")
				|| request.getRequestURI().equals("/mobile_article_detal") || request.getRequestURI().equals("/mobile_getExpress")
				|| request.getRequestURI().equals("/mobile_article_save") || request.getRequestURI().equals("/mobile_article_update")
				|| request.getRequestURI().equals("/mobile_article_delete") || request.getRequestURI().equals("/mobile_express_show")
				|| request.getRequestURI().equals("/mobile_express_save") || request.getRequestURI().equals("/mobile_simu_login_save")
				|| request.getRequestURI().equals("/mobile_simu_login_show") || request.getRequestURI().equals("/mobile_shop_show")
				|| request.getRequestURI().equals("/mobile_shop_detal") || request.getRequestURI().equals("/mobile_shop_update")
				|| request.getRequestURI().equals("//mobile_category_show") || request.getRequestURI().equals("/mobile_sales_show")
				|| request.getRequestURI().equals("//mobile_sales_detal") || request.getRequestURI().equals("/mobile_sales_save")
				|| request.getRequestURI().equals("//mobile_find_article") || request.getRequestURI().equals("/mobile_shop_save")
				){
			return true;
		}else{
		//判断token是否失效
		//BaseUser user = (BaseUser) CacheManager.getCacheInfo("user").getValue();
			//验证Session是否有效
			HttpSession session = request.getSession();
			if(session.isNew()){
				response.sendRedirect("/login?flag=timeout");
				return false;
			}else{
				return true;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
