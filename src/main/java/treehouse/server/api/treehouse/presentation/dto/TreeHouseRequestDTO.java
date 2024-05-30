package treehouse.server.api.treehouse.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeHouseRequestDTO {

    @Getter
    public static class phoneNumRequest{
        private String phoneNum;
    }
}
