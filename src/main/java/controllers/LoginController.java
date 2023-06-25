package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.tuple.Pair;

import managers.ManageUsers;
import models.User;

/* IMPORTS WE ADD */
import javax.servlet.http.Cookie;
import models.Login;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.print("LoginController: ");
		
		User user = new User();
		/* P3 CODE: next line */
		Login login = new Login();
		ManageUsers manager = new ManageUsers();
		String view = "ViewLoginForm.jsp";
		Boolean success = null;
		
	    try {
			
	    	BeanUtils.populate(login, request.getParameterMap());
			
	    	if (login.isComplete()) {
	    		//P3 CODE
	    		success = manager.loginUser(login.getUsername(), login.getPwd());
	    		
	    		if (success) {
		    		System.out.println("login OK, implementing Cookies for 30 days");
		    		
		    		//P3 CODE
					if (login.getRememberMe()) {
						Cookie cUserName = new Cookie("cookuser", login.getUsername());
						Cookie cPassword = new Cookie("cookpass", login.getPwd());
						cUserName.setMaxAge(60 * 60 * 24 * 30); // 30 days
						cPassword.setMaxAge(60 * 60 * 24 * 30);
						response.addCookie(cUserName);
						response.addCookie(cPassword);
						System.out.println("login OK & Cookies set, forwarding to ViewOwnTimeline ");
					}
					
					// added to be able to add user as attribute in the session
					user = manager.getUser(login.getUsername());

	    			HttpSession session = request.getSession();
	    			session.setAttribute("user", user);
	    			view = "ViewOwnTimeline.jsp";
	    			
	    		}
	    		else {
	    			System.out.println("user is not logged (user not found), forwarding to ViewLoginForm ");
	    			request.setAttribute("error", true);
					request.setAttribute("user",user);
				}
		    } 
			
	    	else {
			    System.out.println("user is not logged (first time), forwarding to ViewLoginForm ");
				request.setAttribute("user",user);
	    	}
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		    
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	    
	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

