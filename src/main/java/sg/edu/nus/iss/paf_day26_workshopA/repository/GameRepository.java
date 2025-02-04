package sg.edu.nus.iss.paf_day26_workshopA.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
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


    // db.games.aggregate([
    //     { $match: { "_id": ObjectId("678f60643aef20fc8f8c70ea") }},
    //     { $lookup: {
    //         from: 'comments',
    //         foreignField: 'gid',
    //         localField: 'gid',
    //         as: 'reviews'
    //     }},
    //     { $unwind: "$reviews"},
    //     { $group: {
    //         _id: "$_id",
    //         game_id: {$first: "$gid"},
    //         name: {$first: "$name"},
    //         year: {$first: "$year"},
    //         ranking: {$first: "$ranking"},
    //         average: {$avg: "$reviews.rating"},
    //         users_rated: {$first: "$users_rated"},
    //         url: {$first: "$url"},
    //         thumbnail: {$first: "$image"}
    //     }}
    // ])
    public Document getGameById(String gameId) {

        Criteria criteria = Criteria.where("_id").is(gameId);
        MatchOperation match_id = Aggregation.match(criteria);

        LookupOperation lookupComments = Aggregation.lookup("comments", "gid", "gid", "reviews");

        UnwindOperation unwindReviews = Aggregation.unwind("reviews");

        GroupOperation groupOperation = Aggregation.group("_id")
            .first("gid").as("game_id")
            .first("name").as("name")
            .first("year").as("year")
            .first("ranking").as("ranking")
            .avg("reviews.rating").as("average")
            .first("users_rated").as("users_rated")
            .first("url").as("url")
            .first("image").as("thumbnail");

        Aggregation pipeline = Aggregation.newAggregation(match_id, lookupComments, unwindReviews, groupOperation);

        return template.aggregate(pipeline, "games", Document.class).getUniqueMappedResult();

    }

}
