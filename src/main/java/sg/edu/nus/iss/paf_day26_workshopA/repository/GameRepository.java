package sg.edu.nus.iss.paf_day26_workshopA.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
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
            .include("_id", "gid", "name");

        List<Document> results = template.find(query, Document.class, C_GAMES);

        return results;
    }

}
