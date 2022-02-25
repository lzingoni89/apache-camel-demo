package com.example.apachecameldemo.camel.components.routes.wiretap.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDto {

    private String transactionId;
    private String senderAccountId;
    private String receiverAccountId;
    private String amount;
    private String currency;
    private String transactionDate;

}
