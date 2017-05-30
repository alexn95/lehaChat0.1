package app.messenger;

import java.util.Date;

public class message {


    private String content;

    private String username;

    private Date date;

    public message() {
    }

    public message(String content, String username, Date date) {
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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