package treehouse.server.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Consts {

    @AllArgsConstructor
    @Getter
    public enum FileSizeLimit{

        LIMIT(3145728L);
        private final Long limit;
    }
}
