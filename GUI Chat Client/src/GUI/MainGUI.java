package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

class MainGUI extends JFrame implements ActionListener, KeyListener {

	// OutputStream oss;
	OutputStream oss;
	String ip;

	JButton butt; // 쪽지보내기 버튼
	JButton butt2; // 방 참가 버튼
	JButton butt3; // 방 생성 버튼
	JButton butt4; // 메세지 정송버튼
	JButton butt5; // 회원 정보 수정 버튼
	JButton butt6; // 회원 탈퇴 버튼
	JButton butt7; // 파일전송
	JButton butt8; // 이모티콘

	JTextArea chatarea; // 채팅내용
	JTextPane text;
	Image img = null;
	JList list;
	PrintWriter pw = null; // 메제시 전송 객체
	JTextField tf; // 메세지 쓰는 필드
	String msg = null; // 채팅내용을 담는 변수
	String ID;
	static DefaultListModel lm;// 대기자 리스트
	HashMap user = new HashMap();
	File sourceimage;
	String jobimg;
	Vector roomname; // 방이름 저장
	Vector userlist; // 접속한 유저
	static ButtonData bt;
	ButtonData data;
	String txt;
	JList roomlist;

	HashMap imsihash = new HashMap();
	JFileChooser chooser; // 파일전송 객체

	imoticon imo;

	public class ButtonRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList lst, Object val,
				int idx, boolean isSelected, boolean hasFocus) {
			JLabel comp = null;

			if (val instanceof ButtonData) {
				/* 보여주어야 하는 데이터가 ButtonData인 경우 */
				data = (ButtonData) val;

				/*
				 * 아이템이 선택된 상태이면 문자열에 "<- Selected"를 추가한다. "
				 */
				if (isSelected) {
					txt = data.szName + " <- Selected";
				} else {
					txt = data.szName;
				}
				/* 부모 클래스의 메소드 호출 */
				comp = (JLabel) super.getListCellRendererComponent(lst, txt,
						idx, isSelected, hasFocus);
				/* 아이콘을 정한다. */
				comp.setIcon(data.xIcon);
				/* 경계선을 바꾼다. */
				comp.setBorder(new BevelBorder(BevelBorder.RAISED));
			} else if (val instanceof String) {
				/* 보여주어야 하는 데이터가 일반 문자열인 경우 */
				comp = (JLabel) super.getListCellRendererComponent(lst, val,
						idx, isSelected, hasFocus);
			}
			return comp;
		}
	}

	// 유저리스트를 추가
	public void listadd1(String u) {

		userlist.add(u);
		
		//lm.addElement(new ButtonData(u, new ImageIcon("image/check.gif")));
		list.repaint();

	}

	// 유저리스트 초기화
	public void listreset() {
		
		//lm.removeAllElements();
		list.removeAll();
	}

	public MainGUI(String jobimg) {

		this.jobimg = jobimg; // 직업을 변수에 저장

		try {
			if (jobimg.equals("공무원"))
				sourceimage = new File("image/a.JPG");
			else if (jobimg.equals("회사원"))
				sourceimage = new File("image/b.JPG");
			else if (jobimg.equals("자영업"))
				sourceimage = new File("image/d.JPG");
			else if (jobimg.equals("학생"))
				sourceimage = new File("image/e.JPG");
			else if (jobimg.equals("기타"))
				sourceimage = new File("image/f.JPG");
			else if (jobimg.equals("연구원"))
				sourceimage = new File("image/c.JPG");

			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("이미지파일이 없습니다.");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료버틍 활성화

		setTitle("채팅프로그램");
		JPanel base = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JLabel lb = new JLabel("대기자 목록");
		JLabel lb1 = new JLabel("방 목록");
		JLabel lb2 = new JLabel("채팅창");
		roomlist = new JList();
		roomname = new Vector();
		userlist = new Vector();
		roomlist.setListData(userlist);
		
		roomlist.setListData(roomname);
		butt = new JButton("쪽지 보내기");
		butt.addActionListener(this);
		JButton butt1 = new JButton("방 참가");
		butt2 = new JButton("방 참가");
		butt2.addActionListener(this);
		butt3 = new JButton("방 생성");
		butt3.addActionListener(this);
		butt4 = new JButton("전송");

		butt4.addActionListener(this);
		butt5 = new JButton("정보수정");
		butt5.addActionListener(this);
		butt6 = new JButton("회원탈퇴");
		butt6.addActionListener(this);
		butt7 = new JButton("파일전송");
		butt7.addActionListener(this);
		butt8 = new JButton("※");
		butt8.addActionListener(this);

		tf = new JTextField(30); // 메세지 쓰는 필드
		tf.addKeyListener(this);// 텍스트필드에 이벤트 등록
		base.setLayout(new BorderLayout());
		list = new JList();
		//list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lm = new DefaultListModel(); // 리스트의 내용을 담을 모델을 초기화한다.


	
		//list.setModel(lm);
		//list.setCellRenderer(new ButtonRenderer());

		text = new JTextPane();
		text.setBackground(SystemColor.inactiveCaption);

		chatarea = new JTextArea(30, 50);
		chatarea.setBackground(SystemColor.inactiveCaption); // TextArea 배경색 변경
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
		sendbutt.add(butt8);

		base.add("North", p3);
		p3.add(lb);
		base.add("Center", new JScrollPane(list));
		base.add("South", p2);
		p2.setLayout(new BorderLayout());
		p2.add("North", p4);
		p2.add("Center", p5);
		p4.add(butt);
		p4.add(butt7);
		// p4.add(butt1);
		p5.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p5.add("North", p);
		p.add(lb1);
		p5.add("Center", new JScrollPane(roomlist));
		JPanel p6 = new JPanel();
		p5.add("South", p6);

		p6.setLayout(new BorderLayout());
		JPanel p10 = new JPanel();
		p6.add("North", p10);
		p10.add(butt2);
		p10.add(butt3);
		JPanel p11 = new JPanel();
		p6.add("Center", p11);
		p11.setLayout(new BorderLayout());
		JLabel jlb = new JLabel("내 정보");
		JPanel p12 = new JPanel();
		p11.add("North", p12);
		p12.add(jlb);
		JPanel p13 = new JPanel();
		p11.add("Center", p13);
		JLabel label11 = new JLabel(new ImageIcon(img)); // 이미지 올릴 라벨
		p13.add(label11);
		JPanel p14 = new JPanel();
		p11.add("South", p14);
		p14.add(butt5);
		p14.add(butt6);

		add(first);
	}

	class ButtonData {
		String szName; // 이름
		Icon xIcon; // 이미지

		public ButtonData(String name, Icon icon) {
			szName = name;
			xIcon = icon;
		}

		public String toString() {
			return szName;
		}
	}

	public void actionPerformed(ActionEvent e) { // 액션이벤트
		Object obj = e.getSource();

		// 쪽지버튼 이벤트처리
		if ((JButton) obj == butt) {

			sendmessage s1 = new sendmessage();
			//s1.data = data.szName;
			s1.pw = pw;
			s1.ID = ID;
			s1.data =(String) list.getSelectedValue();
			
			list.clearSelection();

		}

		// 방 참가 버튼
		if ((JButton) obj == butt2) {

			String select = (String) roomlist.getSelectedValue();
			if (select != null) {// 선택한 방의 이름이 null이 아니면
				// 선택한 방이름과 ID를 서버에 보낸다.
				String joinroom = "//enterroom★●" + select + "★●" + ID;

				pw.println(joinroom);
				pw.flush();
			}

			roomlist.clearSelection();

		}

		// 방 생성 버튼
		if ((JButton) obj == butt3) {
			String name = "";
			name = JOptionPane.showInputDialog("방의 이름을 입력하세요");
			if (name != null) {// 방이름이 null이 아니면
				// 방이름과 ID를 서버에 보낸다.
				String mssage = "//roomname★●" + name + "★●" + ID;
				pw.println(mssage);
				pw.flush();

			}

		}

		// 전송버튼 이벤트처리
		if ((JButton) obj == butt4) {

			pw.println(tf.getText());
			pw.flush();
			tf.setText("");

		}

		// 정보수정버튼 이벤트처리
		if ((JButton) obj == butt5) {
			pw.println("//change " + ID);
			pw.flush();

		}

		// 회원탈퇴 이벤트처리
		if ((JButton) obj == butt6) {
			String enter = "\n";
			int n = JOptionPane.showConfirmDialog(null, "계정이 삭제됩니다." + enter
					+ "계속 진행하시겠습니까?", "회원탈퇴", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (n == 0) {
				pw.println("//delete " + ID);
				pw.flush();
			}

		}

		// 파일전송
		if ((JButton) obj == butt7) {

			// System.out.println(oss);
			chooser = new JFileChooser();

			//String select_id = data.szName;
			String select_id=(String) list.getSelectedValue();

			list.clearSelection();

			// //////////////////////////////

			// //////////////////////////////////////////////////////////

			Socket client1;

			int ret = chooser.showOpenDialog(null);
			if (ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath = chooser.getSelectedFile().getPath();
			String filename = chooser.getSelectedFile().getName();

			// System.out.println(filePath);
			pw.println("///file#" + filename + "#" + select_id +"#"+ip);
			pw.flush();

			// try {
			// client1 = new Socket("210.123.254.48", 5006); //파일전송 전용 소켓 생성
			// oss = client1.getOutputStream();
			// } catch (UnknownHostException e2) {
			// // TODO Auto-generated catch block
			// e2.printStackTrace();
			// } catch (IOException e2) {
			// // TODO Auto-generated catch block
			// e2.printStackTrace();
			// } // 소켓 생성

			try {
				// InputStream fin = new
				// FileInputStream("C:\\Users\\정보2-08\\Desktop\\백준영.jpg");
				client1 = new Socket("210.123.254.48", 5006); // 파일전송 전용 소켓 생성
				oss = client1.getOutputStream();
				InputStream fin = new FileInputStream(filePath);

				int readcount = 0;
				byte[] buffer = new byte[512];
				// System.out.println("보냄시작");
				while ((readcount = fin.read(buffer)) != -1) {
					// oss.write(buffer,0,readcount);
					oss.write(buffer, 0, readcount);
				}
				oss.flush();
				oss.close();
				fin.close();

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		if ((JButton) obj == butt8) { // 이모티콘전송
			try {
				imo = new imoticon();
				imo.pw = pw;
				imo.ID = ID;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10) { // 엔터가 눌리면
			butt4.doClick(); // ok버튼 클릭
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	void setEndline() {
		text.selectAll();
		// 문장의 끝에 무조건 커서 이동하게 설정
		text.setSelectionStart(text.getSelectionEnd());
	}

}
