package com.hzyc.registerSystem.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzyc.registerSystem.mapper.UserRoleMapper;
import com.hzyc.registerSystem.mapper.UsersMapper;
import com.hzyc.registerSystem.po.UserRole;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	UsersMapper userMapper;
	
	@Autowired
	UserRoleMapper ump;
	
	/* (non-Javadoc)
	 * @see com.hzyc.registerSystem.services.AdminService#login(com.hzyc.registerSystem.po.Users)
	 * @auther ���ٸ�
	 * @desc:��¼�ķ���,
	 * @param �û���
	 */
	public Users login(Users user){
		return userMapper.login(user);
	}
	
	/* (non-Javadoc)
	 * @see com.hzyc.registerSystem.services.AdminService#register(com.hzyc.registerSystem.po.Users)
	 * @auther ���ٸ�
	 * @desc:ע��ķ���
	 * @param �û���
	 */
	public int register(Users u){
		return userMapper.insert(u);
	}
	/* (non-Javadoc)
	 * @see com.hzyc.registerSystem.services.AdminService#register(com.hzyc.registerSystem.po.Users)
	 * @auther ���ٸ�
	 * @desc:�����û���ɫ��
	 * @param �û���
	 */
	public int insertUserRole(UserRole ur){
		return ump.insert(ur);
	}
	/* (non-Javadoc)
	 * @see com.hzyc.registerSystem.services.AdminService#register(com.hzyc.registerSystem.po.Users)
	 * @auther ���ٸ�
	 * @param �˺�
	 * �����û�����ѯid
	 */
	public Users selUserId(String code){
		return userMapper.selUserId(code);
	}
	
	public List<Users> selStudentNoRegister(String code){
		return userMapper.selStudentNoRegister(code);
	}
	//��ѯ�Ѿ���˵�ѧ��
	public List<Users> selStudentRegister(String code){
		return userMapper.selStudentRegister(code);
	}
	
	
	public List<Users> studentCheckForSel(Users u){
		System.out.println("ѧ������" + u.getRemark());
		System.out.println("���״̬" + u.getName());
		
		
		if(u.getRemark().equals("�����")){
			u.setRemark("1");
			
		}else if(u.getRemark().equals("δ���")){
			u.setRemark("0");
		}
		
		
		
		List<Users> usersList = userMapper.studentCheckForSel(u);
		
		
		return usersList;
	}
	
	public int delUser(int id){
		return userMapper.deleteByPrimaryKey(id);
	}
	public int delUserRole(int userId){
		return ump.delUserRole(userId);
	}
	@Override
	public List<Users> selBoth() {
		List<Users> list = userMapper.selBoth();
		return list;
	}
	@Override
	public int updateRemark(int id){
		return userMapper.updateRemark(id);
	}
	public String selRoleByUserId(int id){
		return ump.JudgeUserHas(id);
	}
	public List<Users> selEmployees(Users u){
		return userMapper.selEmployees(u);
	}
	public Users selEmployeesDetailById(int id){
		return userMapper.selectByPrimaryKey(id);
	}
}
