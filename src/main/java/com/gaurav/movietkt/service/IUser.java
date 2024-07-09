package com.gaurav.movietkt.service;

import java.util.List;

import com.gaurav.movietkt.model.User;

public interface IUser  {
     public String registerUser(User user);
     public User fetchUser(Integer uid);
     public List<User> fetchAllUser();
}
