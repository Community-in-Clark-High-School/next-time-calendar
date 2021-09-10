package tk.shachiku.openntc;

public class NTCUtils {

	/**
	 * 指定された文字列がnullもしくは空文字の場合true、
	 * そうでない場合にfalseを返すにゃ！
	 * @param str チェックする文字列
	 * @return
	 */
	public static final boolean isEmpty(String str) {
		return str==null || str.isEmpty();
	}


	/**
	 * 指定された複数の文字列の中に一つでもnullもしくは空文字
	 * の文字列があった場合にtrue、そうでない場合にfalseを返すにゃ！
	 * @param strings チェックする文字列
	 * @return
	 */
	public static final boolean isEmptyAny(String... strings) {
		for(String str : strings) {
			if (str==null || str.isEmpty())
				return true;
		}
		return false;
	}


	/**
	 * 指定された複数の文字列がすべてnullもしくは空文字
	 * だった場合にtrue、そうでない場合にfalseを返すにゃ！
	 * @param strings チェックする文字列
	 * @return
	 */
	public static final boolean isEmptyAll(String... strings) {
		for(String str : strings) {
			if (str==null || str.isEmpty())
				continue;
			return false;
		}
		return true;
	}


	/**
	 * {"success":false,"cause":"エラー要因"} のようなメッセージを生成するにゃ！
	 * @param cause エラー要因
	 * @return
	 */
	public static final String buildFailMessage(String cause) {
		return "{\"success\":false,\"cause\":\""+cause+"\"}";
	}


	/**
	 * 例外から{"success":false,"cause":"例外:NullPointerException:がっ"}
	 * のようなメッセージを生成するにゃ！
	 * @param e 例外
	 * @return
	 */
	public static final String buildFailMessage(Exception e) {
		return "{\"success\":false,\"cause\":\"例外:"+e.getClass().getSimpleName()+":"+e.getMessage()+"\"}";
	}

}
