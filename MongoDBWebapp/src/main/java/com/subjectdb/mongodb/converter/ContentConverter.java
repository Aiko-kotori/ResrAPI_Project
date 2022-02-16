package com.subjectdb.mongodb.converter;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.subjectdb.mongodb.model.Content;

public class ContentConverter {

	// convert Content Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(Content c) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("temp", c.getTemp()).append("group", c.getGroup()).append("time", c.getTime());
		if (c.getId() != null)
			builder = builder.append("_id", new ObjectId(c.getId()));
		return builder.get();
	}

	// convert DBObject Object to Content
	// take special note of converting ObjectId to String
	public static Content toContent(DBObject doc) {
		Content c = new Content();
		c.setTemp((String) doc.get("temp"));
		c.setGroup((String) doc.get("group"));
		c.setTime((String) doc.get("time"));
		ObjectId id = (ObjectId) doc.get("_id");
		c.setId(id.toString());
		return c;

	}

}
