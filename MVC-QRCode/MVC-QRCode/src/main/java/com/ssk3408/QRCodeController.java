package com.ssk3408;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ssk3408.DAO.EmployeeDAO;
import com.ssk3408.model.Employee;

@MultipartConfig(maxFileSize = 16177215)
public class QRCodeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestDispatcher dispatcher = null;
	EmployeeDAO employeeDAO = null;
	String clickUpdate = "";
	
	public QRCodeController() {
		employeeDAO = new EmployeeDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String action = request.getParameter("action");

		switch (action) {

		case "LIST":
			listEmployee(request, response);
			break;

		case "EDIT":
			clickUpdate = "Yes";
			getSingleEmployee(request, response);
			break;

		case "DELETE":
			deleteEmployee(request, response);
			break;

		default:
			listEmployee(request, response);
			break;

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null) {
			action = "LOGIN";
		}

		switch (action) {

		case "LOGIN":
			loginEmployee(request, response);
			break;

		case "SAVE":
			System.out.println("dah masuk case SAVE");
			saveEmployee(request, response);
			break;

		default:
			listEmployee(request, response);
			break;

		}
	}


	private void loginEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String id = request.getParameter("uname");

		boolean allow = employeeDAO.getlogin(Integer.parseInt(id));
		if (allow) {
			listEmployee(request, response);

		} else {
			request.setAttribute("NOTIFICATION", "Unauthorized Login!");
			dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		if (employeeDAO.delete(Integer.parseInt(id))) {
			request.setAttribute("NOTIFICATION", "Employee Deleted Successfully!");
		}

		listEmployee(request, response);
	}

	private void getSingleEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String id = request.getParameter("id");

		Employee theEmployee = employeeDAO.get(Integer.parseInt(id));

		request.setAttribute("employee", theEmployee);

		dispatcher = request.getRequestDispatcher("/EmployeeForm.jsp");

		dispatcher.forward(request, response);
	}

	private void listEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		EmployeeDAO dao = new EmployeeDAO();
		List<Employee> theList = dao.getEmployee();
		request.setAttribute("employee", theList);
		RequestDispatcher rd = request.getRequestDispatcher("/EmployeeList.jsp");
		rd.forward(request, response);
	}

	protected void saveEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		System.out.println("nilai ID="+id);
		generate_qr(id);

		Employee e = new Employee();

		e.setId(Integer.parseInt(request.getParameter("id")));
		e.setName(request.getParameter("name"));
		e.setDepartment(request.getParameter("department"));
		e.setDob(request.getParameter("dob"));
		
		System.out.println("dah masuk saveEmployee");
		
		System.out.println("nilai profile_pic"+request.getPart("profile_pic"));
		InputStream inputStream = null;
		Part filePart = request.getPart("profile_pic");
		
        if (filePart != null) {
        	System.out.println("dah masuk filePart");
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }  
		e.setProfileIS(inputStream);
		
		if (clickUpdate != "Yes") {
			if (employeeDAO.save(e)) {
				request.setAttribute("NOTIFICATION", "Employee Saved Successfully!");
			}

		} else {
			e.setId(Integer.parseInt(id));
			if (employeeDAO.update(e)) {
				clickUpdate = "";
				request.setAttribute("NOTIFICATION", "Employee Updated Successfully!");
			}
		}

		listEmployee(request, response);
	}

	public static void generate_qr(String qrCodeData) {

		String filePath = "/Users/salmibaharom/QRCode/QRImage.png";

		System.out.println("dah masuk generate QR");
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200);
		} catch (WriterException e) {
			e.printStackTrace();
		}

		Path path = FileSystems.getDefault().getPath(filePath);
		try {
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
