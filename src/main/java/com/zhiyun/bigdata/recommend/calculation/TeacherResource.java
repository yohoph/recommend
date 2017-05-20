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
	 * 计算订单的权值，如果该学生购买过此老师所在年级或者所在科目的课程。则此老师相关的年级和班级权重有增加
	 * 用户购买了a年级b科目课程，则a年级权重+8，b科目权重+6；
	 * 用户购买但未支付a年级b科目课程，则a年级权重+6，b科目权重+4；
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countOrder(Teacher teacher , String userId){
		int gradeCount = 0; //年级权重
		int subjectCount = 0; //科目权重
		List<Order> orders = recommendService.getOrder(userId);
		if(orders != null && !orders.isEmpty()){
			for (Order order : orders) {
				Course course = recommendService.getCourse(order.getCourseId());
				if(course != null){
					if(teacher.getGradeId().equals(course.getGradeId())){
						if(order.getCompleteTime() != null){
							if(gradeCount < 8){//不足8点权重的，把年级权重设为6
								gradeCount = 8;
							}
						} else {
							if(gradeCount < 6){ //不足6点权重的，把科目权重设为6
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
							if(subjectCount < 4){ //不足6点权重的，把年级权重设为6
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
	 * 计算关注维度，如果该用户关注过该老师，则该年级权重+8，对该科目权重+6；
	 * 
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countAttention(Teacher teacher ,String userId){
		int gradeCount = 0; //年级权重
		int subjectCount = 0; //科目权重
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
	 * 计算老师等级的权值
	 * 0≤老师等级<3,综合权重加1；
	 * 3≤老师等级<5,综合权重加2；
	 * 5≤老师等级<10,综合权重加3；
	 * 10≤老师等级<15,综合权重加4；
	 * 15≤老师等级<20,综合权重加5；
	 * 20≤老师等级<25,综合权重加6；
	 * 25≤老师等级<30,综合权重加7；
	 * 30≤老师等级<35,综合权重加8；
	 * 35≤老师等级,综合权重加10；
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
	 * 计算浏览记录维度，通过统计年级数来统计权值
	 * 1次≤同年级<3次,该年级老师的内容权重加2；
	 * 3次≤同年级<5次, 该年级老师的内容权重加5；
	 * 5次≤同年级<10次, 该年级老师的内容权重加7；
	 * 10次≤同年级, 该年级老师的内容权重加9；
	 * 1次≤同科目,该年级老师的内容权重加2；
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countScanLog(Teacher teacher , String userId){
		int gradeCount = 0; //年级权重
		int subjectCount = 0; //科目权重
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
	 * 计算停留时间权重
	 * 0min<观看时长≤10min，则对该课程年级权重+2，科目权重+1；
	 * 10min<观看时长≤20min，则对该课程年级权重+3，科目权重+2；
	 * 20min<观看时长≤30min，则对该课程年级权重+7，科目权重+4；
	 * 30min<观看时长，则对该课程年级权重+8，科目权重+5；
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countStay(Teacher teacher , String userId){
		int gradeCount = 0; //年级权重
		int subjectCount = 0; //科目权重
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
	 * 计算GPS距离
	 * 0km≤距离≤3km,综合权重+7;  
	 * 3km<距离≤5km, 综合权重+6;
	 * 5km<距离≤10km, 综合权重+5;
	 * 10km<距离≤20km, 综合权重+4;
	 * 20km<距离≤30km, 综合权重+3;
	 * 30km<距离≤50km, 综合权重+2;
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
	 * 计算老师参与人数维度
	 * 3≤参加课程人数≤5，综合权重+2；
	 * 5<参加课程人数≤10，综合权重+3；
	 * 10<参加课程人数≤20，综合权重+5；
	 * 20<参加课程人数≤30，综合权重+7；
	 * 30<参加课程人数≤40，综合权重+8；
	 * 40<参加课程人数≤50，综合权重+9；
	 * 50<参加课程人数，综合权重+10；
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
	 * 计算消费指数
	 * 消费金额=0，免费课程权重+10；
	 * 0<用户购买课程最高单价≤50，0<课程价格≤50的课程权重+10，50<课程价格≤100的课程权重+5，100<课程价格≤150的课程权重+2；
	 * 50<用户购买课程最高单价≤100，50<课程价格≤100的课程权重+10，0≤课程价格≤50的课程权重+8，100<课程价格≤150的课程权重+5，150<课程价格≤200的课程权重+2；
	 * 100<用户购买课程最高单价≤150，100<课程价格≤150的课程权重+10，0≤课程价格≤100的课程权重+8，150<课程价格≤200的课程权重+5，200<课程价格≤250的课程权重+2；
	 * 150<用户购买课程最高单价≤200，150<课程价格≤200的课程权重+10，0≤课程价格≤150的课程权重+8，200<课程价格≤250的课程权重+5，250<课程价格≤300的课程权重+2；
	 * 200<用户购买课程最高单价，200<课程价格的课程权重+10，0≤课程价格≤200的课程权重+8。
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
	 * 计算在线指数
	 * 0≤房间内当前在线人数<10,综合权重加2；
	 * 10≤房间内当前在线人数<30, 综合权重加4；
	 * 30≤房间内当前在线人数<50, 综合权重加6；
	 * 50≤房间内当前在线人数<100, 综合权重加8；
	 * 100≤房间内当前在线人数, 综合权重加10；
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
