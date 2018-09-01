package com.hzyc.registerSystem.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.StuMessageService;


@Controller
public class StuMessageController {
	
	@Autowired
	private StuMessageService stuMessageService;
	
	@RequestMapping("/queryStuMessageAll.do")
    public ModelAndView queryStuMessageAll(HttpServletRequest request){
		//System.out.println("����queryStuMessageAll~~~~");
		List<Users> list=stuMessageService.queryStuMessageAll();
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("stuMessageItems",list);
		request.setAttribute("getTiao","1");
		modelAndView.setViewName("stuMessageFrame.jsp");
		return modelAndView;
		
  }
	 
	@RequestMapping("/insertStuMessage.do")
		public String insertStuMessage(Users u,HttpServletRequest request){
		String username=(String) request.getSession().getAttribute("username");
			try {
				boolean result = stuMessageService.insertStuMessage(u);
				if(result){
					return "StuMessage.jsp";	
				}else{
					
					return "";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
			
		}
	
	@RequestMapping("/queryStuMessageByID.do")
	public ModelAndView queryStuMessageByID(String id){
		System.out.println("---------" + id);
		Users f = stuMessageService.queryStuMessageByID(id);
		
		List<Users> rsList = new ArrayList<Users>();
		rsList.add(f);
		System.out.println("---------" + f.getName());
	   // System.out.println("queryStuMessageByID.do��id====="+id);
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("rsList",rsList);
		
		modelAndView.setViewName("update_stuMessage.jsp");
		return modelAndView;
		
		
	}
	
	@RequestMapping("/deleteStuMessageByID.do")
	
	public String deleteStuMessageByID(String id,HttpServletRequest request){
		//System.out.println("����   deleteStuMessageByID!!!!!!" +id);
		
		try {
			boolean result=stuMessageService.deleteStuMessageByID(id);
			if(result){
				//request.setAttribute("message","ɾ���ɹ�!");
				return "queryStuMessageAll.do";	
			}else{
				//request.setAttribute("message","ɾ���ɹ�!");
				return "queryStuMessageAll.do";	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "";
	}
	@RequestMapping("/updateStuMessageByID.do")
	public ModelAndView updateStuMessageByID(String id){
		Users f = stuMessageService.queryStuMessageByID(id);
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("user",f);	
		modelAndView.setViewName("update_stuMessage.jsp");
		return modelAndView;
	}
	@RequestMapping("/updateStuMessageByIDForInsert.do")
	public String  updateStuMessageByIDForInsert(Users u){
		boolean result=stuMessageService.updateStuMessageByIDForInsert(u);
		if(result){
			return "queryStuMessageAll.do";
		}else{
			return "����ʧ�ܣ�����";
		}
		
	}
	@RequestMapping("/queryStuMessageByClassID.do")
	public ModelAndView queryStuMessageByClassID(Users u,HttpServletRequest request){
		List<Users> list=stuMessageService.queryStuMessageByClassID(u);
		
		request.setAttribute("url", "Class.jsp");
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("list",list);
		modelAndView.addObject("url", "Class.jsp");
		modelAndView.addObject("sucess", "û�в�ѯ������");
		//System.out.println("------------>>>>>"+request.getAttribute("sucess").toString());
		modelAndView.setViewName("stuMessageFrame.jsp");
		//System.out.println();
		return modelAndView;
		
	}
}
