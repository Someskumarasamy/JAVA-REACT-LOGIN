package com.somes.test;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.somes.test.bl.SessionHandler;
import com.somes.test.bl.UserController;
import com.somes.test.bl.UserRoleController;

import org.json.simple.JSONObject;

public class UserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4380727350872455350L;
	Connection conn = null;
	public void doOptions(HttpServletRequest requestObj, HttpServletResponse responseObj) {
		responseObj.setHeader("Access-Control-Allow-Origin", "*");
		responseObj.setStatus(200);
	}
	public void doGet(HttpServletRequest requestObj, HttpServletResponse responseObj) {
		String operationType = requestObj.getParameter("oper-type");
		responseObj.setHeader("Access-Control-Allow-Origin", "*");
		// requestObj = SessionHandler.checkSession(requestObj);
		JSONObject json = new JSONObject();
		switch (operationType) {
		case "getuserrole":
		{
			HttpSession session = requestObj.getSession();
			String uid = (String)session.getAttribute("userid");
			if(uid != null ) {
				String dta[] = UserRoleController.getRolenameForUser(uid);
				if(dta != null) {
					json.put("roleName", dta[0]);
					json.put("roleId", dta[1]);
					json.put("name", (String)session.getAttribute("name"));
					json.put("image", (String)session.getAttribute("image"));					
				}else {
					responseObj.setStatus(400);
				}
			}
			else {
				responseObj.setStatus(400);
			}
		}
			break;
		case "logout":{
			HttpSession session = requestObj.getSession();
			session.invalidate();
			try {
				responseObj.sendRedirect("/SimpleLogin/");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}

		default:
			responseObj.setStatus(404);
			break;
		}
		try {
			responseObj.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest requestObj, HttpServletResponse responseObj) {
		
		String operationType = requestObj.getParameter("oper-type");
		// requestObj = SessionHandler.checkSession(requestObj);
		JSONObject json = new JSONObject();
		switch (operationType) {
		case "uservisit": {
			JSONObject tmp = UserController.addNewUser((String) requestObj.getParameter("email"));
			if (tmp == null) {
				responseObj.setStatus(500);
			}
			HttpSession session = requestObj.getSession();
			json.put("data", tmp);
			session.removeAttribute("userid");
			session.setAttribute("userid", tmp.get("userid"));
			System.out.print(session.getId());
			System.out.print((String) session.getAttribute("userid"));
		}
			break;
		case "changerole":{
			HttpSession session = requestObj.getSession(false);
			String uid = (String) session.getAttribute("userid");
			String roleId = Optional.ofNullable((String) requestObj.getSession().getAttribute("roleId")).orElse((String) requestObj.getParameter("roleId"));
			if(uid != null && roleId != null ) {
				String[] dta = UserRoleController.mapUserAndRole(uid,roleId);
				if(dta != null) {
					json.put("roleName", dta[0]);
					json.put("roleId", dta[1]);
				}else {
					responseObj.setStatus(500);
				}
			}
			else {
				responseObj.setStatus(400);
			}
		}
			break;
		default:
			responseObj.setStatus(404);
			break;
		}
		try {
			responseObj.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Function<HttpServletResponse, JSONObject> geterrorJSON = (res) -> {
		res.setStatus(500);
		return new JSONObject();
	};
}
