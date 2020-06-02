
import java.util.Calendar;
import java.util.Date;

public class CodeStudyEvent extends Event{
	private static final long serialVersionUID = -221681851576923318L;
	private Date startDate;

	public CodeStudyEvent(String name, String[] descriptions, Date date) {
		super(name, descriptions);
		// 将时分秒清零
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		startDate = cal.getTime();
	}

	public CodeStudyEvent(String name, String[] descriptions) {
		super(name, descriptions);
		// 将时分秒清零
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		startDate = cal.getTime();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public boolean timeCheck(Date date) {
		switch (getCnt()) {
		case 0:
			// 第一遍，24小时之内
			return date.getTime() - startDate.getTime() >= 0;
		case 1:
			// 第二遍， 2-3天内
			System.out.println((new Date()).getTime() - startDate.getTime());
			return date.getTime() - startDate.getTime() >= (long) 2 * 24 * 60 * 60 * 1000;
		case 2:
			// 第三遍，5-7天
			return date.getTime() - startDate.getTime() >= (long) 5 * 24 * 60 * 60 * 1000;
		default:
			return false;
		}
	}

	@Override
	public String describe() {
		switch (getCnt()) {
		case 0:
			// 第一遍，24小时之内
			return "第一遍，课后24小时之内完成题目";
		case 1:
			// 第二遍， 2-3天内
			return "第二遍，完成题目，并describe";
		case 2:
			// 第三遍，5-7天
			return "第三遍，仔细分析，巩固提高";
		default:
			return "err 1";
		}
	}

	@Override
	public void done() {
		if(getCnt() == 2) {
			setFinished(true);
		}
		setCnt(getCnt() + 1);
	}

}
