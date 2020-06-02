import java.io.Serializable;
import java.util.Date;

public abstract class Event implements Serializable{
	
	private static final long serialVersionUID = -874730235304764569L;
	private String name;
	private String[] descriptions;
	private int cnt = 0;
	private boolean isFinished = false;
	
	public Event(String name, String[] descriptions) {
		super();
		this.name = name;
		this.descriptions = descriptions;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String[] descriptions) {
		this.descriptions = descriptions;
	}
	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public abstract boolean  timeCheck(Date date);
	public abstract String describe();
    public abstract void done();
}
