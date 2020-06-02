
import java.util.Calendar;
import java.util.Date;

public class CodeStudyEvent extends Event{
	private static final long serialVersionUID = -221681851576923318L;
	private Date startDate;

	public CodeStudyEvent(String name, String[] descriptions, Date date) {
		super(name, descriptions);
		// ��ʱ��������
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
		// ��ʱ��������
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
			// ��һ�飬24Сʱ֮��
			return date.getTime() - startDate.getTime() >= 0;
		case 1:
			// �ڶ��飬 2-3����
			System.out.println((new Date()).getTime() - startDate.getTime());
			return date.getTime() - startDate.getTime() >= (long) 2 * 24 * 60 * 60 * 1000;
		case 2:
			// �����飬5-7��
			return date.getTime() - startDate.getTime() >= (long) 5 * 24 * 60 * 60 * 1000;
		default:
			return false;
		}
	}

	@Override
	public String describe() {
		switch (getCnt()) {
		case 0:
			// ��һ�飬24Сʱ֮��
			return "��һ�飬�κ�24Сʱ֮�������Ŀ";
		case 1:
			// �ڶ��飬 2-3����
			return "�ڶ��飬�����Ŀ����describe";
		case 2:
			// �����飬5-7��
			return "�����飬��ϸ�������������";
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
