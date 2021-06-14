package ac.fidoteam.alkhalil.service.mapper;

import java.util.Set;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import ac.fidoteam.alkhalil.domain.BahrCombineBis;
import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.service.dto.BahrCombineBisDTO;
import ac.fidoteam.alkhalil.service.dto.BahrCombineDTO;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;

/**
 * Mapper for the entity {@link RefBahr} and its DTO {@link RefBahrDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeTBMapper.class})
public interface BahrCombineBisMapper extends EntityMapper<BahrCombineBisDTO, BahrCombineBis> {


	BahrCombineBisDTO toDto(BahrCombineBis bahrCombineBis);

    BahrCombineBis toEntity(BahrCombineDTO bahrCombineBisDTO);

    @IterableMapping(qualifiedByName = "toDtos")
    Set<BahrCombineBisDTO> toDtos(Set<BahrCombineBis> derives);

    default BahrCombineBis fromId(Long id) {
        if (id == null) {
            return null;
        }
        BahrCombineBis refBahr = new BahrCombineBis();
        refBahr.setId(id);
        return refBahr;
    }
}
