package userInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 
 * @src http://docs.mongodb.org/ecosystem/tutorial/use-aggregation-framework-with-java-driver/
 *
 */
public class QueryManager 
{
	public static AggregationOutput ProcessQuery(String inQuery, DBCollection collection)
	{
		try
		{
			//First try
			String[] inQueryArray = inQuery.split(" ");
	        List<String> list = new ArrayList<String>();
	        
	        //InQuery for query matching
	        for(int i = 0; i < inQueryArray.length; i++)
	        list.add(inQueryArray[i].toLowerCase());
	        
	        //Create a match
	        DBObject match = new BasicDBObject("$match", new BasicDBObject("_id.word", new BasicDBObject("$in", list)));
	        
	        //Projection required? - if we have to pick out new field like position
	        
	        //Group by operation
	        DBObject group = new BasicDBObject( "$group", new BasicDBObject("_id","$_id.fileName").append("total", new BasicDBObject("$sum", "$value.tfIdf") )
	        .append("count", new BasicDBObject("$sum", 1) ) );
	        
	        //Sort operation
	        DBObject sort = new BasicDBObject("$sort", new BasicDBObject("count", -1).append("total", -1) );
	        
	      //Limit operation
	        DBObject limit = new BasicDBObject("$limit", 5);
	        
	        //Run projection
	        List<DBObject> pipeline = Arrays.asList(match, group, sort, limit);
	        AggregationOutput output = collection.aggregate(pipeline);
	        return output;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		

	}
	
	public static String GetMetaTextBasedOnQuery(String query, DBCollection icsDataCollection, String fileName)
	{
		try
		{
			BasicDBObject queryObject = new BasicDBObject("url", fileName);
			String textToBeProcessed = (String) icsDataCollection.findOne(queryObject).get("text");
			textToBeProcessed = textToBeProcessed.toLowerCase();
			//get the list of query terms
			List<String> queryList = new ArrayList<String>();
			String[] queryArray = query.split(" ");
			StringBuilder sbMetadata;
			for(String item : queryArray)
			{
				queryList.add(item);
			}
			//case where the entire query is a part of the file
			 int indexOfEntireQuery = textToBeProcessed.indexOf(query);
			if(indexOfEntireQuery > 0)
			{ 
				sbMetadata = new StringBuilder();
				sbMetadata.append(textToBeProcessed.substring(((indexOfEntireQuery - 100) > 0) ? 
						(indexOfEntireQuery - 99) : indexOfEntireQuery, 
						((indexOfEntireQuery + 100) < textToBeProcessed.length()) ? 
								(indexOfEntireQuery + 99) : indexOfEntireQuery));
			}
			
			//case where the query terms are not coalesced in the document
			else
			{
				sbMetadata = new StringBuilder();
				for(int i = 0; i < queryList.size(); i++)
				{
					if(textToBeProcessed.contains(queryArray[i]))
					{
						int indexOfEachQueryItem = textToBeProcessed.indexOf(queryArray[i]);
						sbMetadata.append(textToBeProcessed.substring( ((indexOfEachQueryItem - 100) > 0) ? 
								(indexOfEachQueryItem - 99) : indexOfEachQueryItem, 
								((indexOfEachQueryItem + 99) < textToBeProcessed.length()) ? 
										(indexOfEachQueryItem + 99) : indexOfEachQueryItem));
						
						sbMetadata.append("....");
					}
				}
			}
			
			return sbMetadata.toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
	}
}
