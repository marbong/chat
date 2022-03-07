package GUI;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // 이벤트 처리
import java.io.BufferedOutputStream;
import java.io.BufferedReader; // 입출력
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket; // TCP 소켓 
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector; // Vector 클래스

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import DB.IdCheckDAO;
import DB.Logincheck_DAO;
import DB.change_DAO;
import DB.delete_DAO;
import DB.insertDB_DAO;
import DB.memberDTO;
import DB.update_DAO;

// GUI 화면

class GUIChatServer extends JFrame implements ActionListener {
	JButton btn_ext; // 종료 버튼
	TextArea txt_list; // 접속 목록 출력
	protected Vector list; // 접속한 서버 목록 저장
	HashMap user;

	// 생성자
	public GUIChatServer(String title) {
		super(title); // 타이틀바에 출력될 문자열
		list = new Vector(); // 벡터 생성
		user = new HashMap(); // 유저 아이디와 방이름 저장
		btn_ext = new JButton("서버 종료"); // 서버 종료 버튼 생성
		btn_ext.addActionListener(this); // 이벤트 등록
		txt_list = new TextArea(); // txt_list 생성
		add("Center", txt_list); // 화면 가운데 txt_list 출력
		add("South", btn_ext); // 화면 하단에 서버 종료 버튼 출력
		setSize(200, 200); // 화면 크기 설정
		setVisible(true); // 화면 출력
		ServerStart();
	}

	// 채팅 서버
	public void ServerStart() {
		final int port = 5005; // 채팅 서버 포트 상수 지정
		try {
			ServerSocket ss = new ServerSocket(port); // ServerSocket 생성
			while (true) {
				Socket client = ss.accept(); // 클라이언트 접속 기다림
				txt_list.appendText(client.getInetAddress().getHostAddress()
						+ "\n");
				ChatHandle ch = new ChatHandle(this, client, user); // ChatHandle
				// 초기화
				list.addElement(ch); // 클라이언트 관리 list 벡터에 추가
				ch.start(); // ChatHandle 스레드 시작
			}
		} catch (Exception e) { // 예외 처리
			System.out.println(e.getMessage());
		}
	}

	// 서버 종료 버튼이 눌렸을 때
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	// 메시지 출력 메서드
	public void setMsg(String msg) {
		txt_list.appendText(msg + "\n");// 화면에 msg 메시지 출력
	}

	// main 메서드
	public static void main(String[] args) {
		new GUIChatServer(" 채팅 서버 ");
	}
}

// ChatHandle 클래스
// 채팅 서버의 실질적인 역할 처리

class ChatHandle extends Thread {
	HashMap user;
	GUIChatServer server = null;
	Socket client = null; // 접속한 클라이언트
	BufferedReader br = null; // 읽어오기
	memberDTO dto;
	PrintWriter pw = null; // 보내기
	String Login_id, Login_passwd, Login_ip;
	String join_name, join_id, join_passwd, join_gender, join_job, join_info;
	String idcheck;
	InputStream is;
	OutputStream os;

	String filename;
	String getID;
	String savepathname;
	File savefile;

	boolean aaa = false;
	
	String ip ;

	DataInputStream dis;

	BufferedOutputStream bos;

	// 생성자
	public ChatHandle(GUIChatServer server, Socket client, HashMap user)
			throws IOException {
		this.server = server;
		this.client = client;
		this.user = user;

		// 입출력 스트림 생성
		is = client.getInputStream();
		br = new BufferedReader(new InputStreamReader(is)); // 읽기버퍼
		os = client.getOutputStream();
		pw = new PrintWriter(new OutputStreamWriter(os)); // 출력버퍼
	}

	// Thread 클래스의 run 메서드 오버라이딩.
	public void run() {
		String name = "";
		try {

			while (true) {

				String msg = br.readLine(); // 클라이언트 메시지 대기
				String str = client.getInetAddress().getHostName();
				synchronized (server) {
					server.setMsg(str + " : " + msg); // 접속 클라이언트 메시지 출력
				}

				/*
				 * if (msg.equals("@@Exit"))// @@Exit 메시지면 클라이언트 접속 해지 break;
				 */

				/*
				 * if (aaa) { JFileChooser chooser = new JFileChooser();
				 * 
				 * chooser.setCurrentDirectory(new File("C:\\")); // 맨처음경로를 C로 함
				 * chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); //
				 * 디렉토리만 선택가능 int ret = chooser.showSaveDialog(null);
				 * 
				 * if (ret == JFileChooser.APPROVE_OPTION) { // 디렉토리를 선택했으면
				 * savefile = chooser.getSelectedFile(); // 선택된 디렉토리 저장하고
				 * savepathname = savefile.getAbsolutePath() + "\\" + filename;
				 * // 디렉토리결과+파일이름 // System.out.println(savepathname); } else {
				 * JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.", "경고",
				 * JOptionPane.WARNING_MESSAGE); return; }
				 * 
				 * OutputStream fout = new FileOutputStream(savepathname);
				 * 
				 * int readcount = 0; byte[] buffer = new byte[512];
				 * System.out.println("받음시작"); while ((readcount =
				 * is.read(buffer)) != -1) { System.out.println(readcount);
				 * fout.write(buffer, 0, readcount); System.out.println("받음"); }
				 * System.out.println("받음끝"); fout.close(); aaa=false; }
				 */

				// 클라이언트가 로그인 할때
				if (msg.indexOf("//login") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					Login_id = r.nextToken();
					Login_passwd = r.nextToken();
					Login_ip = r.nextToken();

					dto = new memberDTO();

					dto.setid(Login_id);
					dto.setpasswd(Login_passwd);

					// 로그인 성공했는지 체크
					try {
						if (Logincheck_DAO.logincheck(dto)) {

							pw.println("//logincheck_ true "
									+ change_DAO.change(Login_id, dto).getjob());
							pw.flush();

							String room = "//useridandroom";

							user.put(Login_id, "waiter"); // 벡터에 접속한 아이디와 방이름 저장

							Collection collection = user.keySet();
							Iterator iter = collection.iterator();

							while (iter.hasNext()) {

								room = room + " " + (String) iter.next(); // 접속해있는
																			// 모든
																			// 아이디를
																			// 보냄

							}
							// room = room + " " + Login_id;

							// 브로드케스트
							try {
								synchronized (server.list) {
									int size = server.list.size();
									for (int i = 0; i < size; i++) {
										ChatHandle chs = (ChatHandle) server.list
												.elementAt(i);
										synchronized (chs.pw) {
											chs.pw.println(room);
										}
										chs.pw.flush();
									}
								}
							} catch (Exception e) { // 예외 처리
								System.out.println(e.getMessage());
							}

							Collection cc = user.keySet();
							Iterator ii = collection.iterator();
							String roomlist = "//roomlistcheck★●";
							while (ii.hasNext()) {

								roomlist = roomlist
										+ (String) user.get(ii.next()) + "★●";

							}
							Send_All(roomlist);

							name = Login_id;

							Send_All(name + " 님이 새로 입장하셨습니다.");

						} else {
							pw.println("//logincheck_ false");
							pw.flush();
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// 클라이언트가 회원가입할 때
				} else if (msg.indexOf("//join") == 0) {
					StringTokenizer r = new StringTokenizer(msg, "□＃◆");
					r.nextToken();
					join_name = r.nextToken();
					join_id = r.nextToken();
					join_passwd = r.nextToken();
					join_gender = r.nextToken();
					join_job = r.nextToken();
					join_info = r.nextToken();

					dto = new memberDTO();

					dto.setname(join_name);
					dto.setid(join_id);
					dto.setpasswd(join_passwd);
					dto.setgender(join_gender);
					dto.setjob(join_job);
					dto.setinfo(join_info);

					// 회원가입 성공했는지 체크
					try {
						if (insertDB_DAO.create(dto)) {

							pw.println("//joincheck_ true");
							pw.flush();

						} else {
							pw.println("//joincheck_ false");
							pw.flush();
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// 아이디 중복확인
				} else if (msg.indexOf("//idcheck") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();

					idcheck = r.nextToken();

					dto = new memberDTO();

					dto.setid(idcheck);

					// 아이디중복확인 결과 전송
					try {
						if (IdCheckDAO.idcheck(dto)) {

							pw.println("//idcheck_ true");
							pw.flush();

						} else {
							pw.println("//idcheck_ false");
							pw.flush();
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// 정보수정 할때
				} else if (msg.indexOf("//change") == 0) {
					// StringTokenizer r = new StringTokenizer(msg, " ");
					// r.nextToken();
					// memberDTO dto = new memberDTO();
					// String st = r.nextToken();
					String change = "//change "
							+ change_DAO.change(Login_id, dto).getname() + " "
							+ change_DAO.change(Login_id, dto).getgender()
							+ " " + change_DAO.change(Login_id, dto).getjob()
							+ " " + change_DAO.change(Login_id, dto).getinfo();

					pw.println(change);
					pw.flush();
				} else if (msg.indexOf("////changeData") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();

					dto = new memberDTO();

					dto.setname(r.nextToken());
					String Change_id = r.nextToken();
					dto.setid(Change_id);
					dto.setpasswd(r.nextToken());
					dto.setgender(r.nextToken());
					dto.setjob(r.nextToken());
					dto.setinfo(r.nextToken());

					try {

						// 정보가 수정되었으면 true 아니면 false를 전송
						if (update_DAO.update(dto)) {

							pw.println("//update_member true");
							pw.flush();

						} else {
							pw.println("//update_member false");
							pw.flush();
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (msg.indexOf("//delete") == 0) {  //계정 탈퇴
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();

					dto = new memberDTO();
					dto.setid(r.nextToken());

					try {

						// 탈퇴처리가 완료되면 true 아니면 false를 전송
						if (delete_DAO.member_delete(dto)) {

							pw.println("//member_delete true");
							pw.flush();

						} else {
							pw.println("//member_delete false");
							pw.flush();
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (msg.indexOf("//wisper") == 0) { // 쪽지를 보냄
					StringTokenizer r = new StringTokenizer(msg, "★◆");
					r.nextToken();

					String n = r.nextToken();
					String text = r.nextToken();
					String sendid = r.nextToken();

					try {
						synchronized (server.list) {
							int size = server.list.size();
							for (int i = 0; i < size; i++) {
								ChatHandle chs = (ChatHandle) server.list
										.elementAt(i);

								if (chs.Login_id.equals(n)) { // 귓속말 보낼 ID 가 맞으면
									synchronized (chs.pw) {
										chs.pw.write("//wispersend★◆" + text
												+ "★◆" + sendid + "\n");
									}
									chs.pw.flush();
								}
							}
						}
					} catch (Exception e) { // 예외 처리
						System.out.println(e.getMessage());
					}

				}
				// 방을 나간사람 벡터에서 지운다.
				else if (msg.indexOf("//exitroom") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					String id = r.nextToken(); // 아이디 저장
					String roomname = r.nextToken(); // 방이름 저장
					user.put(id, "waiter");

					Collection c = user.keySet();
					Iterator i = c.iterator();

					String add = "//removeroom";
					String allid = "";

					while (i.hasNext()) {
						String ii = (String) i.next(); // 아이디를 가져옴
						String rr = (String) user.get(ii); // 방이름을 가져옴

						add = add + " " + rr;

						if (user.get(ii).equals(roomname)) {
							allid = allid + " " + ii;
						}

					}
					Send_All(add);

					/** 방에 접속해 있는 사람에게 참가자리스트를 보낸다. */
					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String key = (String) iter.next();
						// 방에 있는 아이디에게만 메세지 전송
						if (user.get(key).equals(roomname)) {
							Roomuserlist(key, allid);
						}
					}

				} else if (msg.indexOf("//roomname") == 0) { // 방을 만든다.
					StringTokenizer r = new StringTokenizer(msg, "★●");
					r.nextToken();
					String roomname = r.nextToken();
					String id = r.nextToken();
					boolean flag = true;

					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {
						String enterid = (String) iter.next();
						String ennterroom = (String) user.get(enterid);

						if (ennterroom.equals(roomname)) {
							pw.println("//belocate");
							pw.flush();
							flag = false;
							break;
						}
					}

					if (flag) {
						user.put(id, roomname);

						Collection c = user.keySet();
						Iterator i = c.iterator();

						String add = "//rooom";
						// String allid = "";

						while (i.hasNext()) {
							String ii = (String) i.next();
							String rr = (String) user.get(ii);

							add = add + "★●" + rr;
							// allid = allid+" "+ii;

						}
						Send_All(add);
						// System.out.println(allid);
						pw.println("//maker★●" + roomname + "★●" + id);
						pw.flush();
					}

				} else if (msg.indexOf("//enterroom") == 0) { // 방 참가
					StringTokenizer r = new StringTokenizer(msg, "★●");
					r.nextToken();
					String roomname = r.nextToken();
					String id = r.nextToken();

					user.put(id,roomname);

					Collection c = user.keySet();
					Iterator i = c.iterator();

					String add = "//rooom";
					String allid = "";

					while (i.hasNext()) {
						String ii = (String) i.next();
						String rr = (String) user.get(ii);

						add = add + "★●" + rr;

						if (user.get(ii).equals(roomname)) { // 방이름이 같으면 아이디를 추가
							allid = allid + "★●" + ii;
						}

					}

					Send_All(add);
					pw.println("//remake" + "★●" + roomname);
					pw.flush();

					/** 방에 접속해 있는 사람에게 참가자리스트를 보낸다. */
					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String key = (String) iter.next();

						// 방에 있는 아이디에게만 메세지 전송
						if (user.get(key).equals(roomname)) {
							Roomuserlist(key, allid);
						}
					}

				} else if (msg.indexOf("///roommsg") == 0) { // 방채팅
					StringTokenizer r = new StringTokenizer(msg, "●");
					r.nextToken();
					String room = r.nextToken(); // 방이름 저장
					String text = r.nextToken(); // 전송할 메세지 저장
					String sendid = r.nextToken(); // 보낸사람 id

					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String id = (String) iter.next();

						// 방에 있는 아이디에게만 메세지 전송
						if (user.get(id).equals(room)) {
							String enterid = id;
							RoomChat(text, id, sendid);
						}
					}

				} else if (msg.indexOf("///file") == 0) { // 클라이언트로부터 파일받는 메소드
					StringTokenizer r = new StringTokenizer(msg, "#");
					r.nextToken();
					filename = r.nextToken();
					getID = r.nextToken();
					String ip = r.nextToken();

					// /////////////////////////////////////////////////////////////////////////////////////////////
					ServerSocket ss1 = new ServerSocket(5006); // 파일전송 전용 소켓 생성
					Socket client = ss1.accept(); // 파일전용소켓 접속 기다림

					dis = new DataInputStream(client.getInputStream());

					FileOutputStream fos = new FileOutputStream("C://채팅//"
							+ filename);
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
					fos.close();
					dis.close();
					ss1.close();
					// /////////////////////////////////////////////////////////////////////////////////////////////
					//다시 클라이언트로 보냄
					try {
						synchronized (server.list) {
							int sizee = server.list.size();
							for (int i = 0; i < sizee; i++) {
								ChatHandle chs = (ChatHandle) server.list
										.elementAt(i);

								if (chs.Login_id.equals(getID)) { // 파일 보낼 ID 가
																	// 맞으면
									synchronized (chs.pw) {
										chs.pw.write("//filesend＆☆★" + filename
												+ "\n");
									}
									chs.pw.flush();
									
									Socket client11 = new Socket(ip, 5008); // 파일전송
									OutputStream oss = client11.getOutputStream();
									try {
								
										InputStream fin = new FileInputStream("C://채팅//" + filename);

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

									}catch(FileNotFoundException e){
										System.out.println("파일 못찾음");
										
									}
									
								}
							}
						}
					} catch (Exception e) { // 예외 처리
						System.out.println(e.getMessage());
					}

				}else if (msg.indexOf("////imoticon") == 0) { // 이모티콘 전송
					StringTokenizer r = new StringTokenizer(msg, "%%");
					r.nextToken();
					String text = r.nextToken();
					String ID = r.nextToken();
					Send_All("//imotic%%" + text + "%%" + ID);
				} else if (msg.indexOf("////romticon") == 0) { // 방이모티콘 전송
					StringTokenizer r = new StringTokenizer(msg, "●");
					r.nextToken();
					String text = r.nextToken();
					String sendid = r.nextToken();
					String room = r.nextToken();

					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String id = (String) iter.next();

						// 방에 있는 아이디에게만 메세지 전송
						if (user.get(id).equals(room)) {
							String enterid = id;
							RoomImotic(text, id, sendid);
						}
					}
				} else
					// 현재 접속한 모든 클라이언트에 메시지 전송
					Send_All(name + " >> " + msg);

			}
		} catch (Exception e) { // 예외 처리
			server.setMsg(e.getMessage());
		} finally {
			synchronized (server.list) {

				Send_All(name + " 님이 퇴장하셨습니다.");

				user.remove(Login_id);

				// 유저가 나가면 벡터에서 지우고 남은 인원을 클라이언트에게 전송
				String room = "//useridandroom";
				Collection collection = user.keySet();
				Iterator iter = collection.iterator();

				while (iter.hasNext()) {

					room = room + " " + (String) iter.next();

				}
				// room = room + " " + Login_id;

				// 브로드케스트
				try {
					synchronized (server.list) {
						int size = server.list.size();
						for (int i = 0; i < size; i++) {
							ChatHandle chs = (ChatHandle) server.list
									.elementAt(i);
							synchronized (chs.pw) {
								chs.pw.println(room);
							}
							chs.pw.flush();
						}
					}
				} catch (Exception e) { // 예외 처리
					System.out.println(e.getMessage());
				}

				server.list.removeElement(this); // 접속 목록에서 제거

				// pw.println();
			}
			try { // 스트림 해지
				br.close();
				pw.close();
				client.close(); // 클라이언트 접속 해지
			} catch (IOException e) { // 예외 처리
				server.setMsg(e.getMessage());
			}
		}
	}

	// 현재 서버에 접속한 모든 클라이언트에 msg전송
	public void Send_All(String msg) {

		try {
			synchronized (server.list) { // GUIChatServer 멤버 변수 list 동기화 처리
				int size = server.list.size(); // 현재 접속한 클라이언트 수
				for (int i = 0; i < size; i++) {
					ChatHandle chs = (ChatHandle) server.list.elementAt(i);
					synchronized (chs.pw) { // ChatHandle pw 인스턴스를 이용한 문자 전송
						chs.pw.println(msg);
					}
					chs.pw.flush();
				}
			}
		} catch (Exception e) { // 예외 처리
			System.out.println(e.getMessage());
		}
	}

	public void RoomChat(String msg, String id, String sendid) { // 방채팅 메소드
		String m = msg;
		String idd = id;
		String send = sendid;
		try {
			synchronized (server.list) {
				int size = server.list.size();
				for (int i = 0; i < size; i++) {
					ChatHandle chs = (ChatHandle) server.list.elementAt(i);

					if (chs.Login_id.equals(idd)) {
						synchronized (chs.pw) {
							chs.pw.println("//roomroom●" + m + "●" + send);
						}
						chs.pw.flush();
					}
				}
			}
		} catch (Exception e) { // 예외 처리
			System.out.println(e.getMessage());
		}
	}

	public void RoomImotic(String msg, String id, String sendid) { // 룸이모티콘전송
		String m = msg;
		String idd = id;
		String send = sendid;
		try {
			synchronized (server.list) {
				int size = server.list.size();
				for (int i = 0; i < size; i++) {
					ChatHandle chs = (ChatHandle) server.list.elementAt(i);

					if (chs.Login_id.equals(idd)) {
						synchronized (chs.pw) {
							chs.pw.println("//imorooom●" + m + "●" + send);
						}
						chs.pw.flush();
					}
				}
			}
		} catch (Exception e) { // 예외 처리
			System.out.println(e.getMessage());
		}
	}

	public void Roomuserlist(String key, String id) { // 방에 접속해있는 사람들
		String key2 = key; // 아이디
		String allid = id; // 방에 있는 모든 아이디
		try {
			synchronized (server.list) {
				int size = server.list.size();
				for (int i = 0; i < size; i++) {
					ChatHandle chs = (ChatHandle) server.list.elementAt(i);

					if (chs.Login_id.equals(key2)) {
						synchronized (chs.pw) {
							chs.pw.println("//roomlistset " + allid);
						}
						chs.pw.flush();
					}
				}
			}
		} catch (Exception e) { // 예외 처리
			System.out.println(e.getMessage());
		}
	}

}