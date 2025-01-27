package sg.edu.nus.iss.paf_day26_workshopA.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static sg.edu.nus.iss.paf_day26_workshopA.repository.Constants.*;

@Repository
public class GameRepository {
    
    @Autowired
    private MongoTemplate template;


    /*
     *  db.games.find()
     *          .skip(offset)
     *          .limit(limit)
     */
    public List<Document> getGames(int offset, int limit) {
        
        Query query = new Query()
            .skip(offset)
            .limit(limit);
        query.fields()
            .include(FIELD_ID, FIELD_GID, FIELD_NAME);

        return template.find(query, Document.class, COLLECTION_GAMES);

    }


    /*
     * db.games.find()
     * .sort({'ranking' : 1})
     * .skip(5)
     * .limit(5)
     */
    public List<Document> getGamesByRank(int offset, int limit) {

        Query query = new Query()
            .with(Sort.by(Sort.Direction.ASC, FIELD_RANKING))
            .skip(offset)
            .limit(limit);
        query.fields()
        .include(FIELD_ID, FIELD_GID, FIELD_NAME);

        return template.find(query, Document.class, COLLECTION_GAMES);
    }

}
