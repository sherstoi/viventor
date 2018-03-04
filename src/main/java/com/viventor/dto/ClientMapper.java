package com.viventor.dto;

import com.viventor.entity.Client;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mappings({
            @Mapping(target  = "accountDTOs", expression  =
                    "java(AccountMapper.INSTANCE.toAccountResponses(client.getAccounts()))"),
            @Mapping(target  = "applicationUserDTO", expression  =
                    "java(ApplicationUserMapper.INSTANCE.toApplicationUserResponse(client.getApplicationUser()))"),
    })
    ClientDTO toClientResponse(Client client);

    @Mappings({
            @Mapping(target  = "accounts", expression  =
                    "java(AccountMapper.INSTANCE.toAccounts(clientDTO.getAccountDTOs()))"),
            @Mapping(target  = "applicationUser", expression  =
                    "java(ApplicationUserMapper.INSTANCE.toApplicationUser(clientDTO.getApplicationUserDTO()))"),
    })
    Client toClient(ClientDTO clientDTO);
}