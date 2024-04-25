package treehouse.server.global.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_GUEST("게스트"),
    ROLE_USER("회원"),
    ROLE_ADMIN("관리자"),
    ADMIN("lagacy 관리자"),
    USER("lagacy 회원");

    private final String description;
}
