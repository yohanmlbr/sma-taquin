package model;

import lombok.Data;

@Data
public class Message {
    private Agent sender;
    private Agent receiver;

    public Message(Agent s, Agent r){
        this.sender=s;
        this.receiver=r;
    }
}
