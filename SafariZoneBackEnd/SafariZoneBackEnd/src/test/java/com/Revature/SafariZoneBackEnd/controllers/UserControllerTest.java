package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    private UserController userController;

    private UserService userService = Mockito.mock(UserService.class);

    public UserControllerTest() {
        this.userController = new UserController(userService);
    }

    User user = new User(3, "test", "code", "bob", "pass", "bob@email.com", "bob's house", null);
    JsonResponse jsonResponse;

    @Test
    void createOne() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(true, "User has been created", user);
        Mockito.when(userService.createOne(user)).thenReturn(user);

        //act
        JsonResponse actualOutput = userController.createOne(user);

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void createOneFail() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(false, "Username or Email already taken", null);
        Mockito.when(userService.createOne(user)).thenReturn(null);

        //act
        JsonResponse actualOutput = userController.createOne(user);

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void updateUser() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(true, "User has been successfully updated", user);
        Mockito.when(userService.updateUser(user)).thenReturn(user);

        //act
        JsonResponse actualOutput = userController.updateUser(user);

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void updateUserFail() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(false, "User could not be updated", null);
        Mockito.when(userService.updateUser(user)).thenReturn(null);

        //act
        JsonResponse actualOutput = userController.updateUser(user);

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getOneById() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(true, "User has been successfully found", user);
        Mockito.when(userService.getOneById(user.getUserId())).thenReturn(user);

        //act
        JsonResponse actualOutput = userController.getOneById(user.getUserId());

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getOneByIdFail() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(false, "Invalid userId", null);
        Mockito.when(userService.getOneById(user.getUserId())).thenReturn(null);

        //act
        JsonResponse actualOutput = userController.getOneById(user.getUserId());

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getOneByUsername() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(true, "User has been successfully found", user);
        Mockito.when(userService.getOneByUsername(user.getUsername())).thenReturn(user);

        //act
        JsonResponse actualOutput = userController.getOneByUsername(user.getUsername());

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getOneByUsernameFail() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(false, "Invalid username", null);
        Mockito.when(userService.getOneByUsername(user.getUsername())).thenReturn(null);

        //act
        JsonResponse actualOutput = userController.getOneByUsername(user.getUsername());

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllUsers(){
        //arrange
        List<User> users = new ArrayList<>();
        users.add(user);
        JsonResponse expectedOutput = new JsonResponse(true, "Found all in the Database", users);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        //act
        JsonResponse actualOutput = userController.getAllUsers();

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllUsersFail(){
        //arrange
        List<User> users = new ArrayList<>();
        JsonResponse expectedOutput = new JsonResponse(false, "No user in the Database", null);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        //act
        JsonResponse actualOutput = userController.getAllUsers();

        //assert
        assertEquals(expectedOutput, actualOutput);
    }

}