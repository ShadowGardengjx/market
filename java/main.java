import java.sql.Connection;
import java.sql.DriverManager;

public class main{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_sup002?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功！");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
