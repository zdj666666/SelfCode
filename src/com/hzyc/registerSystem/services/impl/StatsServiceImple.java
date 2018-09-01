package com.hzyc.registerSystem.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzyc.registerSystem.mapper.GradeMapper;
import com.hzyc.registerSystem.mapper.RealScheduleMapper;
import com.hzyc.registerSystem.mapper.SigningMapper;
import com.hzyc.registerSystem.mapper.UsersMapper;
import com.hzyc.registerSystem.po.Grade;
import com.hzyc.registerSystem.po.RealSchedule;
import com.hzyc.registerSystem.po.Signing;
import com.hzyc.registerSystem.po.Users;
import com.hzyc.registerSystem.services.StatsService;

/**
 * @author BIN
 * @date 2017-11-8 ����06:48:18
 */
@Service
public class StatsServiceImple implements StatsService{

	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private RealScheduleMapper realScheduleMapper;
	
	@Autowired
	private SigningMapper signingMapper;
	
	@Autowired
	private GradeMapper gradeMapper; 
	
	@Override
	public List<List<String>> statsHistogram() {
		List<Users> usersList = usersMapper.statsPTWK();
		List<List<String>> binList = new ArrayList<List<String>>();
		List<String> binDbList;
		for (int i=1; i<=usersList.size(); i++) {
			binDbList = new ArrayList<String>();
			binDbList.add(""+i+",'"+usersList.get(i-1).getName()+"'");
			binList.add(binDbList);
		}
		//��Ҫ�����ˣ�����ʲô�����������
		return binList;
	}
	
	@Override
	public List<Users> statsPT(){
		List<Users> usersList = usersMapper.statsPTWK();
		return usersList;
	}

	@Override
	public List<Signing> selSining(Date d) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        String right = sdf.format(cal.getTime());
        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //��һʱ�� 
        
        List<Signing> list = signingMapper.selSigning(left,right);
		return list;
	}

	@Override
	public List<Signing> selSiningNo(Date d) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        String right = sdf.format(cal.getTime());
        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day-7);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //��һʱ�� 
        
        List<Signing> list = signingMapper.selSigningNo(left,right);
		return list;
	}
	
	@Override
	public List<RealSchedule> selPTWT(Date d) {
		

		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        String right = sdf.format(cal.getTime());
        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //��һʱ�� 
		
		List<RealSchedule> list = realScheduleMapper.selPTWT(left, right);
		
		return list;
	}
	
	@Override
	public List<RealSchedule> left(Date d) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        String right = sdf.format(cal.getTime());
        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day-7);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //��һʱ�� 
        
        List<RealSchedule> list = realScheduleMapper.selLeft(left, right);
		
		return list;
	}

	@Override
	public List<Grade> judgment(String name) {
		List<Grade> gList = gradeMapper.judgment(name);
		return gList;
	}

	@Override
	public List<RealSchedule> right(Date d) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day+7);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //����һʱ�� 
        
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()+4);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   
		
        String right = sdf.format(cal.getTime()); //��һʱ�� 
		List<RealSchedule> list = realScheduleMapper.selPTWT(left, right);
		return list;
	}

	@Override
	public List<Signing> selSiningYes(Date d) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day+7);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //��һʱ�� 
        
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()+4);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String right = sdf.format(cal.getTime()); //��һʱ�� 
        
        List<Signing> list = signingMapper.selSigning(left,right);
		
		return list;
	}
	
	public List<Signing> selAll(){
		List<Signing> list = signingMapper.selAll();
		return list;
	}

	@Override
	public List<Signing> selSigningTime(String left, String right) {
		List<Signing> list = signingMapper.selSigning(left, right); 
		return list;
	}

	@Override
	public List<RealSchedule> selPTT(String left, String right) {
		List<RealSchedule> list = realScheduleMapper.selPTWT(left, right);
		return list;
	}

	@Override
	public List<Grade> statsTWK(String username) {

		List<Grade> list = gradeMapper.statsTWK(username);
		
		return list;
	}

	@Override
	public List<Grade> statsATWK() {

		List<Grade> list = gradeMapper.statsATWK();
		
		return list;
	}
	
	@Override
	public List<Map<String, String>> selStudent(String option1) {
		
		List<Users> list = usersMapper.selStudent(option1);
		
		List<Map<String,String>> rsList = new ArrayList<Map<String,String>>();
		
		Map<String,String> map = null;
		
		for (int i=0; i<list.size(); i++) {
			map = new HashMap<String, String>();
			String name = list.get(i).getName();
			map.put("name", name);
			rsList.add(map);
		}
		return rsList;
	}

	@Override
	public List<List<String>> statsGradeHistogram(String option1) {
		List<Users> usersList = usersMapper.selStudent(option1);
		List<List<String>> binList = new ArrayList<List<String>>();
		List<String> binDbList;
		for (int i=1; i<=usersList.size(); i++) {
			binDbList = new ArrayList<String>();
			binDbList.add(""+i+",'"+usersList.get(i-1).getName()+"'");
			binList.add(binDbList);
		}
		return binList;
	}

	@Override
	public List<Users> selS(String option1) {
		List<Users> list = usersMapper.selStudent(option1);
		return list;
	}

	@Override
	public List<Signing> selGradeSining(Date d,String option1) {
		
		String[] time = getMonday(d);
		String left = time[0];
		String right = time[1];
		
		List<Signing> list = signingMapper.selGradeSigning(option1, left, right);
		
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public List<Signing> selGradeSiningNo(Date d,String option1) {
		
		String[] time = getLastMonday(d);
		String left = time[0];
		String right = time[1];
		List<Signing> list = signingMapper.selGradeSigningNo(option1, left, right);
		
		return list;
	}

	@Override
	public String[] getMonday(Date d) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        String right = sdf.format(cal.getTime());
        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //��һʱ�� 
		
        String time[] = new String[2];
		time[0] = left;
		time[1] = right;
		// TODO Auto-generated method stub
		return time;
	}
	
	@Override
	public String[] getLastMonday(Date d) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        String right = sdf.format(cal.getTime());
        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day-7);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //��һʱ�� 
		
        String time[] = new String[2];
		time[0] = left;
		time[1] = right;
		// TODO Auto-generated method stub
		return time;
	}

	@Override
	public List<RealSchedule> selGradeWT(Date d,String option1) {
		String[] time = getMonday(d);
		String left = time[0];
		String right = time[1];
		List<RealSchedule> list = realScheduleMapper.selGradeWT(option1, left, right);
		return list;
	}

	@Override
	public List<RealSchedule> gradeLeft(Date d, String option1) {
		
		String[] time = getLastMonday(d);
		String left = time[0];
		String right = time[1];
		
		List<RealSchedule> list = realScheduleMapper.selGradeLeft(option1, left, right);
		return list;
	}

	@Override
	public String[] getNextMonday(Date d) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ  

        Calendar cal = Calendar.getInstance();  

        cal.setTime(d);  

        //�ж�Ҫ����������Ƿ������գ���������һ����������ģ����������⣬���㵽��һ��ȥ��  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        if(1 == dayWeek) {  

          cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        //System.out.println("Ҫ��������Ϊ:"+sdf.format(cal.getTime())); //���Ҫ��������  

        
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ  

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day+7);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   

        String left = sdf.format(cal.getTime()); //����һʱ�� 
        
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()+4);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ   
		
        String right = sdf.format(cal.getTime()); //������ʱ�� 
		
        String[] time = new String[2];
        time[0] = left;
        time[1] = right;
		// TODO Auto-generated method stub
		return time;
	}

	@Override
	public List<RealSchedule> gradeRight(Date d, String option1) {
		
		String[] time = getNextMonday(d);
		String left = time[0];
		String right = time[1];
		
		List<RealSchedule> list = realScheduleMapper.selGradeWT(option1, left, right);
		
		return list;
	}

	@Override
	public List<Signing> selGradeAll(String option1) {
		List<Signing> list = signingMapper.selGradeAll(option1);
		return list;
	}

	@Override
	public List<Signing> selGradeSiningYes(Date d, String option1) {
		
		String[] time = getNextMonday(d);
		String left = time[0];
		String right = time[1];
		List<Signing> list = signingMapper.selGradeSigning(option1, left, right);
		
		return list;
	}

	@Override
	public List<Signing> selGradeStudent(Date d, String option3) {

		String[] time = getMonday(d);
		String left = time[0];
		String right = time[1];
		
		List<Signing> list = signingMapper.selGradeStudent(option3, left, right);
		
		return list;
	}

	@Override
	public List<Signing> statsGradeSigning(String option1, String left,
			String right) {
		List<Signing> list = signingMapper.selGradeSigning(option1, left, right);
		return list;
	}

	@Override
	public List<RealSchedule> statsGrade(String option1, String left,
			String right) {
		List<RealSchedule> list = realScheduleMapper.selGradeWT(option1, left, right);
		return list;
	}
	
	

}
