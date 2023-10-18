package CampusFlea.demo.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import CampusFlea.demo.websocket.pojo.*;
@RestController
@RequestMapping("user")
public class loginController {
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {
        Result result = new Result();
        if(user != null && "12345".equals(user.getPassword())) {
            result.setFlag(true);

            session.setAttribute("user",user.getUsername());
        } else {
            result.setFlag(false);
            result.setMessage("Login failed");
        }
        return result;
    }

    @GetMapping("/getUsername")
    public String getUsername(HttpSession session) {

        String username = (String) session.getAttribute("user");
        return username;
    }
}
