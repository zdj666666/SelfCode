package com.hzyc.registerSystem.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.hzyc.registerSystem.po.RealSchedule;

public class ArrayToList {
	public List<RealSchedule> transform(StringBuffer[] arr){
		List<RealSchedule> rsList = new ArrayList<RealSchedule>();
		for (int a = 0; a < arr.length-1; a++) {
			int temp = 0;
			String weekDate = "";
			// ʵ��������
			Calendar cal = Calendar.getInstance();
			// ���õ�ǰ����
			cal.set(2017, 10, 13);
			// ���ڵ������ʽ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.get(Calendar.DAY_OF_WEEK);
			// ��ȡ���µ����һ��
			cal.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
			cal.roll(Calendar.DATE, -1);// ���ڻع�һ�죬Ҳ�������һ��
			String finalData = sdf.format(cal.getTime());
			// ��������ʱ��
			cal.set(2017, 10, 13);
			// ��ȡ�����ǵڼ������ڼ�������һ:2,�����գ�1
			if (arr[a].substring(0, 3).equals("����һ")) {
				temp = 2;
				weekDate = arr[a].substring(4);
			}
			if (arr[a].substring(0, 3).equals("���ڶ�")) {
				temp = 3;
				weekDate = arr[a].substring(4);
			}
			if (arr[a].substring(0, 3).equals("������")) {
				temp = 4;
				weekDate = arr[a].substring(4);
			}
			if (arr[a].substring(0, 3).equals("������")) {
				temp = 5;
				weekDate = arr[a].substring(4);
			}
			if (arr[a].substring(0, 3).equals("������")) {
				temp = 6;
				weekDate = arr[a].substring(4);
			}
			if (arr[a].substring(0, 3).equals("������")) {
				temp = 7;
				weekDate = arr[a].substring(4);
			}
			if (arr[a].substring(0, 3).equals("������")) {
				temp = 1;
				weekDate = arr[a].substring(4);
			}

			switch (temp) {
			case 1:
				for (int x = 0; x < 31; x++) {
					cal.add(Calendar.DATE, 1);// DATE=��
					if ((cal.get(Calendar.DAY_OF_WEEK)) == 1) {
						RealSchedule rs = new RealSchedule();
						rs.setTime(weekDate);
						rs.setDate(sdf.format(cal.getTime()));
						rsList.add(rs);
					}
					if (sdf.format(cal.getTime()).equals(finalData))
						break;
				}
				break;
			case 2:
				for (int x = 0; x < 31; x++) {
					cal.add(Calendar.DATE, 1);// DATE=��
					if ((cal.get(Calendar.DAY_OF_WEEK)) == 2) {
						RealSchedule rs = new RealSchedule();
						rs.setTime(weekDate);
						rs.setDate(sdf.format(cal.getTime()));
						rsList.add(rs);
					}
					if (sdf.format(cal.getTime()).equals(finalData))
						break;
				}
				break;
			case 3:
				for (int x = 0; x < 31; x++) {
					cal.add(Calendar.DATE, 1);// DATE=��
					if ((cal.get(Calendar.DAY_OF_WEEK)) == 3) {
						RealSchedule rs = new RealSchedule();
						rs.setTime(weekDate);
						rs.setDate(sdf.format(cal.getTime()));
						rsList.add(rs);
					}
					if (sdf.format(cal.getTime()).equals(finalData))
						break;
				}
				break;
			case 4:
				for (int x = 0; x < 31; x++) {
					cal.add(Calendar.DATE, 1);// DATE=��
					if ((cal.get(Calendar.DAY_OF_WEEK)) == 4) {
						RealSchedule rs = new RealSchedule();
						rs.setTime(weekDate);
						rs.setDate(sdf.format(cal.getTime()));
						rsList.add(rs);
					}
					if (sdf.format(cal.getTime()).equals(finalData))
						break;
				}
				break;
			case 5:
				for (int x = 0; x < 31; x++) {
					cal.add(Calendar.DATE, 1);// DATE=��
					if ((cal.get(Calendar.DAY_OF_WEEK)) == 5) {
						RealSchedule rs = new RealSchedule();
						rs.setTime(weekDate);
						rs.setDate(sdf.format(cal.getTime()));
						rsList.add(rs);
					}
					if (sdf.format(cal.getTime()).equals(finalData))
						break;
				}
				break;
			case 6:
				for (int x = 0; x < 31; x++) {
					cal.add(Calendar.DATE, 1);// DATE=��
					if ((cal.get(Calendar.DAY_OF_WEEK)) == 6) {
						RealSchedule rs = new RealSchedule();
						rs.setTime(weekDate);
						rs.setDate(sdf.format(cal.getTime()));
						rsList.add(rs);
					}
					if (sdf.format(cal.getTime()).equals(finalData))
						break;
				}
				break;
			case 7:
				for (int x = 0; x < 31; x++) {
					cal.add(Calendar.DATE, 1);// DATE=��
					if ((cal.get(Calendar.DAY_OF_WEEK)) == 7) {
						RealSchedule rs = new RealSchedule();
						rs.setTime(weekDate);
						rs.setDate(sdf.format(cal.getTime()));
						rsList.add(rs);
					}
					if (sdf.format(cal.getTime()).equals(finalData))
						break;
				}
				break;
			}

		}
		return rsList;
	}
}
