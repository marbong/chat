package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class update_DAO {

	public static boolean update(memberDTO dto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null; // 데이터를 전송하는 객체
		String id = dto.getid();
		String passwd = dto.getpasswd();
		String name = dto.getname();
		String gender = dto.getgender();
		String job = dto.getjob();
		String info = dto.getinfo();


		try {
			String sql = " UPDATE join_member set name ='"
					+ new String(name.getBytes(), "ISO-8859-1")
					+ "' , password = '"
					+ new String(passwd.getBytes(), "ISO-8859-1")
					+ "' , gender = '"
					+ new String(gender.getBytes(), "ISO-8859-1") + "' , job = '"
					+ new String(job.getBytes(), "ISO-8859-1") + "' , info = '"
					+ new String(info.getBytes(), "ISO-8859-1") + "' WHERE id = '"
					+ new String(id.getBytes(), "ISO-8859-1") + "' ";

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
