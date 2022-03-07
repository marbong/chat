package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class insertDB_DAO {

	public static boolean create(memberDTO dto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null; // 데이터를 전송하는 객체
		String id = dto.getid();
		String passwd = dto.getpasswd();
		String name = dto.getname();
		String gender = dto.getgender();
		String job = dto.getjob();
		String info = dto.getinfo();

		String sql = "INSERT INTO join_member(id, password, name, gender, job,info) VALUES";

		try {
			sql += "('" + new String(id.getBytes(), "ISO-8859-1") + "','"
					+ new String(passwd.getBytes(), "ISO-8859-1") + "','"
					+ new String(name.getBytes(), "ISO-8859-1") + "','"
					+ new String(gender.getBytes(), "ISO-8859-1") + "','"
					+ new String(job.getBytes(), "ISO-8859-1") + "','"
					+ new String(info.getBytes(), "ISO-8859-1") + "')";

			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/member", "root", "");
			stmt = con.createStatement();
			stmt.executeUpdate(sql);

			flag = true;
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
