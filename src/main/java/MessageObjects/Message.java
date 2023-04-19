package MessageObjects;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

public class Message implements java.io.Serializable {
    private  String sendersName = "";
    private int number;
    private long messageComposeTime;


    public String getSendersName() {
        return sendersName;
    }

    public void setSendersName(String sendersName) {
        this.sendersName = sendersName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getMessageComposeTime() {
        return messageComposeTime;
    }

    public void setMessageComposeTime(long messageComposeTime) {
        this.messageComposeTime = messageComposeTime;
    }

    public Message(String sendersName, int number) {
        this.sendersName = sendersName;
        this.number = number;
        this.messageComposeTime = System.currentTimeMillis();
    }

    @Override
    public String toString(){
        return "Message{" +
                "number=" + number +
                ", senderName='" + sendersName + '\'' +
                ", timestamp=" + new Date(messageComposeTime) +
                '}';
    }


}
