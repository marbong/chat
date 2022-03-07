package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Logincheck_DAO {

	public static boolean logincheck(memberDTO dto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null; // 데이터를 전송하는 객체
		ResultSet rs = null; // 데이터를 디비에서 받아오는 객체
		String id = dto.getid();
		String passwd = dto.getpasswd();


		try {
			String sql = "SELECT id FROM join_member WHERE id = '"
					+ new String(id.getBytes(), "ISO-8859-1")
					+ "' AND password = '"
					+ new String(passwd.getBytes(), "ISO-8859-1") + "'";

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/member", "root", "");
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			flag = rs.next();
			
		} catch (Exception e) {
			System.out.println(e);
			flag = false;
		} finally {

			try {

				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return flag;

	}

}
