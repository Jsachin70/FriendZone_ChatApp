package com.pro.sachin.friendzone;

/**
 * Created by SACHIN on 4/13/2017.
 */

public class Chat_values  {
    private String sender;
    private String receiver;
    private String senderUid;
    private String receiverUid;
    private String message;
    private long timestamp;

    public Chat_values() {}

    public Chat_values(String sender, String receiver, String senderUid, String receiverUid, String message, long timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.message = message;
        this.timestamp = timestamp;
    }
}
