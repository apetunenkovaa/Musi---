package com.example.music;


import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {
    private Integer ID_Music;
    private String Name;
    private String Executor;
    private String Genre;
    private String Duration;
    private String Image;


    public Mask(Integer ID_Music, String name, String executor, String genre, String duration, String image) {
        this.ID_Music = ID_Music;
        Name = name;
        Executor = executor;
        Genre = genre;
        Duration = duration;
        Image = image;
    }

    protected Mask(Parcel in) {
        ID_Music = in.readInt();
        Name = in.readString();
        Executor = in.readString();
        Genre = in.readString();
        Duration = in.readString();
        Image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID_Music);
        dest.writeString(Name);
        dest.writeString(Executor);
        dest.writeString(Genre);
        dest.writeString(Duration);
        dest.writeString(Image);
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };



    public void setID_Music(int ID_Music) {
        this.ID_Music = ID_Music;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setExecutor(String executor) {Executor = executor;}

    public void setGenre(String genre) {Genre = genre;}

    public void setDuration(String duration) {Duration = duration;}

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getName() {
        return Name;
    }

    public String getExecutor() {
        return Executor;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDuration() {
        return Duration;
    }

    public String getImage() { return Image;}

    public int getID() { return ID_Music;}



}
