package edu.rosehulman.keinslc.foodrater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by keinslc on 12/13/2016.
 */

public class FoodAdapter extends BaseAdapter {
    private HashMap<String, Integer> sDefaultNamesAndIds;
    private ArrayList<Food> mFoods;
    private Context mContext;
    private Random random;

    public FoodAdapter(Context context) {
        // Puts all of the image IDs into the hashmap for later
        // courtesy of Dr. Boutell
        sDefaultNamesAndIds = new HashMap<>();
        sDefaultNamesAndIds.put("banana", R.drawable.banana);
        sDefaultNamesAndIds.put("broccoli", R.drawable.broccoli);
        sDefaultNamesAndIds.put("homemade bread", R.drawable.bread);
        sDefaultNamesAndIds.put("chicken", R.drawable.chicken);
        sDefaultNamesAndIds.put("chocolate", R.drawable.chocolate);
        sDefaultNamesAndIds.put("ice cream", R.drawable.icecream);
        sDefaultNamesAndIds.put("lima beans", R.drawable.limabeans);
        sDefaultNamesAndIds.put("steak", R.drawable.steak);
        mFoods = new ArrayList<>();
        generateDefaultFood();
        mContext = context;
        random = new Random();
    }

    /**
     * Fills the arraylist with a default list of food
     */
    private void generateDefaultFood() {
        mFoods.add(new Food("steak", sDefaultNamesAndIds.get("steak"), 0));
        mFoods.add(new Food("banana", sDefaultNamesAndIds.get("banana"), 0));
        mFoods.add(new Food("chocolate", sDefaultNamesAndIds.get("chocolate"), 0));
        mFoods.add(new Food("chicken", sDefaultNamesAndIds.get("chicken"), 0));
        mFoods.add(new Food("ice cream", sDefaultNamesAndIds.get("ice cream"), 0));
    }

    /**
     * Generates an array of food names from the hashmaps keySet then randomly picks
     * a string and creates a new food with 0 stars
     */
    public void addRandomFood() {
        Set<String> foodKeys = sDefaultNamesAndIds.keySet();
        String[] foodNames = new String[foodKeys.size()];
        foodKeys.toArray(foodNames);
        int rand = random.nextInt(foodNames.length);
        mFoods.add(new Food(foodNames[rand], sDefaultNamesAndIds.get(foodNames[rand]), 0));
        notifyDataSetChanged();
    }


    public void removeFood(int i) {
        mFoods.remove(i);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return mFoods.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view = null;
        if (convertView == null) {
            // We have to make it
            view = LayoutInflater.from(mContext).inflate(R.layout.food_row_view, viewGroup, false);
        } else {
            // Reuse it
            view = convertView;
        }
        // Customize the view
        TextView mFoodNameTextView = (TextView) view.findViewById(R.id.foodNameTextView);
        ImageView mFoodImageView = (ImageView) view.findViewById(R.id.foodImageView);
        RatingBar mFoodRatingBar = (RatingBar) view.findViewById(R.id.foodRatingBar);

        // Really Fun Bug when this isn't here
        mFoodRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mFoods.get(position).setRating(rating);
            }
        });

        mFoodImageView.setImageResource(mFoods.get(position).getResourceID());
        mFoodNameTextView.setText(mFoods.get(position).getName());
        mFoodRatingBar.setRating(mFoods.get(position).getRating());

        return view;
    }

}
