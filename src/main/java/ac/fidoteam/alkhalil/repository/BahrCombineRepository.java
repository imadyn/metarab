package ac.fidoteam.alkhalil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ac.fidoteam.alkhalil.domain.BahrCombine;


/**
 * Spring Data  repository for the BahrCombine entity.
 */
@Repository
public interface BahrCombineRepository extends JpaRepository<BahrCombine, Long>, JpaSpecificationExecutor<BahrCombine> {

    @Query("select distinct b.code from BahrCombine b where b.valeurRhythm = :vr ")
    List<String> findBahrByBait(@Param("vr") String bait);

}
