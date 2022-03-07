package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class change_DAO {

	public static memberDTO change(String id, memberDTO dto) throws Exception {

		Connection con = null;
		Statement stmt = null; // 데이터를 전송하는 객체
		ResultSet rs = null; // 데이터를 디비에서 받아오는 객체
		

		try {
			String sql = "SELECT * FROM join_member WHERE id = '"
					+ new String(id.getBytes("ISO-8859-1"), "EUC-KR") + "'";

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/member", "root", "");

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if(rs.next()){
				//System.out.println(new String(rs.getString("name").getBytes("ISO-8859-1"), "EUC-KR"));
				//System.out.println(new String(rs.getString("gender").getBytes("ISO-8859-1"), "EUC-KR"));
				dto.setname(new String(rs.getString("name").getBytes("ISO-8859-1"), "EUC-KR"));
				dto.setgender(new String(rs.getString("gender").getBytes("ISO-8859-1"), "EUC-KR"));
				dto.setjob(new String( rs.getString("job").getBytes("ISO-8859-1"), "EUC-KR"));
				dto.setinfo(new String(rs.getString("info").getBytes("ISO-8859-1"), "EUC-KR"));
				
			}

		} catch (Exception e) {

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

		return dto;

	}

}
