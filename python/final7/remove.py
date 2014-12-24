import pymongo
import sys

from pymongo import MongoClient

# connect to database
connection = MongoClient('localhost', 27017)

db = connection.photosharing

# handle to images collection
images = db.images
albums = db.albums

try:
    iter = images.find()

    for item in iter:
        album = albums.find_one({"images":item["_id"]})
        if album is None:
            #print "remove " + str(item["_id"])
            images.remove(item["_id"])

except:
    print "Error trying to read collection:", sys.exc_info()[0]


