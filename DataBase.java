import java.sql.*;
public class DataBase
{
    private String Url ="jdbc:mysql://127.0.0.1:3306/test?useSSL=false";//数据库（test为存放cbook表的数据库）
    private String User="root";		//用户名
    private String Pwd="25802580";		//密码（在这里修改自己的MYSQL密码）

    public DataBase()
    {
        try
        {
            //加载数据库驱动程序9
        	Class.forName("com.mysql.jdbc.Driver"); 
        }
        catch (ClassNotFoundException exception)
        {
        	exception.printStackTrace();
        }
    }
    //获取数据库连接
    public Connection getCon()
    {
        Connection con = null;
        try
        {
          //建立数据库连接
          con = DriverManager.getConnection(Url, User, Pwd);
          System.out.println("Connection_success");
        }
        catch (SQLException exception)
        {
          exception.printStackTrace();
        }
        return con;
    }
    //关闭数据库连接
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

