package org.aimes.redis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {
    private Integer id;
    private String name;
    private Double balance;

}
