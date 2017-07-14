package com.example.baidMarcinos.kolkoikrzyzyk.models;

/**
 * Created by MARCIN on 2017-05-28.
 */

public class TicTacToe {
    public String sender;
    public String receiver;
    public String senderUid;
    public String receiverUid;
    public String buttonID;
    public String playerSign;
    public long timestamp;

    public TicTacToe(){  }

    public TicTacToe(String sender, String receiver, String senderUid, String receiverUid, String buttonID, String playerSign, long timestamp){
        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.buttonID = buttonID;
        this.playerSign = playerSign;
        this.timestamp = timestamp;
    }


}
