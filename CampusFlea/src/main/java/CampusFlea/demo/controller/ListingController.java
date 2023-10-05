package CampusFlea.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListingController {

    @GetMapping("/listing")
    public String list(Model model) {

        return "ListView";
    }


}
