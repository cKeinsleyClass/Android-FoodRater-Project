package edu.rosehulman.keinslc.foodrater;

import java.util.HashMap;

import static android.R.attr.rating;

/**
 * Created by keinslc on 12/13/2016.
 */

public class Food {
    private String name;
    private int resourceID;
    private float rating;

    public Food(String foodName, int imageResourceID, int numOfStars){
        name = foodName;
        resourceID = imageResourceID;
        rating = numOfStars;
    }

    public Food(String name, int resourceID){
        this(name, resourceID, 0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getResourceID() {
        return resourceID;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }
}
