package com.remote.communication;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class PlaylistMemberEntity extends BaseEntity {
    public int mId;

    public int id ;

    public String album;

    public int albumId;

    public int artistId;

    public String artist;

    public int audioId;

    public int size;

    public String title;

    public String data;

    public int playListId;

    public String displayName ;

    public String MIMEType ;

    public int dateAdded ;


    public PlaylistMemberEntity(){

    }

    public PlaylistMemberEntity(Builder builder) {
        this.mId = builder.mId;
        this.id = builder.id;
        this.album = builder.album;
        this.size = builder.size ;
        this.albumId = builder.albumId;
        this.artistId = builder.artistId;
        this.artist = builder.artist;
        this.audioId = builder.audioId;
        this.title = builder.title;
        this.data = builder.data;
        this.playListId = builder.playListId;
        this.displayName = builder.displayName;
        this.MIMEType = builder.MIMEType;
        this.dateAdded = builder.dateAdded;
    }


    public static class Builder {

        public int mId;

        public int id ;

        public String album;

        public int albumId;

        public int artistId;

        public String artist;

        public int audioId;

        public int size;

        public String title;

        public String data;

        public int playListId;

        public String displayName ;

        public String MIMEType ;

        public int dateAdded ;

        public Builder setMId(int mId) {
            this.mId = mId;
            return this;
        }
        public Builder setId (int id){
            this.id = id;
            return this;
        }
        public Builder setTitle(String title){
            this.title = title;
            return this;
        }
        public Builder setSize(int size){
            this.size = size;
            return this;
        }
        public Builder setPlaylistId(int id){
            this.playListId = id;
            return this;
        }
        public Builder setDisplayName(String displayName){
            this.displayName = displayName;
            return this;
        }
        public Builder setMIMEType(String mimeType){
            this.MIMEType = mimeType;
            return this;
        }
        public Builder setDateAdded(int dateAdded){
            this.dateAdded = dateAdded ;
            return this;
        }
        public Builder setAlbum(String album) {
            this.album = album;
            return this;
        }public Builder setAlbumId(int id) {
            this.albumId = id;
            return this;
        }public Builder setArtistId(int id) {
            this.artistId = id;
            return this;
        }
        public Builder setAudioId(int id){
            this.audioId = id ;
            return this;
        }
        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public PlaylistMemberEntity build(){
            return new PlaylistMemberEntity(this);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mId);
        dest.writeString(this.album);
        dest.writeInt(this.albumId);
        dest.writeInt(this.artistId);
        dest.writeString(this.artist);
        dest.writeInt(this.audioId);
        dest.writeInt(this.size);
        dest.writeString(this.title);
        dest.writeString(this.data);
        dest.writeInt(this.playListId);
        dest.writeString(this.displayName);
        dest.writeString(this.MIMEType);
        dest.writeInt(this.dateAdded);
    }

    protected PlaylistMemberEntity(Parcel in) {
        super(in);
        this.mId = in.readInt();
        this.album = in.readString();
        this.albumId = in.readInt();
        this.artistId = in.readInt();
        this.artist = in.readString();
        this.audioId = in.readInt();
        this.size = in.readInt();
        this.title = in.readString();
        this.data = in.readString();
        this.playListId = in.readInt();
        this.displayName = in.readString();
        this.MIMEType = in.readString();
        this.dateAdded = in.readInt();
    }

    public static final Creator<PlaylistMemberEntity> CREATOR = new Creator<PlaylistMemberEntity>() {
        @Override
        public PlaylistMemberEntity createFromParcel(Parcel source) {
            return new PlaylistMemberEntity(source);
        }

        @Override
        public PlaylistMemberEntity[] newArray(int size) {
            return new PlaylistMemberEntity[size];
        }
    };
}
