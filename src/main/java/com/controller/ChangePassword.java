package com.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.EmployeeDAO;


@WebServlet("/changepassword")
public class ChangePassword extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String email = (String) session.getAttribute("email");
		
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		String confirmpassword = request.getParameter("confirmpassword");
		System.out.println(newpassword+"   "+confirmpassword);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ChangePassword.jsp");
		if(newpassword.equals(confirmpassword)) {
			EmployeeDAO dao = new EmployeeDAO();
			try {
				boolean checkPass = dao.checkPassword(email, oldpassword);
				if(!checkPass) {
					request.setAttribute("status", "You have entered wrong current password..");
					dispatcher.forward(request, response);
				}
				boolean status = dao.changePassword(email,oldpassword,newpassword);
				if(status) {
					
					request.setAttribute("status", "Password Changed Successfully.........");
					dispatcher.forward(request, response);
				}
				else {
					
					request.setAttribute("status", "Invalid Credentials.........");
					dispatcher.forward(request, response);
	
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			
			request.setAttribute("status", "New Password and Confirm Password should be same.........");
			dispatcher.forward(request, response);
		}
	}

	

}
