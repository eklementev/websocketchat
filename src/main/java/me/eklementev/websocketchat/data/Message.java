package me.eklementev.websocketchat.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    private String datetime;
    private String name;
    private String text;

    public Message() {
    }

    public static Message fromRequest(MessageRequest request) {
        return new Message(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), request.getName(), request.getText());
    }

    public Message(String datetime, String name, String text) {
        this.datetime = datetime;
        this.name = name;
        this.text = text;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "datetime='" + datetime + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
