package com.hzyc.registerSystem.mapper;

import com.hzyc.registerSystem.po.Users;


public interface TeacherMapper {
   
	String getPasswordByCode(String code);
	//������Ϣ����
	public Users queryTeacherMessageByID(String id);
	public boolean updateTeacherMessageByID(Users u);

}
