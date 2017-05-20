package com.zhiyun.bigdata.recommend.calculation;

import java.util.List;

import com.zhiyun.bigdata.framework.ssh.SpringService;
import com.zhiyun.bigdata.framework.utils.CollectionUtils;
import com.zhiyun.bigdata.framework.utils.GpsUtils;
import com.zhiyun.bigdata.recommend.pojo.Attention;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;
import com.zhiyun.bigdata.recommend.pojo.Student;
import com.zhiyun.bigdata.recommend.pojo.Teacher;
import com.zhiyun.bigdata.recommend.service.RecommendService;

public class TeacherResource extends Resource<Teacher> {
	
	private String userId;
	
	private RecommendService recommendService;
	
	public TeacherResource(String id,CalculationManage calculationManage,List<Teacher> list ,String userId){
		this.id = id;
		this.calculationManage = calculationManage;
		this.list = list;
		this.userId = userId;
	}

	@Override
	public Result execute(Teacher teacher) {
		int total = 0;
		recommendService = SpringService.getBean(RecommendService.class);
		Student student = recommendService.getStudent(userId);
		int orderCount = countOrder(teacher, userId);
		int attentionCount = countAttention(teacher, userId);
		int levelCount = countTeacherLevel(teacher);
		int scanCount = countScanLog(teacher, userId);
		int stayCount = countStay(teacher, userId);
		int gpsCount = countGps(teacher, student);
		int partNumCount = countPartNum(teacher, userId);
		int spendPowerCount = countSpendPower(teacher, student);
		int onlineCount = countOnline(teacher);
		total += orderCount;
		total += attentionCount;
		total += levelCount;
		total += scanCount;
		total += stayCount;
		total += gpsCount;
		total += partNumCount;
		total += spendPowerCount;
		total += onlineCount;
		Result result = new Result();
		result.setBody(teacher.getTeacherId());
		result.setWeight(total);
		return result;
	}
	
	/**
	 * ���㶩����Ȩֵ�������ѧ�����������ʦ�����꼶�������ڿ�Ŀ�Ŀγ̡������ʦ��ص��꼶�Ͱ༶Ȩ��������
	 * �û�������a�꼶b��Ŀ�γ̣���a�꼶Ȩ��+8��b��ĿȨ��+6��
	 * �û�����δ֧��a�꼶b��Ŀ�γ̣���a�꼶Ȩ��+6��b��ĿȨ��+4��
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countOrder(Teacher teacher , String userId){
		int gradeCount = 0; //�꼶Ȩ��
		int subjectCount = 0; //��ĿȨ��
		List<Order> orders = recommendService.getOrder(userId);
		if(orders != null && !orders.isEmpty()){
			for (Order order : orders) {
				Course course = recommendService.getCourse(order.getCourseId());
				if(course != null){
					if(teacher.getGradeId().equals(course.getGradeId())){
						if(order.getCompleteTime() != null){
							if(gradeCount < 8){//����8��Ȩ�صģ����꼶Ȩ����Ϊ6
								gradeCount = 8;
							}
						} else {
							if(gradeCount < 6){ //����6��Ȩ�صģ��ѿ�ĿȨ����Ϊ6
								gradeCount = 6;
							}
						}
					}
					if(teacher.getSubjectId().equals(course.getSubjectId())){
						if(order.getCompleteTime() != null){
							if(subjectCount < 6){
								subjectCount = 6;
							}
						} else {
							if(subjectCount < 4){ //����6��Ȩ�صģ����꼶Ȩ����Ϊ6
								subjectCount = 4;
							}
						}
					}
				}
				
			}
		}
		return gradeCount + subjectCount;
	}
	
	/**
	 * �����עά�ȣ�������û���ע������ʦ������꼶Ȩ��+8���Ըÿ�ĿȨ��+6��
	 * 
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countAttention(Teacher teacher ,String userId){
		int gradeCount = 0; //�꼶Ȩ��
		int subjectCount = 0; //��ĿȨ��
		List<Attention> attes = recommendService.getAttention(userId);
		if(!CollectionUtils.isEmpty(attes)){
			for (Attention attention : attes) {
				Teacher theTeacher = recommendService.getTeacher(attention.getTeacherId());
				if(teacher != null) {
					if(teacher.getGradeId().equals(theTeacher.getGradeId())){
						gradeCount = 8;
					}
					if(teacher.getSubjectId().equals(theTeacher.getSubjectId())){
						subjectCount = 6;
					}
				}
			}
		}
		return gradeCount + subjectCount;
	}
	
	/**
	 * ������ʦ�ȼ���Ȩֵ
	 * 0����ʦ�ȼ�<3,�ۺ�Ȩ�ؼ�1��
	 * 3����ʦ�ȼ�<5,�ۺ�Ȩ�ؼ�2��
	 * 5����ʦ�ȼ�<10,�ۺ�Ȩ�ؼ�3��
	 * 10����ʦ�ȼ�<15,�ۺ�Ȩ�ؼ�4��
	 * 15����ʦ�ȼ�<20,�ۺ�Ȩ�ؼ�5��
	 * 20����ʦ�ȼ�<25,�ۺ�Ȩ�ؼ�6��
	 * 25����ʦ�ȼ�<30,�ۺ�Ȩ�ؼ�7��
	 * 30����ʦ�ȼ�<35,�ۺ�Ȩ�ؼ�8��
	 * 35����ʦ�ȼ�,�ۺ�Ȩ�ؼ�10��
	 * @param teacher
	 * @return
	 */
	public int countTeacherLevel(Teacher teacher){
		int count = 0;
		int level = teacher.getTeacherLevel() == null ? 0 : teacher.getTeacherLevel();
		if(level >= 0 && level < 3){
			count = 1 ;
		} else if(level >= 3 && level < 5){
			count = 2 ;
		} else if(level >= 5 && level < 10){
			count = 3 ;
		} else if(level >= 10 && level < 15){
			count = 4 ;
		} else if(level >= 15 && level < 20){
			count = 5 ;
		} else if(level >= 20 && level < 25){
			count = 6 ;
		} else if(level >= 25 && level < 30){
			count = 7 ;
		} else if(level >= 30 && level < 35){
			count = 8 ;
		} else if(level >= 35){
			count = 10 ;
		}
		return count;
	}
	
	/**
	 * ���������¼ά�ȣ�ͨ��ͳ���꼶����ͳ��Ȩֵ
	 * 1�Ρ�ͬ�꼶<3��,���꼶��ʦ������Ȩ�ؼ�2��
	 * 3�Ρ�ͬ�꼶<5��, ���꼶��ʦ������Ȩ�ؼ�5��
	 * 5�Ρ�ͬ�꼶<10��, ���꼶��ʦ������Ȩ�ؼ�7��
	 * 10�Ρ�ͬ�꼶, ���꼶��ʦ������Ȩ�ؼ�9��
	 * 1�Ρ�ͬ��Ŀ,���꼶��ʦ������Ȩ�ؼ�2��
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countScanLog(Teacher teacher , String userId){
		int gradeCount = 0; //�꼶Ȩ��
		int subjectCount = 0; //��ĿȨ��
		long count = recommendService.countGradeScan(teacher.getGradeId(), userId);
		if(count >= 1 && count < 3){
			gradeCount = 2;
		} else if(count >= 3 && count < 5){
			gradeCount = 5;
		} else if(count >= 5 && count < 10){
			gradeCount = 7;
		} else if(count >= 10){
			gradeCount = 9;
		}
		count = recommendService.countSubjectScan(teacher.getSubjectId(), userId);
		if(count > 1){
			subjectCount = 2;
		}
		return gradeCount + subjectCount;
	}
	
	/**
	 * ����ͣ��ʱ��Ȩ��
	 * 0min<�ۿ�ʱ����10min����Ըÿγ��꼶Ȩ��+2����ĿȨ��+1��
	 * 10min<�ۿ�ʱ����20min����Ըÿγ��꼶Ȩ��+3����ĿȨ��+2��
	 * 20min<�ۿ�ʱ����30min����Ըÿγ��꼶Ȩ��+7����ĿȨ��+4��
	 * 30min<�ۿ�ʱ������Ըÿγ��꼶Ȩ��+8����ĿȨ��+5��
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countStay(Teacher teacher , String userId){
		int gradeCount = 0; //�꼶Ȩ��
		int subjectCount = 0; //��ĿȨ��
		long gradeSum = recommendService.sumGradeStayLog(teacher.getGradeId() , userId);
		long subjectSum = recommendService.sumSubjectStayLog(teacher.getSubjectId(), userId);
		
		if(gradeSum > 0 && gradeSum <= 10*60){
			gradeCount = 2;
		} else if(gradeSum > 10*60 && gradeSum <= 20*60){
			gradeCount = 3;
		} else if(gradeSum > 20*60 && gradeSum <= 30*60){
			gradeCount = 7;
		} else if(gradeSum > 30*60){
			gradeCount = 8;
		} 
		
		if(subjectSum > 0 && subjectSum <= 10*60){
			subjectCount = 1;
		} else if(subjectSum > 10*60 && subjectSum <= 20*60){
			subjectCount = 2;
		} else if(subjectSum > 20*60 && subjectSum <= 30*60){
			subjectCount = 4;
		} else if(subjectSum > 30*60){
			subjectCount = 5;
		} 
		return gradeCount + subjectCount;
	}
	
	/**
	 * ����GPS����
	 * 0km�ܾ����3km,�ۺ�Ȩ��+7;  
	 * 3km<�����5km, �ۺ�Ȩ��+6;
	 * 5km<�����10km, �ۺ�Ȩ��+5;
	 * 10km<�����20km, �ۺ�Ȩ��+4;
	 * 20km<�����30km, �ۺ�Ȩ��+3;
	 * 30km<�����50km, �ۺ�Ȩ��+2;
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countGps(Teacher teacher ,Student student){
		int count = 0;
		if(student != null){
			String teacherGps = teacher.getUserGps();
			String userGps = student.getUserGps();
			long distance = GpsUtils.countGps(teacherGps, userGps);
			if(distance >= 0 && distance <= 3000){
				count = 7;
			} else if(distance > 3000 && distance <= 5000){
				count = 6;
			} else if(distance > 5000 && distance <= 10000){
				count = 5;
			} else if(distance > 10000 && distance <= 20000){
				count = 4;
			} else if(distance > 20000 && distance <= 30000){
				count = 3;
			} else if(distance > 30000 && distance <= 50000){
				count = 3;
			}
		}
		return count;
	}
	
	/**
	 * ������ʦ��������ά��
	 * 3�ܲμӿγ�������5���ۺ�Ȩ��+2��
	 * 5<�μӿγ�������10���ۺ�Ȩ��+3��
	 * 10<�μӿγ�������20���ۺ�Ȩ��+5��
	 * 20<�μӿγ�������30���ۺ�Ȩ��+7��
	 * 30<�μӿγ�������40���ۺ�Ȩ��+8��
	 * 40<�μӿγ�������50���ۺ�Ȩ��+9��
	 * 50<�μӿγ��������ۺ�Ȩ��+10��
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countPartNum(Teacher teacher ,String userId){
		int count = 0;
		long partNum = teacher.getPartNum();
		if(partNum >= 3 && partNum <= 5){
			count = 2;
		} else if(partNum > 5 && partNum <= 10){
			count = 3;
		} else if(partNum > 10 && partNum <= 20){
			count = 5;
		} else if(partNum > 20 && partNum <= 30){
			count = 7;
		} else if(partNum > 30 && partNum <= 40){
			count = 8;
		} else if(partNum > 40 && partNum <= 50){
			count = 9;
		} else if(partNum > 50 ){
			count = 10;
		}
		return count;
	}
	
	/**
	 * ��������ָ��
	 * ���ѽ��=0����ѿγ�Ȩ��+10��
	 * 0<�û�����γ���ߵ��ۡ�50��0<�γ̼۸��50�Ŀγ�Ȩ��+10��50<�γ̼۸��100�Ŀγ�Ȩ��+5��100<�γ̼۸��150�Ŀγ�Ȩ��+2��
	 * 50<�û�����γ���ߵ��ۡ�100��50<�γ̼۸��100�Ŀγ�Ȩ��+10��0�ܿγ̼۸��50�Ŀγ�Ȩ��+8��100<�γ̼۸��150�Ŀγ�Ȩ��+5��150<�γ̼۸��200�Ŀγ�Ȩ��+2��
	 * 100<�û�����γ���ߵ��ۡ�150��100<�γ̼۸��150�Ŀγ�Ȩ��+10��0�ܿγ̼۸��100�Ŀγ�Ȩ��+8��150<�γ̼۸��200�Ŀγ�Ȩ��+5��200<�γ̼۸��250�Ŀγ�Ȩ��+2��
	 * 150<�û�����γ���ߵ��ۡ�200��150<�γ̼۸��200�Ŀγ�Ȩ��+10��0�ܿγ̼۸��150�Ŀγ�Ȩ��+8��200<�γ̼۸��250�Ŀγ�Ȩ��+5��250<�γ̼۸��300�Ŀγ�Ȩ��+2��
	 * 200<�û�����γ���ߵ��ۣ�200<�γ̼۸�Ŀγ�Ȩ��+10��0�ܿγ̼۸��200�Ŀγ�Ȩ��+8��
	 * @param teacher
	 * @param student
	 * @return
	 */
	public int countSpendPower(Teacher teacher , Student student){
		int courseCount = 0;
		long spendPower = student.getSpendPower();
		long nowPrice = teacher.getCoursePrice();
		if(spendPower == 0 ){
			if(nowPrice == 0) {
				courseCount = 10;
			}
		} else if(spendPower > 0 && spendPower <= 50){
			if(nowPrice >= 0 && nowPrice <= 50) {
				courseCount = 10;
			} else if(nowPrice > 50 && nowPrice <= 100) {
				courseCount = 5;
			} else if(nowPrice > 100 && nowPrice <= 150) {
				courseCount = 2;
			} 
		} else if(spendPower > 50 && spendPower <= 100){
			if(nowPrice >= 0 && nowPrice <= 50) {
				courseCount = 8;
			} else if(nowPrice > 50 && nowPrice <= 100) {
				courseCount = 10;
			} else if(nowPrice > 100 && nowPrice <= 150) {
				courseCount = 5;
			} else if(nowPrice > 150 && nowPrice <= 200) {
				courseCount = 2;
			} 
		} else if(spendPower > 100 && spendPower <= 150){
			 if(nowPrice >= 0 && nowPrice <= 100) {
				courseCount = 8;
			} else if(nowPrice > 100 && nowPrice <= 150) {
				courseCount = 10;
			} else if(nowPrice > 150 && nowPrice <= 200) {
				courseCount = 5;
			} else if(nowPrice > 200 && nowPrice <= 250) {
				courseCount = 2;
			}
		} else if(spendPower > 150 && spendPower <= 200){
			if(nowPrice >= 0 && nowPrice <= 150) {
				courseCount = 8;
			} else if(nowPrice > 150 && nowPrice <= 200) {
				courseCount = 10;
			} else if(nowPrice > 200 && nowPrice <= 250) {
				courseCount = 5;
			} else if(nowPrice > 300 && nowPrice <= 300) {
				courseCount = 2;
			}
		} else if(spendPower > 200){
			if(nowPrice >= 0 && nowPrice <= 200) {
				courseCount = 8;
			} else if(nowPrice > 200) {
				courseCount = 10;
			} 
		}
		return courseCount;
	}
	
	/**
	 * ��������ָ��
	 * 0�ܷ����ڵ�ǰ��������<10,�ۺ�Ȩ�ؼ�2��
	 * 10�ܷ����ڵ�ǰ��������<30, �ۺ�Ȩ�ؼ�4��
	 * 30�ܷ����ڵ�ǰ��������<50, �ۺ�Ȩ�ؼ�6��
	 * 50�ܷ����ڵ�ǰ��������<100, �ۺ�Ȩ�ؼ�8��
	 * 100�ܷ����ڵ�ǰ��������, �ۺ�Ȩ�ؼ�10��
	 * @param teacher
	 * @return
	 */
	public int countOnline(Teacher teacher){
		int count = 0;
		long onlineNum = teacher.getOnlineNum();
		if(onlineNum >= 0 && onlineNum <= 10){
			count = 2;
		} else if(onlineNum > 10 && onlineNum <= 30){
			count = 4;
		} else if(onlineNum > 30 && onlineNum <= 50){
			count = 6;
		} else if(onlineNum > 50 && onlineNum <= 100){
			count = 8;
		} else if(onlineNum > 100){
			count = 10;
		}
		return count;
	}
	
	@Override
	public CalculationManage getCalculationManage() {
		return calculationManage;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<Teacher> getList() {
		return list;
	}

}
