package skinapp.luca.com.model;

/**
 * Created by Leif on 12/4/2017.
 */

public class HistoryItem {
    private String type;
    private String value;
    private String date;

    public HistoryItem() {

    }

    public void setType(String value) {
        this.type = value;
    }

    public String getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getDate() {
        return date;
    }
}
