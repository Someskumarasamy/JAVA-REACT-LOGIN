package com.somes.test.bl;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionHandler {
	private static HashMap<String,String>csrfmap = new HashMap<>();
	public static HttpServletResponse checkCsrf(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String csrf=req.getHeader("x-csrf-token");
		String host=req.getRemoteHost();
		if(csrfmap.containsKey(host)) {
			if(!csrfmap.get(host).equals(csrf)) {
				res.setStatus(500);
			}
		}else {
			
		}
		return res;
	}
	public static void destroy(HttpServletRequest req,HttpServletResponse res) {
		HttpSession session=req.getSession(); 
		session.invalidate();
	}
}
