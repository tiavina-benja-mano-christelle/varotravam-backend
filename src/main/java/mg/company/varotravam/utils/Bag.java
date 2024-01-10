package mg.company.varotravam.utils;

public class Bag {
    String error = null;
    Object data = null;

    public Bag() {}

    public Bag(String error, Object data) {
        this.error = error;
        this.data = data;
    }


    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    
}
