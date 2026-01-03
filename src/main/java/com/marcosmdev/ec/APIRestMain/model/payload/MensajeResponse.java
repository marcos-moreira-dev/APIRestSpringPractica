package com.marcosmdev.ec.APIRestMain.model.payload;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Standard API response payload with message and data.
 */
@Data
@ToString
@Builder
public class MensajeResponse  implements Serializable {

    private String message;
    private Object object;

}
