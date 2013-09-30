package database;

public class DatabaseInfo {

	private static String driver = "org.postgresql.Driver";
	private static String connectionString;
	private static String username;
	private static String password;
	public static DatabaseInfo instance;

	public static void initialize(String connectionString,
			String username, String password) {
		if (instance == null) {
			instance = new DatabaseInfo(connectionString, username, password);
		}
	}

	private DatabaseInfo(String connectionString, String username,
			String password) {
		DatabaseInfo.connectionString = connectionString;
		DatabaseInfo.username = username;
		DatabaseInfo.password = password;
	}

	public static String getDriver() {
		return driver;
	}

	public static String getConnectionString() {
		return connectionString;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static DatabaseInfo getInstance() {
		return instance;
	}
}
