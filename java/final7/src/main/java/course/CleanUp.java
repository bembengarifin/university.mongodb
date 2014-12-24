package course;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CleanUp {

    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));
        DB blogDatabase = mongoClient.getDB("blog");

		DBCollection images = blogDatabase.getCollection("images");
		DBCollection albums = blogDatabase.getCollection("albums");
		System.out.println("Start - Total images: " + images.getCount());	
		
		DBCursor cursor = images.find();
		List<Integer> imagesToBeRemoved = new ArrayList<Integer>();
		while( cursor.hasNext() )
		{
     		DBObject obj = cursor.next();	
     		int imageId = (Integer) obj.get("_id");
     		//System.out.println(imageId);
     		
     		DBObject foundAlbum = albums.findOne(new BasicDBObject("images", imageId), new BasicDBObject("_id", 1));
     		if (foundAlbum != null)
     		{
     			//System.out.println("Image Id: " + imageId + " was found in album id: " + foundAlbum.get("_id"));
     		}
     		else
     		{
     			//System.out.println("Image Id: " + imageId + " was not found in any album id, to be removed"); 
     			imagesToBeRemoved.add(imageId);
     		}
		}
		System.out.println("Total images to be removed: " + imagesToBeRemoved.size());
		
		for(int id : imagesToBeRemoved)
		{
			images.remove(new BasicDBObject("_id", id));
		}
		
		System.out.println("Completed - Total images: " + images.getCount());	
    }
}
