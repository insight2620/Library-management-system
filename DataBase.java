import java.sql.*;
public class DataBase
{
    private String Url ="jdbc:mysql://127.0.0.1:3306/test?useSSL=false";//���ݿ⣨testΪ���cbook������ݿ⣩
    private String User="root";		//�û���
    private String Pwd="25802580";		//���루�������޸��Լ���MYSQL���룩

    public DataBase()
    {
        try
        {
            //�������ݿ���������9
        	Class.forName("com.mysql.jdbc.Driver"); 
        }
        catch (ClassNotFoundException exception)
        {
        	exception.printStackTrace();
        }
    }
    //��ȡ���ݿ�����
    public Connection getCon()
    {
        Connection con = null;
        try
        {
          //�������ݿ�����
          con = DriverManager.getConnection(Url, User, Pwd);
          System.out.println("Connection_success");
        }
        catch (SQLException exception)
        {
          exception.printStackTrace();
        }
        return con;
    }
    //�ر����ݿ�����
    public void closeConnection(Connection con)
    {
        try
        {
            if(con!=null) con.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String a[])
    {
    	DataBase db=new DataBase();
    	Connection connection=db.getCon();
    }
}

