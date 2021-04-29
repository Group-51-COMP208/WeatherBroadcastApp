package com.example.weatherbroadcastapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.example.lib.Location;
import com.example.lib.LocationService;
import com.example.lib.Services;
import com.example.lib.WeatherForecastService;

// Custom ArrayAdaptor which works exactly like ArrayAdapter<String> except
// that it uses a custom filter to return the user's favourite locations before
// they start typing.
public class AutoCompleteFavouriteLocationAdapter extends ArrayAdapter<String> {
    public AutoCompleteFavouriteLocationAdapter(Context context, List<String> locations) {
        // Passing a new ArrayList to super keeps it happy while letting us apply our own filtering
        // without it messaging with our locations array. Yes, this does feel a bit hacky.
        super(context, 0, new ArrayList<String>());
        init(locations);
    }

    public AutoCompleteFavouriteLocationAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, new ArrayList<String>());
        init(objects);
    }

    void init(List<String> locations) {
        LocationService locationService = Services.get().getLocationService();
        for(Location l: locationService.getFavouriteLocations()) {
            favouriteLocations.add(l.getDisplayName());
        }

        allLocations = locations;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    // Based on ArrayAdapter.ArrayFilter but suggests favourite locations
    // before the user types anything
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();
            List<String> suggestions = new ArrayList<String>();

            if (prefix == null || prefix.length() == 0) {
                // User hasn't typed anything yet. Suggest favourites.
                suggestions.addAll(favouriteLocations);
            } else {
                final String prefixString = prefix.toString().toLowerCase();
                System.out.println("locations.length(): " + allLocations.size());

                for(String location: allLocations) {
                    String s = location.toLowerCase();
                    System.out.println("Checking location: " + s);
                    if(s.startsWith(prefixString)) {
                        System.out.println(s + " starts with " + prefixString);
                        suggestions.add(location);
                    }
                    else {
                        final String[] words = s.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                suggestions.add(location);
                                break;
                            }
                        }
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
    }

    private final Filter filter = new CustomFilter();
    private ArrayList<String> favouriteLocations = new ArrayList<String>();
    private List<String> filteredLocations;
    private List<String> allLocations;
}
