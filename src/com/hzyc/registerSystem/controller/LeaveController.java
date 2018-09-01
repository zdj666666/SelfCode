package com.hzyc.registerSystem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hzyc.registerSystem.po.LeaveThing;
import com.hzyc.registerSystem.po.Signing;
import com.hzyc.registerSystem.po.UserRole;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.LeaveService;


@Controller
public class LeaveController {
	
	
	@Autowired
	private LeaveService leaveService;
	
	boolean result = false;
	
	List<Users> uList;
	/**
	 * ��ѯ������ټ�¼
	 * 
	 * @author ��ǿ
	 * */
	@RequestMapping("/searchLeave.do")
	public ModelAndView searchLeave(HttpServletResponse response){
		
		ModelAndView modelAndView = new ModelAndView();
		try {
			
			List<LeaveThing> tList = leaveService.searchLeave();
			modelAndView.addObject("tList",tList);
			modelAndView.setViewName("searchLeave.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
		@RequestMapping("/searchLeaveForPro.do")
		public ModelAndView searchLeaveForPro(HttpServletResponse response){
		
			ModelAndView modelAndView = new ModelAndView();
			try {
				
				List<LeaveThing> tList = leaveService.searchLeaveForPro();
				modelAndView.addObject("tList",tList);
				modelAndView.setViewName("searchLeaveForPro.jsp");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return modelAndView;
		}
	/**
	 * ��ѯһ����ټ�¼��ͨ��id��ѯ��
	 * @author ��ǿ
	 * @param id ��ٱ�id
	 * 
	 * */
	@RequestMapping("/selectLeaveById.do")
	public ModelAndView selectLeaveById(String id,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			LeaveThing leaveThing = leaveService.selectLeaveById(id);
		
			modelAndView.addObject("LeaveThing", leaveThing);
			
			modelAndView.setViewName("updateLeave.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 * ��ѯѧԱ��Ϣ��ͨ��classId��ѯ��
	 * @author ��ǿ
	 * @param classId �༶��
	 * 
	 * */
	@RequestMapping("/selectStuByClass.do")
	public ModelAndView selectForLeaveByClass(Users users,HttpServletRequest request,HttpServletResponse response,String classId,String dimSelect){
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.out.println(classId);
			System.out.println(dimSelect);
			if(classId.equals("��ѡ��༶") && dimSelect.equals("")){
				request.setAttribute("sucess", "��������Ҫ���ҵ���Ϣ��");
				request.setAttribute("url", "iframe.jsp");
				modelAndView.setViewName("register_clue.jsp");
			}else if(classId.equals("��ѡ��༶") && !dimSelect.equals("")){
				List<Users> uList = leaveService.dimSelectForLeave(dimSelect);
				
				modelAndView.addObject("uList", uList);
				if(uList.size() > 0){
					modelAndView.setViewName("iframe.jsp?classId="+classId+"");
				}else{
					
					request.setAttribute("sucess", "û����Ҫ���ҵ���Ϣ��");
					request.setAttribute("url", "iframe.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
			}else if(!classId.equals("��ѡ��༶") && dimSelect.equals("")){
				
				List<Users> uList = leaveService.selectForLeave(classId);
				modelAndView.addObject("uList", uList);
				if(uList.size() > 0){
					modelAndView.setViewName("iframe.jsp");
				}else{
					
					request.setAttribute("sucess", "û����Ҫ���ҵ���Ϣ��");
					request.setAttribute("url", "iframe.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
			}else if(!classId.equals("��ѡ��༶") && !dimSelect.equals("")){
				List<Users> uList = leaveService.selectStuByClassDim(users);
				modelAndView.addObject("uList", uList);
				System.out.println(uList.size());
				if(uList.size() > 0){
					modelAndView.setViewName("iframe.jsp?classId="+classId+"");
				}else{
					
					request.setAttribute("sucess", "û����Ҫ���ҵ���Ϣ��");
					request.setAttribute("url", "iframe.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/selectForPro.do")
	public ModelAndView selectForPro(Users users,HttpServletRequest request,HttpServletResponse response,String classId,String dimSelect){
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.out.println("===="+dimSelect);
			if(dimSelect.equals("")){
				request.setAttribute("sucess", "��������Ҫ���ҵ���Ϣ��");
				request.setAttribute("url", "iframeForPro.jsp");
				modelAndView.setViewName("register_clue.jsp");
			}else{
				List<Users> uList = leaveService.dimSelectForPro(dimSelect);
				
				modelAndView.addObject("uList", uList);
				if(uList.size() > 0){
					modelAndView.setViewName("iframeForPro.jsp");
				}else{
					
					request.setAttribute("sucess", "û����Ҫ���ҵ���Ϣ��");
					request.setAttribute("url", "iframeForPro.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	@RequestMapping("/exportStuByClass.do")
	public ModelAndView exportStuByClass(HttpServletRequest request,HttpServletResponse response,String classId){
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.out.println(classId);
			request.setAttribute("sign", 1);
			System.out.println(classId);
			List<Users> allList = leaveService.selectForLeave(classId);
			modelAndView.addObject("allList", allList);
			if(allList.size() > 0){
				//return new ModelAndView("selExportStudent.jsp","classId",classId);
				modelAndView.setViewName("selExportStudent.jsp?classId="+classId+"");
			}else{
				
				request.setAttribute("sucess", "�ð���ʱû��ѧԱ��");
				request.setAttribute("url", "selExportStudent.jsp");
				modelAndView.setViewName("register_clue.jsp");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	
	/**
	 * ��excel����е����ݵ������ݿ�
	 * @author ��ǿ
	 * @param uploadfile ���ص��ļ�
	 * 
	 * */
	@RequestMapping("/stuExcelIn.do")
	public ModelAndView ExcelIn(HttpServletRequest request,HttpServletResponse response,MultipartFile uploadfile) throws IOException{
		        System.out.println("11111111111----------");
		        UserRole userRole = new UserRole();
				ModelAndView modelAndView = new ModelAndView();
				boolean flag = true;
				InputStream input = uploadfile.getInputStream();
				List<Users> codeList = leaveService.selectAllUsers();
		        Workbook bWorkbook = null;  
		        try {  
		        	
		            bWorkbook = Workbook.getWorkbook(input);
		            // ��õ�һ�����������
		            Sheet sheet = bWorkbook.getSheet(0);   
		            	int rows = sheet.getRows();
		            	List<Users> uList = new ArrayList<Users>();
		                //int columns = sheet.getColumns();
		            	System.out.println(rows);
		                for(int i = 1;i < rows;i++){
		                	Users users = new Users();
		                	  
		                    	Cell cell = sheet.getCell(0, i);
		                        String code = cell.getContents();
		                        users.setCode(code);
		                        
		                        Cell cell1 = sheet.getCell(1, i);
		                        String name = cell1.getContents();
		                        users.setName(name);
		                        
		                        Cell cell2 = sheet.getCell(2, i);
		                        String classId = cell2.getContents();
		                        users.setClassId(classId);
		                        
		                        Cell cell3 = sheet.getCell(3, i);
		                        String school = cell3.getContents();
		                        users.setSchool(school);
		                        
		                        Cell cell4 = sheet.getCell(4, i);
		                        String major = cell4.getContents();
		                        users.setMajor(major);
		                        
		                        Cell cell5 = sheet.getCell(5, i);
		                        String phone = cell5.getContents();
		                        users.setPhone(phone);
		                        
		                        
		                        Cell cell6 = sheet.getCell(6, i);
		                        String password = cell6.getContents();
		                        users.setPassword(password);
		                        
		                        Cell cell7 = sheet.getCell(7, i);
		                        String email = cell7.getContents();
		                        users.setEmail(email);
		                        
		                        
		                        uList.add(users);
		                        for(int j = 0;j < codeList.size();j++){
		                          if(code.equals(codeList.get(j).getCode())){
			                        	System.out.println(code);
			                        	System.out.println(codeList.get(j).getCode());
			                        	flag = false;
			                        	
			                        }
		                      	 }
		                }
		                
	                    
		               System.out.println(flag);
		                if(flag){
			                for(int j = 0;j < uList.size();j++){
			                	
			                	result = false;
			                	leaveService.stuExcelIn(uList.get(j));
			                	result = true;
			                	System.out.println("--------------����");
			                	List<Users> usersId = leaveService.selectId();
			                	System.out.println("==============="+usersId.size());
			                	for(int q = 0;q < usersId.size();q++){
			                		System.out.println("=============="+usersId.get(q).getId());
			                		int id = usersId.get(q).getId();
			                		userRole.setUserId(id);
			                		
			                		leaveService.insertRole(userRole);
			                	}
			                }
			               
			                if(result){
		                		
			                	request.setAttribute("sucess", "����ѧԱ��Ϣ�ɹ���");
			    				request.setAttribute("url", "stuInAndOut.jsp");
			    				modelAndView.setViewName("register_clue.jsp");
							}else{
								
								
								request.setAttribute("sucess", "����ѧԱ��Ϣʧ�ܣ�");
			    				request.setAttribute("url", "stuInAndOut.jsp");
			    				modelAndView.setViewName("register_clue.jsp");
							}
		                }else{
		                
		                	request.setAttribute("sucess", "ע�⣺������ظ���");
		    				request.setAttribute("url", "stuInAndOut.jsp");
		    				modelAndView.setViewName("register_clue.jsp");
		                }
		                
	                
	        }catch (Exception e) {
	            System.out.println(e);
	        }finally{
	            if(bWorkbook!=null){
	            	bWorkbook.close();
	            }
	        }
	        return modelAndView;
	}
	
	@RequestMapping("/selExportStudent.do")
	public ModelAndView selExportStudent(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		try {
			request.setAttribute("sign",2);
			System.out.println("========11111");
			List<Users> allList = leaveService.selectAllUsers();
			System.out.println("=====222222222"+allList.size());
			if(allList.size() > 0){
				modelAndView.addObject("allList", allList);
				modelAndView.setViewName("selExportStudent.jsp");
			}else{
				
				request.setAttribute("sucess", "��ʱû��ѧԱ��Ϣ��");
				request.setAttribute("url", "selExportStudent.jsp");
				modelAndView.setViewName("register_clue.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 * �����ݵ�����excel�����
	 * @author ��ǿ
	 * 
	 * */
	@RequestMapping("/stuExcelOut.do")
	public ModelAndView excelOut(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		
		//������д������
		WritableWorkbook bWorkbook = null;
		
		try {
			
			List<Users> uList = leaveService.selectAllUsers();
			if(uList!= null && !uList.isEmpty()){
				response.setContentType("application/vnd.ms-excel"); //��֤������
				String fileName = "�����׳�ѧԱexcel�������.xls";
				/* //����һ��ֵ����attachment�����������Ĺؼ����趨�����ֵ��������ͻ�����ʵʵ����ʾ���Ϊ�Ի���������ֵ��� inline����������������������Զ���������֪�����ĳ�����ļ���
				response.addHeader("Content-Disposition","attachment; filename=\""+ new String(fileName.getBytes("gb2312"),"iso8859-1") + "\""); */
				
				response.setHeader("Content-Disposition", "attachment;"
						+ " filename="
						+ new String(fileName.getBytes(), "ISO-8859-1"));
				OutputStream os = response.getOutputStream();
				if(true){
					//����excel����
					bWorkbook = Workbook.createWorkbook(os);
					//ͨ��excel���󴴽�һ��ѡ�����
					WritableSheet sheet = bWorkbook.createSheet("ѧԱ��Ϣ", 0);
					//ѭ�������������
					for(int i = 0;i < uList.size();i++){
						sheet.addCell(new Label(0, 0, "�˺�")); 
		                sheet.addCell(new Label(1, 0, "����"));
		                sheet.addCell(new Label(2, 0, "�༶"));
		                sheet.addCell(new Label(3, 0, "ѧУ"));
		                sheet.addCell(new Label(4, 0, "רҵ"));
		                sheet.addCell(new Label(5, 0, "��ϵ��ʽ"));
		                sheet.addCell(new Label(6, 0, "����"));
		                sheet.addCell(new Label(7, 0, "����"));
		             
		               
		                //sheet.addCell(new Label(0, i, String.valueOf(uList.get(i).getId()))); 
		                sheet.addCell(new Label(0, i + 1, String.valueOf(uList.get(i).getCode())));
		                sheet.addCell(new Label(1, i + 1, String.valueOf(uList.get(i).getName())));
		                sheet.addCell(new Label(2, i + 1, String.valueOf(uList.get(i).getClassId())));
		                sheet.addCell(new Label(3, i + 1, String.valueOf(uList.get(i).getSchool())));
		                sheet.addCell(new Label(4, i + 1, String.valueOf(uList.get(i).getMajor())));
		                sheet.addCell(new Label(5, i + 1, String.valueOf(uList.get(i).getPhone())));
		                sheet.addCell(new Label(6, i + 1, String.valueOf(uList.get(i).getPassword())));
		                sheet.addCell(new Label(7, i + 1, String.valueOf(uList.get(i).getEmail())));
		                
					}
					// ����һ����Ԫ����󣬵�һ��Ϊ�У��ڶ���Ϊ�У�������Ϊֵ  
		            //Label label = new Label(0, 2, "test");  
		            // �������õĵ�Ԫ�����ѡ���  
		            //sheet.addCell(label);  
		            // д��Ŀ��·��  
		            bWorkbook.write();
		            
		            
					}
				
				}else{
					request.setAttribute("sucess", "����ѧԱ��Ϣʧ�ܣ�");
					request.setAttribute("url", "stuInAndOut.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {  
				try {
					//�ر�
					bWorkbook.close();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	             
	        }
			return modelAndView;
			
	}
	
	@RequestMapping("/stuExcelOutByClass.do")
	public ModelAndView excelOutByClass(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		ModelAndView modelAndView = new ModelAndView();
		
		//String classId = request.getParameter("classId");
		String classId = new String(request.getParameter("classId").getBytes("ISO-8859-1"),"utf-8");
		
		//������д������
		WritableWorkbook bWorkbook = null;
		
		try {
			//String classId = (String) request.getAttribute("classId");
			
			List<Users> uList = leaveService.selectForLeave(classId);
			if(uList!= null && !uList.isEmpty()){
				response.setContentType("application/vnd.ms-excel"); //��֤������
				String fileName = "�����׳�ѧԱexcel�������.xls";
				/* //����һ��ֵ����attachment�����������Ĺؼ����趨�����ֵ��������ͻ�����ʵʵ����ʾ���Ϊ�Ի���������ֵ��� inline����������������������Զ���������֪�����ĳ�����ļ���
				response.addHeader("Content-Disposition","attachment; filename=\""+ new String(fileName.getBytes("gb2312"),"iso8859-1") + "\""); */
				
				response.setHeader("Content-Disposition", "attachment;"
						+ " filename="
						+ new String(fileName.getBytes(), "ISO-8859-1"));
				OutputStream os = response.getOutputStream();
				if(true){
					//����excel����
					bWorkbook = Workbook.createWorkbook(os);
					//ͨ��excel���󴴽�һ��ѡ�����
					WritableSheet sheet = bWorkbook.createSheet("ѧԱ��Ϣ", 0);
					//ѭ�������������
					for(int i = 0;i < uList.size();i++){
						sheet.addCell(new Label(0, 0, "�˺�")); 
		                sheet.addCell(new Label(1, 0, "����"));
		                sheet.addCell(new Label(2, 0, "�༶"));
		                sheet.addCell(new Label(3, 0, "ѧУ"));
		                sheet.addCell(new Label(4, 0, "רҵ"));
		                sheet.addCell(new Label(5, 0, "��ϵ��ʽ"));
		                sheet.addCell(new Label(6, 0, "����"));
		                sheet.addCell(new Label(7, 0, "����"));
		             
		               
		                //sheet.addCell(new Label(0, i, String.valueOf(uList.get(i).getId()))); 
		                sheet.addCell(new Label(0, i + 1, String.valueOf(uList.get(i).getCode())));
		                sheet.addCell(new Label(1, i + 1, String.valueOf(uList.get(i).getName())));
		                sheet.addCell(new Label(2, i + 1, String.valueOf(uList.get(i).getClassId())));
		                sheet.addCell(new Label(3, i + 1, String.valueOf(uList.get(i).getSchool())));
		                sheet.addCell(new Label(4, i + 1, String.valueOf(uList.get(i).getMajor())));
		                sheet.addCell(new Label(5, i + 1, String.valueOf(uList.get(i).getPhone())));
		                sheet.addCell(new Label(6, i + 1, String.valueOf(uList.get(i).getPassword())));
		                sheet.addCell(new Label(7, i + 1, String.valueOf(uList.get(i).getEmail())));
		                
					}
					// ����һ����Ԫ����󣬵�һ��Ϊ�У��ڶ���Ϊ�У�������Ϊֵ  
		            //Label label = new Label(0, 2, "test");  
		            // �������õĵ�Ԫ�����ѡ���  
		            //sheet.addCell(label);  
		            // д��Ŀ��·��  
		            bWorkbook.write();
		            
		            
					}
				
				}else{
					request.setAttribute("sucess", "����ѧԱ��Ϣʧ�ܣ�");
					request.setAttribute("url", "stuInAndOut.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {  
				try {
					//�ر�
					bWorkbook.close();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	             
	        }
			return modelAndView;
			
	}
	
	
	/**
	 * ȷ�����
	 * @author ��ǿ
	 * @param leaveThing һ�������Ϣ
	 * 
	 * */
	@RequestMapping("/isLeave.do")
	public ModelAndView insertLeaveStu (Signing signing,HttpServletRequest request,HttpServletResponse response,LeaveThing leaveThing){
		ModelAndView modelAndView = new ModelAndView();
		try {
			
				boolean result = leaveService.insertLeaveStu(leaveThing);
				if(result){
					String signData = request.getParameter("time");
					signing.setSignData(signData);
					Integer userId = Integer.parseInt(request.getParameter("userId"));
					signing.setUserId(userId);
					System.out.println("----========"+signData);
					boolean aa = leaveService.afterLeaveAdd(signing);
					if(aa){
						request.setAttribute("sucess", "��ٳɹ���");
						request.setAttribute("url", "iframe.jsp");
						modelAndView.setViewName("register_clue.jsp");
					}else{
						System.out.println("-----------");
						request.setAttribute("sucess", "���ʧ�ܣ�");
						request.setAttribute("url", "leave.jsp");
						modelAndView.setViewName("register_clue.jsp");
					}
				}else{
					
					request.setAttribute("sucess", "���ʧ�ܣ�");
					request.setAttribute("url", "leave.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
		
	}
	
	@RequestMapping("/isLeaveForPro.do")
	public ModelAndView insertLeavePro(Signing signing,HttpServletRequest request,HttpServletResponse response,LeaveThing leaveThing){
		ModelAndView modelAndView = new ModelAndView();
		try {
			
				boolean result = leaveService.insertLeaveStuForPro(leaveThing);
				if(result){
					Integer userId = Integer.parseInt(request.getParameter("userId"));
					signing.setUserId(userId);
					System.out.println("----========"+userId);
					String signData = request.getParameter("time");
					signing.setSignData(signData);
					
					System.out.println("----========"+signData);
					boolean aa = leaveService.afterLeaveAdd(signing);
					if(aa){
						request.setAttribute("sucess", "��ٳɹ���");
						request.setAttribute("url", "iframe.jsp");
						modelAndView.setViewName("register_clue.jsp");
					}else{
						System.out.println("-----------");
						request.setAttribute("sucess", "���ʧ�ܣ�");
						request.setAttribute("url", "leave.jsp");
						modelAndView.setViewName("register_clue.jsp");
					}
					
				}else{
					System.out.println("==========");
					request.setAttribute("sucess", "���ʧ�ܣ�");
					request.setAttribute("url", "leave.jsp");
					modelAndView.setViewName("register_clue.jsp");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
		
	}
	/**
	 * ɾ����ټ�¼��ͨ��idɾ����
	 * @author ��ǿ
	 * @param id ��ٱ�id
	 * 
	 * */
	@RequestMapping("/deleteLeave.do")
	public ModelAndView deleteLeave(HttpServletRequest request,String id,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		boolean flag1 = false;
		try {
			leaveService.deleteLeaveById(id);
			flag1 = true;
			if(flag1){
				
				request.setAttribute("sucess", "ɾ���ɹ���");
				request.setAttribute("url", "leave.jsp");
				modelAndView.setViewName("register_clue.jsp");
			}else{
				
				request.setAttribute("sucess", "ɾ��ʧ�ܣ�");
				request.setAttribute("url", "searchLeave.do");
				modelAndView.setViewName("register_clue.jsp");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}	
	
	/**
	 * �޸���ټ�¼
	 * @author ��ǿ
	 * @param leaveThing һ�������Ϣ
	 * 
	 * */
	@RequestMapping("/updateLeave.do")
	public ModelAndView updateLeave(HttpServletRequest request,HttpServletResponse response,LeaveThing leaveThing){
		ModelAndView modelAndView = new ModelAndView();
		boolean flag2 = false;
		try {
			
			leaveService.updateLeave(leaveThing);
			flag2 = true;
			if(flag2){
				
				request.setAttribute("sucess", "�޸ĳɹ���");
				request.setAttribute("url", "leave.jsp");
				modelAndView.setViewName("register_clue.jsp");
			}else{
				
				request.setAttribute("sucess", "�޸�ʧ�ܣ�");
				request.setAttribute("url", "searchLeave.do");
				modelAndView.setViewName("register_clue.jsp");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
}
