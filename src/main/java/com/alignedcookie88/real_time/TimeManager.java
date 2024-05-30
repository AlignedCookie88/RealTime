package com.alignedcookie88.real_time;

import com.alignedcookie88.real_time.api.IPApi;
import com.alignedcookie88.real_time.api.SunriseSunsetIO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class TimeManager {

    LocalDateTime cachedSunrise;

    LocalDateTime cachedSunset;


    private boolean isSunsetSunriseCacheValid() {
        if (cachedSunrise == null)
            return false;
        return cachedSunset != null;
    }

    private void fetchSunriseSunset() {
        IPApi.APIResponse geo = IPApi.makeRequestMe();
        SunriseSunsetIO.APIResponse response = SunriseSunsetIO.makeRequest(geo.lat, geo.lon);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy-MM-dd h:m:s a")
                .toFormatter(Locale.ENGLISH);

        String prefix = "2000-01-01 "; // me when this fixes the issue for some reason (why do i need a date)

        cachedSunset = LocalDateTime.parse(prefix+response.sunset, formatter);
        cachedSunrise = LocalDateTime.parse(prefix+response.sunrise, formatter);
    }

    public LocalDateTime getSunrise() {
        if (!isSunsetSunriseCacheValid())
            fetchSunriseSunset();
        return cachedSunrise;
    }

    public LocalDateTime getSunset() {
        if (!isSunsetSunriseCacheValid())
            fetchSunriseSunset();
        return cachedSunset;
    }

    public long correctForMCTimeOffset(long time) {
        if (((time % 24000) - 6000) < 0) {
            return time + 18000;
        } else {
            return time - 6000;
        }
    }

    public double getLocalDateTimeAsMinecraftTicks(LocalDateTime time) {
        return ((double) time.getHour() * 1000.0d)
                + ((double) time.getMinute() * (1000.0d / 60.0d))
                + ((double) time.getSecond() * (1000.0d / 60.0d / 60.0d));
    }

    public long getTime() {
        // Get times
        double now = getLocalDateTimeAsMinecraftTicks(LocalDateTime.now());
        double sunrise = getLocalDateTimeAsMinecraftTicks(getSunrise());
        double sunset = getLocalDateTimeAsMinecraftTicks(getSunset());

        // Some data
        double dayLength = sunset-sunrise;
        double scaled;

        // Scale time to sunrise/sunset
        if (now <= sunrise) { // Before sunrise
            scaled = (now / sunrise) * 5000;
        } else if (now >= sunset) { // After sunset
            scaled = 24000 - (((24000-now) / (24000-sunset)) * 6000);
        } else { // During day
            scaled = 5000 + (((now - sunrise) / dayLength) * 13000);
        }

        //IRLTime.LOGGER.info("Now: {}, Sunrise: {}, Sunset: {}, Day length: {}, Scaled: {} DBG: {}", now, sunrise, sunset, dayLength, scaled, ((now - sunrise) / dayLength));

        // Correct for MC time offset and return.
        return correctForMCTimeOffset((long) scaled);
    }

    public long getTimeOfDay() {
        return getTime() % 24000;
    }

}
