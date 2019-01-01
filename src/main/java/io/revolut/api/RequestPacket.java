package io.revolut.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPacket {

    private String source;
    private String destination;
    private BigDecimal amount;
    private String currency;

}
