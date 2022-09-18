package com.ssk3408.DAO;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.ssk3408.model.Employee;

public class EmployeeDAO {
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement = null;

	public List<Employee> getEmployee() {

		List<Employee> list = null;
		Employee employee = new Employee();

		try {
			list = new ArrayList<Employee>();
			String sql = "SELECT * FROM tbl_employee order by id asc";
			connection = DBConnectionUtil.openConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				employee = new Employee();
				employee.setId(resultSet.getInt("id"));
				employee.setName(resultSet.getString("name"));
				employee.setProfile_pic(convertBase64Image(resultSet.getBlob("profile_pic")));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDob(resultSet.getString("dob"));
				employee.setBase64Image(convertBase64Image(resultSet.getBlob("qrcode")));
				list.add(employee);
			}
		} catch (Exception e) {
		}

		return list;
	}

	private String convertBase64Image(Blob blob) throws SQLException, IOException {
		Blob image = blob;
		InputStream inputStream = image.getBinaryStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		byte[] imageBytes = outputStream.toByteArray();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		inputStream.close();
		outputStream.close();
		return base64Image;
	}

	public boolean getlogin(int id) {
		Boolean allow = false;
		try {
			String sql = "SELECT * FROM tbl_employee where id=" + id;
			connection = DBConnectionUtil.openConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				allow = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allow;
	}

	public boolean save(Employee e) throws IOException {
		boolean flag = false;

		String sql = "insert into tbl_employee (id, name, department, dob, qrcode, profile_pic) values(?,?,?,?,?,?)";
		try {

			System.out.println("sql==" + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, e.getId());
			preparedStatement.setString(2, e.getName());
			preparedStatement.setString(3, e.getDepartment());
			preparedStatement.setString(4, e.getDob());

			FileInputStream fin = new FileInputStream("/Users/salmibaharom/QRCode/QRImage.png");
			preparedStatement.setBinaryStream(5, fin, fin.available());

			preparedStatement.setBlob(6, e.getProfileIS());

			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return flag;
	}


	public boolean update(Employee e) {
		boolean flag = false;
		String sql = "UPDATE tbl_employee SET name =?, department =?, dob=?, profile_pic=?  where id=?";
		try {

			System.out.println("sql==" + sql);
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, e.getName());
			preparedStatement.setString(2, e.getDepartment());
			preparedStatement.setString(3, e.getDob());
			preparedStatement.setBlob(4, e.getProfileIS());
			preparedStatement.setInt(5, e.getId());
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return flag;
	}

	
	public Employee get(int id) throws IOException {
		Employee employee = null;
		try {
			employee = new Employee();
			String sql = "SELECT * FROM tbl_employee where id=" + id;
			connection = DBConnectionUtil.openConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				employee.setId(resultSet.getInt("id"));
				employee.setName(resultSet.getString("name"));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDob(resultSet.getString("dob"));

				Blob imageQR = resultSet.getBlob("qrcode");
				InputStream inputStream = imageQR.getBinaryStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				byte[] imageBytes = outputStream.toByteArray();
				String base64Image = Base64.getEncoder().encodeToString(imageBytes);

				inputStream.close();
				outputStream.close();

				employee.setBase64Image(base64Image);

				
				Blob imageProfile = resultSet.getBlob("profile_pic");
				inputStream = imageProfile.getBinaryStream();
				outputStream = new ByteArrayOutputStream();
				buffer = new byte[4096];
				bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				imageBytes = outputStream.toByteArray();
				base64Image = Base64.getEncoder().encodeToString(imageBytes);
				inputStream.close();
				outputStream.close();

				employee.setProfileIS(inputStream);
				employee.setProfile_pic(base64Image);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	public boolean delete(int id) {
		boolean flag = false;
		try {
			String sql = "DELETE FROM tbl_employee where id=" + id;
			connection = DBConnectionUtil.openConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
