package ua.byby.myhome.models;

public class User {

    private int userId;
    private String nick;

    public User() {}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getUserId() {
        return userId;
    }

    public String getNick() {
        return nick;
    }
}
