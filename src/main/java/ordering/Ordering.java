package ordering;

import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@Component
public class Ordering {
	
	private static Logger logger = Logger.getLogger(Ordering.class.getName());

	private final String dbURL = "mongodb://cinema:cinema@140.121.196.23:4115";
	private final String dbName = "OrderingList";
	private final String collectionName = "orderingList";
	
	public String newMovieOrdering(String moviesID) {
		try {

	         
	         MongoCollection<Document> collection = mongodbGetCollection();
	         
	         
	         ArrayList<Document> documents = new ArrayList<Document>();
	         
	         
	         // split moviesID from ,
	         String[] moviesIDArr = moviesID.split(",");
	         
	         // insert into documents
	         for(int i = 0; i < moviesIDArr.length; i++) {
		         Document doc = new Document("ObjectID", moviesIDArr[i])
		        		    .append("Category", "Movie")
		        		    .append("Quantity", 0);
		         
		         documents.add(doc);
	         }

	         // insert into collecion
    		collection.insertMany(documents);

	         return "success";
		} catch (Exception e) {  
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            logger.warning("function newMovieOrdering test unsuccessfully");
            return e.getClass().getName() + ": " + e.getMessage();
        }
	}
	
	public String newGroceryOrdering(String groceryID, String quantity) {
		try {
	         
	         MongoCollection<Document> collection = mongodbGetCollection();
	         
	         
	         ArrayList<Document> documents = new ArrayList<Document>();
	         
	         
	         // 
	         String[] groceryIDArr = groceryID.split(",");
	         String[] quantityArr = quantity.split(",");
	         
	         // insert into documents
	         for(int i = 0; i < groceryIDArr.length; i++) {
		         Document doc = new Document("ObjectID", groceryIDArr[i])
		        		    .append("Category", "Grocery")
		        		    .append("Quantity", quantityArr[i]);
		         
		         documents.add(doc);
	         }

	         // insert into collecion
    		collection.insertMany(documents);
    		
	         return "success";
		} catch (Exception e) {  
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            logger.warning("function newGroceryOrdering test unsuccessfully");
            return e.getClass().getName() + ": " + e.getMessage();
        }
	}
	
	
	public String getMovieFromOrderList(String userID) {
		try {

			MongoCollection<Document> collection = mongodbGetCollection();

            String result = "[";

            FindIterable<Document> fi = collection.find(eq("Category","Movie"));
            MongoCursor<Document> cursor = fi.iterator();
            while(cursor.hasNext()) 
            {
            	result += cursor.next().toJson();
            	if(cursor.hasNext())
            		result += ",";
            }
            result += "]";
            System.out.println("Connect to database successfully");
            
            logger.info("function getMovieFromOrderList test successfully");
            
            return result;
            
        } catch (Exception e) {  
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            logger.warning("function getMovieFromOrderList test unsuccessfully");
            return "{}";
        }
	}
	
	
	public String getGroceryFromOrderList(String userID) {
		try {

			MongoCollection<Document> collection = mongodbGetCollection();

            String result = "[";

            FindIterable<Document> fi = collection.find(eq("Category","Grocery"));
            MongoCursor<Document> cursor = fi.iterator();
            while(cursor.hasNext()) 
            {
            	result += cursor.next().toJson();
            	if(cursor.hasNext())
            		result += ",";
            }
            result += "]";
            System.out.println("Connect to database successfully");
            
            logger.info("function getGroceryFromOrderList test successfully");
            
            return result;
            
        } catch (Exception e) {  
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            logger.warning("function getGroceryFromOrderList test unsuccessfully");
            return "{}";
        }
	}

	public MongoCollection<Document> mongodbGetCollection() {
		try {

			System.out.println("MongoDBConnect to database begin");
			logger.info("MongoDBConnect to database begin");

			MongoClient mongoClient = MongoClients.create("mongodb://cinema:cinema@140.121.196.23:4115");

			MongoDatabase mongoDatabase = mongoClient.getDatabase("OrderingList");
			System.out.println("MongoDBConnect to database successfully");
			logger.info("MongoDBConnect to database successfully");

			MongoCollection<Document> collection = mongoDatabase.getCollection("orderingList");

			if( collection != null){
				System.out.println("Get collection successfully");
				logger.info("Get collection successfully");
			}


			return collection;

		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

		return null;
	}
	

}

