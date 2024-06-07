package com.dws.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TransferAmount {

  @NotNull
  @NotEmpty
  private final Account account;
  
  @NotNull
  @NotEmpty
  private final String toAccountId;

  @NotNull
  @Min(value = 0, message = "Amount to Transfer must be positive.")
  private BigDecimal amountToTransfer;

  @JsonCreator
  public TransferAmount(@JsonProperty("account") Account account,@JsonProperty("toAccountId")String toAccountId, @JsonProperty("amountToTransfer") BigDecimal amountToTransfer) {
    this.account = account;
    this.toAccountId=toAccountId;
    this.amountToTransfer=amountToTransfer;
  }

 
}
