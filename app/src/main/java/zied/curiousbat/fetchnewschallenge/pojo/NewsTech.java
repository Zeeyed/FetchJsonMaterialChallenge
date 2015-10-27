package zied.curiousbat.fetchnewschallenge.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import zied.curiousbat.fetchnewschallenge.Logging.L;

/**
 * Description : Class to handle data
 * Created by Zied on 26/10/2015.
 */
public class NewsTech implements Parcelable, Serializable{

    private int id;
    private String title;
    private String website;
    private String author;
    private Date date;
    private String content;
    private String img;

    public NewsTech() {
    }

    public NewsTech(Parcel in) {
        id=in.readInt();
        title = in.readString();
        date = new Date(in.readLong());
        img = in.readString();
    }

    public NewsTech(String title, String website, String author, Date date, String content, String img) {
        this.title = title;
        this.website = website;
        this.author = author;
        this.date = date;
        this.content = content;
        this.img = img;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Title " +title+
                "Date " + date+
                "urlImg " +img;

    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }



    @Override
    public int describeContents() {
        L.m("describe Content Tech News");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        L.m("Write to Parcel Tech News");

        dest.writeString(title);
        dest.writeLong(date.getTime());
        dest.writeString(img);
    }

    public static final Parcelable.Creator<NewsTech> CREATOR
            = new Parcelable.Creator<NewsTech>(){
        public NewsTech createFromParcel(Parcel in){
            L.m("create from Parcel Tech News");
            return new NewsTech(in);
        }

        public NewsTech[] newArray(int size){
            return new NewsTech[size];
        }

    };
}
