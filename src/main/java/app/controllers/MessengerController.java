package app.controllers;

import app.entities.Message;
import app.entities.Role;
import app.entities.Room;
import app.entities.User;
import app.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mrale on 20.05.2017.
 */
@RestController
@RequestMapping("")
public class MessengerController {

    @Autowired
    ServiceImpl service;

    @RequestMapping( method = RequestMethod.GET)
    public ModelAndView getMessenger(ModelAndView modelAndView) {
        List<Room> rooms = service.getCurrentUserRooms();
        modelAndView.addObject("rooms", rooms);
        modelAndView.addObject("error", "");

        modelAndView.setViewName("/messenger");
        return  modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView postMessenger(@ModelAttribute String error, String roomDelete, String userDelete,
                                      String addUser, String addRoom, String currentRoom,  ModelAndView modelAndView) {
        error = "";
        if(roomDelete != null && userDelete != null)
            if(service.deleteUserRoom(roomDelete, userDelete) == null){
                error = "You can not remove the admin";
            } else error = "User successfully removed from the room";

        if(addUser != null && addRoom!= null){
            if(service.findUserByName(addUser) != null){
                boolean temp = true;
                for(String user :service.findRoomByRoomName(addRoom).getUsers()){
                    if(user.equals(addUser)) {
                        error = "Selected user already in the room";
                        temp = false;
                    }
                }
                if (temp) {
                    service.addUserRoom(addRoom, addUser);
                    modelAndView.addObject("reload", true);
//                    error = "User successfully added a room";
                }

            } else {
                error = "User with such login does not exist";
            }
        }
        List<Room> rooms = service.getCurrentUserRooms();
        modelAndView.addObject("rooms", rooms);
        modelAndView.addObject("error", error);
        if(currentRoom != null){
            modelAndView.addObject("room",currentRoom);
        }

        modelAndView.setViewName("/messenger");
        return  modelAndView;
    }

}
