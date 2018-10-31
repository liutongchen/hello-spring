package com.liutong.website.controller;

import com.liutong.website.entities.experiences.Experience;
import com.liutong.website.services.ExperiencesService;
import com.mongodb.BasicDBObject;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/experiences")
public class ExperiencesController {
    private static Logger log = Logger.getLogger(ExperiencesController.class);

    @Resource(name = "experiencesService")
    private ExperiencesService experiencesService;

    /**
     * Display all experiences
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Experience> listExperiences() {
        return experiencesService.getAll();
    }


    @RequestMapping(method = RequestMethod.POST)
    public String createExperience(@RequestBody Experience experience) {
        return experiencesService.addOne(experience);
    }
}
