package com.viventor.dto;

import com.viventor.entity.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ApplicationUserMapper {
    ApplicationUserMapper INSTANCE = Mappers.getMapper(ApplicationUserMapper.class);

    ApplicationUserDTO toApplicationUserResponse(ApplicationUser applicationUser);
    List<ApplicationUserDTO> toApplicationUserResponses(List<ApplicationUser> applicationUsers);
    ApplicationUser toApplicationUser(ApplicationUserDTO applicationUserDTO);
}
