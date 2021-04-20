import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class ModifyBook extends JFrame {

	private JPanel contentPane;
	private JTextField isbn_textField;
	private JTextField class1_textField;
	private JTextField subclass_textField;
	private JTextField name_textField;
	private JTextField author_textField;
	private JTextField price_textField;
	private JTextField pubdate_textField;
	private JTextField introduction_textField;

	private BOOK book;
	private DataBase dBase;
	private Connection connection;
	private java.sql.PreparedStatement ps;
	private java.sql.PreparedStatement ps1;
	private JComboBox comboBox;
	/**
	 * Create the frame.
	 */
	public ModifyBook(final BOOK book1)
	{
		dBase=new DataBase();
		connection=dBase.getCon();
		this.book=book1;
		setResizable(true);
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 500, 500);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "输入", TitledBorder.LEADING, TitledBorder.TOP, null, Color.blue));
		panel.setBounds(100, 52, 260, 370);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("编号");
		label.setBounds(10, 40, 52, 15);
		panel.add(label);
		
		JLabel label_1 = new JLabel("类别");
		label_1.setBounds(10, 70, 52, 15);
		panel.add(label_1);
		
		JLabel label_3 = new JLabel("子类别");
		label_3.setBounds(10, 100, 52, 15);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("书名");
		label_4.setBounds(10, 130, 52, 15);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("作者");
		label_5.setBounds(10, 160, 52, 15);
		panel.add(label_5);

		JLabel label_6 = new JLabel("价格");
		label_6.setBounds(10, 190, 52, 15);
		panel.add(label_6);

		JLabel label_7 = new JLabel("出版日期");
		label_7.setBounds(10, 220, 52, 15);
		panel.add(label_7);

		JLabel label_8 = new JLabel("介绍");
		label_8.setBounds(10, 250, 52, 15);
		panel.add(label_8);

		isbn_textField = new JTextField(book1.getisbn());
		isbn_textField.setColumns(10);
		isbn_textField.setEditable(false);
		isbn_textField.setBounds(72, 40, 150, 21);
		panel.add(isbn_textField);

		class1_textField = new JTextField(book1.getclass1());
		class1_textField.setColumns(10);
		class1_textField.setBounds(72, 70, 150, 21);
		panel.add(class1_textField);

		subclass_textField = new JTextField(book1.getsubclass()+"");
		subclass_textField.setColumns(10);
		subclass_textField.setBounds(72, 100, 150, 21);
		panel.add(subclass_textField);

		name_textField = new JTextField(book1.getname());
		name_textField.setColumns(10);
		name_textField.setBounds(72, 130, 150, 21);
		panel.add(name_textField);

		author_textField = new JTextField(book1.getauthor());
		author_textField.setColumns(10);
		author_textField.setBounds(72, 160, 150, 21);
		panel.add(author_textField);

		price_textField = new JTextField(book1.getprice());
		price_textField.setColumns(10);
		price_textField.setBounds(72, 190, 150, 21);
		panel.add(price_textField);

		pubdate_textField = new JTextField(book1.getpubdate());
		pubdate_textField.setColumns(10);
		pubdate_textField.setBounds(72, 220, 150, 21);
		panel.add(pubdate_textField);

		introduction_textField = new JTextField(book1.getintroduction());
		introduction_textField.setColumns(10);
		introduction_textField.setBounds(72, 250, 150, 21);
		panel.add(introduction_textField);

		JButton button = new JButton("确认修改");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkdatas()){
				try {
					System.out.println(book1.getisbn()+book1.getclass1()+book1.getsubclass()+book1.getname());
					ps=connection.prepareStatement("update cbook set isbn=? ,class=?, subclass=? , name=?,author=?,price=?,pubdate=?,introduction=? WHERE isbn=? ");
				    ps.setString(1, isbn_textField.getText());
				    ps.setString(2, class1_textField.getText());
				    ps.setString(3, subclass_textField.getText());
				    ps.setString(4, name_textField.getText());
				    ps.setString(5, author_textField.getText());
					ps.setString(6, price_textField.getText());
					ps.setString(7, pubdate_textField.getText());
					ps.setString(8, introduction_textField.getText());
					ps.setString(9, isbn_textField.getText());
				    connection.setAutoCommit(false);
				    ps.executeUpdate();
				    connection.commit();
				    JOptionPane.showMessageDialog(null, "信息更新成功！刷新可见");
					setVisible(false);

				    //////////////////////////////////////////////////////////////////////
				    //修改后需要自动刷新一次
					/////////////////////////////////////////////////////////////////////
				}
				catch (SQLException e1)
				{
					  JOptionPane.showMessageDialog(null, "请输入正确的信息格式！");
					  try
					  {
						connection.rollback();
					}
					  catch (SQLException e2) {
						 JOptionPane.showMessageDialog(null, "请输入正确的信息格式！");
						e2.printStackTrace();
					}
					e1.printStackTrace();
				}
				
			}}
		});
		button.setBounds(55, 300, 150, 60);
		panel.add(button);
	}


	//输入合法性检查
	private boolean checkdatas()
	{
		//该方法用来检查进行数据添加的时候是否各个输入都是合法的
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
			JOptionPane.showMessageDialog(null, "编号不能为空！");
			isbn_textField.requestFocus();
			result=false;
		}
		else if(class1==null||class1.equals("")){
			JOptionPane.showMessageDialog(null, "类别不能为空！");
			class1_textField.requestFocus();
			result=false;
		}
		else if(subclass==null||subclass.equals("")){
			JOptionPane.showMessageDialog(null, "子类别不能为空！");
			subclass_textField.requestFocus();
			result=false;
		}
		else if(name==null||name.equals("")){
			JOptionPane.showMessageDialog(null, "书名不能为空！");
			name_textField.requestFocus();
			result=false;
		}
		else if(author==null||author.equals("")){
			JOptionPane.showMessageDialog(null, "作者不能为空！");
			author_textField.requestFocus();
			result=false;
		}
		else if(price==null||price.equals("")){
			JOptionPane.showMessageDialog(null, "价格不能为空！");
			price_textField.requestFocus();
			result=false;
		}
		else if(pubdate==null||pubdate.equals("")){
			JOptionPane.showMessageDialog(null, "出版日期不能为空！");
			pubdate_textField.requestFocus();
			result=false;
		}
		else if(introduction==null||introduction.equals("")){
			JOptionPane.showMessageDialog(null, "介绍不能为空！");
			introduction_textField.requestFocus();
			result=false;
		}
		return result;
	}
}

