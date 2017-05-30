package app.controllers;

import app.entities.Role;
import app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import app.service.impl.ServiceImpl;

import java.util.*;

@RestController
@RequestMapping("users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UsersController {

    @Autowired
    ServiceImpl service;


    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers()
    {
        List<User> result = new ArrayList<>();
        service.getAllUser().forEach(result::add);
        return result;
    }

    @RequestMapping(value = "/inf", method = RequestMethod.GET)
    public UserDetails getUser (){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        return userDetails;
    }

    @RequestMapping(value = "/findUserById/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Long id)
    {
        return service.findUserById(id);
    }

    @RequestMapping(value = "/findByUN/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable("username") String username)
    {
        return service.findUserByName(username);
    }

    @RequestMapping(value = "/findByEM/{email}", method = RequestMethod.GET)
    public User getUserByEmail(@PathVariable("email") String email)
    {
        return service.findUserByEmail(email);
    }

    @RequestMapping(value = "/deleteUserRoom/{user}/{room}", method = RequestMethod.GET)
    public User deleteUserRoom(@PathVariable("user") String user, @PathVariable("room") String room)
    {
        return service.deleteUserRoom(room, user);
    }

}
