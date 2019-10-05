package ac.fidoteam.alkhalil.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TypeTBSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TypeTBSearchRepositoryMockConfiguration {

    @MockBean
    private TypeTBSearchRepository mockTypeTBSearchRepository;

}
