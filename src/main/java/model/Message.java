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

	public Agent getSender() {
		return sender;
	}

	public void setSender(Agent sender) {
		this.sender = sender;
	}

	public Agent getReceiver() {
		return receiver;
	}

	public void setReceiver(Agent receiver) {
		this.receiver = receiver;
	}
}
