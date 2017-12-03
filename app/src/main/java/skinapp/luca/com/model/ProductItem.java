package skinapp.luca.com.model;

/**
 * Created by Finn on 12/3/2017.
 */

public class ProductItem {
    private String name;
    private String advantage;
    private String comment;
    private String photoURL;

    public ProductItem(String arg0, String arg1, String arg2, String arg3) {
        this.name = arg0;
        this.advantage = arg1;
        this.comment = arg2;
        this.photoURL = arg3;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setAdvantage(String value) {
        this.advantage = value;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public String getComment() {
        return comment;
    }

    public void setPhotoURL(String value) {
        this.photoURL = value;
    }

    public String getPhotoURL() {
        return photoURL;
    }
}
