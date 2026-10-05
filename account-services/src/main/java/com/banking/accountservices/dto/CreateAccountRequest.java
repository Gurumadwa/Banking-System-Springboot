package com.banking.accountservices.dto;

import com.banking.accountservices.entity.AccountType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @NotBlank(message = "Account Holder Name is Required!")
    private String accountHolderName;

    @NotBlank(message = "Email is Required!")
    @Email(message = "Invalid Email Format!")
    private String email;

    @NotBlank(message = "Phone Number is Required!")
    private String phone;

    @NotNull(message = "Account Type is Required!")
    private AccountType accountType;

    @NotNull(message = "Initial Deposit is Required!")
    @Positive(message = "Initial Deposit Cannot be Negative!")
    private String initialDeposit;
}
