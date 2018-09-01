package com.hzyc.registerSystem.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hzyc.registerSystem.po.Permission;
import com.hzyc.registerSystem.po.UserRole;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.AdminService;
import com.hzyc.registerSystem.tools.LOCALMAC;

/**
 * @author ���ٸ� 
 * ����Ա��controllers
 * 
 */
@Controller
public class AdminCon {
	
	
	@Autowired
	AdminService as;
	
	//�жϵ�¼�Ƿ�ɹ�
	@RequestMapping("/login.do")
	public void selectUser(HttpServletRequest request,HttpServletResponse response){
		
		try{
			
			boolean trueUer=false;//�жϵ�¼�û��Ƿ���ȷ
			response.setHeader("Content-Type", "text/html; charset=UTF-8");//���÷�������
			PrintWriter out = response.getWriter();
			
			Users u = new Users();
			String code = request.getParameter("code");
			String password = request.getParameter("password");
			System.out.println(""+code+""+password);
			u.setCode(code);
			u.setPassword(password);
			Users user = as.login(u);
			
			if(user!=null&&user.getPassword().equals(password)){
				request.getSession().setAttribute("username", user.getCode());
				request.getSession().setAttribute("user", user);
				//��ѯ�û���Ӧ��Ȩ��
				String role = as.selRoleByUserId(user.getId());
				//��Ȩ�޷���session����
				request.getSession().setAttribute("role", role);
				String imgName = user.getPictureName();
				String realPath = request.getSession().getServletContext().getRealPath("/");    
				System.out.println(realPath+"====");
				String filename = realPath + "photo/" + imgName;
				File file = new File(filename);
				if(file.exists()){
					//���������ڸ�ͼƬ ������
				}else{
					//�����ݿ�ͼƬ�ŵ���������
					byte[] img = user.getPicture();
					FileOutputStream fos = null;
					try {
						fos =  new FileOutputStream(filename);
						fos.write(img);
						System.out.println("д��....");
					} catch ( Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						if(fos != null){
							fos.close();
						}
					}
				}
				trueUer=true;//����Ϊ��
			}else{
				trueUer=false;
			}
			
			out.print(trueUer);//������ݵ�Ŀ��ҳ��
			out.flush();//ˢ��
			out.close();//�ر���Դ
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@RequestMapping("sureUser.do")
	public String login(HttpServletRequest request) throws Exception{
		Users user = (Users)request.getSession().getAttribute("user");
		if(user !=null){
			List<Permission> pList = user.getpList();
			
			if(user.getMac()==null || user.getMac().equals("")){
				request.setAttribute("pList",pList);
				request.setAttribute("user",user);
				return "index.jsp";
			}else{
				LOCALMAC getMAc = new LOCALMAC();
			    String clientIp = getMAc.getClientIp(request);
				String mac=getMAc.getMac(clientIp);
				System.out.println(mac+"----------------");
			if(user.getMac().equalsIgnoreCase(mac)){
				request.setAttribute("pList",pList);
				request.setAttribute("user",user);
				return "index.jsp";
			}else{
				request.setAttribute("sucess", "mac��ַ����");
				request.setAttribute("url", "login.jsp");
				return "register_clue.jsp";
			}
		}
				
		}else{
			
		}
		request.setAttribute("sucess", "mac��ַ����");
		request.setAttribute("url", "login.jsp");
		return "register_clue.jsp";
	}
	
	@RequestMapping("destorySessionclue.do")
	public String destorySessionclue(HttpServletRequest request){
		request.setAttribute("sucess", "ȷ��ע����¼��");
		request.setAttribute("url", "destorySession.do");
		request.setAttribute("cancel", "sureUser.do");
		return "register_clue1.jsp";
	}
	
	@RequestMapping("destorySession.do")
	public String destorySession(HttpServletRequest request){
		request.getSession().invalidate();
		return "login.jsp";
	}
	
	@RequestMapping("register.do")
	public String register(Users u,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String role = request.getParameter("role");
		String code = request.getParameter("code");
		String password = request.getParameter("password");
		String mac = request.getParameter("mac");
		u.setCode(code);
		u.setPassword(password);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		String date = df.format(new Date());
		u.setRegisterTime(date);
		if(mac!=null && !mac.equals("")){
			u.setMac(mac);
		}else{	
		}
		if(role !=null && role.equals("3")){
			u.setClassId("��Ŀ��");
		}
		String url = request.getHeader("Referer");
		//Ҫ��ת�Ľ���
		String uri = url.substring(url.lastIndexOf("/")+1);
		System.out.println("---");
		int flag = as.register(u);
		if(flag !=0){
			Users afterUser = as.selUserId(u.getCode());
			UserRole ur = new UserRole();
			ur.setRoleId(Integer.parseInt(role));
			ur.setUserId(afterUser.getId());
			//�����û���ɫ��
			int sucess = as.insertUserRole(ur);
			if(sucess !=0){
				request.setAttribute("sucess", "���Ա���ɹ�");
				if(uri.equals("registerEmployees.do")){
					request.setAttribute("url", "register_top.jsp");
				}else{
					request.setAttribute("url", "");
				}
				return "register_clue.jsp";
			}else{
				request.setAttribute("sucess", "���Ա��ʧ��");
				request.setAttribute("url", uri);
				return "register_clue.jsp";
			}
		}else{
			request.setAttribute("sucess", "���Ա��ʧ��");
			request.setAttribute("url", uri);
			return "register_clue.jsp";
		}
	}
	@RequestMapping("student_check.do")
	public ModelAndView studentCheck(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String code = (String)request.getSession().getAttribute("username");
		List<Users> uList = as.selStudentNoRegister(code);
		mav.addObject("uList",uList);
		mav.addObject("isFirst","1");
		mav.setViewName("student_check.jsp");
		return mav;
	}
	
	@RequestMapping("student_checkForSel.do")
	public ModelAndView studentCheckForSel(HttpServletRequest request,Users u) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		
		Users nowLoginUser = (Users) request.getSession().getAttribute("user");
		u.setId(nowLoginUser.getId());
		List<Users> uList = as.studentCheckForSel(u);
		System.out.println("uList�Ĵ�С��" +uList.size());
		mav.addObject("uList",uList);
		mav.setViewName("student_check_frame.jsp");
		mav.addObject("isFirst","0");
		
		return mav;
	}
	@RequestMapping("accept.do")
	public String accept(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		//ͬ������ˣ���user_role �������������
		UserRole ur = new UserRole();
		ur.setUserId(Integer.parseInt(id));
		ur.setRoleId(2);
		System.out.println(ur.getId());
		//1��Ҫ��user_role����������� 2���������ݿ�����remark��״̬Ϊ1
		int sucess1 = as.updateRemark(Integer.parseInt(id));
		int sucess = as.insertUserRole(ur);
		
		if(sucess != 0 && sucess1 !=0){
			request.setAttribute("sucess", "��˳ɹ�");
			request.setAttribute("url", "student_check.do");
			return "register_clue.jsp";
		}else{
			//���ʧ��
			request.setAttribute("sucess", "���ʧ��");
			request.setAttribute("url", "student_check.do");
			return "register_clue.jsp";
		}
		
	}
	
	@RequestMapping("deny.do")
	public String deny(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		//�ܾ���ѧԱ�����û�����ɾ�����ѧԱ
		int sucess = as.delUser(Integer.parseInt(id));
		if(sucess != 0){
			//���ʧ��
			request.setAttribute("sucess", "ɾ���ɹ�");
			request.setAttribute("url", "student_check.do");
			return "register_clue.jsp";
		}else{
			
		}
		//���ʧ��
		request.setAttribute("sucess", "ɾ��ʧ��");
		request.setAttribute("url", "student_check.do");
		return "register_clue.jsp";
	}
	
	@RequestMapping("/ajaxUser.do")
	public void ajaxUser(HttpServletRequest request,HttpServletResponse response){
		
		try {
			Date date = new Date();
			int year = date.getYear()-100+2000;
			
			List<Users> list = as.selBoth();
			int id = list.get(0).getId();
			String username = id+1+""+year;
			//ʹ�����ķ�ʽ����Ϣ���ظ�ҳ��
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.print(username);
			
			//ǿ��ˢ�º͹ر�
			writer.flush();
			writer.close();
			
			//List<Map<String,String>> rsList = null;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ��ѯԱ��
	 * @return
	 */
	@RequestMapping("selEmployees.do")
	public String selEmployees(HttpServletRequest request,String name,String remark){
		Users u = new Users();
		u.setName(name);
		u.setRemark(remark);
		//��ѯԱ���ķ���
		List<Users> uList = as.selEmployees(u);
		request.setAttribute("uList",uList);
		return "register_bottom.jsp";
	}
	@RequestMapping("delEmployeesclue.do")
	public String delEmployeesclue(HttpServletRequest request){
		int id = (Integer.parseInt(request.getParameter("id")));
		request.setAttribute("sucess", "ȷ��ɾ��Ա����");
		request.setAttribute("url", "delEmployees.do?id="+id);
		request.setAttribute("cancel", "selEmployees.do");
		return "register_clue1.jsp";
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping("delEmployees.do")
	public String delEmployees(HttpServletRequest request){
		int id = (Integer.parseInt(request.getParameter("id")));
		//ɾ��Ա���ķ���
		int sucess = as.delUser(id);
		int sucess1 = as.delUserRole(id);
		if(sucess+sucess1 !=0){
			request.setAttribute("sucess", "Ա���ѳɹ�ɾ��");  
			request.setAttribute("url", "selEmployees.do");
			return "register_clue.jsp";
		}else{
			request.setAttribute("sucess", "Ա��ɾ��ʧ��");
			request.setAttribute("url", "selEmployees.do");
			return "register_clue.jsp";
		}
		
	}
	
	@RequestMapping("registerEmployees.do")
	public String registerEmployees(HttpServletRequest request){
		//����ע��Ա���Ľ���
		return "register_employees_new.jsp";
	}
}
