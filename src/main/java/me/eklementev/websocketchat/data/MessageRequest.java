package me.eklementev.websocketchat.data;

public class MessageRequest {
    private String name;
    private String text;

    public MessageRequest() {
    }

    public MessageRequest(String name, String text) {
        this.name = name;
        this.text = text;
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
        return "MessageRequest{name='" + name + "',text='" + text + "'}";
    }
}
