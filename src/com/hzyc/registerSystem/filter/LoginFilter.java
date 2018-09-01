package com.hzyc.registerSystem.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hzyc.registerSystem.po.Permission;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.impl.FilterImpl;
import com.hzyc.registerSystem.tools.BeanUtil;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
	
	
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	/*������������Ҫ���˵Ĺ���*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String url = httpRequest.getRequestURI();//�õ����ʵ�·��
		String lastUrl = url.substring(url.lastIndexOf("/") , url.length() );
		Users user = (Users)httpRequest.getSession().getAttribute("user");
		FilterImpl filter = (FilterImpl)BeanUtil.getBean("filterImpl");
		List<Permission> pList = filter.chaAllFunHasPer();
		
		boolean both = true;
		
		for (int i=0; i<pList.size(); i++) {
			String pinjie = "/" + pList.get(i).getResourcePath() ;
			if (pinjie.equals(lastUrl)) {
				both = false;
				break;
			}
		}
		
		if (both) {
			chain.doFilter(request, response);
		} else if (user!=null) {
			boolean flag = false;
			int id = user.getId();
			//��ѯ�����id���ܷ��ʵ�ҳ��
			List<Permission> list = filter.filterSel(id);
			for (int i=0; i<list.size(); i++) {
				String pinjie = "/" + list.get(i).getResourcePath() ;
				if (pinjie.equals(lastUrl)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				chain.doFilter(request, response);
			} else {
				request.setAttribute("sucess", "��û�з��ʸ�ҳ���Ȩ�ޣ�");
				request.setAttribute("url", "login.jsp");
				request.getRequestDispatcher("register_clue.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("sucess", "������ô���ʣ�");
			request.setAttribute("url", "login.jsp");
			request.getRequestDispatcher("register_clue.jsp").forward(request, response);
		}
	}
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}