package mg.company.varotravam.models;

public class Message {

    private String acheteurMessage;
    private String vendeurMessage;

    // Constructeur, getters, setters, etc.

    @Override
    public String toString() {
        return "Message{" +
                "acheteurMessage='" + acheteurMessage + '\'' +
                ", vendeurMessage='" + vendeurMessage + '\'' +
                '}';
    }

    public String getAcheteurMessage() {
        return acheteurMessage;
    }

    public void setAcheteurMessage(String acheteurMessage) {
        this.acheteurMessage = acheteurMessage;
    }

    public String getVendeurMessage() {
        return vendeurMessage;
    }

    public void setVendeurMessage(String vendeurMessage) {
        this.vendeurMessage = vendeurMessage;
    }

    
}

