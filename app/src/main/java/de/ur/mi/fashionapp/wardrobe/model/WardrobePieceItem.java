package de.ur.mi.fashionapp.wardrobe.model;

import android.os.Parcel;

import com.hannesdorfmann.parcelableplease.annotation.ParcelableNoThanks;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 */
@ParcelablePlease
public class WardrobePieceItem extends WardrobeItem {

    @ParcelableNoThanks
    public static final int MAX_IMAGE_HEIGHT = 250;
    @ParcelableNoThanks
    public static final int MAX_IMAGE_WIDTH = 250;
    boolean isSelected = false;
    boolean maxReached = false;
    int category;
    int season;
    int occasion;
    int color;

    public static final Creator<WardrobePieceItem> CREATOR = new Creator<WardrobePieceItem>() {
        @Override
        public WardrobePieceItem createFromParcel(Parcel source) {
            WardrobePieceItem target = new WardrobePieceItem();
            WardrobePieceItemParcelablePlease.readFromParcel(target, source);
            return target;
        }

        @Override
        public WardrobePieceItem[] newArray(int size) {
            return new WardrobePieceItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        WardrobePieceItemParcelablePlease.writeToParcel(this, dest, flags);
    }

    public int getCat() {
        return category;
    }

    public int getSeason() {
        return season;
    }

    public int getOccasion() {
        return occasion;
    }

    public int getColor() {
        return color;
    }

    public void setCat(int cat) {
        category = cat;
    }

    public void setOccasion(int tag) {
        occasion = tag;
    }

    public void setSeason(int tag) {
        season = tag;
    }

    public void setColor(int tag) {
        color = tag;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelection(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isMaxReached() {
        return maxReached;
    }

    public void setMaxReached(boolean maxReached) {
        this.maxReached = maxReached;
    }
}
