package com.servlet.loginServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.classes.users.UsersDataBase;
import com.constants.QueryConstants;



/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email = request.getParameter(com.constants.UIConstants.loginEmail);
			String password = request.getParameter(com.constants.UIConstants.loginPassword);
			Connection con = UsersDataBase.getConnection();
			Statement stmt = con.createStatement();
			String query =  QueryConstants.CHECKEMAIL+ email + "'"; 
			ResultSet rs = stmt.executeQuery(query);
			String userEmail, userPassword, userName, lastName, firstName, userId;
			
			Boolean isAdmin;
			String success = "false";
			if(rs.next()){
				userEmail = rs.getString(1);
				userPassword = rs.getString(2);
				firstName = rs.getString(3);
				isAdmin = rs.getBoolean(4);
				lastName = rs.getString(5);
				userId=rs.getString(6);
			if(userPassword.equals(password)){
				success = "true";
				response.sendRedirect(com.constants.URLConstants.loginSucessUrl + success);
				HttpSession session = request.getSession();	
				session.setAttribute(com.constants.UIConstants.sessionName, firstName+" "+lastName);	
				session.setAttribute(com.constants.UIConstants.sessionUser_id, userId);
				session.setAttribute(com.constants.UIConstants.sessionIsAdmin, isAdmin);
				session.setAttribute(com.constants.UIConstants.sessionLoggedIn, true);
			}
			else{
				success = "false";
				System.out.println("sorry");
				response.sendRedirect(com.constants.URLConstants.loginSucessUrl + success);
			}
			}
			else{
				success="username_incorrect";
				response.sendRedirect(com.constants.URLConstants.loginSucessUrl + success);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
