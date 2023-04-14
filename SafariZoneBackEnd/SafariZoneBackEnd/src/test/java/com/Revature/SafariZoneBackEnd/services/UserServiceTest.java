package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.models.UserRole;
import com.Revature.SafariZoneBackEnd.repositories.RoleRepo;
import com.Revature.SafariZoneBackEnd.repositories.UserRepo;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    private UserRepo userRepo = Mockito.mock(UserRepo.class);
    private RoleRepo roleRepo = Mockito.mock(RoleRepo.class);
    private UserRole userRole = Mockito.mock(UserRole.class);

    // NOTE: Mockito Cannot mock the BasicPasswordEncryptor class

    //public BasicPasswordEncryptor passwordEncoder;

    public UserServiceTest() { this.userService = new UserService(userRepo, roleRepo);}

    //will not work if password passwordEncoder is active in createOne method
    @Test
    void createOne() {
        UserRole role = new UserRole(1, "user");
        User newUser = new User(1, "User", "Name","username","pass123","email.com", "address", role);
        Mockito.when(userRepo.findByUsername(newUser.getUsername())).thenReturn(null);
        Mockito.when(userRepo.findByEmail(newUser.getEmail())).thenReturn(null);
        Mockito.when(roleRepo.findById(newUser.getRole().getRoleId())).thenReturn(Optional.of(role));
        Mockito.when(userRepo.save(newUser)).thenReturn(newUser);
        User actualOutput = userService.createOne(newUser);

        assertEquals(newUser, actualOutput);
    }

    @Test
    void validateCredentialsIncorrectUsername(){
        String expectedPassword = "pass123";
        String expectedUsername = "username";
        String inputUsername = "wrong name";
        User expectedOutput = new User(1, "User", "Name",expectedUsername, expectedPassword,"email.com", "address",null);
        Mockito.when(userRepo.findByUsername(expectedUsername)).thenReturn(expectedOutput);

        User actualOutput = userService.validateCredentials(inputUsername, expectedPassword);

        assertNotEquals(expectedOutput, actualOutput);
    }

    @Test
    void validateCredentialsIncorrectPassword(){
        String expectedPassword = "pass123";
        String expectedUsername = "username";
        String inputPassword = "wrong password";
        User expectedOutput = new User(1, "User", "Name",expectedUsername, expectedPassword,"email.com", "address",null);
        Mockito.when(userRepo.findByUsername(expectedUsername)).thenReturn(expectedOutput);

        User actualOutput = userService.validateCredentials(inputPassword, expectedUsername);

        assertNotEquals(expectedOutput, actualOutput);
    }

    @Test
    void validateCredentials() {
        String expectedPassword = "pass123";
        String expectedUsername = "username";
        User expectedOutput = new User(1, "User", "Name",expectedUsername, expectedPassword,"email.com", "address",null);
        Mockito.when(userRepo.findByUsername(expectedUsername)).thenReturn(expectedOutput);


        User actualOutput = userService.validateCredentials(expectedUsername, expectedPassword);

        assertEquals(expectedOutput, actualOutput);
    }

    //TODO null pointer exception
    //will not work if passwordEncoder is active in updateUser service
    @Test
    void updateUser() {
        UserRole role = new UserRole(1, "user");
        User updateUser = new User(1, "User", "Name","username", "pass123","email.com", "address",role);
        Mockito.when(userRepo.save(updateUser)).thenReturn(updateUser);
        Mockito.when(userRepo.findById(updateUser.getUserId())).thenReturn(Optional.of(updateUser));

        userService.updateUser(updateUser);

        Mockito.verify(userRepo, Mockito.times(1)).save(updateUser);
    }

    @Test
    void getOneById() {
        UserRole role = new UserRole(1, "user");
        Integer expectedId = 1;
        User expectedOutput = new User(expectedId, "User", "Name","username", "pass123","email.com", "address",role);
        Mockito.when(userRepo.findById(expectedId)).thenReturn(Optional.of(expectedOutput));

        User actualOutput = userService.getOneById(expectedId);

        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void getOneByUsername() {
        UserRole role = new UserRole(1, "user");
        String expectedUsername = "username";
        User expectedOutput = new User(1, "User", "Name",expectedUsername, "pass123","email.com", "address",role);
        Mockito.when(userRepo.findByUsername(expectedUsername)).thenReturn(expectedOutput);

        User actualOutput = userService.getOneByUsername(expectedUsername);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getAllUsers() {
        List<User> expectedOutput = new ArrayList<>();
        expectedOutput.add(new User());
        Mockito.when(userRepo.findAll()).thenReturn(expectedOutput);

        List<User> actualOutput = userRepo.findAll();

        assertEquals(expectedOutput, actualOutput);
    }
}