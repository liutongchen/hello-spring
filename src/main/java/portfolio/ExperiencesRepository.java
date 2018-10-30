package portfolio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "experiences", path = "experiences")
public interface ExperiencesRepository extends MongoRepository<Experience, String> {
    List<Experience> getExperienceByCompany_Name(@Param("companyName") String name); // TODO: Think if this is necessary
}
