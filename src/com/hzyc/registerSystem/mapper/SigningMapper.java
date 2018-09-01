package com.hzyc.registerSystem.mapper;

import java.util.List;

import com.hzyc.registerSystem.po.SelectSigning;
import com.hzyc.registerSystem.po.Signing;

public interface SigningMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Signing record);

    int insertSelective(Signing record);

    Signing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Signing record);

    int updateByPrimaryKey(Signing record);
    
	//��Ŀ��ǩ����¼��һ ������--BIN
	List<Signing> selSigning(String left,String right);
	
	//��Ŀ��ǩ����¼ ����һ����һ����������--BIN
	List<Signing> selSigningNo(String left,String right);
	
	//��Ŀ�����м�¼ ���� --BIN
	List<Signing> selAll();
	
	//����ǩ����¼��һ ������--BIN
	List<Signing> selGradeSigning(String option1,String left,String right);
	
	//����ǩ����¼ ����һ����һ����������--BIN
	List<Signing> selGradeSigningNo(String option1,String left,String right);
	
	//�������м�¼ ���� --BIN
	List<Signing> selGradeAll(String option1);
	
	//�ĸ�ѧ�������쵽����ļ�¼
	List<Signing> selGradeStudent(String option3,String left,String right);
	//ǩ�������
	 int insertSigning(Signing record);
	 
	 //��ѯ����ǩ�����
	 List<Signing> selectState(SelectSigning selectSigning);
	 
	 //���ݰ�Ų�ID
	String selectGradeId(String classId);
	
	//��ѯ״̬�ж��Ƿ��Ѿ�ǩ��
	String selectStateById(Signing signing);
	//ȫ��ǩ�����ڸı�������ɫ
	List<String> selectSignDateById(String userId);
	
	void afterLeaveAdd(Signing signing);
}