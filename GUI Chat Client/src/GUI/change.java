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
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class change extends JFrame implements ActionListener, ItemListener,
		FocusListener {

	String name, id, passwd, gender, job, info;
	String change_name ,change_gender,change_job,change_info; 

	PrintWriter pw = null;
	JPanel base, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, pt;
	JLabel l1, l2, l3, l4, l5, l6, label2;
	JTextField tf1, tf2;
	JPasswordField pf;
	JButton ok, cancle;
	JTextArea ta;
	Choice list;
	Checkbox cb1, cb2;
	CheckboxGroup cbg;

	change() {

		init();

		// ��ũ�� ������ ������ screenSize�� ����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setTitle("ȸ����������");
		setSize(450, 400); // â ũ��
		// �������� ȭ�� �߰����� ������ ��
		setLocation((screenSize.width - 400) / 2, (screenSize.height - 400) / 2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // �ش� �����Ӹ� ����
		setVisible(true); // ȭ�鿡 ���̱�

	}


	public void init() {

		base = new JPanel();

		// base.setBackground(Color.blue);

		l1 = new JLabel("��      ��");
		l2 = new JLabel("�� �� ��");
		l3 = new JLabel("�н�����");
		l4 = new JLabel("��      ��");
		l5 = new JLabel("��      ��");
		l6 = new JLabel("�ڱ�Ұ�");
		label2 = new JLabel("��������");

		tf1 = new JTextField(20);
		tf2 = new JTextField(20);
		pf = new JPasswordField(20);

		ok = new JButton("���� ����");
		cancle = new JButton("���� ���");

		ta = new JTextArea(5, 20);

		list = new Choice();
		list.add("�����������ϼ���");
		list.add("������");
		list.add("ȸ���");
		list.add("������");
		list.add("�ڿ���");
		list.add("�л�");
		list.add("��Ÿ");

		cbg = new CheckboxGroup();
		cb1 = new Checkbox("��", false, cbg);
		cb2 = new Checkbox("��", false, cbg);

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
		 * �̺�Ʈ�� ��Ͻ�Ŵ
		 * **/
		ok.addActionListener(this);
		cancle.addActionListener(this);
		cb1.addFocusListener(this);
		cb2.addFocusListener(this);
		list.addItemListener(this); // List ��ü�� ItemListener�� ��� ��Ŵ

		// ta.addKeyListener(this);
		// ta.addTextListener(this);

		pt.add(label2);

		p1.add(tf1);
		p2.add(tf2);
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

		//base.add(pt, "North");
		base.add(p10, "Center");
		base.add(p9, "South");
		JPanel pp = new JPanel();
		pp.setLayout(new BorderLayout());
		pp.add("Center",base);
		pp.add("North",pt);
		
		add(pp);
		

	}

	public void actionPerformed(ActionEvent e) { // �׼��̺�Ʈ

		Object obj = e.getSource();


		if ((JButton) obj == ok) {
			name = tf1.getText();
			id = tf2.getText();
			passwd = new String(pf.getPassword());
			info = ta.getText();
			
			
			if(name.equals("") || id.equals("") || passwd.equals("") || gender.equals("") || job.equals("") || info.equals(""))
			{
				JOptionPane.showMessageDialog(this, "�Էµ��� ���� ĭ�� �ֽ��ϴ�.", "��������",
						JOptionPane.WARNING_MESSAGE);
			} else{
				
				
				String changeData = name + " " + id + " " + passwd + " "
						+ gender + " " + job + " " + info;

				String enter = "\n";
				int n = JOptionPane.showConfirmDialog(null,
						"������ ��� �ԷµǾ����ϴ�." + enter + "��� �����Ͻðڽ��ϱ�?", "��������",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				
				if(n == 0){
				pw.println("////changeData "+changeData);
				pw.flush();

				}

			}
				

		}

		if ((JButton) obj == cancle) {
			setVisible(false); // ȭ�鿡 ���̱�
		}

	}

	public void focusGained(FocusEvent e) {

		if (e.getSource() == cb1)
			gender = "����";
		else if (e.getSource() == cb2)
			gender = "����";

	}

	public void focusLost(FocusEvent e) { // �������� ������� �Ҿ�����
	}

	public void itemStateChanged(ItemEvent e) {// Item�� ���°� ��ȭ ���� ��

		if (list.getSelectedIndex() == 0)
			job = null;
		else if (list.getSelectedIndex() == 1)
			job = "������";
		else if (list.getSelectedIndex() == 2)
			job = "ȸ���";
		else if (list.getSelectedIndex() == 3)
			job = "������";
		else if (list.getSelectedIndex() == 4)
			job = "�ڿ���";
		else if (list.getSelectedIndex() == 5)
			job = "�л�";
		else if (list.getSelectedIndex() == 6)
			job = "��Ÿ";
	}
}
