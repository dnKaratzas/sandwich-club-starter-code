package com.udacity.sandwichclub.utils;

import java.util.List;

public final class Misc {

    public static String join(List<String> values, String seperator) {
        if (values.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            result.append(values.get(i));
            result.append((i != values.size() - 1) ? seperator : "");
        }
        return result.toString();
    }

}
