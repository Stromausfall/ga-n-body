package net.matthiasauer.ganbody.common.repositories;

import net.matthiasauer.ganbody.common.domain.TestRunResult;
import org.springframework.data.repository.CrudRepository;

public interface TestRunResultRepository extends CrudRepository<TestRunResult, String> {
}
