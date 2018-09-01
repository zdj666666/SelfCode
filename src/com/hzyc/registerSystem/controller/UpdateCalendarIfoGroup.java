package com.hzyc.registerSystem.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.hzyc.registerSystem.po.RealSchedule;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.impl.UpdateCalendarIfoServiceImp;
import com.hzyc.registerSystem.services.impl.WeekCalendarServiceImp;
import com.hzyc.registerSystem.tools.SystemLog;

/**
 * @author lvgang
 * @desription �޸Ŀγ���Ϣ��
 * 
 * */
@Controller
public class UpdateCalendarIfoGroup {
	
	@Autowired
	UpdateCalendarIfoServiceImp ucisp;
	
	@Autowired
	private WeekCalendarServiceImp wcsi;
	
	@RequestMapping("getCalendarIfoGroup.do")
	@SystemLog(module="�γ̱����",methods="��ȡ�γ̱���Ϣ")
	public void getCalendarIfo(String grade_id,HttpServletRequest request,HttpServletResponse response){
		try {
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			
			//System.out.println(request.getParameter("user_id"));
			String user_id = request.getSession().getAttribute("role").toString();
			System.out.println(user_id+"--------------------------------");
			grade_id=wcsi.getGradeId(grade_id);
			
			List<RealSchedule> rsLsit =  ucisp.getCalIfo("1",grade_id);
			/*for(int i=0;i<rsLsit.size();i++){
				System.out.println(rsLsit.get(i).getClassroom()+"����");
			}*/
			
			PrintWriter out = response.getWriter();
			
			Gson gson =new Gson();
			
			String gsonString  = gson.toJson(rsLsit); 
			
			System.out.println("###"+gsonString);
			
			out.print(gsonString);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/getCalUserIfoGroup.do")
	@SystemLog(module="�γ̱����",methods="��ȡSession���û���Ϣ")
	public void getUserIfo(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String us = request.getSession().getAttribute("role").toString();
			PrintWriter out = response.getWriter();
			
			if(us!=null){
				
				out.print(Integer.parseInt(us));
				
			}else{
				out.print("");
			}
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("updateCalendarIfoGroup.do")
	@SystemLog(module="�γ̱����",methods="���¿γ̱���Ϣ")
	public void updateCalendarIfo(HttpServletResponse response,HttpServletRequest request){
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			

			Users us = (Users)request.getSession().getAttribute("user");
			String eventsData = request.getParameter("eventsData");
			String user_id =""+ us.getId();
			String grade_id = request.getParameter("grade_id");
			
			grade_id=wcsi.getGradeId(grade_id);
			
			System.out.println("��������"+eventsData+"��ʦ"+user_id+"����"+grade_id);
			//��������[{"title":"����[201],��ϸʱ��[16:30-18:30]","start":"2017-11-14"},{"title":"����[101],��ϸʱ��[12:40-14:30]","start":"2017-11-16"}]��ʦ101����1
			
			//��ɾ������������Ϣ
			ucisp.deleteCalIfo(user_id, grade_id);
			System.out.println("ɾ�����");
			//��ʽ������
			List<RealSchedule> rsList = new ArrayList<RealSchedule>();
			
			System.out.println("##"+eventsData);
			eventsData=eventsData.substring(2, eventsData.length()-2);
			System.out.println("$$"+eventsData);
			
			
			String[] ary1 =  eventsData.split("},");
			//int[] aryIdnex =eventsData.indexOf("},{");
			for(int i=0;i<ary1.length;i++){
				
				RealSchedule rs = new RealSchedule();
				
				System.out.println("��һ��"+ary1[i]);
				String[] ary2 = ary1[i].split(":|,");
				for(int j=0;j<ary2.length;j++){
					System.out.println(j+"�ڶ���"+ary2[j]);
				}
				String classroom = ary2[1].substring(4,ary2[1].length()-1);

				String time =""+ ary2[2].substring(5,ary2[2].length())+":"+ary2[3]+":"+ary2[4].substring(0,2);
				
				String date=""+ary2[6].substring(1,ary2[6].length()-1);
				
				System.out.println(classroom+"#"+time+"#"+date);
				
				rs.setClassroom(classroom);
				rs.setDate(date);
				rs.setGradeId(grade_id);
				rs.setTime(time);
				rs.setUserId(user_id);
				
				rsList.add(rs);
			}
			
			String bool = "false";
			
			//��������Ϣ��ӽ�ȥ
			if(!rsList.isEmpty()){
				for(int i=0;i<rsList.size();i++){
					RealSchedule rs = rsList.get(i);
					bool="true";	
					ucisp.insertCalIfo(rs);
				}
			}
				
			
	 		PrintWriter out = response.getWriter();
	 		out.print(bool);
	 		out.flush();
	 		out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
