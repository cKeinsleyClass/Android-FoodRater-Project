package edu.rosehulman.keinslc.foodrater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Created by keinslc on 12/13/2016.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private HashMap<String, Integer> sDefaultNamesAndIds;
    private ArrayList<Food> mFoods;
    private Context mContext;
    private Random random;
    private RecyclerView mRecyclerView;

    public FoodAdapter(Context context, RecyclerView recyclerView) {
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
        mRecyclerView = recyclerView;
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
        // Add at position 0 a new Food
        mFoods.add(0, new Food(foodNames[rand], sDefaultNamesAndIds.get(foodNames[rand]), 0));
        mRecyclerView.getLayoutManager().scrollToPosition(0);
        notifyItemInserted(0);
    }


    public void removeFood(int i) {
        mFoods.remove(i);
        notifyItemRemoved(i);
    }

    /*        VIEW HOLDER CLASS      */


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView mFoodNameTextView;
        private ImageView mFoodImageView;
        private RatingBar mFoodRatingBar;

        // Happens2nd, captures the views
        public ViewHolder(View itemView) {
            super(itemView);
            // Capture Views
            itemView.setOnLongClickListener(this);
            mFoodNameTextView = (TextView) itemView.findViewById(R.id.foodNameTextView);
            mFoodImageView = (ImageView) itemView.findViewById(R.id.foodImageView);
            mFoodRatingBar = (RatingBar) itemView.findViewById(R.id.foodRatingBar);

            mFoodRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    // Uses the adapter to retrive the position
                    mFoods.get(getAdapterPosition()).setRating(rating);
                }
            });
        }

        // Long Click Deletion
        @Override
        public boolean onLongClick(View v) {
            removeFood(getAdapterPosition());
            return true;
        }
        // Used for swiping things off
    }

    // Happens First, inflates our view holder with the item view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.food_row_view, parent, false);
        return new ViewHolder(itemView);
    }

    // When it binds, update the views Happens 3rd
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mFoodImageView.setImageResource(mFoods.get(position).getResourceID());
        holder.mFoodNameTextView.setText(mFoods.get(position).getName());
        holder.mFoodRatingBar.setRating(mFoods.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

}
