package net.matthiasauer.ga.nbody.common.repositories;

import net.matthiasauer.ga.nbody.common.domain.TestRunResult;
import org.springframework.data.repository.CrudRepository;

public interface TestRunResultRepository extends CrudRepository<TestRunResult, String> {
}
