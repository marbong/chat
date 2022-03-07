package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MenuBar;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class chatroom extends JFrame implements ActionListener, ItemListener,
		WindowListener, FocusListener,KeyListener {

	PrintWriter pw;
	romimoticon imo;
	
	String ID;
	String roomname;

	JButton butt4;
	JButton butt5;
	JButton butt7;
	
	JTextField tf;
	JTextArea chatarea;
	JTextPane text;
	
	JList roomlist;
	Vector all = new Vector();

	chatroom() {

		init();

		// 스크린 사이즈 가져와 screenSize에 저장
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setSize(600, 500); // 창 크기
		// 프레임을 화면 중간에서 열리게 함
		setLocation((screenSize.width - 600) / 2, (screenSize.height - 500) / 2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 해당 프레임만 종료
		setVisible(true); // 화면에 보이기

	}

	public void init() {

		addWindowListener(this);

		JPanel base = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JLabel lb = new JLabel("모든 채팅유저 목록");
		JLabel lb1 = new JLabel("참여자 목록");
		JLabel lb2 = new JLabel("채팅창");
		roomlist = new JList();// 방참가자



		butt4 = new JButton("전송");
		butt4.addActionListener(this);
		butt5 = new JButton("쪽지 보내기");
		butt5.addActionListener(this);
		butt7 = new JButton("※");
		butt7.addActionListener(this);
		
		tf = new JTextField(20); // 메세지 쓰는 필드
		tf.addKeyListener(this);// 텍스트필드에 이벤트 등록
		base.setLayout(new BorderLayout());

		//chatarea = new JTextArea(20, 25);
		//chatarea.setBackground(SystemColor.inactiveCaption); // TextArea 배경색 변경
		text = new JTextPane();
		text.setBackground(SystemColor.inactiveCaption);
		JPanel p7 = new JPanel();
		JPanel base2 = new JPanel();
		MenuBar menu = new MenuBar(); // 메뉴바

		JPanel first = new JPanel();
		first.setLayout(new BorderLayout());
		first.add("West", base);
		first.add("Center", base2);
		JPanel ppp = new JPanel();

		base2.setLayout(new BorderLayout());
		JPanel chat = new JPanel();
		base2.add("North", chat);
		chat.add(lb2);
		base2.add("Center", new JScrollPane(text));
		JPanel sendbutt = new JPanel();
		base2.add("South", sendbutt);
		sendbutt.add(tf);
		sendbutt.add(butt4);
		sendbutt.add(butt7);

		base.add("North", p3);
		p3.add(lb1);
		base.add("Center", new JScrollPane(roomlist));
		base.add("South", p2);
		p2.setLayout(new BorderLayout());
		p2.add("North", p5);

		p5.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		JPanel p6 = new JPanel();
		
		JPanel p14 = new JPanel();
		p5.add("South", p14);
		p14.add(butt5);
		

		JPanel p10 = new JPanel();

		JPanel p11 = new JPanel();
		p6.add("Center", p11);
		p11.setLayout(new BorderLayout());
		JPanel p12 = new JPanel();
		p11.add("North", p12);
		JPanel p13 = new JPanel();
		//p13.add(butt7);
		p11.add("Center", p13);

		add(first);

	}

	public void actionPerformed(ActionEvent e) { // 액션이벤트
		Object obj = e.getSource();
		
		
		// 전송버튼 이벤트 처리
		if ((JButton) obj == butt4) {
			String msg = tf.getText();
			tf.setText("");

			//System.out.println(msg);
			if(msg.equals(null) || msg.equals(" ") || msg.equals("")){  //아무것도 입력 안하면 전송 안됨
			}
			else{
				
			pw.println("///roommsg●"+roomname+"●"+msg+ "●"+ ID);
			pw.flush();
			}
		}

		//쪽지보내기
		if ((JButton) obj == butt5) {
			
			sendmessage s1 = new sendmessage();  //쪽지보내기창 생성
			s1.data = (String)roomlist.getSelectedValue();  //보낼사람 ID 
			s1.pw = pw;  //printWriter 
			s1.ID = ID;  //보낸사람 ID
			roomlist.clearSelection();//리스트 눌린거 초기화
		}
		
		if ((JButton) obj == butt7) {
			try {
				imo = new romimoticon();
				imo.pw = pw;
				imo.ID = ID;
				imo.roomname = roomname;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) { // 선택하지 않을경우
	}

	public void itemStateChanged(ItemEvent e) {// Item의 상태가 변화 했을 때

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		String msg = "//exitroom " + ID + " " + roomname;
		pw.println(msg);
		pw.flush();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10) { // 엔터가 눌리면
			butt4.doClick(); // ok버튼 클릭
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	void setEndline() {
		text.selectAll();
		//문장의 끝에 무조건 커서 이동하게 설정
		text.setSelectionStart(text.getSelectionEnd());
		}
}
