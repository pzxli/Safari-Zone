package com.Revature.SafariZoneBackEnd.services;

import com.Revature.SafariZoneBackEnd.models.User;
import com.Revature.SafariZoneBackEnd.models.UserRole;
import com.Revature.SafariZoneBackEnd.repositories.RoleRepo;
import com.Revature.SafariZoneBackEnd.repositories.UserRepo;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;

    public BasicPasswordEncryptor passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BasicPasswordEncryptor passwordEncoder, RoleRepo roleRepo) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BasicPasswordEncryptor();
        this.roleRepo = roleRepo;
    }


    /**
     * Create a new user, base on the user role to get as Admin or User
     * Checking if username and email are available
     * Encrypt the password when creating a user
     * Setting a role for a new user
     * @param user
     * @return a user from Database
     */
    public User createOne(User user){
        try{
            User userByUsername =userRepo.findByUsername(user.getUsername());
            User userByEmail = userRepo.findByEmail(user.getEmail());

            if (userByUsername == null && userByEmail == null){
                user.setPassword(passwordEncoder.encryptPassword(user.getPassword()));
                User userFromDB = userRepo.save(user);

                UserRole role = roleRepo.findById(userFromDB.getRole().getRoleId()).orElse(null);
                userFromDB.setRole(role);

                return userFromDB;
            } else {
                return null;
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Validate User by username and password, if it validate or not and return user. Using to login user
     * @param username
     * @param password
     * @return User form Database
     */
    public User validateCredentials(String username, String password){

        User userFromDB = userRepo.findByUsername(username);

        if (userFromDB == null){
            return null;
        }

        try {
            if (!passwordEncoder.checkPassword(password, userFromDB.getPassword())) {
                return null;
            }
        } catch (EncryptionOperationNotPossibleException e){
            if (!Objects.equals(password, userFromDB.getPassword())){
                return null;
            }
        }

        return userFromDB;
    }

    /**
     * Update User by searching user by ID. Edit user information
     * @param user
     * @return User
     */
    public User updateUser(User user){
        user.setPassword(passwordEncoder.encryptPassword(user.getPassword()));
        User userFromDB = userRepo.findById(user.getUserId()).orElse(null);
        if (userFromDB == null)
        {
            return null;
        }
        return userRepo.save(user);
    }

    /**
     * Get one user by user ID or return null
     * @param userId
     * @return User
     */
    public User getOneById(Integer userId){
        return userRepo.findById(userId).orElse(null);
    }

    /**
     * Get one user by username or return null
     * @param username
     * @return User
     */
    public User getOneByUsername (String username){
        return userRepo.findByUsername(username);
    }

    /**
     * Get all users
     * @return List of Users
     */
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

}
