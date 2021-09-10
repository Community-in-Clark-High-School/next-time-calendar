package tk.shachiku.openntc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.Driver;

public class Database {

	private static Connection connection;
	private static Statement statement;
	private static String dbPath = Config.getString("DBPath", true);
	private static String dbUser = Config.getString("DBUser");
	private static String dbPass =Config.getString("DBPassword");


	static {
		try {
			new Driver();
			Class.forName("org.sqlite.JDBC");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		execute("CREATE TABLE IF NOT EXISTS "+Constants.USERS_TABLE+" (user_id TEXT, password TEXT, user_name TEXT, groups TEXT, admin BOOLEAN)");
		execute("CREATE TABLE IF NOT EXISTS "+Constants.ACCESS_TOKENS_TABLE+" (access_token TEXT, user_id TEXT, expiration BIGINT)");
		execute("CREATE TABLE IF NOT EXISTS "+Constants.REFRESH_TOKENS_TABLE+" (refresh_token TEXT, user_id TEXT, expiration BIGINT)");
	}


	/**
	 * DBへの接続を返すにゃ！
	 * もし接続が切断されてたりした時は再接続をするにゃ！
	 * 例外はスタックトレースとして標準出力されるにゃ
	 * @return DBへの接続
	 */
	public static Connection getConnection() {
		try {
			if (connection==null || !connection.isValid(Constants.DB_TIME_OUT)) {
				connection = DriverManager.getConnection(dbPath, dbUser, dbPass);
			}
			return connection;

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * DBへのStatementを返すにゃ！
	 * もし接続が切断されてた時は再接続するにゃ！
	 * 例外はスタックトレースとして標準出力されるにゃ
	 * @return Statement
	 */
	public static Statement getStatement() {
		try {
			if (statement == null || connection.isValid(Constants.DB_TIME_OUT)) {
				statement = getConnection().createStatement();
			}
			return statement;

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * SQLクエリを実行するにゃー
	 * 例外はスタックトレースとして標準出力されるにゃ
	 * @param sql SQLコマンド
	 * @return {@link Statement#execute(String)}と同じにゃ。例外時はfalse。
	 */
	public static boolean execute(String sql) {
		try {
			return getStatement().execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * {@link Statement#executeQuery(String)}と同じにゃ。
	 * @param sql SQLコマンド
	 * @return ResultSet。例外時はnull。
	 */
	public static ResultSet executeQuery(String sql) {
		try {
			return getStatement().executeQuery(sql);
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
