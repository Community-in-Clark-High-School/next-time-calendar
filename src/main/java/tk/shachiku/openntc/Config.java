package tk.shachiku.openntc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 設定ファイルから値を読み込むクラスにゃ！
 * ちなみにコメントアウトの処理がめんどくさかったのでReadOnlyにゃ～！
 * ファイル形式はYamlのコメントアウトと、ネスト無しの連想配列的だけにしたみたいな感じにゃ！()
 */
public class Config {

	private static Map<String, String> config;


	static {
		load();
	}


	/**
	 * 全てのキーと値が入ったMapを返すにゃ～！
	 * でもでもコピーしたものじゃないから書き換えないでね！！
	 * @return 全てのキーと値が代入されたMap
	 */
	public static Map<String, String> getAll() {
		return config;
	}


	/**
	 * 設定ファイルから指定された文字列を返すにゃー。
	 * 実質的にgetString(String, boolean)と同じにゃ。
	 * @param key キー
	 * @return 文字列の値
	 * @see Config#getString(String, boolean)
	 */
	public static String getString(String key) {
		return config.get(key);
	}


	/**
	 * 設定ファイルから指定された文字列を返すにゃー。
	 * checkをtrueにした場合、値が空文字もしくはnullの時に警告を出します。
	 * @param key キー
	 * @param check null、空文字チェックを有効化
	 * @return 文字列の値
	 * @see Config#getString(String)
	 */
	public static String getString(String key, boolean check) {
		String value = config.get(key);
		if (check && value == null || value.isEmpty())
			System.out.println("設定ファイルの項目("+key+")の値が見つかりません！");
		return value;
	}


	/**
	 * 設定ファイルから指定されたboolean型の値を返すにゃ！
	 * @param key キー
	 * @return boolean型の値
	 */
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key, true));
	}


	/**
	 * 設定ファイルから指定された8bit整数を返すにゃ。
	 * @param key キー
	 * @return byte型の値
	 */
	public static byte getByte(String key) {
		return Byte.parseByte(getString(key, true));
	}


	/**
	 * 設定ファイルから指定された32bit整数を返すにゃー。
	 * @param key キー
	 * @return int型の値
	 * @throws NumberFormatException 値のフォーマットがおかしい場合
	 */
	public static int getInt(String key) {
		return Integer.parseInt(getString(key, true));
	}


	/**
	 * 設定ファイルから指定された64bit整数を返すにゃー。
	 * @param key キー
	 * @return long型の値
	 * @throws NumberFormatException 値のフォーマットがおかしい場合
	 */
	public static long getLong(String key) {
		return Long.parseLong(getString(key, true));
	}


	/**
	 * 設定ファイルから指定された32bit浮動小数点数を返すにゃ。
	 * @param key キー
	 * @return float型の値
	 * @throws NumberFormatException 値のフォーマットがおかしかった場合
	 */
	public static Float getFloat(String key) {
		return Float.parseFloat(getString(key, true));
	}


	/**
	 * 設定ファイルから指定された64bit浮動小数点数を返すにゃ。
	 * @param key キー
	 * @return double型の値
	 * @throws NumberFormatException 値のフォーマットがおかしかった場合
	 */
	public static double getDouble(String key) {
		return  Double.parseDouble(getString(key, true));
	}


	/**
	 * 指定されたキーが存在するか返すにゃ！
	 * @param key 探すキー
	 * @return 存在した場合true、そうでない場合false
	 */
	public static boolean containsKey(String key) {
		return config.containsKey(key);
	}


	/**
	 * 指定された値が存在するか返すにゃ！
	 * @param value 探す値
	 * @return 存在した場合true、そうでない場合false
	 */
	public static boolean containsValue(String value) {
		return config.containsValue(value);
	}


	/**
	 * 設定ファイルの絶対パスを返すにゃ！
	 * @return 絶対にゃんぱすー
	 */
	public static String getConfigPath() {
		return Constants.CFG_FILE.getAbsolutePath();
	}


	/**
	 * 設定ファイルをロードするにゃ！
	 */
	public static void load() {
		if (!Constants.CFG_FILE.exists())
			saveDefaultConfig();
		try(BufferedReader reader = new BufferedReader(new FileReader(Constants.CFG_FILE))) {
			config = new HashMap<>();
			reader.lines().forEach(l-> {
				if (l.startsWith("# "))
					return;
				String[] tmp = l.split(": ", 2);
				config.put(tmp[0], tmp[1]);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void saveDefaultConfig() {
		try(BufferedReader in = new BufferedReader(new FileReader(new File(Constants.WEB_INF, "default.cfg")));
				BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CFG_FILE))) {
			in.lines().forEach(l -> {
				try {
					out.write(l);
					out.write(System.lineSeparator());
				} catch(IOException e) {
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
