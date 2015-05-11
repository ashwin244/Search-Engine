package userInterface;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class DatabaseClient {
	private static MongoClient dbInstance = null;

	// returns a Mongo client. Can be instantiated only once.
	public static MongoClient getClient() throws Exception {
		try {
			if (dbInstance == null) {
				dbInstance = new MongoClient(new ServerAddress("localhost", 27017));
			}
			return dbInstance;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	// returns the database
	public static DB GetDatabase(String dbName) throws Exception {
		try {
			return dbInstance.getDB(dbName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public static DBCollection GetDBCollection(DB database,
			String collectionName) throws Exception {
		try {
			return database.getCollection(collectionName);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
