package com.somes.test.bl;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.somes.db.QueryObject;

public class UserRoleController {
	private static boolean changeUserRole(String userId, String roleId) throws Exception {
		String sqlAddUser = "UPDATE userrolemapping SET role_id = ?::BIGINT where user_id = ?::BIGINT";
		ArrayList<Object> tt = new ArrayList<>();
		tt.add(roleId);
		tt.add(userId);
		try(QueryObject ob = new QueryObject();) {
			ob.executeUpdate(sqlAddUser,tt);
			return true;
		}
	}
	private static boolean addUserRole(String userId, String roleId) throws Exception {
		String sqlAddUser = "INSERT into userrolemapping (user_id,role_id) values (?::BIGINT,?::BIGINT)";
		ArrayList<Object> tt = new ArrayList<>();
		tt.add(userId);
		tt.add(roleId);
		try(QueryObject ob = new QueryObject();) {
			ob.executeUpdate(sqlAddUser,tt);
			return true;
		}
	}
	public static String[] mapUserAndRole(String userId, String roleId) {
		String usrrole = isUserHasRole(userId);
		try {
			if(usrrole!=null) {
				UserRoleController.changeUserRole(userId,roleId);
			}else {
				UserRoleController.addUserRole(userId, roleId);
			}
			return getRolenameForUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String[] getRolenameForUser(String userId) {
		String sql_query = "select * from userrolemapping inner join approles on userrolemapping.role_id = approles.role_id where user_id = ?::BIGINT";
		try(QueryObject ob = new QueryObject();ResultSet dataSet = ob.execute(sql_query,(Object)userId)) {
			while(dataSet.next()) {
				if( dataSet.getString("user_id") == null) {
					return null;
				}else {
					return new String[] {dataSet.getString("role_common_name"),dataSet.getString("role_id")};
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String isUserHasRole(String userId) {
		String sql_query = "select * from userrolemapping where user_id = ?::BIGINT";
		try(QueryObject ob = new QueryObject();ResultSet dataSet = ob.execute(sql_query,(Object)userId)) {
			while(dataSet.next()) {
				return dataSet.getString("role_id");	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
