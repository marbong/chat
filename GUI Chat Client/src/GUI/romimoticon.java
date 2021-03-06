package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class romimoticon extends JFrame implements ActionListener, ItemListener,Runnable{
	
	PrintWriter pw;
	String ID;
	
	JRadioButton a1;
	JRadioButton a2;
	JRadioButton a3;
	JRadioButton a4;
	JRadioButton a5;
	JRadioButton a6;
	JRadioButton a7;
	JRadioButton a8;
	JRadioButton a9;
	JRadioButton a10;
	JRadioButton a11;
	JRadioButton a12;
	JRadioButton a13;
	JRadioButton a14;
	JRadioButton a15;
	JRadioButton a16;
	
	
	ImageIcon i1;
	ImageIcon i2;
	ImageIcon i3;
	ImageIcon i4;
	ImageIcon i5;
	ImageIcon i6;
	ImageIcon i7;
	ImageIcon i8;
	ImageIcon i9;
	ImageIcon i10;
	ImageIcon i11;
	ImageIcon i12;
	ImageIcon i13;
	ImageIcon i14;
	ImageIcon i15;
	ImageIcon i16;
	
	JButton b1;
	
	String msg="";
	String roomname="";
	
	
	public romimoticon() throws IOException {
		super();
		
		init();

		this.setLocation(100, 100);	//프레임 시작위치
		super.setVisible(true);	//실제로 프레임을 화면에 출력
		super.setSize(400, 500);	//프레임의 처음 크기
		super.setResizable(true);	//프레임 사이즈 조절
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 해당 프레임만 종료

	}

	public void init() throws IOException {
		
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		b1 = new JButton("전송");
		b1.addActionListener(this);
		
		ImageIcon img = new ImageIcon("image/imoticon/angree.png");
		ImageIcon img1 = new ImageIcon("image/imoticon/bigsmile.png");
		ImageIcon img2 = new ImageIcon("image/imoticon/cry.png");
		ImageIcon img3 = new ImageIcon("image/imoticon/hart.png");
		ImageIcon img4 = new ImageIcon("image/imoticon/moon.png");
		ImageIcon img5 = new ImageIcon("image/imoticon/nn.png");
		ImageIcon img6 = new ImageIcon("image/imoticon/ok.png");
		ImageIcon img7 = new ImageIcon("image/imoticon/oppse.png");
		ImageIcon img8 = new ImageIcon("image/imoticon/pumpkin.png");
		ImageIcon img9 = new ImageIcon("image/imoticon/suck.png");
		ImageIcon img10 = new ImageIcon("image/imoticon/qlwla.png");
		ImageIcon img11 = new ImageIcon("image/imoticon/shy.png");
		ImageIcon img12 = new ImageIcon("image/imoticon/smile.png");
		ImageIcon img13 = new ImageIcon("image/imoticon/star.png");
		ImageIcon img14 = new ImageIcon("image/imoticon/victory.png");
		ImageIcon img15 = new ImageIcon("image/imoticon/chikin.png");
		//ImageIcon selectedCherryIcon = new ImageIcon("images/selectedCherry.jpg");
		i1 = new ImageIcon("image/angree.png");
		i2 = new ImageIcon("image/bigsmile.png");
		i3 = new ImageIcon("image/cry.png");
		i4 = new ImageIcon("image/hart.png");
		i5 = new ImageIcon("image/moon.png");
		i6 = new ImageIcon("image/nn.png");
		i7 = new ImageIcon("image/ok.png");
		i8 = new ImageIcon("image/oppse.png");
		i9 = new ImageIcon("image/pumpkin.png");
		i10 = new ImageIcon("image/suck.png");
		i11 = new ImageIcon("image/qlwla.png");
		i12 = new ImageIcon("image/shy.png");
		i13 = new ImageIcon("image/smile.png");
		i14 = new ImageIcon("image/star.png");
		i15 = new ImageIcon("image/victory.png");
		i16 = new ImageIcon("image/chikin.png");
		
		
		ButtonGroup g = new ButtonGroup();
		
		a1 = new JRadioButton(img);
		a1.setBorderPainted(true);
		a1.setSelectedIcon(img);
		
		a2 = new JRadioButton(img1);
		a2.setBorderPainted(true);
		a2.setSelectedIcon(img1);
		
		a3 = new JRadioButton(img2);
		a3.setBorderPainted(true);
		a3.setSelectedIcon(img2);
		
		a4 = new JRadioButton(img3);
		a4.setBorderPainted(true);
		a4.setSelectedIcon(img3);
		
		a5 = new JRadioButton(img4);
		a5.setBorderPainted(true);
		a5.setSelectedIcon(img4);
		
		a6 = new JRadioButton(img5);
		a6.setBorderPainted(true);
		a6.setSelectedIcon(img5);
		
		a7 = new JRadioButton(img6);
		a7.setBorderPainted(true);
		a7.setSelectedIcon(img6);
		
		a8 = new JRadioButton(img7);
		a8.setBorderPainted(true);
		a8.setSelectedIcon(img7);
		
		a9 = new JRadioButton(img8);
		a9.setBorderPainted(true);
		a9.setSelectedIcon(img8);
		
		a10 = new JRadioButton(img9);
		a10.setBorderPainted(true);
		a10.setSelectedIcon(img9);
		
		a11 = new JRadioButton(img10);
		a11.setBorderPainted(true);
		a11.setSelectedIcon(img10);
		
		a12 = new JRadioButton(img11);
		a12.setBorderPainted(true);
		a12.setSelectedIcon(img11);
		
		a13 = new JRadioButton(img12);
		a13.setBorderPainted(true);
		a13.setSelectedIcon(img12);
		
		a14 = new JRadioButton(img13);
		a14.setBorderPainted(true);
		a14.setSelectedIcon(img13);
		
		a15 = new JRadioButton(img14);
		a15.setBorderPainted(true);
		a15.setSelectedIcon(img14);
		
		a16 = new JRadioButton(img15);
		a16.setBorderPainted(true);
		a16.setSelectedIcon(img15);

		
		a1.addItemListener(this);
		a2.addItemListener(this);
		a3.addItemListener(this);
		a4.addItemListener(this);
		a5.addItemListener(this);
		a6.addItemListener(this);
		a7.addItemListener(this);
		a8.addItemListener(this);
		a9.addItemListener(this);
		a10.addItemListener(this);
		a11.addItemListener(this);
		a12.addItemListener(this);
		a13.addItemListener(this);
		a14.addItemListener(this);
		a15.addItemListener(this);
		a16.addItemListener(this);
		
		
		g.add(a1);
		g.add(a2);
		g.add(a3);
		g.add(a4);
		g.add(a5);
		g.add(a6);
		g.add(a7);
		g.add(a8);
		g.add(a9);
		g.add(a10);
		g.add(a11);
		g.add(a12);
		g.add(a13);
		g.add(a14);
		g.add(a15);
		g.add(a16);
		
		
		
		p.add(a1);
		p.add(a2);
		p.add(a3);
		p.add(a4);
		p.add(a5);
		p.add(a6);
		p.add(a7);
		p.add(a8);
		p.add(a9);
		p.add(a10);
		p.add(a11);
		p.add(a12);
		p.add(a13);
		p.add(a14);
		p.add(a15);
		p.add(a16);
		
		
		p1.add("Center",p);
		p1.add("South",b1);
		
		add(p1);
		
		
	}

	public void run() { //스레드 정의부분
		
	}

	public void itemStateChanged(ItemEvent e) { // 체크상태 확인
		if(e.getStateChange() ==ItemEvent.DESELECTED)
				return;
		
		if(a1.isSelected())
			msg="////romticon●"+"angree";
		else if(a2.isSelected())
			msg="////romticon●"+"bigsmile";
		else if(a3.isSelected())
			msg="////romticon●"+"cry";
		else if(a4.isSelected())
			msg="////romticon●"+"hart";
		else if(a5.isSelected())
			msg="////romticon●"+"moon";
		else if(a6.isSelected())
			msg="////romticon●"+"nn";
		else if(a7.isSelected())
			msg="////romticon●"+"ok";
		else if(a8.isSelected())
			msg="////romticon●"+"oppse";
		else if(a9.isSelected())
			msg="////romticon●"+"pumpkin";
		else if(a10.isSelected())
			msg="////romticon●"+"suck";
		else if(a11.isSelected())
			msg="////romticon●"+"qlwla";
		else if(a12.isSelected())
			msg="////romticon●"+"shy";
		else if(a13.isSelected())
			msg="////romticon●"+"smile";
		else if(a14.isSelected())
			msg="////romticon●"+"star";
		else if(a15.isSelected())
			msg="////romticon●"+"victory";
		else if(a16.isSelected())
			msg="////romticon●"+"chikin";
		
				
	}


	public void actionPerformed(ActionEvent e) { //액션이벤트
		Object obj = e.getSource();

		// 쪽지버튼 이벤트처리
		if ((JButton) obj == b1) {
		//	pw.println("///roommsg●"+roomname+"●"+msg+ "●"+ ID);
			pw.flush();
			pw.println(msg+"●"+ID+"●"+roomname );
			pw.flush();
			setVisible(false);
		}
	}

}
