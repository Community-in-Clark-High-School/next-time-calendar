package tk.shachiku.openntc;

import java.io.File;

public class Constants {

	public static final String APP_NAME = "NextTimeCalendar";

	public static final File WEB_INF = new File(Constants.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
	public static final File CFG_FILE = new File(System.getProperty("catalina.base"), "conf/openntc.cfg");

	public static final int DB_TIME_OUT = 3;//sec
	public static final String ACCESS_TOKENS_TABLE = "access_tokens";
	public static final String REFRESH_TOKENS_TABLE = "refresh_tokens";
	public static final String USERS_TABLE = "users";

	public static final int ACCESS_TOKEN_AVAILABLE = 86400;//sec
	public static final int REFRESH_TOKEN_AVAILABLE = 2592000;//sec

}
