package org.gridgain.demo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobAdapter;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskAdapter;
import org.apache.ignite.internal.util.typedef.F;

import static java.util.function.Function.identity;

/**
 * Example of a task, returning some result from pseudo repository.
 */
public class CityFilterTask extends ComputeTaskAdapter<Object, List<String>> {
    /** {@inheritDoc} */
    @Override
    public Map<? extends ComputeJob, ClusterNode> map(List<ClusterNode> subgrid, Object arg) throws IgniteException {
        CityComputeJob job = new CityComputeJob(CountryCode.SPA);
//        CityComputeJob job = new CityComputeJob(CountryCode.FRA);

        return subgrid.stream()
            .collect(Collectors.toMap(node -> job, identity()));
    }

    /** {@inheritDoc} */
    @Override public List<String> reduce(List<ComputeJobResult> results) throws IgniteException {
        ComputeJobResult jobResult = F.first(results);
        return jobResult.getData();
    }

    /**
     * Sample compute job.
     */
    public static class CityComputeJob extends ComputeJobAdapter {
        /** Repository. */
        private static final CityRepository repo = new CityRepository();

        /**
         * @param code Code.
         */
        public CityComputeJob(CountryCode code) {
            super(code);
        }

        /** {@inheritDoc} */
        @Override public Object execute() throws IgniteException {
            Object countryCode = argument(0);
            if (!(countryCode instanceof CountryCode)) {
                throw new IllegalArgumentException("Invalid argument value " + countryCode +
                    ". Expected one from " + CountryCode.class.getName());
            }

            List<String> cities = repo.cities((CountryCode) countryCode);
            Collections.sort(cities);

            System.out.println(
                Joiner.on("").join(Lists.newArrayList(">> Found ", cities.size(), " cities with code ", countryCode))
            );

            return cities;
        }
    }
}
