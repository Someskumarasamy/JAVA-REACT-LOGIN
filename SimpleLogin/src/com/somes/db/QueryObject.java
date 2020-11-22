package com.somes.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class QueryObject implements AutoCloseable {
	private Connection conn = null;
	private PreparedStatement ps = null;

	public QueryObject() {
		conn = null;
		ps = null;
	}

	private void prepareStatement(String query, ArrayList<Object>args) throws SQLException, IOException {
		if (this.conn == null) {
			this.conn = DBCPDataSource.getConnection();
		}
		this.ps = conn.prepareStatement(query);
		if (args != null && !args.isEmpty()) {
			int count = 1;
			for(Object i : args) {
				this.ps.setObject(count, i);
				count++;
			}
			
		}

	}
	public ResultSet execute(String query,ArrayList<Object>param) throws SQLException, IOException {
		ResultSet dta = null;
		this.prepareStatement(query, param);
		dta = ps.executeQuery();
		return dta;
	}
	public void executeUpdate(String query,ArrayList<Object>param) throws SQLException, IOException {
		this.prepareStatement(query, param);
		this.ps.executeUpdate();
		this.ps.close();
		this.ps=null;
		return;
	}
	public void executeUpdate(String query,Object param) throws SQLException, IOException {
		ArrayList<Object> tt = new ArrayList<>();
		tt.add(param);
		this.executeUpdate(query, tt);
		return;
	}
	public ResultSet execute(String query,Object param) throws SQLException, IOException {
		ArrayList<Object> tt = new ArrayList<>();
		tt.add(param);
		return this.execute(query, tt);
	}
	public ResultSet execute(String query) throws SQLException, IOException {
		return this.execute(query, null);
	}
	public Connection getConnection() throws SQLException {
		this.conn = DBCPDataSource.getConnection();
		return this.conn;
	}
	private void closeConnection() throws SQLException {
		if (this.conn != null) {
			this.conn.close();
		}
	}
	private void closePs() throws SQLException {
		if (this.ps != null) {
			this.ps.close();
		}
	}

	@Override
	public void close() throws Exception {
		System.out.println("Closing ds");
		this.closePs();
		this.closeConnection();
		
	}
}
