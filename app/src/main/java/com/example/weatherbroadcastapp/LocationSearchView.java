package com.example.weatherbroadcastapp;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;

/*
 * Overrides the AutoCompleteTextView to add the functionality of suggesting
 * the user's favourite locations before they start typing.
 */
public class LocationSearchView extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    public LocationSearchView(@NonNull Context context) {
        super(context);
    }

    public LocationSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged (boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(focused) {
            performFiltering("", 0);
            showDropDown();
        }
    }
}
