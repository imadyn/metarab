package ac.fidoteam.alkhalil.service.mapper;

import ac.fidoteam.alkhalil.domain.*;
import ac.fidoteam.alkhalil.service.dto.RefRhythmDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RefRhythm} and its DTO {@link RefRhythmDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefRhythmMapper extends EntityMapper<RefRhythmDTO, RefRhythm> {

    @Mapping(source = "parent.id", target = "parentId")
    RefRhythmDTO toDto(RefRhythm refRhythm);

    @Mapping(source = "parentId", target = "parent")
    RefRhythm toEntity(RefRhythmDTO refRhythmDTO);

    default RefRhythm fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefRhythm refRhythm = new RefRhythm();
        refRhythm.setId(id);
        return refRhythm;
    }
}
