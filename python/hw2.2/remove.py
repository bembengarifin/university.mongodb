import pymongo
import sys

from pymongo import MongoClient

# connect to database
connection = MongoClient('localhost', 27017)

db = connection.students

# handle to grades collection
grades = db.grades

# Write a program in the language of your choice that will remove the grade of type "homework" with the lowest score for each student from the dataset that you imported in HW 2.1. Since each document is one grade, it should remove one document per student.
# If you select homework grade-documents, sort by student and then by score, you can iterate through and find the lowest score for each student by noticing a change in student id. As you notice that change of student_id, remove the document. 
#grade = grades.find_one()

lastStudentId = None
lastId = None

try:
    iter = grades.find({"type": "homework"}).sort([('student_id', pymongo.ASCENDING), ('score', pymongo.DESCENDING)])

    for item in iter:
        if lastStudentId != None and lastStudentId != item["student_id"]:
            print "remove " + str(lastId)
            grades.remove(lastId)

        print item
        lastStudentId = item["student_id"]
        lastId = item["_id"]

    if lastId != None:
        grades.remove(lastId)

except:
    print "Error trying to read collection:", sys.exc_info()[0]

#print grade
#print grade['student_id']

