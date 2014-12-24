5.1
db.posts.aggregate([
	{"$unwind":"$comments"
	},
	{"$group":{
		"_id":"$comments.author",
		"cc":{"$sum":1}
		}
	},
	{"$sort":{
		"cc":-1
		}
	},
	{"$limit":10
	},		
])

5.2
db.zips.aggregate([
    {$match:{
		"state": { "$in" : [ "CA", "NY" ]}
     }
    },
    {$group:{
    	"_id": { "state":"$state", "city":"$city" },
    	"citypop" : { $sum : "$pop" }
     }
    },
    {$match:{
		"citypop" : { "$gt" : 25000 }
     }
    },    
    {$group:{
    	"_id": "total",
	 	"cityavg": { "$avg" : "$citypop" }
     } 
	}    
])

5.3
db.grades.aggregate([
	{"$unwind":"$scores"
	},
	{"$project":{
		"student_id":1,
		"class_id":1,
		"score_type":"$scores.type",		
		"score_score":"$scores.score",		
		}
	},
    {$match:{
		"score_type": { "$in" : [ "exam", "homework" ]}
     }
	},	
	{"$group":{
		"_id": { "class_id" : "$class_id", "student_id" : "$student_id" },
		"avg_score":{"$avg":"$score_score"}
		}
	},	
	{"$group":{
		"_id": "$_id.class_id",
		"avg_class_score":{"$avg":"$avg_score"}
		}
	},		
	{"$sort":{
		"avg_class_score":-1
		}
	},		
	{"$limit":10
	},			
])

5.4
db.zips.aggregate([
    {$project: {
	first_char: {$substr : ["$city",0,1]},
	pop:1
     }	 
   },
	{$match: {
	first_char: { "$in" : [ "0","1","2","3","4","5","6","7","8","9"] }
     }	 
   },
    {$group:{
    	"_id": "total",
	 	"total": { "$sum" : "$pop" }
     }     
		}
])

db.zips.aggregate([
    {$group:
     {
	 _id:"$state", 
	 population:{$avg:"$pop"}
     }
    }
])


db.zips.aggregate([
    {$group:
     {
	 _id:"$city", 
	 "postal_codes":{$addToSet:"$_id"}
     }
    }
])

db.zips.aggregate([
    {$group:
     {
	 _id:"$state", 
	 pop:{$max:"$pop"}
     }
    }
])


db.zips.aggregate([
    {$project:
     {
	 _id:0, 
	 city:{$toLower:"$city"},
	 pop:"$pop",
	 state:"$state",
	 zip:"$_id"
     }
    }
])

db.zips.aggregate([
    {$match:
     {
	 "pop": { "$gt" : 100000 }
     }
    }
])


db.zips.aggregate([
    {$sort:
     {
	 "state": 1,
	 "city":1
     }
    }
])






db.posts.aggregate([
	{"$unwind":"$comments"
	},
	{"$project":{
		"email":"comments.email",
		"date":1,
		"author":1,		
		}
	},
])



db.grades.aggregate([
    {$match:{
		"scores.type": { "$in" : [ "exam", "homework" ]}
     }
	}
])

db.zips.aggregate([
    {$match:{
		"state": { "$in" : [ "CA", "NY" ]}, 
		"pop" : { "$gt" : 25000 }
     }
    },
    {$group:{
    	"_id": "total",
	 	"avg": { "$avg" : "$pop" }
     }
    },
])

db.zips.aggregate([
    {$match:{
		"state": { "$in" : [ "CT", "NJ" ]}, 
		"pop" : { "$gt" : 25000 }
     }
    },
    {$group:{
    	"_id": "total",
	 	"avg": { "$avg" : "$pop" }
     }
    },
])


,
    {$group:{
    	"_id": "total",
	 	"cityavg": { "$avg" : "$citypop" }
     }  

db.zips.find({
first_char: {$substr : ["$city",0,1]},
"pop" : { "$gt" : 25000 }
})



