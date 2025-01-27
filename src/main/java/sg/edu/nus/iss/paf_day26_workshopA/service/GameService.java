package sg.edu.nus.iss.paf_day26_workshopA.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                .add("_id", d.getObjectId("_id").toString())
                .add("game_id", d.getInteger("gid"))
                .add("name", d.getString("name"))
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
}