/**
 *
 *		Classe de teste de conexao por JDBC com banco MySQL local
 *
 */
package connection;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
class ConnectionBaseTest {

	private Connection conn;

	@Test
	void testInit() {
		
		try {
			ConnectionBase connBase = new ConnectionBase();
			connBase.setConnection(DriverManager.getConnection(connBase.getStrConnection()));
			System.out.println(connBase.getConnection().getSchema());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

			fail(ex.getMessage());
		}
	}

	@Test
	void testGetConnection() {
		try {
			String url = "jdbc:mysql://localhost:3306/locadora?user=root&password=root@localhost&useTimezone=true&serverTimezone=UTC";
			conn = DriverManager.getConnection(url);
			System.out.println(conn.getSchema());
			System.out.println(conn.isClosed());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

			fail(ex.getMessage());
		}
	}

	@Test
	void testResultSQL() {
		try {
			String url = "jdbc:mysql://localhost:3306/locadora?user=root&password=root@localhost&useTimezone=true&serverTimezone=UTC";
			conn = DriverManager.getConnection(url);

			String sql = "SELECT * FROM usuario";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				System.out.println(rs.getString("nome"));
			}
			
			conn.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

			fail(ex.getMessage());
		}
	}
	
}
