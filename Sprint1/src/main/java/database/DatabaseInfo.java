package database;

public class DatabaseInfo {

	protected static String driver = "org.postgresql.Driver";
	protected static String connectionString;
	protected static String username;
	protected static String password;
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
}
