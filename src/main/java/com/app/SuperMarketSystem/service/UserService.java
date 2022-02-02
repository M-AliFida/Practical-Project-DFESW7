package com.app.SuperMarketSystem.service;

import com.app.SuperMarketSystem.dto.ApiResponse;
import com.app.SuperMarketSystem.model.Order;
import com.app.SuperMarketSystem.model.User;
import com.app.SuperMarketSystem.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

/*    public ApiResponse findAllUsers() {
        ApiResponse apiResponse = new ApiResponse();
        try {
            List<User> userList = userRepository.findAll();
            if (userList.isEmpty()) {
                apiResponse.setMessage("There is no user in the database");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setData(null);
            } else {
                apiResponse.setMessage("Successfully fetched the users from the database");
                apiResponse.setData(userList);
                apiResponse.setStatus(HttpStatus.OK.value());
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }*/

    public List<User> findAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            return userList;
        } catch (Exception e) {
            throw new RuntimeException("There are no users in the database");
        }
    }

    public ApiResponse addNewUser(User user) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            userRepository.save(user);
            apiResponse.setMessage("Successfully added user in the database");
            apiResponse.setData(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ApiResponse deleteUser(Integer userId) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                user.get().setOrders(null);
                userRepository.delete(user.get());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successfully deleted the user from the database");
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no user against this ID");
            }
            apiResponse.setData(null);
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    public ApiResponse updateUser(User user) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> optionalUser = userRepository.findById(user.getId());
            if (optionalUser.isPresent()) {
                userRepository.save(user);
                apiResponse.setMessage("User Successfully updated in the database");
                apiResponse.setData(user);
                apiResponse.setStatus(HttpStatus.OK.value());
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no user against this ID");
                apiResponse.setData(null);
            }
            return apiResponse;

        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }

    /*public ApiResponse getUserById(Integer userId) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Successful");
                apiResponse.setData(user);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no user in the database");
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }*/
    public User getUserById(Integer userId) {
        try {
            User user = userRepository.getById(userId);
            if (null != user) {
                return user;
            } else {
                throw new NotFoundException("User not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ApiResponse getOrdersByUserId(Integer userId) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                List<Order> orders = user.get().getOrders();
                if (orders.isEmpty()) {
                    apiResponse.setMessage("There are no orders made yet by this user");
                } else {
                    apiResponse.setMessage("These are the orders that this user made");
                }
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setData(orders);
            } else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("There is no user against this id in the database");
            }
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return apiResponse;
        }
    }
}