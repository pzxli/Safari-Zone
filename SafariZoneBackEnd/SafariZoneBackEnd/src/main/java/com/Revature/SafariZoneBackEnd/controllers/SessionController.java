package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("session")
public class SessionController {
    private UserService userService;


    @Autowired
    public SessionController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Login session by username and password, print out invalid if they are incorrect
     * @param httpSession
     * @param userLoggingIn
     * @return JsonResponse Invalid Username or Password or Login Successful
     */
    @PostMapping
    public JsonResponse loginSession (HttpSession httpSession, @RequestBody User userLoggingIn){
        User retrievedUser = userService.validateCredentials(userLoggingIn.getUsername(),userLoggingIn.getPassword());

        if (retrievedUser == null){
            return new JsonResponse(false, "Invalid Username or Password", null);

        } else {

            httpSession.setAttribute("user", retrievedUser);

            return new JsonResponse(true, "Login Successful", retrievedUser);
        }
    }

    /**
     * Logout Session from user
     * @param httpSession
     * @return JsonResponse Successfully logged out and session invalidated
     */
    @DeleteMapping
    public JsonResponse logoutSession (HttpSession httpSession){
        httpSession.invalidate();
        return new JsonResponse(true, "Successfully logged out and session invalidated.", null);
    }

    /**
     * Check if user already logged in success
     * @param httpSession
     * @return JsonResponse No session found or Session found
     */
    @GetMapping
    public JsonResponse checkSession (HttpSession httpSession){
        User userLoggedIn = (User) httpSession.getAttribute("user");

        if(userLoggedIn == null){
            return (new JsonResponse(false, "No session found", null));
        } else {
            return new JsonResponse(true, "Session found", userLoggedIn);
        }

    }
}
