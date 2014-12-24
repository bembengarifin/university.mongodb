var MongoClient = require('mongodb').MongoClient;

MongoClient.connect('mongodb://localhost:27017/test', function(err, db) {
	"use strict";

	if (err) throw err;
	
	var options = { };
	var totalRecordsUpdated = 0;
	
	//db.collection('images').find({ '_id' : 0 }, options, function(err, cursor) {
	db.collection('images').find({}, options, function(err, cursor) {
		"use strict";

		if (err) throw err;

		cursor.each(function(err, doc) {
			if (err) throw err;
		
			if (doc != null) {
				console.log(doc._id);
				
				db.collection('albums').findOne({ 'images' : doc._id }, function(err, album) {
					"use strict";
					
					if (err) throw err;
					
					if (album != null) {
						//console.log(album._id);
					}
					else {
					
						db.collection('images').remove({ '_id' : doc._id}, function(err) {
							if (err) throw err;
							//console.log('Removed orphan image ' + doc._id);
							totalRecordsUpdated += 1;
						});
					}
					
				});	
			}
			else {
				console.log('Completed the removal process for ' + totalRecordsUpdated + ' rows');
				//return db.close();	
				return true;						
			}
		});

	});

});
