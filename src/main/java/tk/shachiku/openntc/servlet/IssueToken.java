package tk.shachiku.openntc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tk.shachiku.openntc.Constants;
import tk.shachiku.openntc.Database;
import tk.shachiku.openntc.NTCUtils;

@SuppressWarnings("serial")
public class IssueToken extends HttpServlet {

	private static ObjectMapper mapper = new ObjectMapper();


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			String userId = req.getParameter("id");
			String password = req.getParameter("pass");
			String refresh = req.getParameter("refresh");

			//ユーザーIDとパスワード使用時
			if (!NTCUtils.isEmptyAny(userId, password)) {
				ResultSet result = Database.executeQuery("SELECT * FROM "+Constants.USERS_TABLE+" WHERE user_id='"+userId+"' AND password='"+password+"'");
				if (result.next())
					out.print(issueToken(userId).toString());
				else
					out.print(NTCUtils.buildFailMessage("IDかパスワードが間違っています。"));

			//リフレッシュトークン使用時
			} else if (!NTCUtils.isEmpty(refresh)) {
				ResultSet result = Database.executeQuery("SELECT * FROM "+Constants.REFRESH_TOKENS_TABLE+" WHERE refresh_token='"+refresh+"' AND expiration>"+getUnixTime());
				if (result.next())
					out.print(issueToken(result.getString("user_id")).toString());
				else
					out.print(NTCUtils.buildFailMessage("リフレッシュトークンが無効です。"));

			//パラメータが不足時
			} else {
				out.print(NTCUtils.buildFailMessage("IDとパスワードか、リフレッシュトークンのどちらかを指定してください"));
			}

		} catch(Exception e) {
			e.printStackTrace();
			out.print(NTCUtils.buildFailMessage(e));
		}

	}


	/**
	 * アクセストークンとリフレッシュトークンを返すにゃ
	 * @param userId ユーザーID
	 * @return トークンとその他情報を含むJson
	 */
	private ObjectNode issueToken(String userId) {
		ObjectNode root = mapper.createObjectNode();
		ObjectNode access = root.putObject("access_token");
		ObjectNode refresh = root.putObject("refresh_token");

		root.put("success", true);

		UUID accessToken = UUID.randomUUID();
		long accessExpiration = getUnixTime()+Constants.ACCESS_TOKEN_AVAILABLE;
		Database.execute("INSERT INTO "+Constants.ACCESS_TOKENS_TABLE+" VALUES ('"+accessToken+"','"+userId+"',"+accessExpiration+")");
		access.put("value", accessToken.toString());
		access.put("expiration", accessExpiration);

		UUID refreshToken = UUID.randomUUID();
		long refreshExpiration = getUnixTime()+Constants.REFRESH_TOKEN_AVAILABLE;
		Database.execute("INSERT INTO "+Constants.REFRESH_TOKENS_TABLE+" VALUES ('"+refreshToken+"','"+userId+"',"+refreshExpiration+")");
		refresh.put("value", refreshToken.toString());
		refresh.put("expiration", refreshExpiration);

		return root;
	}


	private static long getUnixTime() {
		return System.currentTimeMillis()/1000;
	}

}
