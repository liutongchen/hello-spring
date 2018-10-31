package com.liutong.website.factory;

import com.mongodb.*;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.omg.CORBA.PUBLIC_MEMBER;

@SuppressWarnings("deprecation")
public class MongoFactory {
    private static Logger log = Logger.getLogger(MongoFactory.class);
    private static Mongo mongo;

    public static Mongo getMongo() {
        int portNum = 27017;
        String hostName = "localhost";
        if (mongo == null) {
            try {
                mongo = new Mongo(hostName, portNum);
            } catch(MongoException e) {
                log.error(e);
            }
        }
        return mongo;
    }

    public static DB getDB(String dbName) {
        return getMongo().getDB(dbName);
    }

    public static DBCollection getCollection(String dbName, String collectionName) {
        return getDB(dbName).getCollection(collectionName);
    }

    public static DBObject getDBObjectById(String dbName, String collectionName, String id) {
        DBCollection coll = getCollection(dbName, collectionName);
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return coll.findOne(query);
    }
}
