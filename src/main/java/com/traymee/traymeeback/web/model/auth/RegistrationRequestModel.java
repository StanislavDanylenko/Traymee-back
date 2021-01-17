package com.traymee.traymeeback.web.model.auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestModel {

    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String repeatPassword;

}
