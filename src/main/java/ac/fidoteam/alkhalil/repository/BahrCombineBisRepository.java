package ac.fidoteam.alkhalil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

import ac.fidoteam.alkhalil.domain.BahrCombineBis;


/**
 * Spring Data  repository for the BahrCombineBis entity.
 */
@Repository
public interface BahrCombineBisRepository extends JpaRepository<BahrCombineBis, Long>, JpaSpecificationExecutor<BahrCombineBis> {

    @Query("select distinct b.code from BahrCombineBis b where b.valeurRhythm = :vr ")
    List<String> findBahrBaitByKey(@Param("vr") String valeurRhythm);

}
