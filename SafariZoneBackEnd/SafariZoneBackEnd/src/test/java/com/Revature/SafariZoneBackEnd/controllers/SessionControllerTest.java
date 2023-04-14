package com.Revature.SafariZoneBackEnd.controllers;

import com.Revature.SafariZoneBackEnd.models.JsonResponse;
import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.models.UserRole;
import com.Revature.SafariZoneBackEnd.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    private SessionController sessionController;
    private JsonResponse jsonResponse;
    private HttpSession httpSession = Mockito.mock(HttpSession.class);
    private UserService userService = Mockito.mock(UserService.class);

    public SessionControllerTest() {
        this.sessionController = new SessionController(userService);
    }

    @Test
    void loginValidCredentials() {
        //arrange
        String username = "tester";
        String password = "correctPassword";
        UserRole role = new UserRole(1,"Users");
        User userFromDb = new User(1,"Test","User", username, password,"email@email.com","coolAddress",role);
        User userLoggingIn = new User(null,null,null,username,password,null,null,null);
        JsonResponse expectedOutput = new JsonResponse(true, "Login Successful", userFromDb);
        Mockito.when(userService.validateCredentials(username,password)).thenReturn(userFromDb);

        //act
        JsonResponse actualOutput = sessionController.loginSession(httpSession,userLoggingIn);

        //assert
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void loginInvalidCredentials() {
        //arrange
        String username = "tester";
        String password = "incorrectPassword";
        User userLoggingIn = new User(null,null,null,username,password,null,null,null);
        JsonResponse expectedOutput = new JsonResponse(false, "Invalid Username or Password", null);
        Mockito.when(userService.validateCredentials(username,password)).thenReturn(null);


        //act
        JsonResponse actualOutput = sessionController.loginSession(httpSession,userLoggingIn);

        //assert
        assertEquals(expectedOutput,actualOutput);

    }

    @Test
    void logoutSession() {
        //arrange
        JsonResponse expectedOutput = new JsonResponse(true, "Successfully logged out and session invalidated.", null);

        //act
        JsonResponse actualOutput = sessionController.logoutSession(httpSession);

        //assert
        assertEquals(expectedOutput,actualOutput);

    }

    @Test
    void checkSessionNoSessionFound() {
        //arrange
        Mockito.when(httpSession.getAttribute("user")).thenReturn(null);
        JsonResponse expectedOutput = new JsonResponse(false, "No session found", null);


        //act
        JsonResponse actualOutput = sessionController.checkSession(httpSession);

        //assert
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void checkSessionSessionFound() {
        //arrange
        String username = "tester";
        String password = "correctPassword";
        UserRole role = new UserRole(1,"Users");
        User userFromDb = new User(1,"Test","User", username, password,"email@email.com","coolAddress",role);

        Mockito.when(httpSession.getAttribute("user")).thenReturn(userFromDb);
        JsonResponse expectedOutput = new JsonResponse(true, "Session found", userFromDb);

        //act
        JsonResponse actualOutput = sessionController.checkSession(httpSession);


        //assert
        assertEquals(expectedOutput,actualOutput);

    }
}