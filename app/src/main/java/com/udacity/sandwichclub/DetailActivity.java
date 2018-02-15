package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_SCROLL_VIEW_POSITION = "extra_scroll_view_position";
    private static final int DEFAULT_POSITION = -1;

    private NestedScrollView svDetails;
    private ImageView ivSandwichImage;
    private TextView tvAlsoKnownLabel;
    private TextView tvOriginLabel;
    private TextView tvIngredientsLabel;
    private TextView tvDescriptionLabel;
    private TextView tvAlsoKnownValue;
    private TextView tvOriginValue;
    private TextView tvIngredientsValue;
    private TextView tvDescriptionValue;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the scroll view position
        outState.putInt(EXTRA_SCROLL_VIEW_POSITION, svDetails.getScrollY());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int position = savedInstanceState.getInt(EXTRA_SCROLL_VIEW_POSITION);

        svDetails.post(new Runnable() {
            public void run() {
                svDetails.scrollTo(0, position);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        svDetails = findViewById(R.id.svDetails);
        ivSandwichImage = findViewById(R.id.ivSandwichImage);
        tvAlsoKnownLabel = findViewById(R.id.tvAlsoKnownLabel);
        tvOriginLabel = findViewById(R.id.tvOriginLabel);
        tvIngredientsLabel = findViewById(R.id.tvIngredientsLabel);
        tvDescriptionLabel = findViewById(R.id.tvDescriptionLabel);
        tvAlsoKnownValue = findViewById(R.id.tvAlsoKnownValue);
        tvOriginValue = findViewById(R.id.tvOriginValue);
        tvIngredientsValue = findViewById(R.id.tvIngredientsValue);
        tvDescriptionValue = findViewById(R.id.tvDescriptionValue);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ivSandwichImage);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Also Known As
        String alsoKnownAs = sandwich.getAlsoKnownAsFormatted();

        if (alsoKnownAs.isEmpty()) {
            tvAlsoKnownLabel.setVisibility(View.GONE);
            tvAlsoKnownValue.setVisibility(View.GONE);
        } else {
            tvAlsoKnownValue.setText(alsoKnownAs);
        }

        // Ingredients
        String ingredients = sandwich.getIngredientsFormatted();

        if (ingredients.isEmpty()) {
            tvIngredientsLabel.setVisibility(View.GONE);
            tvIngredientsValue.setVisibility(View.GONE);
        } else {
            tvIngredientsValue.setText(ingredients);
        }

        // Place of Origin
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            tvOriginLabel.setVisibility(View.GONE);
            tvOriginValue.setVisibility(View.GONE);
        } else {
            tvOriginValue.setText(sandwich.getPlaceOfOrigin());
        }

        // Place of Origin
        if (sandwich.getDescription().isEmpty()) {
            tvDescriptionLabel.setVisibility(View.GONE);
            tvDescriptionValue.setVisibility(View.GONE);
        } else {
            tvDescriptionValue.setText(sandwich.getDescription());
        }
    }

}
