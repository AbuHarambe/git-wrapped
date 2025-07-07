package de.muenchen.gitwrapped.theentity.dto;

import de.muenchen.gitwrapped.theentity.TheEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TheEntityMapper {

    TheEntityResponseDTO toDTO(TheEntity theEntity);

    TheEntity toEntity(TheEntityRequestDTO theEntityRequestDTO);
}
