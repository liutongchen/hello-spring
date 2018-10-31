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
        // TODO: ASSUMING ID IS AUTO CREATED HERE, SHOULD RETURN THE NEW OBJECT OR JUST THE ID?
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
}
