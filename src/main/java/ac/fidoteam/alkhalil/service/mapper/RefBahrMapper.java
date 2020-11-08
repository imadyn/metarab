package ac.fidoteam.alkhalil.service.mapper;

import ac.fidoteam.alkhalil.domain.*;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;

import org.mapstruct.*;
import java.util.Set;

/**
 * Mapper for the entity {@link RefBahr} and its DTO {@link RefBahrDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeTBMapper.class})
public interface RefBahrMapper extends EntityMapper<RefBahrDTO, RefBahr> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "parent.style", target = "parentStyle")
    RefBahrDTO toDto(RefBahr refBahr);

    @Mapping(source = "parentId", target = "parent")
    RefBahr toEntity(RefBahrDTO refBahrDTO);

    @IterableMapping(qualifiedByName = "toDtos")
    Set<RefBahrDTO> toDtos(Set<RefBahr> derives);

    default RefBahr fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefBahr refBahr = new RefBahr();
        refBahr.setId(id);
        return refBahr;
    }
}
