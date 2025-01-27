package sg.edu.nus.iss.paf_day26_workshopA.service;

import static sg.edu.nus.iss.paf_day26_workshopA.repository.Constants.*;

import java.lang.classfile.ClassFile.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_day26_workshopA.repository.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;


    public JsonObject buildResponse(JsonArray gamesArray, int limit, int offset) {
        JsonObject response = Json.createObjectBuilder()
            .add("games", gamesArray)
            .add("offset", offset)
            .add("limit", limit)
            .add("total", gamesArray.size())
            .add("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .build();
        return response;
    }

    public JsonArray buildGamesArray(List<Document> queryResults) {
        
        JsonArrayBuilder gamesJsonArrayBuilder = Json.createArrayBuilder();

        for (Document d : queryResults) {
            JsonObject game = Json.createObjectBuilder()
                .add("object_id", d.getObjectId(FIELD_ID).toString())
                .add("game_id", d.getInteger(FIELD_GID))
                .add("name", d.getString(FIELD_NAME))
                .build();
            gamesJsonArrayBuilder.add(game);
        }

        return gamesJsonArrayBuilder.build();
    }


    public JsonObject getGames(int offset, int limit) {
        
        List<Document> results = gameRepository.getGames(offset, limit);

        JsonArray gamesArray = buildGamesArray(results);

        return buildResponse(gamesArray, limit, offset);

    }


    public JsonObject getGamesByRank(int offset, int limit) {

        List<Document> results = gameRepository.getGamesByRank(offset, limit);

        JsonArray gamesArray = buildGamesArray(results);

        return buildResponse(gamesArray, limit, offset);
    }


    public Optional<JsonObject> getGameById(String gameId) {

        Document d = gameRepository.getGameById(gameId);

        if (d == null) {
            return Optional.empty();
        }

        JsonObject gameObject = Json.createObjectBuilder()
            .add("object_id", d.getObjectId(FIELD_ID).toString())
            .add("game_id", d.getInteger(FIELD_GID))
            .add("name", d.getString(FIELD_NAME))
            .add("year", d.getInteger(FIELD_YEAR))
            .add("ranking", d.getInteger(FIELD_RANKING))
            .add("average", d.getInteger(FIELD_AVERAGE) == null ? 0 : d.getInteger(FIELD_AVERAGE))
            .add("users_rated", d.getInteger(FIELD_USERS_RATED))
            .add("url", d.getString(FIELD_URL))
            .add("thumbnail", d.getString(FIELD_THUMBNAIL))
            .build();
    
        return Optional.of(gameObject);
    }
}