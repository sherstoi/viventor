package com.viventor.dto;

import java.util.Set;

public class ClientDTO {
    private Long clientId;
    private String clientName;
    private Set<AccountDTO> accountDTOs;
    private ApplicationUserDTO applicationUserDTO;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Set<AccountDTO> getAccountDTOs() {
        return accountDTOs;
    }

    public void setAccountDTOs(Set<AccountDTO> accountDTOs) {
        this.accountDTOs = accountDTOs;
    }

    public ApplicationUserDTO getApplicationUserDTO() {
        return applicationUserDTO;
    }

    public void setApplicationUserDTO(ApplicationUserDTO applicationUserDTO) {
        this.applicationUserDTO = applicationUserDTO;
    }
}