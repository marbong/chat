package GUI;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // �̺�Ʈ ó��
import java.io.BufferedOutputStream;
import java.io.BufferedReader; // �����
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
import java.net.ServerSocket; // TCP ���� 
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector; // Vector Ŭ����

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

// GUI ȭ��

class GUIChatServer extends JFrame implements ActionListener {
	JButton btn_ext; // ���� ��ư
	TextArea txt_list; // ���� ��� ���
	protected Vector list; // ������ ���� ��� ����
	HashMap user;

	// ������
	public GUIChatServer(String title) {
		super(title); // Ÿ��Ʋ�ٿ� ��µ� ���ڿ�
		list = new Vector(); // ���� ����
		user = new HashMap(); // ���� ���̵�� ���̸� ����
		btn_ext = new JButton("���� ����"); // ���� ���� ��ư ����
		btn_ext.addActionListener(this); // �̺�Ʈ ���
		txt_list = new TextArea(); // txt_list ����
		add("Center", txt_list); // ȭ�� ��� txt_list ���
		add("South", btn_ext); // ȭ�� �ϴܿ� ���� ���� ��ư ���
		setSize(200, 200); // ȭ�� ũ�� ����
		setVisible(true); // ȭ�� ���
		ServerStart();
	}

	// ä�� ����
	public void ServerStart() {
		final int port = 5005; // ä�� ���� ��Ʈ ��� ����
		try {
			ServerSocket ss = new ServerSocket(port); // ServerSocket ����
			while (true) {
				Socket client = ss.accept(); // Ŭ���̾�Ʈ ���� ��ٸ�
				txt_list.appendText(client.getInetAddress().getHostAddress()
						+ "\n");
				ChatHandle ch = new ChatHandle(this, client, user); // ChatHandle
				// �ʱ�ȭ
				list.addElement(ch); // Ŭ���̾�Ʈ ���� list ���Ϳ� �߰�
				ch.start(); // ChatHandle ������ ����
			}
		} catch (Exception e) { // ���� ó��
			System.out.println(e.getMessage());
		}
	}

	// ���� ���� ��ư�� ������ ��
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	// �޽��� ��� �޼���
	public void setMsg(String msg) {
		txt_list.appendText(msg + "\n");// ȭ�鿡 msg �޽��� ���
	}

	// main �޼���
	public static void main(String[] args) {
		new GUIChatServer(" ä�� ���� ");
	}
}

// ChatHandle Ŭ����
// ä�� ������ �������� ���� ó��

class ChatHandle extends Thread {
	HashMap user;
	GUIChatServer server = null;
	Socket client = null; // ������ Ŭ���̾�Ʈ
	BufferedReader br = null; // �о����
	memberDTO dto;
	PrintWriter pw = null; // ������
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

	// ������
	public ChatHandle(GUIChatServer server, Socket client, HashMap user)
			throws IOException {
		this.server = server;
		this.client = client;
		this.user = user;

		// ����� ��Ʈ�� ����
		is = client.getInputStream();
		br = new BufferedReader(new InputStreamReader(is)); // �б����
		os = client.getOutputStream();
		pw = new PrintWriter(new OutputStreamWriter(os)); // ��¹���
	}

	// Thread Ŭ������ run �޼��� �������̵�.
	public void run() {
		String name = "";
		try {

			while (true) {

				String msg = br.readLine(); // Ŭ���̾�Ʈ �޽��� ���
				String str = client.getInetAddress().getHostName();
				synchronized (server) {
					server.setMsg(str + " : " + msg); // ���� Ŭ���̾�Ʈ �޽��� ���
				}

				/*
				 * if (msg.equals("@@Exit"))// @@Exit �޽����� Ŭ���̾�Ʈ ���� ���� break;
				 */

				/*
				 * if (aaa) { JFileChooser chooser = new JFileChooser();
				 * 
				 * chooser.setCurrentDirectory(new File("C:\\")); // ��ó����θ� C�� ��
				 * chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); //
				 * ���丮�� ���ð��� int ret = chooser.showSaveDialog(null);
				 * 
				 * if (ret == JFileChooser.APPROVE_OPTION) { // ���丮�� ����������
				 * savefile = chooser.getSelectedFile(); // ���õ� ���丮 �����ϰ�
				 * savepathname = savefile.getAbsolutePath() + "\\" + filename;
				 * // ���丮���+�����̸� // System.out.println(savepathname); } else {
				 * JOptionPane.showMessageDialog(null, "��θ� ���������ʾҽ��ϴ�.", "���",
				 * JOptionPane.WARNING_MESSAGE); return; }
				 * 
				 * OutputStream fout = new FileOutputStream(savepathname);
				 * 
				 * int readcount = 0; byte[] buffer = new byte[512];
				 * System.out.println("��������"); while ((readcount =
				 * is.read(buffer)) != -1) { System.out.println(readcount);
				 * fout.write(buffer, 0, readcount); System.out.println("����"); }
				 * System.out.println("������"); fout.close(); aaa=false; }
				 */

				// Ŭ���̾�Ʈ�� �α��� �Ҷ�
				if (msg.indexOf("//login") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					Login_id = r.nextToken();
					Login_passwd = r.nextToken();
					Login_ip = r.nextToken();

					dto = new memberDTO();

					dto.setid(Login_id);
					dto.setpasswd(Login_passwd);

					// �α��� �����ߴ��� üũ
					try {
						if (Logincheck_DAO.logincheck(dto)) {

							pw.println("//logincheck_ true "
									+ change_DAO.change(Login_id, dto).getjob());
							pw.flush();

							String room = "//useridandroom";

							user.put(Login_id, "waiter"); // ���Ϳ� ������ ���̵�� ���̸� ����

							Collection collection = user.keySet();
							Iterator iter = collection.iterator();

							while (iter.hasNext()) {

								room = room + " " + (String) iter.next(); // �������ִ�
																			// ���
																			// ���̵�
																			// ����

							}
							// room = room + " " + Login_id;

							// ��ε��ɽ�Ʈ
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
							} catch (Exception e) { // ���� ó��
								System.out.println(e.getMessage());
							}

							Collection cc = user.keySet();
							Iterator ii = collection.iterator();
							String roomlist = "//roomlistcheck�ڡ�";
							while (ii.hasNext()) {

								roomlist = roomlist
										+ (String) user.get(ii.next()) + "�ڡ�";

							}
							Send_All(roomlist);

							name = Login_id;

							Send_All(name + " ���� ���� �����ϼ̽��ϴ�.");

						} else {
							pw.println("//logincheck_ false");
							pw.flush();
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// Ŭ���̾�Ʈ�� ȸ�������� ��
				} else if (msg.indexOf("//join") == 0) {
					StringTokenizer r = new StringTokenizer(msg, "�ࣣ��");
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

					// ȸ������ �����ߴ��� üũ
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

					// ���̵� �ߺ�Ȯ��
				} else if (msg.indexOf("//idcheck") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();

					idcheck = r.nextToken();

					dto = new memberDTO();

					dto.setid(idcheck);

					// ���̵��ߺ�Ȯ�� ��� ����
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
					// �������� �Ҷ�
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

						// ������ �����Ǿ����� true �ƴϸ� false�� ����
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

				} else if (msg.indexOf("//delete") == 0) {  //���� Ż��
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();

					dto = new memberDTO();
					dto.setid(r.nextToken());

					try {

						// Ż��ó���� �Ϸ�Ǹ� true �ƴϸ� false�� ����
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

				} else if (msg.indexOf("//wisper") == 0) { // ������ ����
					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
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

								if (chs.Login_id.equals(n)) { // �ӼӸ� ���� ID �� ������
									synchronized (chs.pw) {
										chs.pw.write("//wispersend�ڡ�" + text
												+ "�ڡ�" + sendid + "\n");
									}
									chs.pw.flush();
								}
							}
						}
					} catch (Exception e) { // ���� ó��
						System.out.println(e.getMessage());
					}

				}
				// ���� ������� ���Ϳ��� �����.
				else if (msg.indexOf("//exitroom") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					String id = r.nextToken(); // ���̵� ����
					String roomname = r.nextToken(); // ���̸� ����
					user.put(id, "waiter");

					Collection c = user.keySet();
					Iterator i = c.iterator();

					String add = "//removeroom";
					String allid = "";

					while (i.hasNext()) {
						String ii = (String) i.next(); // ���̵� ������
						String rr = (String) user.get(ii); // ���̸��� ������

						add = add + " " + rr;

						if (user.get(ii).equals(roomname)) {
							allid = allid + " " + ii;
						}

					}
					Send_All(add);

					/** �濡 ������ �ִ� ������� �����ڸ���Ʈ�� ������. */
					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String key = (String) iter.next();
						// �濡 �ִ� ���̵𿡰Ը� �޼��� ����
						if (user.get(key).equals(roomname)) {
							Roomuserlist(key, allid);
						}
					}

				} else if (msg.indexOf("//roomname") == 0) { // ���� �����.
					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
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

							add = add + "�ڡ�" + rr;
							// allid = allid+" "+ii;

						}
						Send_All(add);
						// System.out.println(allid);
						pw.println("//maker�ڡ�" + roomname + "�ڡ�" + id);
						pw.flush();
					}

				} else if (msg.indexOf("//enterroom") == 0) { // �� ����
					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
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

						add = add + "�ڡ�" + rr;

						if (user.get(ii).equals(roomname)) { // ���̸��� ������ ���̵� �߰�
							allid = allid + "�ڡ�" + ii;
						}

					}

					Send_All(add);
					pw.println("//remake" + "�ڡ�" + roomname);
					pw.flush();

					/** �濡 ������ �ִ� ������� �����ڸ���Ʈ�� ������. */
					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String key = (String) iter.next();

						// �濡 �ִ� ���̵𿡰Ը� �޼��� ����
						if (user.get(key).equals(roomname)) {
							Roomuserlist(key, allid);
						}
					}

				} else if (msg.indexOf("///roommsg") == 0) { // ��ä��
					StringTokenizer r = new StringTokenizer(msg, "��");
					r.nextToken();
					String room = r.nextToken(); // ���̸� ����
					String text = r.nextToken(); // ������ �޼��� ����
					String sendid = r.nextToken(); // ������� id

					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String id = (String) iter.next();

						// �濡 �ִ� ���̵𿡰Ը� �޼��� ����
						if (user.get(id).equals(room)) {
							String enterid = id;
							RoomChat(text, id, sendid);
						}
					}

				} else if (msg.indexOf("///file") == 0) { // Ŭ���̾�Ʈ�κ��� ���Ϲ޴� �޼ҵ�
					StringTokenizer r = new StringTokenizer(msg, "#");
					r.nextToken();
					filename = r.nextToken();
					getID = r.nextToken();
					String ip = r.nextToken();

					// /////////////////////////////////////////////////////////////////////////////////////////////
					ServerSocket ss1 = new ServerSocket(5006); // �������� ���� ���� ����
					Socket client = ss1.accept(); // ����������� ���� ��ٸ�

					dis = new DataInputStream(client.getInputStream());

					FileOutputStream fos = new FileOutputStream("C://ä��//"
							+ filename);
					bos = new BufferedOutputStream(fos);

					// ����Ʈ �����͸� ���۹����鼭 ���
					int len = 0;
					int size = 512;
					byte[] data = new byte[size];
					while ((len = dis.read(data)) != -1) {
						// System.out.println(len);
						// System.out.println("������");
						bos.write(data, 0, len);

					}

					bos.flush();
					bos.close();
					fos.close();
					dis.close();
					ss1.close();
					// /////////////////////////////////////////////////////////////////////////////////////////////
					//�ٽ� Ŭ���̾�Ʈ�� ����
					try {
						synchronized (server.list) {
							int sizee = server.list.size();
							for (int i = 0; i < sizee; i++) {
								ChatHandle chs = (ChatHandle) server.list
										.elementAt(i);

								if (chs.Login_id.equals(getID)) { // ���� ���� ID ��
																	// ������
									synchronized (chs.pw) {
										chs.pw.write("//filesend���١�" + filename
												+ "\n");
									}
									chs.pw.flush();
									
									Socket client11 = new Socket(ip, 5008); // ��������
									OutputStream oss = client11.getOutputStream();
									try {
								
										InputStream fin = new FileInputStream("C://ä��//" + filename);

										int readcount = 0;
										byte[] buffer = new byte[512];
										// System.out.println("��������");
										while ((readcount = fin.read(buffer)) != -1) {
											// oss.write(buffer,0,readcount);
											oss.write(buffer, 0, readcount);
										}
										oss.flush();
										oss.close();
										fin.close();

									}catch(FileNotFoundException e){
										System.out.println("���� ��ã��");
										
									}
									
								}
							}
						}
					} catch (Exception e) { // ���� ó��
						System.out.println(e.getMessage());
					}

				}else if (msg.indexOf("////imoticon") == 0) { // �̸�Ƽ�� ����
					StringTokenizer r = new StringTokenizer(msg, "%%");
					r.nextToken();
					String text = r.nextToken();
					String ID = r.nextToken();
					Send_All("//imotic%%" + text + "%%" + ID);
				} else if (msg.indexOf("////romticon") == 0) { // ���̸�Ƽ�� ����
					StringTokenizer r = new StringTokenizer(msg, "��");
					r.nextToken();
					String text = r.nextToken();
					String sendid = r.nextToken();
					String room = r.nextToken();

					Collection collection = user.keySet();
					Iterator iter = collection.iterator();

					while (iter.hasNext()) {

						String id = (String) iter.next();

						// �濡 �ִ� ���̵𿡰Ը� �޼��� ����
						if (user.get(id).equals(room)) {
							String enterid = id;
							RoomImotic(text, id, sendid);
						}
					}
				} else
					// ���� ������ ��� Ŭ���̾�Ʈ�� �޽��� ����
					Send_All(name + " >> " + msg);

			}
		} catch (Exception e) { // ���� ó��
			server.setMsg(e.getMessage());
		} finally {
			synchronized (server.list) {

				Send_All(name + " ���� �����ϼ̽��ϴ�.");

				user.remove(Login_id);

				// ������ ������ ���Ϳ��� ����� ���� �ο��� Ŭ���̾�Ʈ���� ����
				String room = "//useridandroom";
				Collection collection = user.keySet();
				Iterator iter = collection.iterator();

				while (iter.hasNext()) {

					room = room + " " + (String) iter.next();

				}
				// room = room + " " + Login_id;

				// ��ε��ɽ�Ʈ
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
				} catch (Exception e) { // ���� ó��
					System.out.println(e.getMessage());
				}

				server.list.removeElement(this); // ���� ��Ͽ��� ����

				// pw.println();
			}
			try { // ��Ʈ�� ����
				br.close();
				pw.close();
				client.close(); // Ŭ���̾�Ʈ ���� ����
			} catch (IOException e) { // ���� ó��
				server.setMsg(e.getMessage());
			}
		}
	}

	// ���� ������ ������ ��� Ŭ���̾�Ʈ�� msg����
	public void Send_All(String msg) {

		try {
			synchronized (server.list) { // GUIChatServer ��� ���� list ����ȭ ó��
				int size = server.list.size(); // ���� ������ Ŭ���̾�Ʈ ��
				for (int i = 0; i < size; i++) {
					ChatHandle chs = (ChatHandle) server.list.elementAt(i);
					synchronized (chs.pw) { // ChatHandle pw �ν��Ͻ��� �̿��� ���� ����
						chs.pw.println(msg);
					}
					chs.pw.flush();
				}
			}
		} catch (Exception e) { // ���� ó��
			System.out.println(e.getMessage());
		}
	}

	public void RoomChat(String msg, String id, String sendid) { // ��ä�� �޼ҵ�
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
							chs.pw.println("//roomroom��" + m + "��" + send);
						}
						chs.pw.flush();
					}
				}
			}
		} catch (Exception e) { // ���� ó��
			System.out.println(e.getMessage());
		}
	}

	public void RoomImotic(String msg, String id, String sendid) { // ���̸�Ƽ������
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
							chs.pw.println("//imorooom��" + m + "��" + send);
						}
						chs.pw.flush();
					}
				}
			}
		} catch (Exception e) { // ���� ó��
			System.out.println(e.getMessage());
		}
	}

	public void Roomuserlist(String key, String id) { // �濡 �������ִ� �����
		String key2 = key; // ���̵�
		String allid = id; // �濡 �ִ� ��� ���̵�
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
		} catch (Exception e) { // ���� ó��
			System.out.println(e.getMessage());
		}
	}

}