package ac.fidoteam.alkhalil.repository;

import ac.fidoteam.alkhalil.domain.RefAlphabet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RefAlphabet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefAlphabetRepository extends JpaRepository<RefAlphabet, Long>, JpaSpecificationExecutor<RefAlphabet> {

}
