package mg.company.varotravam.utils;

public class Bag {
    String error = null;
    Object data = null;
    String tokens = null;
    boolean success = true;

    public Bag() {}

    public Bag(String error, Object data) {
        this.error = error;
        this.data = data;
    }


    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.success = false;
        this.error = error;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    
}
