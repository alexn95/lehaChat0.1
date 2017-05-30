package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="rooms")
public class Room  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "room_name")
    private String roomName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    protected Room(){}
    public Room(String room)
    {
        this.roomName = room;
    }

    @JsonIgnore
    @OneToMany(mappedBy="room")
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "rooms")
    private List<User> users;

    public List<String> getUsers() {
        List<String> usersName = new ArrayList<>();
        for(User user: users){
            usersName.add(user.getUsername());
        }
        return usersName;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}