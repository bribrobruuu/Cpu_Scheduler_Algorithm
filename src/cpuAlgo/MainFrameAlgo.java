package cpuAlgo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class MainFrameAlgo extends JFrame {

	private JPanel contentPane;
	private JTable proc;
	private CustomPanel chartPanel;
	private JScrollPane chartPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrameAlgo frame = new MainFrameAlgo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrameAlgo() {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("CPU Scheduling Algorithm");
		String column[] = { "Process", "Arrival Time", "Burst Time", "Priority", "Waiting Time", "Turnaround Time" };

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1107, 775);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(71, 36, 958, 107);
		contentPane.add(scrollPane);
		String data[][] = { { "", "", "", "", "", "" }, { "", "", "", "", "", "" }, { "", "", "", "", "", "" },
				{ "", "", "", "", "", "" }, { "", "", "", "", "", "" } };

		proc = new JTable(data, column);
		scrollPane.setViewportView(proc);
		proc.setEnabled(false);
		proc.getTableHeader().setReorderingAllowed(false);
		chartPanel = new CustomPanel();
		// chartPanel.setPreferredSize(new Dimension(700, 10));
		chartPanel.setBackground(Color.WHITE);
		chartPane = new JScrollPane(chartPanel);
		chartPane.setBounds(71, 327, 958, 100);

		contentPane.add(chartPane);

		JLabel lblFirstClickgenerate = new JLabel(
				"Then, Click \"Generate\" to generate random datas for the algorithm");
		lblFirstClickgenerate.setBounds(117, 232, 386, 14);
		contentPane.add(lblFirstClickgenerate);

		JLabel lblFinallyClickproceed = new JLabel("Finally, Click \"Compute\" if you are satisfied with the data");
		lblFinallyClickproceed.setBounds(633, 232, 332, 14);
		contentPane.add(lblFinallyClickproceed);

		JLabel lblDataForCpu = new JLabel("Data for Cpu Scheduling Algorithm");
		lblDataForCpu.setBounds(451, 11, 199, 14);
		contentPane.add(lblDataForCpu);

		JLabel lblSelect = new JLabel("First, Select what Algorithm do you want to view:");
		lblSelect.setBounds(403, 154, 295, 19);
		contentPane.add(lblSelect);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(427, 184, 247, 20);
		contentPane.add(comboBox);

		comboBox.addItem("First Come First Serve");
		comboBox.addItem("Shortest Job First");
		comboBox.addItem("Priority (Non Preemptive)");
		comboBox.addItem("Priority (Preemptive)");
		comboBox.addItem("Shortest Remaining Time");
		comboBox.addItem("Round Robin");

		JLabel lblAverageWaitingTime = new JLabel("Average Waiting Time:");
		lblAverageWaitingTime.setBounds(38, 579, 170, 14);
		contentPane.add(lblAverageWaitingTime);

		JLabel lblAverageTurnaroundTime = new JLabel("Average Turnaround Time:");
		lblAverageTurnaroundTime.setBounds(38, 604, 170, 14);
		contentPane.add(lblAverageTurnaroundTime);

		JLabel wtResultLabel = new JLabel("");
		wtResultLabel.setBounds(238, 579, 170, 14);
		contentPane.add(wtResultLabel);

		JLabel tatResultLabel = new JLabel("");
		tatResultLabel.setBounds(238, 604, 199, 14);
		contentPane.add(tatResultLabel);

		JLabel lblProjectBy = new JLabel("Project by:");
		lblProjectBy.setBounds(745, 554, 81, 14);
		contentPane.add(lblProjectBy);

		JLabel lblBrianJamesDuquiatan = new JLabel("Brian James Duquiatan");
		lblBrianJamesDuquiatan.setBounds(745, 579, 199, 14);
		contentPane.add(lblBrianJamesDuquiatan);

		JLabel lblRonCarlAlfon = new JLabel("Ron Carl Alfon Yu");
		lblRonCarlAlfon.setBounds(745, 604, 182, 14);
		contentPane.add(lblRonCarlAlfon);

		JLabel lblAnyaTirazona = new JLabel("Anya Tirazona");
		lblAnyaTirazona.setBounds(745, 629, 199, 14);
		contentPane.add(lblAnyaTirazona);

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(200, 474, 182, 59);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Row row = new Row();
				main(null);
				setVisible(false);

				// System.exit(1);
				// Row row=Utility.deepDelete();
			}
		});
		JButton btnStart = new JButton("Generate");
		btnStart.setBounds(200, 257, 182, 59);

		JButton btnCompute = new JButton("Compute");
		btnCompute.setBounds(717, 257, 182, 59);
		btnCompute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBox.getSelectedItem();
				CPUScheduler scheduler;

				switch (selected) {
				case "First Come First Serve":
					scheduler = new FirstComeFirstServe();
					break;
				case "Shortest Job First":
					scheduler = new ShortestJobFirst();
					break;
				case "Shortest Remaining Time":
					scheduler = new ShortestRemainingTime();
					break;
				case "Priority (Non Preemptive)":
					scheduler = new PriorityNonPreemptive();
					break;
				case "Priority (Preemptive)":
					scheduler = new PriorityPreemptive();
					break;
				case "Round Robin":
					String tq = JOptionPane.showInputDialog("Time Quantum");
					if (tq == null) {
						return;
					}
					scheduler = new RoundRobin();
					scheduler.setTimeQuantum(Integer.parseInt(tq));
					break;
				default:
					return;
				}

				for (int i = 0; i < proc.getRowCount(); i++) {
					String process = (String) proc.getValueAt(i, 0);
					int at = Integer.parseInt((String) proc.getValueAt(i, 1));
					int bt = Integer.parseInt((String) proc.getValueAt(i, 2));
					int pl;

					if (selected.equals("PSN") || selected.equals("PSP")) {
						if (!proc.getValueAt(i, 3).equals("")) {
							pl = Integer.parseInt((String) proc.getValueAt(i, 3));
						} else {
							pl = 1;
						}
					} else {
						pl = 1;
					}

					scheduler.add(new Row(process, at, bt, pl));
				}

				scheduler.process();

				for (int i = 0; i < proc.getRowCount(); i++) {
					String process = (String) proc.getValueAt(i, 0);
					Row row = scheduler.getRow(process);

					proc.setValueAt(String.valueOf(row.getWaitingTime()), i, 4);
					proc.setValueAt(String.valueOf(row.getTurnaroundTime()), i, 5);
					proc.getTableHeader().setReorderingAllowed(false);
				}

				wtResultLabel.setText(Double.toString(scheduler.getAverageWaitingTime()));
				tatResultLabel.setText(Double.toString(scheduler.getAverageTurnAroundTime()));

				chartPanel.setTimeline(scheduler.getTimeline());
				btnStart.setEnabled(false);
				btnCompute.setEnabled(false);
				proc.getTableHeader().setReorderingAllowed(false);
			}

		});

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp[][];
				if (comboBox.getSelectedItem().equals("Priority (Non Preemptive)")
						|| comboBox.getSelectedItem().equals("Priority (Preemptive)")) {

					temp = randomizePriority();

				} else {
					temp = randomize();
				}

				proc = new JTable(temp, column);
				scrollPane.setViewportView(proc);
				proc.setEnabled(false);
				proc.getTableHeader().setReorderingAllowed(false);
				// proc.setAutoCreateColumnsFromModel(false);
				btnCompute.setEnabled(true);
				chartPanel.removeAll();
				// add your elements
				revalidate();
				repaint();
			}
		});

		contentPane.add(btnStart);
		btnCompute.setForeground(new Color(255, 255, 255));
		btnCompute.setEnabled(false);
		btnCompute.setBackground(new Color(50, 205, 50));
		contentPane.add(btnCompute);
		contentPane.add(btnReset);

		JLabel lblresetToRestart = new JLabel("\"Reset\" to restart the program.");
		lblresetToRestart.setBounds(200, 449, 182, 14);
		contentPane.add(lblresetToRestart);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(ABORT);
			}
		});
		btnClose.setBounds(717, 474, 182, 59);
		contentPane.add(btnClose);

		JLabel lblcloseToClose = new JLabel("\"Close\" to close the program.");
		lblcloseToClose.setBounds(717, 449, 168, 14);
		contentPane.add(lblcloseToClose);
		proc.getTableHeader().setReorderingAllowed(false);
		proc.setAutoCreateColumnsFromModel(false);
	}

	class CustomPanel extends JPanel {
		private List<Event> timeline;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (timeline != null) {
				// int width = 30;

				for (int i = 0; i < timeline.size(); i++) {
					Event event = timeline.get(i);
					int x = 30 * (i + 1);
					int y = 20;

					g.drawRect(x, y, 30, 30);
					g.setFont(new Font("Segoe UI", Font.BOLD, 13));
					g.drawString(event.getProcessName(), x + 10, y + 20);
					g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
					g.drawString(Integer.toString(event.getStartTime()), x - 5, y + 45);

					if (i == timeline.size() - 1) {

						g.drawString(Integer.toString(event.getFinishTime()), x + 27, y + 45);

					}

				}

				// width += 30;

				// this.setPreferredSize(new Dimension(width, 75));
			}
		}

		public void setTimeline(List<Event> timeline) {
			this.timeline = timeline;
			repaint();
		}
	}

	public String[][] randomize() {
		Random rand = new Random();
		String data[][] = { { "A", "0", "0", "", "", "" }, { "B", "0", "0", "", "", "" }, { "C", "0", "0", "", "", "" },
				{ "D", "0", "0", "", "", "" }, { "E", "0", "0", "", "", "" } };

		for (int x = 0; x < 5; x++) {
			data[x][1] = String.valueOf(rand.nextInt(7));
			data[x][2] = String.valueOf(rand.nextInt(10) + 1);

		}
		return data;
	}

	public String[][] randomizePriority() {
		Random rand = new Random();
		String data[][] = { { "A", "0", "0", "0", "", "" }, { "B", "0", "0", "0", "", "" },
				{ "C", "0", "0", "0", "", "" }, { "D", "0", "0", "0", "", "" }, { "E", "0", "0", "0", "", "" } };

		for (int x = 0; x < 5; x++) {
			data[x][1] = String.valueOf(rand.nextInt(7));
			data[x][2] = String.valueOf(rand.nextInt(10) + 1);
			data[x][3] = String.valueOf(rand.nextInt(5) + 1);

		}
		return data;
	}
}

abstract class CPUScheduler {
	private final List<Row> rows;
	private final List<Event> timeline;
	private int timeQuantum;

	public CPUScheduler() {
		rows = new ArrayList();
		timeline = new ArrayList();
		timeQuantum = 1;
	}

	public boolean add(Row row) {
		return rows.add(row);
	}

	public void setTimeQuantum(int timeQuantum) {
		this.timeQuantum = timeQuantum;
	}

	public int getTimeQuantum() {
		return timeQuantum;
	}

	public double getAverageWaitingTime() {
		double avg = 0.0;

		for (Row row : rows) {
			avg += row.getWaitingTime();
		}

		return avg / rows.size();
	}

	public double getAverageTurnAroundTime() {
		double avg = 0.0;

		for (Row row : rows) {
			avg += row.getTurnaroundTime();
		}

		return avg / rows.size();
	}

	public Event getEvent(Row row) {
		for (Event event : timeline) {
			if (row.getProcessName().equals(event.getProcessName())) {
				return event;
			}
		}

		return null;
	}

	public Row getRow(String process) {
		for (Row row : rows) {
			if (row.getProcessName().equals(process)) {
				return row;
			}
		}

		return null;
	}

	public void clearRows() {
		rows.clear();
		timeline.clear();
	}

	public List<Row> getRows() {
		return rows;
	}

	public List<Event> getTimeline() {
		return timeline;
	}

	public abstract void process();
}

class Event {
	private final String processName;
	private final int startTime;
	private int finishTime;

	public Event(String processName, int startTime, int finishTime) {
		this.processName = processName;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}

	public String getProcessName() {
		return processName;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
}

class Row {
	private String processName;
	private int arrivalTime;
	private int burstTime;
	private int priorityLevel;
	private int waitingTime;
	private int turnaroundTime;

	private Row(String processName, int arrivalTime, int burstTime, int priorityLevel, int waitingTime,
			int turnaroundTime) {
		this.processName = processName;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priorityLevel = priorityLevel;
		this.waitingTime = waitingTime;
		this.turnaroundTime = turnaroundTime;
	}

	public Row() {
		processName = "";
		arrivalTime = 0;
		burstTime = 0;
		priorityLevel = 0;
		waitingTime = 0;
		turnaroundTime = 0;
	}

	public Row(String processName, int arrivalTime, int burstTime, int priorityLevel) {
		this(processName, arrivalTime, burstTime, priorityLevel, 0, 0);
	}

	public Row(String processName, int arrivalTime, int burstTime) {
		this(processName, arrivalTime, burstTime, 0, 0, 0);
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public String getProcessName() {
		return this.processName;
	}

	public int getArrivalTime() {
		return this.arrivalTime;
	}

	public int getBurstTime() {
		return this.burstTime;
	}

	public int getPriorityLevel() {
		return this.priorityLevel;
	}

	public int getWaitingTime() {
		return this.waitingTime;
	}

	public int getTurnaroundTime() {
		return this.turnaroundTime;
	}
}

class Utility {
	public static List<Row> deepCopy(List<Row> oldList) {
		List<Row> newList = new ArrayList();

		for (Row row : oldList) {
			newList.add(
					new Row(row.getProcessName(), row.getArrivalTime(), row.getBurstTime(), row.getPriorityLevel()));
		}

		return newList;
	}

	public static void deepDelete(List<Row> oldList) {
		List<Row> newList = new ArrayList();
		for (Row row : oldList) {
			newList.remove(row);
		}

	}
}

class FirstComeFirstServe extends CPUScheduler {
	@Override
	public void process() {
		Collections.sort(this.getRows(), (Object o1, Object o2) -> {
			if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
				return 0;
			} else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
				return -1;
			} else {
				return 1;
			}
		});

		List<Event> timeline = this.getTimeline();

		for (Row row : this.getRows()) {
			if (timeline.isEmpty()) {
				timeline.add(new Event(row.getProcessName(), row.getArrivalTime(),
						row.getArrivalTime() + row.getBurstTime()));
			} else {
				Event event = timeline.get(timeline.size() - 1);
				timeline.add(new Event(row.getProcessName(), event.getFinishTime(),
						event.getFinishTime() + row.getBurstTime()));
			}
		}

		for (Row row : this.getRows()) {
			row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
			row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
		}
	}
}

class PriorityNonPreemptive extends CPUScheduler {
	@Override
	public void process() {
		Collections.sort(this.getRows(), (Object o1, Object o2) -> {
			if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
				return 0;
			} else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
				return -1;
			} else {
				return 1;
			}
		});

		List<Row> rows = Utility.deepCopy(this.getRows());
		int time = rows.get(0).getArrivalTime();

		while (!rows.isEmpty()) {
			List<Row> availableRows = new ArrayList();

			for (Row row : rows) {
				if (row.getArrivalTime() <= time) {
					availableRows.add(row);
				}
			}

			Collections.sort(availableRows, (Object o1, Object o2) -> {
				if (((Row) o1).getPriorityLevel() == ((Row) o2).getPriorityLevel()) {
					return 0;
				} else if (((Row) o1).getPriorityLevel() < ((Row) o2).getPriorityLevel()) {
					return -1;
				} else {
					return 1;
				}
			});

			Row row = availableRows.get(0);
			this.getTimeline().add(new Event(row.getProcessName(), time, time + row.getBurstTime()));
			time += row.getBurstTime();

			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).getProcessName().equals(row.getProcessName())) {
					rows.remove(i);
					break;
				}
			}
		}

		for (Row row : this.getRows()) {
			row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
			row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
		}
	}
}

class PriorityPreemptive extends CPUScheduler {
	@Override
	public void process() {
		Collections.sort(this.getRows(), (Object o1, Object o2) -> {
			if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
				return 0;
			} else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
				return -1;
			} else {
				return 1;
			}
		});

		List<Row> rows = Utility.deepCopy(this.getRows());
		int time = rows.get(0).getArrivalTime();

		while (!rows.isEmpty()) {
			List<Row> availableRows = new ArrayList();

			for (Row row : rows) {
				if (row.getArrivalTime() <= time) {
					availableRows.add(row);
				}
			}

			Collections.sort(availableRows, (Object o1, Object o2) -> {
				if (((Row) o1).getPriorityLevel() == ((Row) o2).getPriorityLevel()) {
					return 0;
				} else if (((Row) o1).getPriorityLevel() < ((Row) o2).getPriorityLevel()) {
					return -1;
				} else {
					return 1;
				}
			});

			Row row = availableRows.get(0);
			this.getTimeline().add(new Event(row.getProcessName(), time, ++time));
			row.setBurstTime(row.getBurstTime() - 1);

			if (row.getBurstTime() == 0) {
				for (int i = 0; i < rows.size(); i++) {
					if (rows.get(i).getProcessName().equals(row.getProcessName())) {
						rows.remove(i);
						break;
					}
				}
			}
		}

		for (int i = this.getTimeline().size() - 1; i > 0; i--) {
			List<Event> timeline = this.getTimeline();

			if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName())) {
				timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());
				timeline.remove(i);
			}
		}

		Map map = new HashMap();

		for (Row row : this.getRows()) {
			map.clear();

			for (Event event : this.getTimeline()) {
				if (event.getProcessName().equals(row.getProcessName())) {
					if (map.containsKey(event.getProcessName())) {
						int w = event.getStartTime() - (int) map.get(event.getProcessName());
						row.setWaitingTime(row.getWaitingTime() + w);
					} else {
						row.setWaitingTime(event.getStartTime() - row.getArrivalTime());
					}

					map.put(event.getProcessName(), event.getFinishTime());
				}
			}

			row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
		}
	}
}

class RoundRobin extends CPUScheduler {
	@Override
	public void process() {
		Collections.sort(this.getRows(), (Object o1, Object o2) -> {
			if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
				return 0;
			} else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
				return -1;
			} else {
				return 1;
			}
		});

		List<Row> rows = Utility.deepCopy(this.getRows());
		int time = rows.get(0).getArrivalTime();
		int timeQuantum = this.getTimeQuantum();

		while (!rows.isEmpty()) {
			Row row = rows.get(0);
			int bt = (row.getBurstTime() < timeQuantum ? row.getBurstTime() : timeQuantum);
			this.getTimeline().add(new Event(row.getProcessName(), time, time + bt));
			time += bt;
			rows.remove(0);

			if (row.getBurstTime() > timeQuantum) {
				row.setBurstTime(row.getBurstTime() - timeQuantum);

				for (int i = 0; i < rows.size(); i++) {
					if (rows.get(i).getArrivalTime() > time) {
						rows.add(i, row);
						break;
					} else if (i == rows.size() - 1) {
						rows.add(row);
						break;
					}
				}
			}
		}

		Map map = new HashMap();

		for (Row row : this.getRows()) {
			map.clear();

			for (Event event : this.getTimeline()) {
				if (event.getProcessName().equals(row.getProcessName())) {
					if (map.containsKey(event.getProcessName())) {
						int w = event.getStartTime() - (int) map.get(event.getProcessName());
						row.setWaitingTime(row.getWaitingTime() + w);
					} else {
						row.setWaitingTime(event.getStartTime() - row.getArrivalTime());
					}

					map.put(event.getProcessName(), event.getFinishTime());
				}
			}

			row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
		}
	}
}

class ShortestJobFirst extends CPUScheduler {
	@Override
	public void process() {
		Collections.sort(this.getRows(), (Object o1, Object o2) -> {
			if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
				return 0;
			} else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
				return -1;
			} else {
				return 1;
			}
		});

		List<Row> rows = Utility.deepCopy(this.getRows());
		int time = rows.get(0).getArrivalTime();

		while (!rows.isEmpty()) {
			List<Row> availableRows = new ArrayList();

			for (Row row : rows) {
				if (row.getArrivalTime() <= time) {
					availableRows.add(row);
				}
			}

			Collections.sort(availableRows, (Object o1, Object o2) -> {
				if (((Row) o1).getBurstTime() == ((Row) o2).getBurstTime()) {
					return 0;
				} else if (((Row) o1).getBurstTime() < ((Row) o2).getBurstTime()) {
					return -1;
				} else {
					return 1;
				}
			});

			Row row = availableRows.get(0);
			this.getTimeline().add(new Event(row.getProcessName(), time, time + row.getBurstTime()));
			time += row.getBurstTime();

			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).getProcessName().equals(row.getProcessName())) {
					rows.remove(i);
					break;
				}
			}
		}

		for (Row row : this.getRows()) {
			row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
			row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
		}
	}
}

class ShortestRemainingTime extends CPUScheduler {
	@Override
	public void process() {
		Collections.sort(this.getRows(), (Object o1, Object o2) -> {
			if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
				return 0;
			} else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
				return -1;
			} else {
				return 1;
			}
		});

		List<Row> rows = Utility.deepCopy(this.getRows());
		int time = rows.get(0).getArrivalTime();

		while (!rows.isEmpty()) {
			List<Row> availableRows = new ArrayList();

			for (Row row : rows) {
				if (row.getArrivalTime() <= time) {
					availableRows.add(row);
				}
			}

			Collections.sort(availableRows, (Object o1, Object o2) -> {
				if (((Row) o1).getBurstTime() == ((Row) o2).getBurstTime()) {
					return 0;
				} else if (((Row) o1).getBurstTime() < ((Row) o2).getBurstTime()) {
					return -1;
				} else {
					return 1;
				}
			});

			Row row = availableRows.get(0);
			this.getTimeline().add(new Event(row.getProcessName(), time, ++time));
			row.setBurstTime(row.getBurstTime() - 1);

			if (row.getBurstTime() == 0) {
				for (int i = 0; i < rows.size(); i++) {
					if (rows.get(i).getProcessName().equals(row.getProcessName())) {
						rows.remove(i);
						break;
					}
				}
			}
		}

		for (int i = this.getTimeline().size() - 1; i > 0; i--) {
			List<Event> timeline = this.getTimeline();

			if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName())) {
				timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());
				timeline.remove(i);
			}
		}

		Map map = new HashMap();

		for (Row row : this.getRows()) {
			map.clear();

			for (Event event : this.getTimeline()) {
				if (event.getProcessName().equals(row.getProcessName())) {
					if (map.containsKey(event.getProcessName())) {
						int w = event.getStartTime() - (int) map.get(event.getProcessName());
						row.setWaitingTime(row.getWaitingTime() + w);
					} else {
						row.setWaitingTime(event.getStartTime() - row.getArrivalTime());
					}

					map.put(event.getProcessName(), event.getFinishTime());
				}
			}

			row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
		}
	}
}
