package issuedHistory;

import java.sql.Connection;

/** Kept so existing code still compiles. The real settings live in db/Database.java and db.properties. */
public class databaseConnection
{
	public static Connection doConnect()
	{
		return db.Database.connect();
	}
}
