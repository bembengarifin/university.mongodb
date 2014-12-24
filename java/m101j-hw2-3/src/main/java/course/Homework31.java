package course;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class Homework31 {

	public static void main(String[] args) throws UnknownHostException {
		MongoClient mc = new MongoClient();
		DB db = mc.getDB("school");
		DBCollection students = db.getCollection("students");

		DBCursor cur = students.find();

		while (cur.hasNext()) {
			BasicDBObject student = (BasicDBObject) cur.next();
			// System.out.println(obj);

			BasicDBList list = (BasicDBList) student.get("scores");
			// System.out.println(list);

			BasicDBObject lowestHomeworkScore = null;
			for (Object item : list) {
				BasicDBObject dbObject = (BasicDBObject) item;
				// System.out.println(dbObject);
				if (dbObject.get("type").equals("homework")) {
					//System.out.println(dbObject);
					
					if (lowestHomeworkScore == null)
					{
						lowestHomeworkScore = dbObject;
					}
					else
					{
						if (dbObject.getDouble("score") < lowestHomeworkScore.getDouble("score"))
						{
							lowestHomeworkScore = dbObject;
						}
					}
				}
			}
			
			if (lowestHomeworkScore != null)
			{
				list.remove(lowestHomeworkScore);
			}
			
			//System.out.println(student.get("_id") + ":" + lowestHomeworkScore.getDouble("score"));
			//System.out.println(newScores);
			//student.remove("scores");
			//student.append("scores", newScores);
			
			//System.out.println(student);
			
			WriteResult wr = students.update(new BasicDBObject("_id", student.get("_id")), new BasicDBObject("$set", new BasicDBObject("scores", list)));
			System.out.println(wr);
		}
	}

}
