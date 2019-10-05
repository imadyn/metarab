package ac.fidoteam.alkhalil.repository;

import ac.fidoteam.alkhalil.domain.RefRhythm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RefRhythm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefRhythmRepository extends JpaRepository<RefRhythm, Long>, JpaSpecificationExecutor<RefRhythm> {

}
