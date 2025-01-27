package sg.edu.nus.iss.paf_day26_workshopA.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day26_workshopA.model.Game;

@Repository
public class GamesRepository {
    
    @Autowired
    private MongoTemplate template;


    /*
     *  db.games.find()
     *          .skip(offset)
     *          .limit(limit)
     */
    private List<Game> getGames(int offset, int limit) {
        
    }

}
