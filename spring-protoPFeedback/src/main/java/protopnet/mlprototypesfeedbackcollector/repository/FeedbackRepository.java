package protopnet.mlprototypesfeedbackcollector.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;

public interface FeedbackRepository extends MongoRepository<FeedbackData, String> {

}
