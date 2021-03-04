package ru.sfedu.diplomaapp.utils.RecycleView;
import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable {
    private String nameText;
    private String raceText;
    private String lvlText;

    public ExampleItem(String name, String race, String lvl) {
        nameText = name;
        raceText = race;
        lvlText = lvl;
    }
    protected ExampleItem(Parcel in) {
        nameText = in.readString();
        raceText = in.readString();
        lvlText = in.readString();
    }
    public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
        @Override
        public ExampleItem createFromParcel(Parcel in) {
            return new ExampleItem(in);
        }
        @Override
        public ExampleItem[] newArray(int size) {
            return new ExampleItem[size];
        }
    };
    public String getText() {
        return nameText;
    }
    public String getText1() {
        return raceText;
    }
    public String getText2() {
        return lvlText;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameText);
        dest.writeString(raceText);
        dest.writeString(lvlText);
    }
}