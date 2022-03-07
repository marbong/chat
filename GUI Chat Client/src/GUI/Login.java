package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Login extends JFrame implements Runnable, ActionListener,
		KeyListener {

	/** 이모티콘 생성 **/
	ImageIcon i1 = new ImageIcon("image/angree.png");
	ImageIcon i2 = new ImageIcon("image/bigsmile.png");
	ImageIcon i3 = new ImageIcon("image/cry.png");
	ImageIcon i4 = new ImageIcon("image/hart.png");
	ImageIcon i5 = new ImageIcon("image/moon.png");
	ImageIcon i6 = new ImageIcon("image/nn.png");
	ImageIcon i7 = new ImageIcon("image/ok.png");
	ImageIcon i8 = new ImageIcon("image/oppse.png");
	ImageIcon i9 = new ImageIcon("image/pumpkin.png");
	ImageIcon i10 = new ImageIcon("image/suck.png");
	ImageIcon i11 = new ImageIcon("image/Qlwla.png");
	ImageIcon i12 = new ImageIcon("image/shy.png");
	ImageIcon i13 = new ImageIcon("image/smile.png");
	ImageIcon i14 = new ImageIcon("image/star.png");
	ImageIcon i15 = new ImageIcon("image/victory.png");
	ImageIcon i16 = new ImageIcon("image/chikin.png");

	Vector v = new Vector();

	String Login_id, Login_passwd, Login_ip;
	String name, id, passwd, gender, job, infol;
	// 로그인이 정상적으로 되었는지 체크
	public Boolean join_Login_Check = false;
	join join_create = null;

	Image img = null;
	JButton ok;
	JButton create;
	JTextField input_ID;
	JPasswordField input_password;
	JTextField input_IP;
	Socket client; // 접속 클라이언트 소켓
	Socket client1; // 접속 클라이언트 소켓
	BufferedReader br = null; // 읽어오기 스트림
	PrintWriter pw = null; // 보내기 스트림
	MainGUI log;
	change chan;
	chatroom frame = null; // 채팅방 프레임

	String roomname; // 방이름 임시저장 변수

	Login() {

		try {
			File sourceimage = new File("image/LoginImg.JPG");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("이미지파일이 없습니다.");
		}

		init();

		// 스크린 사이즈 가져와 screenSize에 저장
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setTitle("로그인");
		// 프레임을 화면 중간에서 열리게 함
		setLocation((screenSize.width - 310) / 2, (screenSize.height - 600) / 2);

		setSize(310, 600); // 창 크기
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료버틍 활성화
		setVisible(true); // 화면에 보이기
		setResizable(false); // 프레임 크기 고정

		Thread th = new Thread(this);
		th.start(); // 스레드 시작

	}

	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		
            Login log = new Login();

	}

	public void init() {

		JPanel base = new JPanel(); // 기본 패널
		JPanel background = new JPanel(); // 이미지가 들어가는 패널
		JPanel Login = new JPanel(); // 로그인부분의 패널
		JPanel butt = new JPanel();

		JLabel ID = new JLabel("아이디"); // 아이디 라벨
		JLabel pssword = new JLabel("비밀번호"); // 비밀번호 라벨
		JLabel IP = new JLabel("서버IP"); // 서버 IP 주소

		input_ID = new JTextField(7); // 아이디 입력
		input_password = new JPasswordField(7); // 비밀번호 입력
		input_IP = new JTextField(20);
		input_password.addKeyListener(this);// 텍스트필드에 이벤트 등록

		ok = new JButton("로그인"); // ok버튼
		create = new JButton("회원가입"); // 회원가입
		ok.addActionListener(this);
		create.addActionListener(this);

		BorderLayout b = new BorderLayout();

		JLabel label = new JLabel(new ImageIcon(img)); // 이미지 올릴 라벨

		base.setLayout(new BorderLayout());
		base.add("North", background);
		background.add(label);
		base.add("Center", Login);
		base.add("South", butt);
		Login.setLayout(new GridLayout(2, 2, 5, 5));
		Login.add(new JLabel("                아이디"));
		Login.add(input_ID);
		Login.add(new JLabel("               패스워드"));
		Login.add(input_password);
		butt.add(ok);
		butt.add(create);

		add(base);

	}

	public void run() { // 스레드 정의부분

		try {
			client = new Socket("210.123.254.48", 5005); // 소켓 생성
			// 입출력 스트림 생성
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			// OutputStream oss = client1.getOutputStream();
			br = new BufferedReader(new InputStreamReader(is));
			pw = new PrintWriter(new OutputStreamWriter(os));

			String msg = "";

			while (true) {
				msg = br.readLine(); // 서버 데이터 수신

				// 로그인에 성공했는지 체크하는 프로토콜
				if (msg.indexOf("//logincheck_") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {
						setVisible(false);
						String jobimg = r.nextToken();
						log = new MainGUI(jobimg);
						log.setSize(850, 800);
						log.setVisible(true);
						Dimension screenSize = Toolkit.getDefaultToolkit()
								.getScreenSize();
						log.setLocation((screenSize.width - 850) / 2,
								(screenSize.height - 800) / 2);
						log.ID = Login_id;
						log.pw = pw;
						log.ip = client.getInetAddress().getHostName();
						// log.oss = oss;

					} else {
						JOptionPane.showMessageDialog(null, "정보를 다시 확인해 주세요",
								"회원가입", JOptionPane.WARNING_MESSAGE);
					}
					
					ok.setEnabled(true);
				}
				// 회원가입 셋팅
				else if (msg.indexOf("//joincheck_") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {
						join_create.setVisible(false);
						JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.",
								"회원가입", JOptionPane.INFORMATION_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "아이디 중복체크를 해주세요",
								"회원가입", JOptionPane.WARNING_MESSAGE);
					}
				} else if (msg.indexOf("//idcheck_") == 0) {  //아이디 중복 체크
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.",
								"회원가입", JOptionPane.WARNING_MESSAGE);
						// join_create.tf2.setText("");
					} else {
						JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.",
								"회원가입", JOptionPane.INFORMATION_MESSAGE);
					}
					join_create.check.setEnabled(true);
				}
				// 회원정보 수정 셋팅
				else if (msg.indexOf("//change") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					// System.out.println(msg);
					r.nextToken();

					chan = new change();
					chan.pw = pw;

					chan.tf2.setText(Login_id);
					chan.tf2.setEnabled(false);

					chan.tf1.setText(r.nextToken());

					String gen = r.nextToken();

					if (gen.equals("남자")) {
						chan.cb1.setState(true);
						chan.gender = "남자";
					} else {
						chan.cb2.setState(true);
						chan.gender = "여자";
					}

					String jobb = r.nextToken();

					if (jobb.equals("공무원")) {
						chan.list.select(1);
						chan.job = "공무원";
					} else if (jobb.equals("회사원")) {
						chan.list.select(2);
						chan.job = "회사원";
					} else if (jobb.equals("연구원")) {
						chan.list.select(3);
						chan.job = "연구원";
					} else if (jobb.equals("자영업")) {
						chan.list.select(4);
						chan.job = "자영업";
					} else if (jobb.equals("학생")) {
						chan.list.select(5);
						chan.job = "학생";
					} else if (jobb.equals("기타")) {
						chan.list.select(6);
						chan.job = "기타";
					}
					chan.ta.setText(r.nextToken());

				}
				// 회원정보수정 완료되면 어떻게 할지 셋팅
				else if (msg.indexOf("//update_member") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {
						chan.setVisible(false);
						JOptionPane.showMessageDialog(this, "정보가 수정되었습니다.",
								"정보수정", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "수정되지 않았습니다.",
								"정보수정", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				// 탈퇴처리 셋팅
				else if (msg.indexOf("//member_delete") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {

						JOptionPane.showMessageDialog(this, "탈퇴처리가 완료되었습니다.",
								"회원탈퇴", JOptionPane.INFORMATION_MESSAGE);
						input_ID.setText("");
						input_password.setText("");
						log.setVisible(false);
						setVisible(true);
					} else {
						String n = "\n";
						JOptionPane.showMessageDialog(this, "탈퇴처리가 되지 않았습니다.",
								"회원탈퇴", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				// 접속한 유저를 셋팅
				else if (msg.indexOf("//useridandroom") == 0) {

					// System.out.println(msg);
					log.userlist.removeAllElements();
					//log.lm.removeAllElements();
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					
					while (r.hasMoreTokens()) {
						String u = r.nextToken();
						// System.out.println(r.nextToken());
						log.listadd1(u);

					}
					log.list.setListData(log.userlist);
					log.roomlist.setListData(log.roomname);

				} else if (msg.indexOf("//wispersend") == 0) { // 쪽지를 보낸다
					StringTokenizer r = new StringTokenizer(msg, "★◆");
					// System.out.println(msg);
					r.nextToken();
					String text = r.nextToken();
					String sendid = r.nextToken();

					String message = text;
					// JOptionPane.showMessageDialog(null, message, "Message",
					// JOptionPane.INFORMATION_MESSAGE);
					recivemessage send = new recivemessage();
					send.lb.setText(sendid + " 님께서 쪽지를 보내셨습니다.");
					send.ta.setText(message);

				} else if (msg.indexOf("//roomlistcheck") == 0) {// 방 참가

					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();

					log.roomlist.removeAll(); // 기존에 있던 리스트 초기화
					log.roomname.clear(); // 기존 방이름저장한 벡터 초기화
					log.imsihash.clear(); // 임시 해쉬맵 초기화
					while (r.hasMoreTokens()) {
						String rr = r.nextToken();
						if (!rr.equals("waiter")) {
							log.imsihash.put(rr, "ddd");// 임시해쉬맵에 방이름저장

						}

					}
					Collection c = log.imsihash.keySet();
					Iterator i = c.iterator();

					while (i.hasNext()) {
						String ii = (String) i.next();
						log.roomname.addElement(ii);
					}
					log.roomlist.setListData(log.roomname);

				} else if (msg.indexOf("//rooom") == 0) { // 방을 만든다.(대기실 셋팅)

					StringTokenizer r = new StringTokenizer(msg, "★●");
					r.nextToken();
					log.roomlist.removeAll();
					log.roomname.clear();
					log.imsihash.clear();

					while (r.hasMoreTokens()) {
						String rr = r.nextToken();
						if (!rr.equals("waiter")) {
							log.imsihash.put(rr, "ddd");
						}

					}
					Collection c = log.imsihash.keySet();
					Iterator i = c.iterator();

					while (i.hasNext()) {
						String ii = (String) i.next();
						log.roomname.addElement(ii);
					}
					log.roomlist.setListData(log.roomname);

					// 방에 모든인원이 나가면 방리스트를 지움
				} else if (msg.indexOf("//removeroom") == 0) {

					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					log.roomlist.removeAll();
					log.roomname.clear();
					log.imsihash.clear();

					while (r.hasMoreTokens()) {
						String rr = r.nextToken();
						if (!rr.equals("waiter")) {
							log.imsihash.put(rr, "ddd");
						}

					}
					Collection c = log.imsihash.keySet();
					Iterator i = c.iterator();

					while (i.hasNext()) {
						String ii = (String) i.next();
						log.roomname.addElement(ii);
					}
					log.roomlist.setListData(log.roomname);

				} else if (msg.indexOf("//maker") == 0) { // 채팅방 생성
					StringTokenizer r = new StringTokenizer(msg, "★●");
					r.nextToken();
					String roomname = r.nextToken();
					String id = r.nextToken();
					frame = new chatroom();
					frame.setVisible(true);
					frame.pw = pw; // pw를 넘겨줌
					frame.ID = Login_id; // 로그인한 아이디를 넘겨줌
					frame.roomname = roomname; // 방이름을 넘겨줌
					frame.setTitle(roomname); // 창의 제목을 방이름으로 셋팅

					frame.all.removeAllElements(); // 방에 있는 id를 저장하는 벡터 초기화
					frame.all.add(id); // 방을 생성한 유저의 id를 벡터에 저장
					frame.roomlist.setListData(frame.all); // 리스트 셋팅

				} else if (msg.indexOf("//remake") == 0) { // 채팅방 참가

					StringTokenizer r = new StringTokenizer(msg, "★●");
					r.nextToken();
					String roomname = r.nextToken();
					frame = new chatroom();
					frame.setVisible(true);
					frame.pw = pw; // pw를 넘겨줌
					frame.ID = Login_id; // 로그인한 아이디를 넘겨줌
					frame.roomname = roomname; // 방이름을 넘겨줌
					// System.out.println(roomname);
					frame.setTitle(roomname);
					// 창의 제목을 방이름으로 셋팅

				} else if (msg.indexOf("//belocate") == 0) { // 방이름이 이미 존재할 때
					JOptionPane.showMessageDialog(this, "이미 존재하는 방입니다.", "방생성",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (msg.indexOf("//roomroom") == 0) { // 방에서 채팅한 내용 출력
					StringTokenizer r = new StringTokenizer(msg, "●");
					r.nextToken();
					String roommsg = r.nextToken();
					String sendid = r.nextToken();
					// frame.chatarea.append(sendid + " >> " + roommsg + "\n");
					frame.text.replaceSelection(sendid + " >> " + roommsg
							+ "\n");
					frame.setEndline();

				} else if (msg.indexOf("//roomlistset") == 0) { // 방 유저리스트 셋
					frame.all.removeAllElements(); // 방에 있는 id를 저장하는 벡터 초기화

					StringTokenizer r = new StringTokenizer(msg, "★●");
					r.nextToken();
					while (r.hasMoreTokens()) {
						String u = r.nextToken();
						frame.all.add(u); // 방에 있는유저의 id를 벡터에 저장
					}
					frame.roomlist.setListData(frame.all); // 리스트 셋팅
				} else if (msg.indexOf("//filesend") == 0) { // 파일이 오면 받는다.

					StringTokenizer r = new StringTokenizer(msg, "＆☆★");
					r.nextToken();
					String filename = r.nextToken();
					String ip = client.getInetAddress().getHostName();

					ServerSocket ss1 = new ServerSocket(5008); // 파일전송 전용 소켓 생성
					Socket client = ss1.accept(); // 파일전용소켓 접속 기다림
					
					String savepathname;
					File savefile;
					DataInputStream dis;
					BufferedOutputStream bos;
					boolean flag = true;
					
					JFileChooser chooser = new JFileChooser();

					chooser.setCurrentDirectory(new File("C:\\")); // 맨처음경로를
					// C로 함
					chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY);
					// 디렉토리만 선택가능

					int ret = chooser.showSaveDialog(null);

					if (ret == JFileChooser.APPROVE_OPTION) { // 디렉토리를 선택했으면
						savefile = chooser.getSelectedFile(); // 선택된 디렉토리 저장하고
						savepathname = savefile.getAbsolutePath() + "\\"
								+ filename; // 디렉토리결과+파일이름
						// System.out.println(savepathname);
					} else {
						JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.",
								"경고", JOptionPane.WARNING_MESSAGE);
						flag = false;
						ss1.close();
						
						return;
					}

					if(flag){
					dis = new DataInputStream(client.getInputStream());

					// File file = new File("c:\\", "test.pdf");
					// file.delete();

					FileOutputStream fos = new FileOutputStream(savepathname);
					bos = new BufferedOutputStream(fos);

					// 바이트 데이터를 전송받으면서 기록
					int len = 0;
					int size = 512;
					byte[] data = new byte[size];
					while ((len = dis.read(data)) != -1) {
						// System.out.println(len);
						// System.out.println("쓰는중");
						bos.write(data, 0, len);

					}
					bos.flush();
					bos.close();
					dis.close();
					fos.close();
					ss1.close();
					}

					

				} else if (msg.indexOf("//imotic") == 0) { // 이모티콘 받는 메소드
					StringTokenizer r = new StringTokenizer(msg, "%%");
					r.nextToken();
					String text = r.nextToken();
					String thisID = r.nextToken();

					// log.text.replaceSelection(text + "\n");
					// log.setEndline();
					if (text.equals("angree")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i1));
						log.setEndline();
					} else if (text.equals("bigsmile")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i2));
						log.setEndline();
					} else if (text.equals("cry")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i3));
						log.setEndline();
					} else if (text.equals("hart")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i4));
						log.setEndline();
					} else if (text.equals("moon")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i5));
						log.setEndline();
					} else if (text.equals("nn")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i6));
						log.setEndline();
					} else if (text.equals("ok")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i7));
						log.setEndline();
					} else if (text.equals("oppse")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i8));
						log.setEndline();
					} else if (text.equals("pumpkin")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i9));
						log.setEndline();
					} else if (text.equals("suck")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i10));
						log.setEndline();
					} else if (text.equals("Qlwla")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i11));
						log.setEndline();
					} else if (text.equals("shy")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i12));
						log.setEndline();
					} else if (text.equals("smile")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i13));
						log.setEndline();
					} else if (text.equals("star")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i14));
						log.setEndline();
					} else if (text.equals("victory")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i15));
						log.setEndline();
					} else if (text.equals("chikin")) {
						log.text.replaceSelection(thisID + ">>");
						log.setEndline();
						log.text.replaceSelection("\n");
						log.text.insertComponent(new JLabel(i16));
						log.setEndline();
					}

				} else if (msg.indexOf("//imorooom") == 0) { // 방에 이모티콘 띄운다.
					StringTokenizer r = new StringTokenizer(msg, "●");
					r.nextToken();
					String roommsg = r.nextToken();
					String sendid = r.nextToken();

					if (roommsg.equals("angree")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i1));
						frame.setEndline();
					} else if (roommsg.equals("bigsmile")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i2));
						frame.setEndline();
					} else if (roommsg.equals("cry")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i3));
						frame.setEndline();
					} else if (roommsg.equals("hart")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i4));
						frame.setEndline();
					} else if (roommsg.equals("moon")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i5));
						frame.setEndline();
					} else if (roommsg.equals("nn")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i6));
						frame.setEndline();
					} else if (roommsg.equals("ok")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i7));
						frame.setEndline();
					} else if (roommsg.equals("oppse")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i8));
						frame.setEndline();
					} else if (roommsg.equals("pumpkin")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i9));
						frame.setEndline();
					} else if (roommsg.equals("suck")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i10));
						frame.setEndline();
					} else if (roommsg.equals("qlwla")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i11));
						frame.setEndline();
					} else if (roommsg.equals("shy")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i12));
						frame.setEndline();
					} else if (roommsg.equals("smile")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i13));
						frame.setEndline();
					} else if (roommsg.equals("star")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i14));
						frame.setEndline();
					} else if (roommsg.equals("victory")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i15));
						frame.setEndline();
					} else if (roommsg.equals("chikin")) {
						frame.text.replaceSelection(sendid + ">>");
						frame.setEndline();
						frame.text.replaceSelection("\n");
						frame.text.insertComponent(new JLabel(i16));
						frame.setEndline();
					}

				} else {
					// log.chatarea.append(msg + "\n");// 화면에 msg 메시지 출력
					log.text.replaceSelection(msg + "\n");
					log.setEndline();
				}
			}
		} catch (IOException e) { // 예외 처리
			System.out.print(e.getMessage());
		}

	}

	public void actionPerformed(ActionEvent e) { // 액션이벤트
		Object obj = e.getSource();

		
		// 로그인버튼 이벤트처리
		if ((JButton) obj == ok) {
			
			ok.setEnabled(false);
			
			Login_id = input_ID.getText();
			Login_passwd = new String(input_password.getPassword());
			Login_ip = "210.123.254.48";

			if (Login_id.equals("") || Login_passwd.equals("")) {
				JOptionPane.showMessageDialog(this, "입력되지 않은 칸이 있습니다.", "로그인",
						JOptionPane.WARNING_MESSAGE);
			} else {
				pw.println("//login " + Login_id + " " + Login_passwd + " "
						+ Login_ip);
				pw.flush();

			}
			

		}

		// 회원가입버튼 이벤트처리
		if ((JButton) obj == create) {
			join_create = new join();
			join_create.pw = pw;

		}

	}

	public void textValueChanged(TextEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) { // 키이벤트
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10) { // 엔터가 눌리면
			ok.doClick(); // ok버튼 클릭
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
}
