package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import GUI.MainGUI.ButtonData;

public class sendmessage extends JFrame implements ActionListener {

	JButton b1;
	JButton b2;
	String text;
	String ID;
	JTextArea ta;
	JLabel lb;
	boolean check = false;
	String data;
	PrintWriter pw = null;

	sendmessage() {

		init();

		setTitle("쪽지");
		setSize(250, 250); // 창 크기
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 종료버틍 활성화
		setVisible(true); // 화면에 보이기
		// 스크린 사이즈 가져와 screenSize에 저장
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 프레임을 화면 중간에서 열리게 함
		setLocation((screenSize.width - 250) / 2, (screenSize.height - 250) / 2);

	}

	public void init() {
		JPanel base = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		lb = new JLabel("보내실 메세지를 작성하세요");
		ta = new JTextArea(10, 20);
		ta.setBackground(SystemColor.inactiveCaption); // TextArea 배경색 변경
		b1 = new JButton("보내기");
		b2 = new JButton("취소");
		
		b1.addActionListener(this);
		b2.addActionListener(this);

		base.setLayout(new BorderLayout());

		p1.add(lb);
		p2.add(ta);
		p3.add(b1);
		p3.add(b2);

		base.add("North", p1);
		base.add("Center", p2);
		base.add("South", p3);

		add(base);

	}

	public void actionPerformed(ActionEvent e) { // 액션이벤트
		Object obj = e.getSource();

		//정송버튼 이벤트 처리
		if ((JButton) obj == b1) {
			pw.println("//wisper★◆" + data + "★◆" + ta.getText() + "★◆"
					+ ID);
			pw.flush();
			
			setVisible(false);

		}
		
		//취소버튼
		if ((JButton) obj == b2) {
			setVisible(false);

		}

	}

}
