package protopnet.mlprototypesfeedbackcollector.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;

/**
 * Repository interface for FeedbackData entities.
 */
public interface FeedbackRepository extends MongoRepository<FeedbackData, String> {

}
