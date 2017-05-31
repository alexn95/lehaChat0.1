package app.service.impl;

import app.entities.Message;
import app.entities.Role;
import app.entities.Room;
import app.entities.User;
import app.repositories.MessageRepository;
import app.repositories.RoleRepository;
import app.repositories.RoomRepository;
import app.service.Service;
import app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.*;


@org.springframework.stereotype.Service
@Configurable
public class ServiceImpl implements Service {

    @Autowired
    UsersRepository users;

    @Autowired
    RoleRepository roles;

    @Autowired
    MessageRepository messages;

    @Autowired
    RoomRepository rooms;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public List<User> getAllUser (){
        return users.findAll();
    }

    @Override
    public User addBaseUser(User user){
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roles.findOne(2L));
            user.setRoles(userRoles);

            List<Room> userRooms = new ArrayList<>();
            userRooms.add(rooms.findByRoomName("mainRoom"));
            userRooms.add(rooms.findByRoomName("customRoom"));
            userRooms.add(rooms.findByRoomName("guestRoom"));
            user.setRooms(userRooms);

            return users.save(user);
    }

    @Override
    public User addCustomUser(User user, List<Role> roles, List<Room> rooms){
        user.setRoles(roles);
        user.setRooms(rooms);

        return users.save(user);
    }

    @Override
    public void deleteUser (long id){
        users.delete(id);
    }

    @Override
    public User findUserByName(String name){
        return users.findByUsername(name);
    }

    @Override
    public User findUserByEmail(String email){
        return users.findByEmail(email);
    }

    @Override
    public User findUserById(long id){
        return users.findOne(id);
    }

    @Override
    public UserDetails getCurrentUserDetails(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return userDetails;
        }
        return null;
    }

    @Override
    public User getCurrentUser(){
        if (getCurrentUserDetails() == null)
            return null;
        return findUserByName(getCurrentUserDetails().getUsername());
    }

    @Override
    public List<Room> getCurrentUserRooms(){
        if (getCurrentUser() == null) {
            List<Room> listRoom = new ArrayList<>();
            listRoom.add(rooms.findByRoomName("guestRoom"));
            return listRoom;
        }
        return getCurrentUser().getRooms();
    }

    @Override
    public List<Message> getMessages(Room room){
        return room.getMessages();
    }

    @Override
    public Message saveMessage (String content, String roomName, String username){
        Message message = new Message(content, rooms.findByRoomName(roomName), username, new Date());
        return messages.saveAndFlush(message);
    }

    @Override
    public Message findMessageById (long id){
       return messages.findOne(id);
    }

    @Override
    public Room findRoomByRoomName (String roomName){
        return rooms.findByRoomName(roomName);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public User deleteUserRoom(String roomName, String userName){
        User user = users.findByUsername(userName);
        if(isAdmin(user)) return null;
        List<Room> userRooms = new ArrayList<>();

        user.getRooms().forEach(userRooms::add);

        userRooms.remove(rooms.findByRoomName(roomName));

        user.setRooms(userRooms);

        return users.saveAndFlush(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public User addUserRoom(String roomName, String userName){
        User user = users.findByUsername(userName);
        List<Room> userRooms = new ArrayList<>();

        user.getRooms().forEach(userRooms::add);

        userRooms.add(rooms.findByRoomName(roomName));

        user.setRooms(userRooms);

        return users.saveAndFlush(user);
    }

    @Override
    public User bannedUser(String userName){
        User user = users.findByUsername(userName);
        List<Room> userRooms = new ArrayList<>();

        user.setRooms(userRooms);

        return users.save(user);
    }

    @Override
    public boolean isAdmin(User user){
        for(Role role:user.getRoles()){
            if (role.getRole().equals("ROLE_ADMIN")) return true;
        }
        return false;
    }

    @Override
    public User changePassword (String password){
        User user = getCurrentUser();
        user.setPassword(encoder.encode(password));

        return users.save(user);
    }

}
