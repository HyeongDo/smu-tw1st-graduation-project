package com.smu.tw1st;

import java.util.ArrayList;

public class ListPlace {
    private ArrayList<PlaceData> Places;

    public ListPlace(ArrayList<PlaceData> Places) {
        this.Places = Places;
    }

    public ArrayList<PlaceData> getPlaces() {
        return Places;
    }

    public void setPlaces(ArrayList<PlaceData> Places) {
        this.Places = Places;
    }
}
