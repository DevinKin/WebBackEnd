import java.sql.Connection;
import java.sql.DriverManager;

public class oracle {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:helowin";
            String user = "oracle";
            String password = "oracle";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
