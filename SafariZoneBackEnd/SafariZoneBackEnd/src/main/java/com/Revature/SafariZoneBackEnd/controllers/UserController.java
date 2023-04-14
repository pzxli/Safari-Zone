package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Create User
     * @return JsonResponse user has been created or Username or Email already taken or An Error Has Occurred
     */
    @PostMapping
    public JsonResponse createOne(@RequestBody User user) {
        JsonResponse jsonResponse;
        try {
            User userFromDB = userService.createOne(user);

            if (userFromDB != null){
                jsonResponse = new JsonResponse(true, "User has been created", userFromDB);
            } else {
                jsonResponse = new JsonResponse(false, "Username or Email already taken", null);
            }

        } catch (Exception e){
            jsonResponse = new JsonResponse(false, "An Error Has Occurred", null);
            }
        return jsonResponse;
    }

    /**
     * Update information for User
     * @return JsonResponse User could not be updated or User has been successfully updated
     */
    @PutMapping
    public JsonResponse updateUser(@RequestBody User user) {
        JsonResponse jsonResponse;
        User userFromDB = userService.updateUser(user);
        if (userFromDB == null) {
            jsonResponse = new JsonResponse(false, "User could not be updated", null);
        } else {
            jsonResponse = new JsonResponse(true, "User has been successfully updated", userFromDB);
        }
        return jsonResponse;
    }

    /**
     * Looking up one user by ID
     * @param userId
     * @return JsonResponse Invalid userId or User has been successfully found
     */
    @GetMapping("id/{userId}")
    public JsonResponse getOneById(@PathVariable Integer userId){
        JsonResponse jsonResponse;
        User userFromDB = userService.getOneById(userId);
        if (userFromDB == null) {
            jsonResponse = new JsonResponse(false, "Invalid userId", null);
        } else {
            jsonResponse = new JsonResponse(true, "User has been successfully found", userFromDB);
        }
        return jsonResponse;
    }

    /**
     * Getting one user by username
     * @param username
     * @return JsonResponse Invalid username or User has been successfully found
     */
    @GetMapping("username/{username}")
    public JsonResponse getOneByUsername(@PathVariable String username){
        JsonResponse jsonResponse;
        User userFromDB = userService.getOneByUsername(username);
        if (userFromDB == null) {
            jsonResponse = new JsonResponse(false, "Invalid username", null);
        } else {
            jsonResponse = new JsonResponse(true, "User has been successfully found", userFromDB);
        }
        return jsonResponse;
    }
    /**
     * Getting all users
     * @return JsonResponse No user in the Database or Found all in the Database
     */
    @GetMapping
    public JsonResponse getAllUsers(){
        JsonResponse jsonResponse;
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            jsonResponse = new JsonResponse(false, "No user in the Database", null);
        } else {
            jsonResponse = new JsonResponse(true, "Found all in the Database", users);
        }
        return jsonResponse;
    }
}