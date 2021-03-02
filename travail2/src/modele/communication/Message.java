package modele.communication;

public class Message {
    //ATTRIBUTS
    String numDeDestination;
    String message;

    public Message(String numDeDestination, String message) {
        this.numDeDestination = numDeDestination;
        this.message = message;
    }


    public String getNumDeDestination() {

        return numDeDestination;
    }

    public String getMessage() {
        return message;
    }
}
