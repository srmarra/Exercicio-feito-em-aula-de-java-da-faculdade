package banco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexao {
	static Connection conexao = null;
	static String user = "root";
	static String senha = "root";
	static String url = "jdbc:mysql://localhost:3306/mybank?useTimezone=true&serverTimezone=UTC";//verificar porta
	static String driver = "com.mysql.cj.jdbc.Driver";
	
	public static Connection conectar(){
			try {
				Class.forName(driver);
				conexao = DriverManager.getConnection(url, user, senha);
				//System.out.println("Conectou");
			}catch(ClassNotFoundException | SQLException ex) {
				System.out.println("Falha ao se conectar"+ex);
			}
			return conexao;
	}
}
