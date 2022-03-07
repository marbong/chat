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

	JButton butt; // ���������� ��ư
	JButton butt2; // �� ���� ��ư
	JButton butt3; // �� ���� ��ư
	JButton butt4; // �޼��� ���۹�ư
	JButton butt5; // ȸ�� ���� ���� ��ư
	JButton butt6; // ȸ�� Ż�� ��ư
	JButton butt7; // ��������
	JButton butt8; // �̸�Ƽ��

	JTextArea chatarea; // ä�ó���
	JTextPane text;
	Image img = null;
	JList list;
	PrintWriter pw = null; // ������ ���� ��ü
	JTextField tf; // �޼��� ���� �ʵ�
	String msg = null; // ä�ó����� ��� ����
	String ID;
	static DefaultListModel lm;// ����� ����Ʈ
	HashMap user = new HashMap();
	File sourceimage;
	String jobimg;
	Vector roomname; // ���̸� ����
	Vector userlist; // ������ ����
	static ButtonData bt;
	ButtonData data;
	String txt;
	JList roomlist;

	HashMap imsihash = new HashMap();
	JFileChooser chooser; // �������� ��ü

	imoticon imo;

	public class ButtonRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList lst, Object val,
				int idx, boolean isSelected, boolean hasFocus) {
			JLabel comp = null;

			if (val instanceof ButtonData) {
				/* �����־�� �ϴ� �����Ͱ� ButtonData�� ��� */
				data = (ButtonData) val;

				/*
				 * �������� ���õ� �����̸� ���ڿ��� "<- Selected"�� �߰��Ѵ�. "
				 */
				if (isSelected) {
					txt = data.szName + " <- Selected";
				} else {
					txt = data.szName;
				}
				/* �θ� Ŭ������ �޼ҵ� ȣ�� */
				comp = (JLabel) super.getListCellRendererComponent(lst, txt,
						idx, isSelected, hasFocus);
				/* �������� ���Ѵ�. */
				comp.setIcon(data.xIcon);
				/* ��輱�� �ٲ۴�. */
				comp.setBorder(new BevelBorder(BevelBorder.RAISED));
			} else if (val instanceof String) {
				/* �����־�� �ϴ� �����Ͱ� �Ϲ� ���ڿ��� ��� */
				comp = (JLabel) super.getListCellRendererComponent(lst, val,
						idx, isSelected, hasFocus);
			}
			return comp;
		}
	}

	// ��������Ʈ�� �߰�
	public void listadd1(String u) {

		userlist.add(u);
		
		//lm.addElement(new ButtonData(u, new ImageIcon("image/check.gif")));
		list.repaint();

	}

	// ��������Ʈ �ʱ�ȭ
	public void listreset() {
		
		//lm.removeAllElements();
		list.removeAll();
	}

	public MainGUI(String jobimg) {

		this.jobimg = jobimg; // ������ ������ ����

		try {
			if (jobimg.equals("������"))
				sourceimage = new File("image/a.JPG");
			else if (jobimg.equals("ȸ���"))
				sourceimage = new File("image/b.JPG");
			else if (jobimg.equals("�ڿ���"))
				sourceimage = new File("image/d.JPG");
			else if (jobimg.equals("�л�"))
				sourceimage = new File("image/e.JPG");
			else if (jobimg.equals("��Ÿ"))
				sourceimage = new File("image/f.JPG");
			else if (jobimg.equals("������"))
				sourceimage = new File("image/c.JPG");

			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("�̹��������� �����ϴ�.");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ������v Ȱ��ȭ

		setTitle("ä�����α׷�");
		JPanel base = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JLabel lb = new JLabel("����� ���");
		JLabel lb1 = new JLabel("�� ���");
		JLabel lb2 = new JLabel("ä��â");
		roomlist = new JList();
		roomname = new Vector();
		userlist = new Vector();
		roomlist.setListData(userlist);
		
		roomlist.setListData(roomname);
		butt = new JButton("���� ������");
		butt.addActionListener(this);
		JButton butt1 = new JButton("�� ����");
		butt2 = new JButton("�� ����");
		butt2.addActionListener(this);
		butt3 = new JButton("�� ����");
		butt3.addActionListener(this);
		butt4 = new JButton("����");

		butt4.addActionListener(this);
		butt5 = new JButton("��������");
		butt5.addActionListener(this);
		butt6 = new JButton("ȸ��Ż��");
		butt6.addActionListener(this);
		butt7 = new JButton("��������");
		butt7.addActionListener(this);
		butt8 = new JButton("��");
		butt8.addActionListener(this);

		tf = new JTextField(30); // �޼��� ���� �ʵ�
		tf.addKeyListener(this);// �ؽ�Ʈ�ʵ忡 �̺�Ʈ ���
		base.setLayout(new BorderLayout());
		list = new JList();
		//list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lm = new DefaultListModel(); // ����Ʈ�� ������ ���� ���� �ʱ�ȭ�Ѵ�.


	
		//list.setModel(lm);
		//list.setCellRenderer(new ButtonRenderer());

		text = new JTextPane();
		text.setBackground(SystemColor.inactiveCaption);

		chatarea = new JTextArea(30, 50);
		chatarea.setBackground(SystemColor.inactiveCaption); // TextArea ���� ����
		JPanel p7 = new JPanel();
		JPanel base2 = new JPanel();
		MenuBar menu = new MenuBar(); // �޴���

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
		JLabel jlb = new JLabel("�� ����");
		JPanel p12 = new JPanel();
		p11.add("North", p12);
		p12.add(jlb);
		JPanel p13 = new JPanel();
		p11.add("Center", p13);
		JLabel label11 = new JLabel(new ImageIcon(img)); // �̹��� �ø� ��
		p13.add(label11);
		JPanel p14 = new JPanel();
		p11.add("South", p14);
		p14.add(butt5);
		p14.add(butt6);

		add(first);
	}

	class ButtonData {
		String szName; // �̸�
		Icon xIcon; // �̹���

		public ButtonData(String name, Icon icon) {
			szName = name;
			xIcon = icon;
		}

		public String toString() {
			return szName;
		}
	}

	public void actionPerformed(ActionEvent e) { // �׼��̺�Ʈ
		Object obj = e.getSource();

		// ������ư �̺�Ʈó��
		if ((JButton) obj == butt) {

			sendmessage s1 = new sendmessage();
			//s1.data = data.szName;
			s1.pw = pw;
			s1.ID = ID;
			s1.data =(String) list.getSelectedValue();
			
			list.clearSelection();

		}

		// �� ���� ��ư
		if ((JButton) obj == butt2) {

			String select = (String) roomlist.getSelectedValue();
			if (select != null) {// ������ ���� �̸��� null�� �ƴϸ�
				// ������ ���̸��� ID�� ������ ������.
				String joinroom = "//enterroom�ڡ�" + select + "�ڡ�" + ID;

				pw.println(joinroom);
				pw.flush();
			}

			roomlist.clearSelection();

		}

		// �� ���� ��ư
		if ((JButton) obj == butt3) {
			String name = "";
			name = JOptionPane.showInputDialog("���� �̸��� �Է��ϼ���");
			if (name != null) {// ���̸��� null�� �ƴϸ�
				// ���̸��� ID�� ������ ������.
				String mssage = "//roomname�ڡ�" + name + "�ڡ�" + ID;
				pw.println(mssage);
				pw.flush();

			}

		}

		// ���۹�ư �̺�Ʈó��
		if ((JButton) obj == butt4) {

			pw.println(tf.getText());
			pw.flush();
			tf.setText("");

		}

		// ����������ư �̺�Ʈó��
		if ((JButton) obj == butt5) {
			pw.println("//change " + ID);
			pw.flush();

		}

		// ȸ��Ż�� �̺�Ʈó��
		if ((JButton) obj == butt6) {
			String enter = "\n";
			int n = JOptionPane.showConfirmDialog(null, "������ �����˴ϴ�." + enter
					+ "��� �����Ͻðڽ��ϱ�?", "ȸ��Ż��", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (n == 0) {
				pw.println("//delete " + ID);
				pw.flush();
			}

		}

		// ��������
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
				JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�", "���",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath = chooser.getSelectedFile().getPath();
			String filename = chooser.getSelectedFile().getName();

			// System.out.println(filePath);
			pw.println("///file#" + filename + "#" + select_id +"#"+ip);
			pw.flush();

			// try {
			// client1 = new Socket("210.123.254.48", 5006); //�������� ���� ���� ����
			// oss = client1.getOutputStream();
			// } catch (UnknownHostException e2) {
			// // TODO Auto-generated catch block
			// e2.printStackTrace();
			// } catch (IOException e2) {
			// // TODO Auto-generated catch block
			// e2.printStackTrace();
			// } // ���� ����

			try {
				// InputStream fin = new
				// FileInputStream("C:\\Users\\����2-08\\Desktop\\���ؿ�.jpg");
				client1 = new Socket("210.123.254.48", 5006); // �������� ���� ���� ����
				oss = client1.getOutputStream();
				InputStream fin = new FileInputStream(filePath);

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

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		if ((JButton) obj == butt8) { // �̸�Ƽ������
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
		if (e.getKeyCode() == 10) { // ���Ͱ� ������
			butt4.doClick(); // ok��ư Ŭ��
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
		// ������ ���� ������ Ŀ�� �̵��ϰ� ����
		text.setSelectionStart(text.getSelectionEnd());
	}

}
