package com.example.lib;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

/*
 * A function library of useful utilities
 */
public class Utilities {
    // Returns a new Calender specifying the time that is period * number after startTime
    public static Calendar addTime(Calendar startTime, Duration period, int number) {
        Calendar newTime = (Calendar) startTime.clone();
        newTime.add(Calendar.MINUTE, (int) period.toMinutes() * number);
        return newTime;
    }

    // Returns a new Calendar with time equal to startTime + period
    public static Calendar addTime(Calendar startTime, Duration period) {
        return addTime(startTime, period, 1);
    }


    // Finds the closest location in the given array to the specified latitude and longitude
    // Uses Euclidian distance.
    public static Location findClosestLocation(ArrayList<Location> locations,
                                               double latitude, double longitude) {
        Location closest = null;
        double closestDistanceSquared = Double.POSITIVE_INFINITY;
        for(Location l: locations) {
            final double dx = longitude - l.getLongitude();
            final double dy = latitude - l.getLatitude();
            // Distance squared suffices for the comparison and is
            // computationally easier (for all the difference it makes)
            // as it avoids the square root
            final double distanceSquared = dx*dx + dy*dy;
            if(distanceSquared < closestDistanceSquared) {
                closest = l;
                closestDistanceSquared = distanceSquared;
            }
        }
        return closest;
    }
}
