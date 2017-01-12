package me.lynnchurch.mvpdemo.mvp.model.bean;

public class User {
    private final int id;
    private final String login;
    private final String avatar_url;

    public User(int id, String login, String avatar_url) {
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        if (avatar_url.isEmpty()) {
            return avatar_url;
        }
        return avatar_url.split("\\?")[0];
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
