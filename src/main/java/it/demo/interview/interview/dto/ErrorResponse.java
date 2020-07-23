package it.demo.interview.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse  implements Serializable {

    private static final long serialVersionUID = 202000502_1422L;

    private String errMessage;

}
