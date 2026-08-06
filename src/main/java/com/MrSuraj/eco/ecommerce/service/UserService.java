package com.MrSuraj.eco.ecommerce.service;

import com.MrSuraj.eco.ecommerce.Exception.UserException;
import com.MrSuraj.eco.ecommerce.entity.User;


public interface UserService {
    public User finduserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
