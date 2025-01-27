package sg.edu.nus.iss.paf_day26_workshopA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  
    @GetMapping()
    public String redirect(){
        return "redirect:/games";
    }
}
