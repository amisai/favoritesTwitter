package org.okiju.favoritestwitter.util;

import java.util.Properties;

public class EmailBean {
    public EmailBean(Properties props) {
        username = props.getProperty("username");
        password = props.getProperty("password");
        recipient = props.getProperty("recipient");
        from = props.getProperty("from");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private String username;
    private String password;
    private String recipient;
    private String from;
}