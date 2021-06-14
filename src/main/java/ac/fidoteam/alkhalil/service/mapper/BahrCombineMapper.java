package ac.fidoteam.alkhalil.service.mapper;

import java.util.Set;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import ac.fidoteam.alkhalil.domain.BahrCombine;
import ac.fidoteam.alkhalil.domain.RefBahr;
import ac.fidoteam.alkhalil.service.dto.BahrCombineDTO;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;

/**
 * Mapper for the entity {@link RefBahr} and its DTO {@link RefBahrDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeTBMapper.class})
public interface BahrCombineMapper extends EntityMapper<BahrCombineDTO, BahrCombine> {


	BahrCombineDTO toDto(BahrCombine bahrCombine);

    BahrCombine toEntity(BahrCombineDTO bahrCombineDTO);

    @IterableMapping(qualifiedByName = "toDtos")
    Set<BahrCombineDTO> toDtos(Set<BahrCombine> derives);

    default BahrCombine fromId(Long id) {
        if (id == null) {
            return null;
        }
        BahrCombine refBahr = new BahrCombine();
        refBahr.setId(id);
        return refBahr;
    }
}
