package app.service;

import app.entities.Message;
import app.entities.Role;
import app.entities.Room;
import app.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface Service {
    User addBaseUser(User user);
    User addCustomUser(User user, List<Role> roles, List<Room> rooms);
    void deleteUser (long id);
    User findUserByName(String name);
    User findUserById(long id);
    List<User> getAllUser ();
    User findUserByEmail(String email);
    UserDetails getCurrentUserDetails();
    User getCurrentUser();
    User bannedUser(String userName);
    public User addUserRoom(String roomName, String userName);
    boolean isAdmin(User user);
    User changePassword (String userName);

    Message saveMessage (String content, String RoomName, String username);
    Message findMessageById (long id);

    List<Room> getCurrentUserRooms();
    List<Message> getMessages(Room room);
    Room findRoomByRoomName(String roomName);
    User deleteUserRoom(String roomName, String userName);
}
