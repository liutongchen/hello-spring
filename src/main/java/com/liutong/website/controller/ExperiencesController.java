package com.liutong.website.controller;

import com.liutong.website.entities.experiences.Experience;
import com.liutong.website.services.ExperiencesService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @GetMapping
    public ResponseEntity<List<Experience>> listExperiences() {
        return ResponseEntity.ok(experiencesService.getAll());
    }


    @PostMapping
    public ResponseEntity<?> createExperience(@RequestBody Experience experience) {
        // TODO: HOW TO RETURN THE NEW URL? Aadd error handling.
        String id = experiencesService.addOne(experience);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExperience(@PathVariable String id, @RequestBody Experience experience) {
        String editedId = experiencesService.editOne(id, experience);
        return ResponseEntity.ok(editedId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable String id) {
        experiencesService.deleteOne(id);
        return ResponseEntity.ok("Delete Success");
    }

}
