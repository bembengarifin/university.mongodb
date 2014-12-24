var MongoClient = require('mongodb').MongoClient;

MongoClient.connect('mongodb://localhost:27017/school', function(err, db) {
	"use strict";

	if (err) throw err;
	
	//var options = { "limit" : 2 };
	var options = { };
	var totalRecordsUpdated = 0;
	
	//db.collection('students').findOne({ '_id': 0 }, function(err, doc) {
	db.collection('students').find({}, options, function(err, cursor) {
		"use strict";

		if (err) throw err;

		cursor.each(function(err, doc) {
			if (err) throw err;
		
			if (doc != null) {
				//console.log(doc);
				//console.log(doc.name);

				var lowest;
				var scores = doc.scores;
				for (var score in scores) {
					//console.log(scores[score].type);
					if (scores[score].type == "homework") {
						//console.log(scores[score].score);
						if (lowest == null || (lowest != null && lowest.score > scores[score].score)) {
							lowest = scores[score];
						}
					}
				}

				//console.log(lowest);
				if (lowest != null) {
					var itemIndexToRemove = scores.indexOf(lowest);
					scores.splice(itemIndexToRemove,1);
				}
				//console.log(doc);
		
				//db.collection('students').update({ '_id' : doc._id }, { $set: { 'scores' : scores } }, { w : 0}, function(err, recordsUpdated) {
				//	console.log(recordsUpdated);
				//	totalRecordsUpdated = totalRecordsUpdated + recordsUpdated;
				//});
				
				db.collection('students').update({ '_id' : doc._id }, { $set: { 'scores' : scores } }, { w : 0 });
				totalRecordsUpdated += 1;				
			}
			else {
				console.log('Completed the update process for ' + totalRecordsUpdated + ' rows');
				return db.close();							
			}
		});

	});

});
