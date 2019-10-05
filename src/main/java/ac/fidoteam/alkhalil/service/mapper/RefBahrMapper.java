package ac.fidoteam.alkhalil.service.mapper;

import ac.fidoteam.alkhalil.domain.*;
import ac.fidoteam.alkhalil.service.dto.RefBahrDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefBahr} and its DTO {@link RefBahrDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefBahrMapper extends EntityMapper<RefBahrDTO, RefBahr> {

    @Mapping(source = "parent.id", target = "parentId")
    RefBahrDTO toDto(RefBahr refBahr);

    @Mapping(source = "parentId", target = "parent")
    RefBahr toEntity(RefBahrDTO refBahrDTO);

    default RefBahr fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefBahr refBahr = new RefBahr();
        refBahr.setId(id);
        return refBahr;
    }
}
