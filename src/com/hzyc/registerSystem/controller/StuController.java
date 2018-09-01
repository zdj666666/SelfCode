package com.hzyc.registerSystem.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hzyc.registerSystem.po.RealSchedule;
import com.hzyc.registerSystem.po.SelectSigning;
import com.hzyc.registerSystem.po.Signing;
import com.hzyc.registerSystem.po.UserRole;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.StuService;
import com.hzyc.registerSystem.services.emPowerService;
import com.hzyc.registerSystem.tools.LOCALMAC;

@Controller
public class StuController {
	@Autowired
	StuService  stuService;
	@Autowired
	emPowerService emPowerService;
	
	@RequestMapping("/stuRegister.do")
	public String insertByuser(HttpServletRequest request,Users users, MultipartFile caseImage,HttpSession session){
	try {
		//ͼƬ����
	byte[] picFile=caseImage.getBytes();
	users.setPicture(picFile); 
		//��ȡͼƬ����
	    String picNameCode=users.getCode();
		String pictureName = picNameCode+caseImage.getOriginalFilename();
		//////System.out.println("�ϴ��ļ���ͼƬ����"+pictureName);	
		String tomcatPath = session.getServletContext().getRealPath("/photo");
		//////System.out.println(tomcatPath);
		String finalPath = tomcatPath+"\\" + pictureName;
		//////System.out.println("ͼƬ·��"+finalPath);
		//��ͼƬ����ֵ����������ӵ����ݿ���
		users.setPictureName(pictureName);
		//��ͼƬд���������ϡ�
		File target = new File(finalPath);
		caseImage.transferTo(target);
				
	    LOCALMAC getMAc=new	LOCALMAC();
	    String clientIp = getMAc.getClientIp(request);
	   ////System.out.println(clientIp+"��1111111111111111111111111");
		String mac=getMAc.getMac(clientIp);
		users.setMac(mac);
		//////System.out.println("��������Ҫ��ȡ��mac��ַ��"+macAddress);
		boolean result=stuService.insert(users);
		//result=true��ӳɹ�,�ص�index.jsp,����ص�ע��ҳ��
		if(result){
			request.setAttribute("sucess", "ע��ɹ�");
			request.setAttribute("url", "login.jsp");
			return "register_clue.jsp";
			
		}else{
			request.setAttribute("sucess", "ע��ʧ��");
			request.setAttribute("url", "stuRegister.jsp");
			return "register_clue.jsp";
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		return "stuRegister.jsp";	
	}
	
	//return "login.jsp";
		
	}
	
	//��ѯѧ������������Ϣ
	@RequestMapping("/selectByCode.do")
	public 	ModelAndView  selectByCode(String code,HttpServletRequest request,Users users){
		    //String codeValue=session.getAttribute("code").toString();
		Users u = (Users) request.getSession().getAttribute("user");
		
		//////System.out.println("----------->"+u.getCode());
			Users stuUsersInfo=stuService.selectByCode(u.getCode());
		
			ModelAndView modelAndView=new ModelAndView();
			////System.out.println(u.getId()+"���Id��ʲô��");
			String getPower=emPowerService.JudgeUserHas(u.getId());
			////System.out.println(getPower+"��������Ŷ���ϵ������������������������������������������������");
			request.setAttribute("getSureShow",getPower);
			request.getSession().setAttribute("role",getPower);
			modelAndView.addObject("stuUsersInfo", stuUsersInfo);
			modelAndView.setViewName("stuInfoManager.jsp");
			return modelAndView;	
			}
	
	@RequestMapping("/insert_class.do")
	public ModelAndView insert_class(HttpServletRequest request){
		ModelAndView Model=new ModelAndView();
		try{
			Users u = (Users) request.getSession().getAttribute("user");
			request.setAttribute("UserCode",u.getId());
			Model.setViewName("insert_class.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return Model;
	}
	
	
	//��ѯѧ������������Ϣ
	@RequestMapping("/selectCode.do")
	public 	ModelAndView  selectCode(HttpServletRequest request){
			Users u = (Users) request.getSession().getAttribute("user");
			Users stuUsersInfo=stuService.selectByCode(u.getCode());
			////System.out.println("------------>"+u.getCode());
			////System.out.println(u.getId()+"���Id��ʲô��");
			String getPower=emPowerService.JudgeUserHas(u.getId());
		//////System.out.println(u.getPictureName()+"---------------");
			request.setAttribute("getSureShow",getPower);
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("stuUsersInfo", stuUsersInfo);
			modelAndView.setViewName("stuInfoSignning.jsp");
			return modelAndView;	
			}
	//�޸�ѧ����Ϣ
	@RequestMapping("/updateStuInfo.do")
	public String updateByPrimaryKeySelective( Users users,MultipartFile caseImage,HttpSession session,HttpServletRequest request){
		try {
			byte[] picFile= null;
			picFile=caseImage.getBytes();
			if(caseImage.getOriginalFilename().equals("")){
				Users us = 	(Users)request.getSession().getAttribute("user");
				picFile = us.getPicture();
			}else{
				picFile=caseImage.getBytes();
			}
			////System.out.println(caseImage.getOriginalFilename()+"-------------------------");
			users.setPicture(picFile); 
			 String picNameCode=users.getCode();
				String pictureName = picNameCode+caseImage.getOriginalFilename();
				//////System.out.println("�ϴ��ļ���ͼƬ����"+pictureName);	
				String tomcatPath = session.getServletContext().getRealPath("/photo");
				Users u = (Users)request.getSession().getAttribute("user");
				String finalPath = tomcatPath+"\\" + u.getPictureName();
				//////System.out.println("ͼƬ·��"+finalPath);
				//��ͼƬ����ֵ����������ӵ����ݿ���
				File file1 = new File(finalPath);
				////System.out.println(finalPath);
				file1.delete();
				users.setPictureName(pictureName);
				
			stuService.updateByPrimaryKeySelective(users);
			request.getSession().invalidate();
			request.getSession().setAttribute("user",users);
			request.getSession().setAttribute("username", users.getCode());
			String imgName = users.getPictureName();
			String realPath = request.getSession().getServletContext().getRealPath("/");    
			//////System.out.println(realPath+"====");
			String filename = realPath + "photo/" + imgName;
			File file = new File(filename);
			if(file.exists()){
				//���������ڸ�ͼƬ ������
			}else{
				//�����ݿ�ͼƬ�ŵ���������
				byte[] img = users.getPicture();
				FileOutputStream fos = null;
				try {
					fos =  new FileOutputStream(filename);
					fos.write(img);
					////System.out.println("д��....");
				} catch ( Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(fos != null){
						fos.close();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "selectByCode.do";
	
	}
	
	//deleteByIdɾ����Ϣͨ��Id
	@RequestMapping("/deleteStuInfo.do")
	public String deleteById(String id){
		stuService.deleteById(id);
		return "login.jsp";
	
	}
	
	//��ѯ�α��ж��Ƿ�ٵ�selectReal_Schedule.do
	@RequestMapping("/selectReal_Schedule.do")
	public void selectTimeByDate(RealSchedule realSchedule,HttpServletResponse response,HttpServletRequest  request){
			  String date= request.getParameter("date");
			  //String classroom= request.getParameter("classroom");
			  //realSchedule.setClassroom(classroom);
			  //���ݰ༶ID��ѯʱ��
			 Users u = (Users) request.getSession().getAttribute("user");
			 String gradeId=request.getSession().getAttribute("gradeId").toString();
			 realSchedule.setGradeId(gradeId);
			  //////System.out.println("----------�༶-----"+u.getClassId());
			  realSchedule.setDate(date);
		try {
			String selectTime=stuService.selectTimeByDate(realSchedule);
			//////System.out.println("--------�鵽��ʱ��------"+selectTime);
			response.setCharacterEncoding("utf-8");
			//��������ʽ����ֵ
			PrintWriter writer=response.getWriter();
			writer.print(selectTime);
			writer.flush();
			writer.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	//���ݰ�Ų�ѯgrade�õ�grade_id    SELECT id FROM grade WHERE class_id='�ڷ�2��';
	//�����SignningMapper
	@RequestMapping("/selectGradeId.do")
	public ModelAndView selectGradeId(String classId,HttpServletRequest  request){
		Users u = (Users) request.getSession().getAttribute("user");
		classId=u.getClassId();
		String gradeId=stuService.selectGradeId(classId);
		request.getSession().setAttribute("gradeId", gradeId);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("newSignning.jsp");
		//modelAndView.setViewName("signTest.jsp");
		 return modelAndView;		
	}
	                                    
	                                    
	//ǩ�����״̬�����ݿ�ǩ����
	@RequestMapping("/insertSignning.do")
	public String insertSignning( Signing signing,HttpServletRequest request){
		//////System.out.println("-------------�ύ���ɹ�-----------");
		Users u = (Users) request.getSession().getAttribute("user");
		//////System.out.println("----userid------->"+u.getId());
		signing.setUserId(u.getId());
		String sates=signing.getState().toString();
		String info="";
		if(sates.equals("1")){
		 info="��ϲ�㣡׼ʱǩ��";
		}else{
		info="���ѳٵ����´����";	
		}
		/*System.out.println("-----------״̬-----"+sates);*/
		//////System.out.println(signing.getState());
		try {
			boolean result=stuService.insertSigning(signing);
			if(result){
				request.setAttribute("sucess", "ǩ���ɹ���"+info);
				request.setAttribute("url", "newSignning.jsp");
				return "register_clue.jsp";
				/*return "newSignning.jsp";*/
				
			}else{
				
				request.setAttribute("sucess", "ǩ��ʧ��");
				request.setAttribute("url", "newSignning.jsp");
				return "register_clue.jsp";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "newSignning.jsp";
		
	}
	
	//��ѯ���֤���Ƿ����
	@RequestMapping("/selectIdNumber.do")
	public void selectIdNumber(String idNumber,HttpServletResponse response,HttpServletRequest  request){
	try {
		String result="";
		response.setCharacterEncoding("utf-8");
		String selectResult=stuService.selectIdNumber(idNumber);
		if(selectResult!=null){
			result="NO";	
		}else{
			result="YES";
		}
		
		//��������ʽ����ֵ
		PrintWriter writer=response.getWriter();
		writer.print(result);
		writer.flush();
		writer.close();	
	} catch (Exception e) {
		e.printStackTrace();
	}

}
	
//��ѯ����ǩ�����
	@RequestMapping("/selectAction.do")
	public ModelAndView	selectState(SelectSigning selectSigning,HttpServletRequest request){
		//--------------------------------
		//Date��:���Ի�ȡ��ǰ��ϵͳʱ��
		Date d = new Date();
		//////System.out.println("ϵͳʱ���ʱ��---------->"+d);
		//��ʽ��ʱ��:2017-05-18 19:33:00
		//�����ڸ�ʽ��������:������ָ����Ҫ�ĸ�ʽ
		//MM��ʾ�·ݣ�mm��ʾ���ӣ�HH��ʾ24Сʱ�ƣ�hh��ʾ12Сʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = sdf.format(d);
		//////System.out.println("ת��ʱ���ʱ��yyyy-MM-dd---------->"+endDate);
		
		//2017��05��18�գ�����53��֮����Ǽ��¼��š�
		//ԭ������ǰ��ʱ�仯Ϊ������������53��ĺ���������ӣ�֮��ת��Ϊ����
		//1000���� = 1��
		//��������ʱ�䣺��1970��0��Ϊ��ʼ�����ڵ�ʱ��ĺ���
		long dmMinutes = d.getTime();
		//////System.out.println("ϵͳʱ���ʱ��---------->+dmMinutes:"+dmMinutes);
		long after = 30 * 24 * 60 * 60 * 1000L;
		//////System.out.println("ϵͳʱ��֮���ʱ��---------->"+after);
		long sum = dmMinutes - after;
		
		//�Ѻ�����ת��ΪDate�ֱ࣬�ӽ�����д��()��
		Date later = new Date(sum);
		//////System.out.println("����ʱ��---------->"+later);
		String startDate = sdf.format(later);
		//////System.out.println("���ת����ʱ��---------->"+startDate);
		//-----------------------------
		Users u = (Users) request.getSession().getAttribute("user");
		selectSigning.setUserId(u.getId().toString());
		selectSigning.setStartDate(startDate);
		selectSigning.setEndDate(endDate);
		//////System.out.println("----aaaauserid------->"+selectSigning.getUserId());
		List<Signing> signingState=stuService.selectState(selectSigning);
		int inTimeCount=0;
		int noTimeCount=0;
		int leave=0;
		for(int i=0;i<signingState.size();i++){
			Signing signingValue=signingState.get(i);
			String state=signingValue.getState().toString();
			if(state.equals("1")){
				inTimeCount++;	
			}else if(state.equals("2")){
				noTimeCount++;
			}else{
				leave++;
				
			}
			
		}
	
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("signingState", signingState);
		modelAndView.addObject("inTimeCount",inTimeCount);
		modelAndView.addObject("noTimeCount",noTimeCount);
		modelAndView.addObject("leave",leave);
		modelAndView.setViewName("stuState.jsp");
		//modelAndView.setViewName("newStuSate.jsp");
		return modelAndView;
		
	}
	//�ж��Ƿ��Ѿ�ǩ����Singnning��----------
	@RequestMapping("/selectStateById.do")
	public void	selectStateById(Signing signing,HttpServletResponse response,HttpServletRequest  request){
		try {
			Users u = (Users) request.getSession().getAttribute("user");
			////System.out.println("----��Ҫǩ���˵�Id-----"+u.getId());
			////System.out.println("----����----"+signing.getSignData());
			signing.setUserId(u.getId());
			String result="";
			response.setCharacterEncoding("utf-8");
			String selectResult=stuService.selectStateById(signing);
			////System.out.println("----�����������״̬-----"+selectResult);
			if(selectResult!=null){
				result="NO";	
			}else{
				result="YES";
			}
			
			//��������ʽ����ֵ
			PrintWriter writer=response.getWriter();
			writer.print(result);
			writer.flush();
			writer.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//ȫ��ǩ��������
	@RequestMapping("/selectSignningAll.do")
	public void selectSignDateById(HttpServletResponse response,HttpServletRequest  request){
			 Users u = (Users) request.getSession().getAttribute("user");
			 String userId=u.getId().toString();
			 ////System.out.println("-------�޸���ɫ��userId--------"+userId);
		try {
			List<String> selectDate=stuService.selectSignDateById(userId);
			for(int i=0;i<selectDate.size();i++){	
				String dd[]=selectDate.get(i).split("-");
				for(int j=0;j<dd.length;j++){
					
						//��1-9,�첻��1-9
					if((dd[1].startsWith("0"))&&(!(dd[2].startsWith("0")))){
						
						String aa=dd[0]+"-"+dd[1].substring(1)+"-"+dd[2];
						selectDate.set(i,aa);
						//�²���1-9,��1-9
					}else if((!(dd[1].startsWith("0")))&&(dd[2].startsWith("0"))){
						String aa=dd[0]+"-"+dd[1]+"-"+dd[2].substring(1);
						selectDate.set(i,aa);
						//��1-9,��1-9
					}else if((dd[1].startsWith("0"))&&(dd[2].startsWith("0"))){
						String aa=dd[0]+"-"+dd[1].substring(1)+"-"+dd[2].substring(1);
						selectDate.set(i,aa);
					}else{
						String aa=dd[0]+"-"+dd[1]+"-"+dd[2];
						selectDate.set(i,aa);
					}
					
				}
				//System.out.println("aaaaaaa---------"+selectDate.get(i));
			
			}
			//System.out.println("--------�޸���ɫ�Ĳ鵽������------"+selectDate);
			response.setCharacterEncoding("utf-8");
			//��������ʽ����ֵ
			PrintWriter writer=response.getWriter();
			
			Gson gson = new Gson();
			String gsonString = gson.toJson(selectDate);
			//System.out.println("----------�޸���ɫ�Ĳ鵽������ת��json-----"+gsonString+"json");
			writer.print(gsonString);
			writer.flush();
			writer.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
