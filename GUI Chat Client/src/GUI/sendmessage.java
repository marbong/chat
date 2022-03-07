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

		setTitle("����");
		setSize(250, 250); // â ũ��
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ������v Ȱ��ȭ
		setVisible(true); // ȭ�鿡 ���̱�
		// ��ũ�� ������ ������ screenSize�� ����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// �������� ȭ�� �߰����� ������ ��
		setLocation((screenSize.width - 250) / 2, (screenSize.height - 250) / 2);

	}

	public void init() {
		JPanel base = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		lb = new JLabel("������ �޼����� �ۼ��ϼ���");
		ta = new JTextArea(10, 20);
		ta.setBackground(SystemColor.inactiveCaption); // TextArea ���� ����
		b1 = new JButton("������");
		b2 = new JButton("���");
		
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

	public void actionPerformed(ActionEvent e) { // �׼��̺�Ʈ
		Object obj = e.getSource();

		//���۹�ư �̺�Ʈ ó��
		if ((JButton) obj == b1) {
			pw.println("//wisper�ڡ�" + data + "�ڡ�" + ta.getText() + "�ڡ�"
					+ ID);
			pw.flush();
			
			setVisible(false);

		}
		
		//��ҹ�ư
		if ((JButton) obj == b2) {
			setVisible(false);

		}

	}

}
