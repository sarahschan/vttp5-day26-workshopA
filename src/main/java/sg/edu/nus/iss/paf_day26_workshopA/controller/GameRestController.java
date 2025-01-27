package sg.edu.nus.iss.paf_day26_workshopA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_day26_workshopA.service.GameService;

@RestController
@RequestMapping("/games")
public class GameRestController {
    
    @Autowired
    private GameService gameService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGames(
        @RequestParam(required = false, defaultValue = "25") int limit, 
        @RequestParam(required = false, defaultValue = "0") int offset) {
        
    
        JsonObject result = gameService.getGames(offset, limit);
        
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }
}
