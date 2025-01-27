package sg.edu.nus.iss.paf_day26_workshopA.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GamesRestController {
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGames(
        @RequestParam(defaultValue = "25") int limit, 
        @RequestParam(defaultValue = "0") int offset) {
        
        return null;
    }
}
