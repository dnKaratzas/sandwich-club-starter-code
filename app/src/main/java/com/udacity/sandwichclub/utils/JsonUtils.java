package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            Sandwich sandwich = new Sandwich();

            JSONObject sandwichJsonObject = new JSONObject(json);
            JSONObject namesJsonObject = sandwichJsonObject.getJSONObject("name");
            sandwich.setMainName(namesJsonObject.getString("mainName"));
            sandwich.setDescription(sandwichJsonObject.getString("description"));
            sandwich.setPlaceOfOrigin(sandwichJsonObject.getString("placeOfOrigin"));
            sandwich.setAlsoKnownAs(parseJsonArray(namesJsonObject.getJSONArray("alsoKnownAs")));
            sandwich.setIngredients(parseJsonArray(sandwichJsonObject.getJSONArray("ingredients")));
            sandwich.setImage(sandwichJsonObject.getString("image"));

            return sandwich;
        } catch (JSONException ex) {
            Log.e(JsonUtils.class.getSimpleName(), "Problem parsing json", ex);
        }
        return null;
    }

    private static List<String> parseJsonArray(JSONArray jsonArray) {
        List<String> result = new ArrayList<>();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    result.add(jsonArray.getString(i));
                } catch (JSONException ex) {
                    Log.e(JsonUtils.class.getSimpleName(), "Problem parsing json array", ex);
                }
            }
        }
        return result;
    }
}
