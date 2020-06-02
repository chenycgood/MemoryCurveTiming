import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPanel {
	private ArrayList<Event> events;
	private JFrame mPanel;
	private JPanel ctnt;

	@SuppressWarnings("unchecked")
	public MainPanel() {
		// 读取数据
		try {
			File f = new File("code_timing_data");
			if (f.exists()) {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object obj = ois.readObject();
				ois.close();
				fis.close();
				if (obj instanceof ArrayList) {
					events = (ArrayList<Event>) obj;
				}
			} else {
				events = new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MainPanel m = new MainPanel();

		// 主面板
		m.mPanel = new JFrame("code Timing");
		m.mPanel.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// 关闭前存档
				m.events.removeIf(event -> (event.isFinished()));
				File file = new File("code_timing_data");
				try {
					if (file.exists()) {
						file.delete();

					}
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(m.events);
					oos.close();
					fos.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);

			}

		});
		m.mPanel.setSize(700, 400);
		JButton addEvent = new JButton("点击添加新的事件");
		addEvent.addActionListener(m.new AddAction());
		m.ctnt = m.generateCtnt(new Date());
		JLabel hd = new JLabel("今日待完成任务(单击任务卡标记为已完成)");
		hd.setFont(new java.awt.Font("Dialog", 1, 30));
		m.mPanel.getContentPane().add(BorderLayout.NORTH, hd);
		m.mPanel.getContentPane().add(BorderLayout.CENTER, m.ctnt);
		m.mPanel.getContentPane().add(BorderLayout.SOUTH, addEvent);
		m.mPanel.setVisible(true);
	}

	private JPanel generateCtnt(Date date) {
		JPanel ctnt = new JPanel();
		ctnt.setSize(300, 300);
		for (Event event : events) {
			if (event.timeCheck(date)) {
				EventBlog blog = new EventBlog(event);
				blog.addActionListener(new DoneAction());
				ctnt.add(blog);
			}
		}
		return ctnt;
	}

	private void update() {
		JPanel newCtnt = generateCtnt(new Date());
		ctnt.removeAll();
		mPanel.getContentPane().remove(ctnt);
		ctnt = newCtnt;
		mPanel.getContentPane().add(BorderLayout.CENTER, ctnt);
		mPanel.invalidate();
		mPanel.validate();
	}

	class AddAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String eventName = JOptionPane.showInputDialog(null, "请输入事件名称，例：算法课第一节binary search");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			String time = JOptionPane.showInputDialog("请输入开始时间，格式“MM:DD”",
					"" + (cal.get(Calendar.MONTH) + 1) + ":" + cal.get(Calendar.DAY_OF_MONTH));
			if ( time != null&& time.length() != 0) {
				String[] sp = time.split(":");
				cal.set(Calendar.MONTH, Integer.parseInt(sp[0]) - 1);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sp[1]));
			}
			date = cal.getTime();
			if (eventName != null  && eventName.length()!= 0) {
				events.add(new CodeStudyEvent(eventName, null, date));
			}
			update();
		}
	}

	class DoneAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((EventBlog) (e.getSource())).getEvent().done();
			update();
		}
	}

}
