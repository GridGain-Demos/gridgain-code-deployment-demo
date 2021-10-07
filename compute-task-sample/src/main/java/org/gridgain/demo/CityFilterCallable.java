package org.gridgain.demo;

import java.util.Collections;
import java.util.List;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.ignite.Ignite;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.resources.IgniteInstanceResource;

/**
 * Example of a callable, returning some result from pseudo repository.
 */
public class CityFilterCallable implements IgniteCallable<List<String>> {
    /** Repository. */
    private static final CityRepository repo = new CityRepository();

    /** Ignite. */
    @IgniteInstanceResource
    Ignite ignite;

    @Override public List<String> call() throws Exception {
        System.out.println(">> Executing the compute task");

        return getCities(CountryCode.SPA);
//        return getCities(CountryCode.FRA);
    }

    private List<String> getCities(CountryCode code) {
        List<String> cities = repo.cities(code);
        Collections.sort(cities);

        System.out.println(
            Joiner.on("").join(Lists.newArrayList(">> Found ", cities.size(), " cities with code ", code))
        );

        return cities;
    }
}
