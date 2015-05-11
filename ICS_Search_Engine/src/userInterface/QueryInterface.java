package userInterface;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

public class QueryInterface 
{	
	private DB database;
	
	private DBCollection collection_ICSData;
	private DBCollection collection_IndexData;
	
	public QueryInterface()
	{
		InitializeCollections();
	}
	
	//method to initialize all collections ( ICS data and index data)
	public void InitializeCollections()
	{
		try
		{
			MongoClient mongoClient = DatabaseClient.getClient();
			mongoClient.setWriteConcern(WriteConcern.JOURNALED);
			
			database = DatabaseClient.GetDatabase("ICS");
			
			collection_ICSData = DatabaseClient.GetDBCollection(database, "cICSData2");
			collection_IndexData = DatabaseClient.GetDBCollection(database, "indexStorage3");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
		public String GetSearchResults(String query) { 
		try
		{
			AggregationOutput output = QueryManager.ProcessQuery(query , collection_IndexData);
			StringBuilder str = new StringBuilder();
			for(DBObject result : output.results())
	        {
	        	String urlName = (String) result.get("_id");
	        	String metaData = QueryManager.GetMetaTextBasedOnQuery(query, collection_ICSData, urlName);
	        	str.append("<a href=").append(urlName).append(" target='_blank'").append(">").append(urlName).append("</a>").append("<br>");
	        	str.append(metaData).append("<br><br>");
	        }
			return str.toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
		
		
		public List<String> GetStringSearchResults(String query)
		{
			try
			{
				List<String> resultList = new ArrayList<String>();
				AggregationOutput output = QueryManager.ProcessQuery(query , collection_IndexData);
				for(DBObject result : output.results())
		        {
		        	String urlName = (String) result.get("_id");
		        	resultList.add(urlName);
		        }
				return resultList;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw ex;
			}
		}
	
}
