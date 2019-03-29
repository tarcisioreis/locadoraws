/**
 *
 *		Classe de conexao por JDBC com banco MySQL local
 *
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class ConnectionBase {

	private Connection conn;
	private String strConnection;
	
	public ConnectionBase() {
		try {
			conn = null;

			String url = "jdbc:mysql://localhost:3306/locadora?user=root&password=root@localhost&useTimezone=true&serverTimezone=UTC";
			this.setStrConnection(url);
			conn = DriverManager.getConnection(this.getStrConnection());
			this.setConnection(conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.conn;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public String getStrConnection() {
		return strConnection;
	}
	private void setStrConnection(String strConnection) {
		this.strConnection = strConnection;
	}
	
}
