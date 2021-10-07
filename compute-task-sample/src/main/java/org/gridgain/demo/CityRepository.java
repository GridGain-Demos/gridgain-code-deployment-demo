package org.gridgain.demo;

import java.util.List;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;

/**
 * City pseudo repository.
 */
public class CityRepository {
    /** List of cities. */
    private static final ListMultimap<CountryCode, String> ALL_CITIES;

    static {
        ArrayListMultimap<CountryCode, String> cities = ArrayListMultimap.create();
        cities.putAll(CountryCode.SPA, Lists.newArrayList(
            "Mallorca",
            "Madrid",
            "Barcelona",
            "Sevilla",
            "Valencia",
            "Zaragoza",
            "Málaga",
            "Bilbao",
            "Murcia",
            "Valladolid",
            "Córdoba",
            "Vigo",
            "Gijón"
        ));
        cities.putAll(CountryCode.FRA, Lists.newArrayList(
            "Bordeaux",
            "Brest",
            "Toulon",
            "Angers",
            "Lille",
            "Lyon",
            "Reims",
            "Toulouse",
            "Strasbourg",
            "Paris",
            "Nantes",
            "Montpellier",
            "Marseille",
            "Nice",
            "Rennes",
            "Grenoble",
            "Dijon"
        ));
        ALL_CITIES = cities;
    }

    /**
     * Returns cities by country code.
     *
     * @param code Country code.
     */
    public List<String> cities(CountryCode code) {
        return Lists.newArrayList(ALL_CITIES.get(code));
    }
}
