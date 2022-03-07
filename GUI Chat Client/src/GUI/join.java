package GUI;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class join extends JFrame implements ActionListener, ItemListener,
		FocusListener {

	String name = null, id = null, passwd = null, gender = null, job = null,
			info = null;

	PrintWriter pw = null;
	JPanel base, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, pt;
	JLabel l1, l2, l3, l4, l5, l6, label2;
	JTextField tf1, tf2;
	JPasswordField pf;
	JButton check, ok, cancle;
	JTextArea ta;
	Choice list;
	Checkbox cb1, cb2;
	CheckboxGroup cbg;

	join() {

		init();

		// 스크린 사이즈 가져와 screenSize에 저장
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setTitle("회원가입");
		setSize(450, 400); // 창 크기
		// 프레임을 화면 중간에서 열리게 함
		setLocation((screenSize.width - 450) / 2, (screenSize.height - 400) / 2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 해당 프레임만 종료
		setVisible(true); // 화면에 보이기

	}

	public void init() {

		base = new JPanel();

		l1 = new JLabel("이      름");
		l2 = new JLabel("아 이 디");
		l3 = new JLabel("패스워드");
		l4 = new JLabel("성      별");
		l5 = new JLabel("직      업");
		l6 = new JLabel("자기소개");
		label2 = new JLabel("회원가입");

		tf1 = new JTextField(20);
		tf2 = new JTextField(20);
		pf = new JPasswordField(20);

		check = new JButton("아이디중복체크");
		ok = new JButton("회원가입");
		cancle = new JButton("가입취소");

		ta = new JTextArea(5, 20);

		list = new Choice();
		list.add("직업을선택하세요");
		list.add("공무원");
		list.add("회사원");
		list.add("연구원");
		list.add("자영업");
		list.add("학생");
		list.add("기타");

		cbg = new CheckboxGroup();
		cb1 = new Checkbox("남", false, cbg);
		cb2 = new Checkbox("여", false, cbg);

		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
		p9 = new JPanel();
		p10 = new JPanel();
		pt = new JPanel();

		/**
		 * 이벤트를 등록시킴
		 * **/
		check.addActionListener(this);
		ok.addActionListener(this);
		cancle.addActionListener(this);
		cb1.addFocusListener(this);
		cb2.addFocusListener(this);
		list.addItemListener(this);


		pt.add(label2);

		p1.add(tf1);
		p2.add(tf2);
		p2.add(check);
		p3.add(pf);
		p4.add(cb1);
		p4.add(cb2);
		p5.add(list);

		p6.setLayout(new GridLayout(5, 1));
		p6.add(l1);
		p6.add(l2);
		p6.add(l3);
		p6.add(l4);
		p6.add(l5);
		p7.setLayout(new GridLayout(5, 1));
		p7.add(p1);
		p7.add(p2);
		p7.add(p3);
		p7.add(p4);
		p7.add(p5);
		p8.setLayout(new BorderLayout());
		p8.add(l6, "West");
		p8.add(ta, "Center");

		p9.add(ok);
		p9.add(cancle);

		p10.setLayout(new BorderLayout());
		p10.add(p6, "West");
		p10.add(p7, "Center");
		p10.add(p8, "South");

		base.add(pt, "North");
		base.add(p10, "Center");
		base.add(p9, "South");

		add(base);

	}

	public void actionPerformed(ActionEvent e) { // 액션이벤트

		Object obj = e.getSource();

		//아이디 체크버튼 눌렀을 떄
		if ((JButton) obj == check) {
			check.setEnabled(false);
			id = tf2.getText();
			
			if(id.equals(""))
			{
				JOptionPane.showMessageDialog(this, "아이디를 입력하세요.", "회원가입",
						JOptionPane.WARNING_MESSAGE);
			}
			else{
			pw.println("//idcheck "+id);
			pw.flush();
			}
		}

		//확인버튼 눌렀을 때
		if ((JButton) obj == ok) {
			name = tf1.getText();
			id = tf2.getText();
			passwd = new String(pf.getPassword());
			info = ta.getText();

			if(name.equals("") || id.equals("") || passwd.equals("") || gender.equals("") || job.equals("") || info.equals(""))
			{
				JOptionPane.showMessageDialog(this, "입력되지 않은 칸이 있습니다.", "회원가입",
						JOptionPane.WARNING_MESSAGE);
			} else{
				String creatememberdata = name + "□＃◆" + id + "□＃◆" + passwd + "□＃◆"
						+ gender + "□＃◆" + job + "□＃◆" + info;

				String enter = "\n";
				int n = JOptionPane.showConfirmDialog(null,
						"정보가 모두 입력되었습니다." + enter + "계속 진행하시겠습니까?", "회원가입",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				
				if(n == 0){
				pw.println("//join□＃◆"+creatememberdata);
				pw.flush();
				}

			}
				

		}

		if ((JButton) obj == cancle) {
			setVisible(false); // 회원가입창 닫음
		}

	}

	public void focusGained(FocusEvent e) {

		if (e.getSource() == cb1)
			gender = "남자";
		else if (e.getSource() == cb2)
			gender = "여자";

	}

	public void focusLost(FocusEvent e) { // 선택하지 않을경우
	}

	public void itemStateChanged(ItemEvent e) {// Item의 상태가 변화 했을 때

		if (list.getSelectedIndex() == 0)
			job = null;
		else if (list.getSelectedIndex() == 1)
			job = "공무원";
		else if (list.getSelectedIndex() == 2)
			job = "회사원";
		else if (list.getSelectedIndex() == 3)
			job = "연구원";
		else if (list.getSelectedIndex() == 4)
			job = "자영업";
		else if (list.getSelectedIndex() == 5)
			job = "학생";
		else if (list.getSelectedIndex() == 6)
			job = "기타";
	}
}
