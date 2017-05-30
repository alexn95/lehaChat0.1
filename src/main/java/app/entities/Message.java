package app.entities;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    private String username;

    private Date date;

    @ManyToOne
    @JoinColumn(name="room_id", referencedColumnName="id")
    private Room room;


    public Message() {}

    public Message(String content, Room room ,String username, Date date) {
        this.content = content;
        this.room = room;
        this.username = username;
        this.date = date;
    }

    public long getId() {
        return id;
    }


    public String getContent() {
        return content;
    }

//    public Room getRoom() {
//        return room;
//    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}