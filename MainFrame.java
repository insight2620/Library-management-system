import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Dimension;
import java.awt.Toolkit;


public class MainFrame extends JFrame
{
	private JPanel contentPane;
	private JTable table;
	private JTextField search_textField;
	private JButton add_Button,showAll_button,clear_Button,search_button;
	private DefaultTableModel tableModel;
	private DataBase db;
	private Connection con;
    private Connection con1;
	private PreparedStatement ps;
	private static String bookInf[]={"���","���","�����","����","����","�۸�","��������","����"};
	private JComboBox comboBox_search;
	private JPopupMenu popMenu;
	private JMenuItem deleteMenuItem;
	private JMenuItem modifyJMenuItem;
	ModifyBook modifyBook;

	private JTextField isbn_textField;
	private JTextField class1_textField;
	private JTextField subclass_textField;
	private JTextField name_textField;
	private JTextField author_textField;
	private JTextField price_textField;
	private JTextField pubdate_textField;
	private JTextField introduction_textField;

	public static void main(String[] args)
	{
	    MainFrame frame = new MainFrame();
	    frame.setTitle("ͼ�����ϵͳ");
	    frame.setVisible(true);
        /*---------------------------------------------------------------*/
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screensize.getWidth();//�����Ļ�Ŀ�
        int screenHeight = (int) screensize.getHeight();//�����Ļ�ĸ�
        /*---------------------------------------------------------------*/
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);
	}


	public MainFrame()
    {
        /*---------------------------------------------------------------*/
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screensize.getWidth();//�����Ļ�Ŀ�
        int screenHeight = (int) screensize.getHeight();//�����Ļ�ĸ�
        /*---------------------------------------------------------------*/
		db=new DataBase();
		con=db.getCon();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);



		//ɾ����¼
		popMenu=new JPopupMenu();
        deleteMenuItem=new JMenuItem("ɾ��������¼");
        deleteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row=table.getSelectedRow();
				String stuId=table.getValueAt(row, 0)+"";
				try {
					ps=con.prepareStatement("delete from cbook where isbn=?");
					ps.setString(1, stuId);
					con.setAutoCommit(false);
					ps.execute();
					con.setAutoCommit(true);
					tableModel.removeRow(row);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "ɾ��ʱ�����˴���!");
					try {
						con.rollback();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "ɾ��ʱ�����˴���!");
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		});
        popMenu.add(deleteMenuItem);


        //�޸ļ�¼
        modifyJMenuItem=new JMenuItem("�޸ĸ�����¼");
        modifyJMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				int row=table.getSelectedRow();

				String isbn=table.getValueAt(row, 0)+"";
				String class1=table.getValueAt(row, 1)+"";
				String subclass=table.getValueAt(row, 2)+"";
				String name=table.getValueAt(row, 3)+"";
				String author=table.getValueAt(row, 4)+"";
				String price=table.getValueAt(row, 5)+"";
				String pubdate=table.getValueAt(row, 6)+"";
				String introduction=table.getValueAt(row, 7)+"";

				BOOK book=new BOOK(isbn,class1,subclass,name,author,price,pubdate,introduction);
				modifyBook=new ModifyBook(book);
                modifyBook.setTitle("�޸ļ�¼");
				modifyBook.setVisible(true);
			}
		});
        popMenu.add(modifyJMenuItem);

		//�����߿�
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "����", TitledBorder.LEADING, TitledBorder.TOP, null, Color.blue));
        //panel.setBounds(0, 22, 2500, 100);
        panel.setBounds(0, 22, (screenWidth-20), 100);
		contentPane.add(panel);
		panel.setLayout(null);

		//�����ı���
		search_textField = new JTextField();
		//search_textField.setText("���������");
		search_textField.setBounds((screenWidth-750), 40, 150, 35);
		panel.add(search_textField);
		search_textField.setColumns(20);


		//ѡ����ʲô����
		comboBox_search = new JComboBox();
		comboBox_search.setModel(new DefaultComboBoxModel(new String[] {"��������","����������","����������","�����߲���","���۸����","����Ų���","�����ܲ���","���������ڲ���"}));
		comboBox_search.setSelectedIndex(0);
		comboBox_search.setBounds((screenWidth-880), 40, 100, 35);
		panel.add(comboBox_search);

		///////////////////////////////////////////////////////////////
		//�����Ϣ����
		JLabel isbn_label = new JLabel("���:");//1
		isbn_label.setBounds(10, 30, 32, 15);
		panel.add(isbn_label);

		isbn_textField = new JTextField();
		isbn_textField.setText("");
		isbn_textField.setColumns(10);
		isbn_textField.setBounds(50, 30, 100, 21);
		panel.add(isbn_textField);

		JLabel class_lable = new JLabel("���:");//2
		class_lable.setBounds(160, 30, 32, 15);
		panel.add(class_lable);

		class1_textField = new JTextField();
		class1_textField.setText("");
		class1_textField.setColumns(10);
		class1_textField.setBounds(200, 30, 100, 21);
		panel.add(class1_textField);

		JLabel subclass_label = new JLabel("�����:");//3
		subclass_label.setBounds(312, 30, 42, 15);
		panel.add(subclass_label);

		subclass_textField = new JTextField();
		subclass_textField.setText("");
		subclass_textField.setColumns(10);
		subclass_textField.setBounds(356, 30, 100, 21);
		panel.add(subclass_textField);

		JLabel name_label = new JLabel("����:");//4
		name_label.setBounds(466, 30, 32, 15);
		panel.add(name_label);

		name_textField = new JTextField();
		name_textField.setText("");
		name_textField.setColumns(10);
		name_textField.setBounds(506, 30, 100, 21);
		panel.add(name_textField);
		
		JLabel author_label = new JLabel("����:");//5
		author_label.setBounds(10, 70, 32, 15);
		panel.add(author_label);

		author_textField = new JTextField();
		author_textField.setText("");
		author_textField.setColumns(10);
		author_textField.setBounds(50, 70, 100, 21);
		panel.add(author_textField);

		JLabel price_label = new JLabel("�۸�:");//6
		price_label.setBounds(160, 70, 32, 15);
		panel.add(price_label);

		price_textField = new JTextField();
		price_textField.setText("");
		price_textField.setColumns(10);
		price_textField.setBounds(200, 70, 100, 21);
		panel.add(price_textField);

		JLabel pubdate_label = new JLabel("��������:");//7
		pubdate_label.setBounds(300, 70, 58, 15);
		panel.add(pubdate_label);

		pubdate_textField = new JTextField();
		pubdate_textField.setText("");
		pubdate_textField.setColumns(10);
		pubdate_textField.setBounds(356, 70, 100, 21);
		panel.add(pubdate_textField);

		JLabel introduction_label = new JLabel("����:");//8
		introduction_label.setBounds(466, 70, 32, 15);
		panel.add(introduction_label);

		introduction_textField = new JTextField();
		introduction_textField.setText("");
		introduction_textField.setColumns(10);
		introduction_textField.setBounds(506, 70, 100, 21);
		panel.add(introduction_textField);
		/////////////////////////////////////////////////////////////

		//��Ӽ�¼��ť
		 add_Button = new JButton("��Ӽ�¼");
		 add_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkDatas())
				{
					String isbn=isbn_textField.getText();
					try {
						ps=con.prepareStatement("select *  from cbook where isbn=?");
						ps.setString(1, isbn);
						ResultSet rs=ps.executeQuery();
						if(rs.next()){//˵���������Ѿ���ʹ����
							JOptionPane.showMessageDialog(null, "�������Ѿ�������ʹ�ã�������һ�����");
							rs.close();
						}
						else{
							con.setAutoCommit(false);
							ps=con.prepareStatement("insert into cbook values(?,?,?,?,?,?,?,?)");
							ps.setString(1, isbn_textField.getText());
							ps.setString(2, class1_textField.getText());
							ps.setString(3, subclass_textField.getText());
							ps.setString(4, name_textField.getText());
							ps.setString(5, author_textField.getText());
							ps.setString(6, price_textField.getText());
							ps.setString(7, pubdate_textField.getText());
							ps.setString(8, introduction_textField.getText());
							ps.execute();
							con.commit();
							JOptionPane.showMessageDialog(null, "��ӳɹ����ڱ�β��ˢ�¿ɼ���");
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "��������ȷ�����ڸ�ʽ��");
						e1.printStackTrace();
					}
				}}
		});
		add_Button.setToolTipText("��Ӽ�¼");
		add_Button.setBounds(630, 35, 100, 45);
		panel.add(add_Button);



			//���
			table = new JTable();
		 	tableModel =new DefaultTableModel();
			table = new JTable();
			table.setModel(tableModel);
			table.setEnabled(false);
			tableModel.setColumnIdentifiers(bookInf);

			//��������¼�����
			 table.addMouseListener(new MouseAdapter() {//��ӵ�����굯������ѡ��
					public void mouseReleased(MouseEvent e)
					{
						popMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				});
			 
			  table.addMouseMotionListener(new MouseMotionListener() {
					//�������ÿ����¼�����ߵ�ʱ�򣬼�¼�ᱻѡ��
					@Override
					public void mouseMoved(MouseEvent e) {
						Point p=e.getPoint();
						int x=table.rowAtPoint(p);
						table.getSelectionModel().setSelectionInterval(x, x);
						popMenu.setVisible(false);
					}
					@Override
					public void mouseDragged(MouseEvent arg0) {
						// TODO Auto-generated method stub
					}
				});


		//���Ҽ�¼����ť��
		search_button = new JButton("����");
		search_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cleardatas();
				try {
					int m=comboBox_search.getSelectedIndex();//��ȡ��Ͽ��ѡ��
					String SE=search_textField.getText();//��ȡ�ı���
					//��������","����������","����������","�����߲���","���۸����","����Ų���","�����ܲ���","���������ڲ���"
					//String sql = "select * from cbook where class='"  +SE+  "'";
					String sql1 = "select * from cbook where class like '%"  +SE+  "%'";
					String sql2 = "select * from cbook where subclass like '%"  +SE+  "%'";
					String sql3 = "select * from cbook where name like '%"  +SE+  "%'";
					String sql4 = "select * from cbook where author like '%"  +SE+  "%'";
					String sql5 = "select * from cbook where price like '%"  +SE+  "%'";
					String sql6 = "select * from cbook where isbn like '%"  +SE+  "%'";
					String sql7 = "select * from cbook where introduction like '%"  +SE+  "%'";
					String sql8 = "select * from cbook where pubdate like '%"  +SE+  "%'";
					switch(m)
					{
						case 0:
							ps=con.prepareStatement(sql1);
							break;
						case 1:
							ps=con.prepareStatement(sql2);
							break;
						case 2:
							ps=con.prepareStatement(sql3);
							break;
						case 3:
							ps=con.prepareStatement(sql4);
							break;
						case 4:
							ps=con.prepareStatement(sql5);
							break;
						case 5:
							ps=con.prepareStatement(sql6);
							break;
						case 6:
							ps=con.prepareStatement(sql7);
							break;
						case 7:
							ps=con.prepareStatement(sql8);
							break;
						default:
			                System.out.println(m);
					}		
					ResultSet rs=ps.executeQuery();

					System.out.println(SE);
					boolean flag=false;
					while(rs.next())
					{
						flag=true;
						String data[]=new String[255];
						data[0]=rs.getString(1);
						data[1]=rs.getString(2);
						data[2]=rs.getString(3);
						data[3]=rs.getString(4);
						data[4]=rs.getString(5);
						data[5]=rs.getString(6);
						data[6]=rs.getString(7);
						data[7]=rs.getString(8);
						tableModel.addRow(data);
					}
					if(!flag)
					{
						JOptionPane.showMessageDialog(null, "��ѯ���Ϊ�գ�");
					}
				}
				catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		search_button.setToolTipText("����");
		search_button.setBounds((screenWidth-580),40, 80,35 );
		panel.add(search_button);




			//������壨��ʾCBOOK��
			JScrollPane scrollPane = new JScrollPane(table);
			//scrollPane.setBounds(50, 130, 1830, 1200);
            scrollPane.setBounds(0, 130, (screenWidth-15), (screenHeight-220));
			scrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u663E\u793A", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(13, 27, 255)));
			contentPane.add(scrollPane);
			showAll_button = new JButton("ˢ��");
			////////////////////////////////////////////////////////////
        	//�������޸ĺ��Զ�ˢ��
        	try {
                ps=con.prepareStatement("select * from cbook");
                ResultSet rs=ps.executeQuery();
                boolean flag=false;
                while(rs.next()){
                    flag=true;
                    String data[]=new String[255];
                    data[0]=rs.getString(1);
                    data[1]=rs.getString(2);
                    data[2]=rs.getString(3);
                    data[3]=rs.getString(4);
                    data[4]=rs.getString(5);
                    data[5]=rs.getString(6);
                    data[6]=rs.getString(7);
                    data[7]=rs.getString(8);
                    tableModel.addRow(data);}
        	} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        /////////////////////////////////////////////////////////////
		 	showAll_button.addActionListener(new ActionListener()
            {
		 	public void actionPerformed(ActionEvent e)
            {
		 		cleardatas();
		 		try {
					ps=con.prepareStatement("select * from cbook");
					ResultSet rs=ps.executeQuery();
					boolean flag=false;
					while(rs.next()){
					flag=true;
						String data[]=new String[255];
						data[0]=rs.getString(1);
						data[1]=rs.getString(2);
						data[2]=rs.getString(3);
						data[3]=rs.getString(4);
						data[4]=rs.getString(5);
						data[5]=rs.getString(6);
						data[6]=rs.getString(7);
						data[7]=rs.getString(8);
						tableModel.addRow(data);
					}
					if(!flag){
						JOptionPane.showMessageDialog(null, "��ѯ���Ϊ�գ�");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 		
		 	}
		 });
		showAll_button.setToolTipText("ˢ�¿�");
		showAll_button.setBounds((screenWidth-430),40, 130,35 );
		panel.add(showAll_button);
		clear_Button = new JButton("�رտ�");
		clear_Button.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		cleardatas();
		 	}
		 });
		clear_Button.setBounds((screenWidth-280), 40, 130, 35);
		panel.add(clear_Button);
	}

	private void cleardatas(){//�رտ�
		int rows = tableModel.getRowCount();
		if (rows != 0)
			for (int i = 0; i < rows; i++)
				tableModel.removeRow(0);
	}


	//����Ϸ��Լ��
	private boolean checkDatas()
	{
		//�÷�������������������ӵ�ʱ���Ƿ�������붼�ǺϷ���
		boolean result=true;
		String isbn=isbn_textField.getText();
		String class1=class1_textField.getText();
		String subclass=subclass_textField.getText();
		String name=name_textField.getText();
		String author=author_textField.getText();
		String price=price_textField.getText();
		String pubdate=pubdate_textField.getText();
		String introduction=introduction_textField.getText();

 		if(isbn==null||isbn.equals("")){
 			JOptionPane.showMessageDialog(null, "��Ų���Ϊ�գ�");
			isbn_textField.requestFocus();
 			result=false;
 		}
 		else if(class1==null||class1.equals("")){
 			JOptionPane.showMessageDialog(null, "�����Ϊ�գ�");
 			class1_textField.requestFocus();
 			result=false;
 		}
 		else if(subclass==null||subclass.equals("")){
 			JOptionPane.showMessageDialog(null, "�������Ϊ�գ�");
			subclass_textField.requestFocus();
 			result=false;
 		}
 		else if(name==null||name.equals("")){
 			JOptionPane.showMessageDialog(null, "��������Ϊ�գ�");
			name_textField.requestFocus();
 			result=false;
 		}
		else if(author==null||author.equals("")){
			JOptionPane.showMessageDialog(null, "���߲���Ϊ�գ�");
			author_textField.requestFocus();
			result=false;
		}
		else if(price==null||price.equals("")){
			JOptionPane.showMessageDialog(null, "�۸���Ϊ�գ�");
			price_textField.requestFocus();
			result=false;
		}
		else if(pubdate==null||pubdate.equals("")){
			JOptionPane.showMessageDialog(null, "�������ڲ���Ϊ�գ�");
			pubdate_textField.requestFocus();
			result=false;
		}
		else if(introduction==null||introduction.equals("")){
			JOptionPane.showMessageDialog(null, "���ܲ���Ϊ�գ�");
			introduction_textField.requestFocus();
			result=false;
		}
		else {
			try {
				int price1=Integer.parseInt(price);
				if(price1<0){
					JOptionPane.showMessageDialog(null, "�۸���Ϊ������");
					result=false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "�۸����Ϊ���֣������������۸�");
				result=false;
			}
		}
 		return result;
	}
}
