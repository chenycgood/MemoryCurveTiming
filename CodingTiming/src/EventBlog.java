import javax.swing.JButton;

public class EventBlog extends JButton {
	private static final long serialVersionUID = 1L;
	private Event event;
	
	
	public EventBlog(Event event) {
		super("<html>" + event.getName() + "<br>" + event.describe() + "</html>");
		this.event = event;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

}
