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

	/** �̸�Ƽ�� ���� **/
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
	// �α����� ���������� �Ǿ����� üũ
	public Boolean join_Login_Check = false;
	join join_create = null;

	Image img = null;
	JButton ok;
	JButton create;
	JTextField input_ID;
	JPasswordField input_password;
	JTextField input_IP;
	Socket client; // ���� Ŭ���̾�Ʈ ����
	Socket client1; // ���� Ŭ���̾�Ʈ ����
	BufferedReader br = null; // �о���� ��Ʈ��
	PrintWriter pw = null; // ������ ��Ʈ��
	MainGUI log;
	change chan;
	chatroom frame = null; // ä�ù� ������

	String roomname; // ���̸� �ӽ����� ����

	Login() {

		try {
			File sourceimage = new File("image/LoginImg.JPG");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("�̹��������� �����ϴ�.");
		}

		init();

		// ��ũ�� ������ ������ screenSize�� ����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setTitle("�α���");
		// �������� ȭ�� �߰����� ������ ��
		setLocation((screenSize.width - 310) / 2, (screenSize.height - 600) / 2);

		setSize(310, 600); // â ũ��
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ������v Ȱ��ȭ
		setVisible(true); // ȭ�鿡 ���̱�
		setResizable(false); // ������ ũ�� ����

		Thread th = new Thread(this);
		th.start(); // ������ ����

	}

	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		
            Login log = new Login();

	}

	public void init() {

		JPanel base = new JPanel(); // �⺻ �г�
		JPanel background = new JPanel(); // �̹����� ���� �г�
		JPanel Login = new JPanel(); // �α��κκ��� �г�
		JPanel butt = new JPanel();

		JLabel ID = new JLabel("���̵�"); // ���̵� ��
		JLabel pssword = new JLabel("��й�ȣ"); // ��й�ȣ ��
		JLabel IP = new JLabel("����IP"); // ���� IP �ּ�

		input_ID = new JTextField(7); // ���̵� �Է�
		input_password = new JPasswordField(7); // ��й�ȣ �Է�
		input_IP = new JTextField(20);
		input_password.addKeyListener(this);// �ؽ�Ʈ�ʵ忡 �̺�Ʈ ���

		ok = new JButton("�α���"); // ok��ư
		create = new JButton("ȸ������"); // ȸ������
		ok.addActionListener(this);
		create.addActionListener(this);

		BorderLayout b = new BorderLayout();

		JLabel label = new JLabel(new ImageIcon(img)); // �̹��� �ø� ��

		base.setLayout(new BorderLayout());
		base.add("North", background);
		background.add(label);
		base.add("Center", Login);
		base.add("South", butt);
		Login.setLayout(new GridLayout(2, 2, 5, 5));
		Login.add(new JLabel("                ���̵�"));
		Login.add(input_ID);
		Login.add(new JLabel("               �н�����"));
		Login.add(input_password);
		butt.add(ok);
		butt.add(create);

		add(base);

	}

	public void run() { // ������ ���Ǻκ�

		try {
			client = new Socket("210.123.254.48", 5005); // ���� ����
			// ����� ��Ʈ�� ����
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			// OutputStream oss = client1.getOutputStream();
			br = new BufferedReader(new InputStreamReader(is));
			pw = new PrintWriter(new OutputStreamWriter(os));

			String msg = "";

			while (true) {
				msg = br.readLine(); // ���� ������ ����

				// �α��ο� �����ߴ��� üũ�ϴ� ��������
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
						JOptionPane.showMessageDialog(null, "������ �ٽ� Ȯ���� �ּ���",
								"ȸ������", JOptionPane.WARNING_MESSAGE);
					}
					
					ok.setEnabled(true);
				}
				// ȸ������ ����
				else if (msg.indexOf("//joincheck_") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {
						join_create.setVisible(false);
						JOptionPane.showMessageDialog(this, "ȸ�������� �Ϸ�Ǿ����ϴ�.",
								"ȸ������", JOptionPane.INFORMATION_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "���̵� �ߺ�üũ�� ���ּ���",
								"ȸ������", JOptionPane.WARNING_MESSAGE);
					}
				} else if (msg.indexOf("//idcheck_") == 0) {  //���̵� �ߺ� üũ
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {
						JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ���̵��Դϴ�.",
								"ȸ������", JOptionPane.WARNING_MESSAGE);
						// join_create.tf2.setText("");
					} else {
						JOptionPane.showMessageDialog(this, "��밡���� ���̵��Դϴ�.",
								"ȸ������", JOptionPane.INFORMATION_MESSAGE);
					}
					join_create.check.setEnabled(true);
				}
				// ȸ������ ���� ����
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

					if (gen.equals("����")) {
						chan.cb1.setState(true);
						chan.gender = "����";
					} else {
						chan.cb2.setState(true);
						chan.gender = "����";
					}

					String jobb = r.nextToken();

					if (jobb.equals("������")) {
						chan.list.select(1);
						chan.job = "������";
					} else if (jobb.equals("ȸ���")) {
						chan.list.select(2);
						chan.job = "ȸ���";
					} else if (jobb.equals("������")) {
						chan.list.select(3);
						chan.job = "������";
					} else if (jobb.equals("�ڿ���")) {
						chan.list.select(4);
						chan.job = "�ڿ���";
					} else if (jobb.equals("�л�")) {
						chan.list.select(5);
						chan.job = "�л�";
					} else if (jobb.equals("��Ÿ")) {
						chan.list.select(6);
						chan.job = "��Ÿ";
					}
					chan.ta.setText(r.nextToken());

				}
				// ȸ���������� �Ϸ�Ǹ� ��� ���� ����
				else if (msg.indexOf("//update_member") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {
						chan.setVisible(false);
						JOptionPane.showMessageDialog(this, "������ �����Ǿ����ϴ�.",
								"��������", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "�������� �ʾҽ��ϴ�.",
								"��������", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				// Ż��ó�� ����
				else if (msg.indexOf("//member_delete") == 0) {
					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();
					if (r.nextToken().equals("true")) {

						JOptionPane.showMessageDialog(this, "Ż��ó���� �Ϸ�Ǿ����ϴ�.",
								"ȸ��Ż��", JOptionPane.INFORMATION_MESSAGE);
						input_ID.setText("");
						input_password.setText("");
						log.setVisible(false);
						setVisible(true);
					} else {
						String n = "\n";
						JOptionPane.showMessageDialog(this, "Ż��ó���� ���� �ʾҽ��ϴ�.",
								"ȸ��Ż��", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				// ������ ������ ����
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

				} else if (msg.indexOf("//wispersend") == 0) { // ������ ������
					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
					// System.out.println(msg);
					r.nextToken();
					String text = r.nextToken();
					String sendid = r.nextToken();

					String message = text;
					// JOptionPane.showMessageDialog(null, message, "Message",
					// JOptionPane.INFORMATION_MESSAGE);
					recivemessage send = new recivemessage();
					send.lb.setText(sendid + " �Բ��� ������ �����̽��ϴ�.");
					send.ta.setText(message);

				} else if (msg.indexOf("//roomlistcheck") == 0) {// �� ����

					StringTokenizer r = new StringTokenizer(msg, " ");
					r.nextToken();

					log.roomlist.removeAll(); // ������ �ִ� ����Ʈ �ʱ�ȭ
					log.roomname.clear(); // ���� ���̸������� ���� �ʱ�ȭ
					log.imsihash.clear(); // �ӽ� �ؽ��� �ʱ�ȭ
					while (r.hasMoreTokens()) {
						String rr = r.nextToken();
						if (!rr.equals("waiter")) {
							log.imsihash.put(rr, "ddd");// �ӽ��ؽ��ʿ� ���̸�����

						}

					}
					Collection c = log.imsihash.keySet();
					Iterator i = c.iterator();

					while (i.hasNext()) {
						String ii = (String) i.next();
						log.roomname.addElement(ii);
					}
					log.roomlist.setListData(log.roomname);

				} else if (msg.indexOf("//rooom") == 0) { // ���� �����.(���� ����)

					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
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

					// �濡 ����ο��� ������ �渮��Ʈ�� ����
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

				} else if (msg.indexOf("//maker") == 0) { // ä�ù� ����
					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
					r.nextToken();
					String roomname = r.nextToken();
					String id = r.nextToken();
					frame = new chatroom();
					frame.setVisible(true);
					frame.pw = pw; // pw�� �Ѱ���
					frame.ID = Login_id; // �α����� ���̵� �Ѱ���
					frame.roomname = roomname; // ���̸��� �Ѱ���
					frame.setTitle(roomname); // â�� ������ ���̸����� ����

					frame.all.removeAllElements(); // �濡 �ִ� id�� �����ϴ� ���� �ʱ�ȭ
					frame.all.add(id); // ���� ������ ������ id�� ���Ϳ� ����
					frame.roomlist.setListData(frame.all); // ����Ʈ ����

				} else if (msg.indexOf("//remake") == 0) { // ä�ù� ����

					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
					r.nextToken();
					String roomname = r.nextToken();
					frame = new chatroom();
					frame.setVisible(true);
					frame.pw = pw; // pw�� �Ѱ���
					frame.ID = Login_id; // �α����� ���̵� �Ѱ���
					frame.roomname = roomname; // ���̸��� �Ѱ���
					// System.out.println(roomname);
					frame.setTitle(roomname);
					// â�� ������ ���̸����� ����

				} else if (msg.indexOf("//belocate") == 0) { // ���̸��� �̹� ������ ��
					JOptionPane.showMessageDialog(this, "�̹� �����ϴ� ���Դϴ�.", "�����",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (msg.indexOf("//roomroom") == 0) { // �濡�� ä���� ���� ���
					StringTokenizer r = new StringTokenizer(msg, "��");
					r.nextToken();
					String roommsg = r.nextToken();
					String sendid = r.nextToken();
					// frame.chatarea.append(sendid + " >> " + roommsg + "\n");
					frame.text.replaceSelection(sendid + " >> " + roommsg
							+ "\n");
					frame.setEndline();

				} else if (msg.indexOf("//roomlistset") == 0) { // �� ��������Ʈ ��
					frame.all.removeAllElements(); // �濡 �ִ� id�� �����ϴ� ���� �ʱ�ȭ

					StringTokenizer r = new StringTokenizer(msg, "�ڡ�");
					r.nextToken();
					while (r.hasMoreTokens()) {
						String u = r.nextToken();
						frame.all.add(u); // �濡 �ִ������� id�� ���Ϳ� ����
					}
					frame.roomlist.setListData(frame.all); // ����Ʈ ����
				} else if (msg.indexOf("//filesend") == 0) { // ������ ���� �޴´�.

					StringTokenizer r = new StringTokenizer(msg, "���١�");
					r.nextToken();
					String filename = r.nextToken();
					String ip = client.getInetAddress().getHostName();

					ServerSocket ss1 = new ServerSocket(5008); // �������� ���� ���� ����
					Socket client = ss1.accept(); // ����������� ���� ��ٸ�
					
					String savepathname;
					File savefile;
					DataInputStream dis;
					BufferedOutputStream bos;
					boolean flag = true;
					
					JFileChooser chooser = new JFileChooser();

					chooser.setCurrentDirectory(new File("C:\\")); // ��ó����θ�
					// C�� ��
					chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY);
					// ���丮�� ���ð���

					int ret = chooser.showSaveDialog(null);

					if (ret == JFileChooser.APPROVE_OPTION) { // ���丮�� ����������
						savefile = chooser.getSelectedFile(); // ���õ� ���丮 �����ϰ�
						savepathname = savefile.getAbsolutePath() + "\\"
								+ filename; // ���丮���+�����̸�
						// System.out.println(savepathname);
					} else {
						JOptionPane.showMessageDialog(null, "��θ� ���������ʾҽ��ϴ�.",
								"���", JOptionPane.WARNING_MESSAGE);
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
					dis.close();
					fos.close();
					ss1.close();
					}

					

				} else if (msg.indexOf("//imotic") == 0) { // �̸�Ƽ�� �޴� �޼ҵ�
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

				} else if (msg.indexOf("//imorooom") == 0) { // �濡 �̸�Ƽ�� ����.
					StringTokenizer r = new StringTokenizer(msg, "��");
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
					// log.chatarea.append(msg + "\n");// ȭ�鿡 msg �޽��� ���
					log.text.replaceSelection(msg + "\n");
					log.setEndline();
				}
			}
		} catch (IOException e) { // ���� ó��
			System.out.print(e.getMessage());
		}

	}

	public void actionPerformed(ActionEvent e) { // �׼��̺�Ʈ
		Object obj = e.getSource();

		
		// �α��ι�ư �̺�Ʈó��
		if ((JButton) obj == ok) {
			
			ok.setEnabled(false);
			
			Login_id = input_ID.getText();
			Login_passwd = new String(input_password.getPassword());
			Login_ip = "210.123.254.48";

			if (Login_id.equals("") || Login_passwd.equals("")) {
				JOptionPane.showMessageDialog(this, "�Էµ��� ���� ĭ�� �ֽ��ϴ�.", "�α���",
						JOptionPane.WARNING_MESSAGE);
			} else {
				pw.println("//login " + Login_id + " " + Login_passwd + " "
						+ Login_ip);
				pw.flush();

			}
			

		}

		// ȸ�����Թ�ư �̺�Ʈó��
		if ((JButton) obj == create) {
			join_create = new join();
			join_create.pw = pw;

		}

	}

	public void textValueChanged(TextEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) { // Ű�̺�Ʈ
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10) { // ���Ͱ� ������
			ok.doClick(); // ok��ư Ŭ��
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
