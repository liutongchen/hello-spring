package com.liutong.website.services;

import com.liutong.website.entities.experiences.*;
import com.liutong.website.factory.MongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service("experiencesService")
public class ExperiencesService {
    static String dbName = "personalWebsite", collectionName = "experiences";
    private static Logger log = Logger.getLogger(ExperiencesService.class);

    /**
     * Fetch a list of all experiences
     * @return
     */
    public List<Experience> getAll() {
        List<Experience> experienceList = new ArrayList<>();
        DBCollection dbCollection = MongoFactory.getCollection(dbName, collectionName);
        DBCursor cursor = dbCollection.find();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();

            Company company = new Company();
            company.setName(((DBObject)dbObject.get("company")).get("name").toString());
            try {
                company.setIconLink(new URL(((DBObject)dbObject.get("company")).get("iconLink").toString()));
                company.setInfoLink(new URL(((DBObject)dbObject.get("company")).get("infoLink").toString()));
            } catch (MalformedURLException e) {
                log.error(e);
            }


            Experience experience = new Experience();
            experience.setId(dbObject.get("_id").toString());
            experience.setCompany(company);
            experience.setJobDescription(dbObject.get("jobDescription").toString());
            experience.setTechUsed(dbObject.get("techUsed").toString());
            experience.setTitle(dbObject.get("title").toString());

            experienceList.add(experience);
        }
        log.debug("Total records fetched from the mongo database are= " + experienceList.size());
        return experienceList;
    }

    /**
     * Add a new experience to db
     * @param experience
     * @return
     */
    public String addOne(Experience experience) {
        DBCollection dbCollection = MongoFactory.getCollection(dbName, collectionName);
        try {
            BasicDBObject newComp = new BasicDBObject();
            newComp.put("name", experience.getCompany().getName());
            newComp.put("iconLink", experience.getCompany().getIconLink().toString());
            newComp.put("infoLink", experience.getCompany().getInfoLink().toString());

            BasicDBObject newExp = new BasicDBObject();
            newExp.put("company", newComp);
            newExp.put("title", experience.getTitle());
            newExp.put("jobDescription", experience.getJobDescription());
            newExp.put("techUsed", experience.getTechUsed());

            dbCollection.insert(newExp);
            return newExp.get("_id").toString();
        } catch(Error e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Update the selected experience.
     * @param experience
     * @return
     */
    public String editOne(String id, Experience experience) {
        try {
            DBCollection coll = MongoFactory.getCollection(dbName, collectionName);
            DBObject existing = MongoFactory.getDBObjectById(dbName, collectionName, id);
            BasicDBObject editedExp = new BasicDBObject();

            BasicDBObject editedComp = new BasicDBObject();
            if (experience.getCompany() != null) {
                editedComp.put("name", experience.getCompany().getName());
                editedComp.put("iconLink", experience.getCompany().getIconLink());
                editedComp.put("infoLink", experience.getCompany().getInfoLink());
            } else {
                editedComp.put("name", ((DBObject)existing.get("company")).get("name").toString());
                editedComp.put("iconLink", ((DBObject)existing.get("company")).get("iconLink").toString());
                editedComp.put("infoLink", ((DBObject)existing.get("company")).get("infoLink").toString());
            }
            String jd = experience.getJobDescription() != null ?
                    experience.getJobDescription()
                    : existing.get("jobDescription").toString();
            String title = experience.getTitle() != null ?
                    experience.getTitle() :
                    existing.get("title").toString();
            String techUsed = experience.getTechUsed() != null ?
                    experience.getTechUsed() :
                    existing.get("techUsed").toString();

            editedExp.put("company", editedComp);
            editedExp.put("title", title);
            editedExp.put("jobDescription", jd);
            editedExp.put("techUsed", techUsed);

            coll.update(existing, editedExp);
            return id;
        } catch(Error e) {
            log.error(e);
            return null;
        }
    }

    public void deleteOne(String id) {
        try {
            DBCollection coll = MongoFactory.getCollection(dbName, collectionName);
            DBObject item = MongoFactory.getDBObjectById(dbName, collectionName, id);
            coll.remove(item);
        } catch (Error e) {
             log.error(e);
        }
    }
}
