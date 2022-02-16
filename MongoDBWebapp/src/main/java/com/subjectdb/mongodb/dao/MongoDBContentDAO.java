package com.subjectdb.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.subjectdb.mongodb.converter.ContentConverter;
import com.subjectdb.mongodb.model.Content;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoDBContentDAO {

	private DBCollection col;

	public MongoDBContentDAO(MongoClient mongo) {
		this.col = mongo.getDB("subjectdb").getCollection("Contents");
	}

	public Content createContent(Content c) {
		DBObject doc = ContentConverter.toDBObject(c);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		c.setId(id.toString());
		return c;
	}

	public void updateContent(Content c) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(c.getId())).get();
		this.col.update(query, ContentConverter.toDBObject(c));
	}

	public List<Content> readAllContent() {
		List<Content> data = new ArrayList<Content>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			Content c = ContentConverter.toContent(doc);
			data.add(c);
		}
		return data;
	}

	public void deleteContent(Content c) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(c.getId())).get();
		this.col.remove(query);
	}

	public Content readContent(Content c) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(c.getId())).get();
		DBObject data = this.col.findOne(query);
		return ContentConverter.toContent(data);
	}

}
