package com.somes.test.bl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.somes.db.QueryObject;

public class UserController {
	Connection conn = null;
	public static JSONObject addNewUser(String mail) {
		String userId = UserController.checkUser(mail);
		JSONObject json = new JSONObject();
		if(userId == null) {
			String sqlAddUser = "INSERT into appuser(email) values (?)";
			try(QueryObject ob = new QueryObject();) {
				ob.executeUpdate(sqlAddUser,mail);
				json.put("newUser", true);
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			userId = UserController.checkUser(mail);
		}
		else {
			json.put("newUser", false);
		}
		json.put("userid", userId);
		return json;
	}
	public static String checkUser(String mail) {
		String sqlAddUser = "SELECT * from appuser where email = ?";
		try(QueryObject ob = new QueryObject();ResultSet dataSet = ob.execute(sqlAddUser,mail)) {
			while(dataSet.next()) {
				return dataSet.getString("user_id");	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
}
