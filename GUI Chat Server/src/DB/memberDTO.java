
package DB;

public class memberDTO {

	private String name;
	private String id;
	private String passwd;
	private String gender;
	private String job;
	private String info;

	public memberDTO() {
		// TODO Auto-generated constructor stub
	}

	public memberDTO(String name, String id, String passwd,
			String gender, String job, String info) {

		this.name = name;
		this.id = id;
		this.passwd = passwd;
		this.gender = gender;
		this.job = job;
		this.info = info;
	}


	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getgender() {
		return gender;
	}

	public void setgender(String gender) {
		this.gender = gender;
	}

	public String getpasswd() {
		return passwd;
	}

	public void setpasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getjob() {
		return job;
	}

	public void setjob(String job) {
		this.job = job;
	}

	public String getinfo() {
		return info;
	}

	public void setinfo(String info) {
		this.info = info;
	}

}
