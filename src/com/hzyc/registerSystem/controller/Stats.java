package com.hzyc.registerSystem.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hzyc.registerSystem.po.Grade;
import com.hzyc.registerSystem.po.RealSchedule;
import com.hzyc.registerSystem.po.Signing;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.StatsService;
import com.hzyc.registerSystem.tools.SystemLog;

/**
 * @author BIN
 * @date 2017-11-8 ����10:11:01
 */
@Controller
public class Stats {

	@Autowired
	private StatsService statsService;
	
	//��Ŀ��ͳ��ͼ����
	@RequestMapping("/StatsPTWK.do")
	@SystemLog(module="��Ŀ��ͳ�ƹ���",methods="�鿴���ܳ�����")
	public ModelAndView StatsPTWK(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");
		//������ֵ
		String value = new String(request.getParameter("value").getBytes("ISO-8859-1"),"utf-8"); 
		//String value = request.getParameter("value");
		//ʲôͼ
		
		ModelAndView modelAndView = new ModelAndView();
		
		
		
		//�̶���ʽ��Ŀ���������˺�
		List<List<String>> binList = statsService.statsHistogram();
		//��Ŀ���Աid,�������˺�
		List<Users> usersList = statsService.statsPT();
		//����ʱ��
		Date d = new Date();
		
		//ǩ����¼
		List<Signing> list = statsService.selSining(d);
		//��Ŀ��Ӧ���ϰ�ʱ�� ���ڵ�������һ��С�ڵ��ڽ���
		List<RealSchedule> selList = statsService.selPTWT(d);
		
		//ʱ�������
		StatsGrade st = new StatsGrade();
		List<List<String>> doubleList = new ArrayList<List<String>>();
		List<String> fList = null;
		for (int j=0; j<usersList.size(); j++) {
			fList = new ArrayList<String>();
			for (int i=1; i<=list.size(); i++) {
				if (usersList.get(j).getId().equals(list.get(i-1).getUserId())) {
					int day = st.data(list.get(i-1).getSignData());
					String x = "["+day+","+j+",1]";
					fList.add(x);
					
				} 
				
			}
			if(fList.size()<=0){
				String x = "[1,"+j+",0]";
				fList.add(x);
			}
			doubleList.add(fList);
			
			
		}
		
		List<String> sList =new ArrayList<String>();
		String format ="";
		List<String> dList = new ArrayList<String>();
		String dd = "";
		for (int i=0; i<usersList.size(); i++) {
			String name = usersList.get(i).getName();
			if (i+1==usersList.size()) {
				format +="'" + name + "'";
				dd += "d"+""+(i+1)+"";
			} else {
				format += "'" + name + "'" +",";
				dd += "d"+""+(i+1)+",";
			}
		}
		dList.add(dd);
		sList.add(format);
		//������
		List<String> countList = new ArrayList<String>();
		//����
		List<String> binCount = new ArrayList<String>();
		
		//���Ƿ�����Ŀ��
		List<Grade> gList = statsService.judgment("��Ŀ��");
		
		//û����Ŀ�黹��ë��
		boolean flag = false;
		
		if (gList.size()!=0 && selList.size()!=0) {
			flag = true;
		}
		for(int i=0;i<usersList.size();i++){
			int count = 0;
			for(int j=0;j<list.size();j++){
				if(usersList.get(i).getId().equals(list.get(j).getUserId())){
					count++;
				}
			}
			double x = (double)count/(selList.size());
			countList.add(i, x+"");
			binCount.add(i, count+"");
		}
		//��ʾ��״ͼ��
		boolean sign = false;
		//��ʾ���ߵ�
		boolean result = false;
		
		if (flag) {
			if (value.equals("��״ͼ")) {
				sign = true;
				modelAndView.addObject("binList",binList);
				modelAndView.addObject("usersList",usersList);
				modelAndView.addObject("list",list);
				modelAndView.addObject("selList",selList);
				modelAndView.addObject("countList",countList);
				modelAndView.addObject("binCount",binCount);
				modelAndView.setViewName("statistics_projectteam_week.jsp?sign="+sign+"&result="+result+"&click="+0+"");
			} else if (value.equals("����ͼ")) {
				result = true;
				modelAndView.addObject("binList",binList);
				modelAndView.addObject("usersList",usersList);
				modelAndView.addObject("list",list);
				modelAndView.addObject("selList",selList);
				modelAndView.addObject("countList",countList);
				modelAndView.addObject("binCount",binCount);
				modelAndView.setViewName("statistics_projectteam_week.jsp?sign="+sign+"&result="+result+"&click="+0+"");
			} else if (value.equals("ʱ����")) {
				
				modelAndView.addObject("dList",dList);
				modelAndView.addObject("sList",sList);
				modelAndView.addObject("doubleList",doubleList);
				modelAndView.setViewName("stats_pt_week_time.jsp");
				
			} else {
				request.setAttribute("sucess", "�����ˣ��������Ա��ϵ");
				request.setAttribute("url", "stats_no.jsp");
				modelAndView.setViewName("register_clue.jsp");
			}
		} else {
			request.setAttribute("sucess", "����û�ΰ���");
			request.setAttribute("url", "stats_no.jsp");
			modelAndView.setViewName("register_clue.jsp");
		}
		
		return modelAndView;
	}
	
	//��Ŀ��ͳ��ͼ��һ�ܴ���
	@RequestMapping("/left.do")
	@SystemLog(module="��Ŀ��ͳ�ƹ���",methods="�鿴���ܳ�����")
	public ModelAndView left(HttpServletRequest request) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		
		request.setCharacterEncoding("utf-8");
		String sign = new String(request.getParameter("sign").getBytes("ISO-8859-1"),"utf-8"); 
		
		String result = "false";
		if (sign.equals("true")) {
			
		} else {
			result = "true";
		}
		String left = new String(request.getParameter("left").getBytes("ISO-8859-1"),"utf-8"); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(left);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��Ŀ��Ӧ���ϰ�ʱ�� ���ڵ�������һ��С�ڵ��ڽ���
		List<RealSchedule> selList = statsService.left(date);
		
		//����Ŀ�鿪��ʱ��
		List<Grade> gList = statsService.judgment("��Ŀ��");
		
		String click = "2";
		
		if (selList.get(0).getDate().compareTo(gList.get(0).getClassData())<=0) {
        	click = "1";
        } else {
        }
		
		//�̶���ʽ��Ŀ���������˺�
		List<List<String>> binList = statsService.statsHistogram();
		//��Ŀ��id,�������˺�
		List<Users> usersList = statsService.statsPT();
		//ǩ����¼
		List<Signing> list = statsService.selSiningNo(date);
		System.out.println(list.size());
		//������
		List<String> countList = new ArrayList<String>();
		//����
		List<String> binCount = new ArrayList<String>();
		
		for(int i=0;i<usersList.size();i++){
			int count = 0;
			for(int j=0;j<list.size();j++){
				if(usersList.get(i).getId().equals(list.get(j).getUserId())){
					count++;
				}
			}
			double x = (double)count/(selList.size());
			countList.add(i, x+"");
			binCount.add(i, count+"");
		}
		
		modelAndView.addObject("binList",binList);
		modelAndView.addObject("usersList",usersList);
		modelAndView.addObject("list",list);
		modelAndView.addObject("selList",selList);
		modelAndView.addObject("countList",countList);
		modelAndView.addObject("binCount",binCount);
		modelAndView.setViewName("statistics_projectteam_week.jsp?sign="+sign+"&result="+result+"&click="+click+"");
		
		return modelAndView;
	}
	
	//��Ŀ��ͳ��ͼ��һ�ܴ���
	@RequestMapping("/right.do")
	@SystemLog(module="��Ŀ��ͳ�ƹ���",methods="�鿴���ܳ�����")
	public ModelAndView right(HttpServletRequest request) throws UnsupportedEncodingException{
		
		
		request.setCharacterEncoding("utf-8");
		String sign = new String(request.getParameter("sign").getBytes("ISO-8859-1"),"utf-8"); 
		
		
		ModelAndView modelAndView = new ModelAndView();
		String result = "true";
		if (sign.equals("false")) {
			
		} else {
			result = "false";
		}
		String left = new String(request.getParameter("left").getBytes("ISO-8859-1"),"utf-8"); 
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(left);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��Ŀ���ϰ�ʱ�� ���ڵ�������һ+7 С�ڵ�һ����һ+14
		List<RealSchedule> selList = statsService.right(date);
		
		//������ǩ����¼
		List<Signing> gList = statsService.selAll();
		
		String click = "2";
		
		if (selList.get(selList.size()-1).getDate().compareTo(gList.get(0).getSignData())>=0) {
        	click = "0";
        } else {
        }
		
		//�̶���ʽ��Ŀ���������˺�
		List<List<String>> binList = statsService.statsHistogram();
		//��Ŀ��id,�������˺�
		List<Users> usersList = statsService.statsPT();
		//ǩ����¼
		List<Signing> list = statsService.selSiningYes(date);
		//������
		List<String> countList = new ArrayList<String>();
		//����
		List<String> binCount = new ArrayList<String>();
		
		
		for(int i=0;i<usersList.size();i++){
			int count = 0;
			for(int j=0;j<list.size();j++){
				if(usersList.get(i).getId().equals(list.get(j).getUserId())){
					count++;
				}
			}
			double x = (double)count/(selList.size());
			countList.add(i, x+"");
			binCount.add(i, count+"");
		}
		
		modelAndView.addObject("binList",binList);
		modelAndView.addObject("usersList",usersList);
		modelAndView.addObject("list",list);
		modelAndView.addObject("selList",selList);
		modelAndView.addObject("countList",countList);
		modelAndView.addObject("binCount",binCount);
		modelAndView.setViewName("statistics_projectteam_week.jsp?sign="+sign+"&result="+result+"&click="+click+"");
		
		return modelAndView;
	}
	
	
}
