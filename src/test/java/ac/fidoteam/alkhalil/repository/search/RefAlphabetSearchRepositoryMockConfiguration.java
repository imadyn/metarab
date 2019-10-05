package ac.fidoteam.alkhalil.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RefAlphabetSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RefAlphabetSearchRepositoryMockConfiguration {

    @MockBean
    private RefAlphabetSearchRepository mockRefAlphabetSearchRepository;

}
