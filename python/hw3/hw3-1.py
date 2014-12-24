import pymongo
import sys

from pymongo import MongoClient

# connect to database
connection = MongoClient('localhost', 27017)

db = connection.school

# handle students collection
students = db.students

# Write a program in the language of your choice that will remove the lowest homework score for each student. Since there is a single document for each student containing an array of scores, you will need to update the scores array and remove the homework.
#Remember, just remove a homework score. Don't remove a quiz or an exam!
#Hint/spoiler: With the new schema, this problem is a lot harder and that is sort of the point. One way is to find the lowest homework in code and then update the scores array with the low homework pruned.

lastStudentId = None
lastId = None

try:
    iter = students.find({})

    for item in iter:
        lowestHomeworkScore = None
        #print item['scores']
        for score in item['scores']:
            if score['type'] == "homework":
                #print score['type']
                if lowestHomeworkScore is None or lowestHomeworkScore['score'] > score['score']:
                    lowestHomeworkScore = score
        print "Lowest home work score for " + str(item['_id']) + " is " + str(lowestHomeworkScore)

        item['scores'].remove(lowestHomeworkScore);

        result = students.update({ "_id" : item['_id']}, { "$set" : { "scores" : item["scores"] }})
        print result;
except:
    print "Error trying to read collection:", sys.exc_info()[0]

#print grade
#print grade['student_id']
