package me.mert1602.advancedapi.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lombok.Getter;
import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.tool.Tool;

public class MySQL {

	@Getter private String host;
	@Getter private int port = 3306;
	@Getter private String user;
	@Getter private String password;
	@Getter private String database;
	@Getter private int timeout = 5;

	public MySQL host(String host){
		this.host = host;
		return this;
	}

	public MySQL port(int port){
		this.port = port;
		return this;
	}

	public MySQL user(String user){
		this.user = user;
		return this;
	}

	public MySQL password(String password){
		this.password = password;
		return this;
	}

	public MySQL database(String database){
		this.database = database;
		return this;
	}

	public MySQL timeout(int timeout){
		this.timeout = timeout;
		return this;
	}

	private Connection currentConnection;

	public PreparedStatement prepareStatement(final String sql){

		PreparedStatement ps = null;

		try {

			if(this.getConnection() == null) return null;

			ps = this.getConnection().prepareStatement(sql);
			ps.executeUpdate();

			return ps;

		} catch (final Throwable e) {
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return null;
		} finally{
			Tool.close(ps);
		}

	}

	public ResultSet resultSet(final PreparedStatement ps){

		ResultSet rs = null;

		try {
			return rs = ps.executeQuery();
		} catch (final Throwable e) {
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return null;
		} finally {
			Tool.close(ps, rs);
		}

	}

	public boolean isValid(){

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return true;
		} catch (final Throwable e) {
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return false;
		}

	}

	public Connection getConnection(){
		return !this.hasConnection() ? this.openConnection() : this.currentConnection;
	}

	public boolean hasConnection(){

		if((this.isValid() == false) || (this.currentConnection == null)){
			return false;
		}

		try {

			if(this.currentConnection.isClosed()){
				return false;
			}

			if(this.currentConnection.isValid(this.timeout) == false){
				return false;
			}

			return true;

		} catch (final Throwable e) {
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return false;
		}

	}

	public Connection openConnection(){

		if(this.isValid() == false) return null;
		if(this.hasConnection()) return this.currentConnection;

		this.closeConnection();

		try {
			return this.currentConnection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
		} catch (final Throwable e) {
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return null;
		}

	}

	public void closeConnection(){

		if(this.currentConnection == null) return;

		try {

			if(!this.currentConnection.isClosed()) this.currentConnection.close();

		} catch (final Throwable e) {
			Log.exception(e, this.getClass(), Tool.getLineNumber());
		} finally {
			MySQL.this.currentConnection = null;
		}

	}

}