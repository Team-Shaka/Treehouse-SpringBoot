package treehouse.server.api.member.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.member.implementation.MemberCommandAdapter;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.member.presentation.dto.MemberRequestDTO;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

@Service
@AllArgsConstructor
@Slf4j
public class MemberService {

    private final MemberQueryAdapter memberQueryAdapter;

    private final MemberCommandAdapter memberCommandAdapter;
    private final TreehouseQueryAdapter treehouseQueryAdapter;

    /**
     * 회원가입
     * @param user
     * @param request
     * @return
     */
    @Transactional
    public MemberResponseDTO.registerMember register(User user, MemberRequestDTO.registerMember request){
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(request.getTreehouseId());
        Member member = MemberMapper.toMember(user, request.getMemberName(),
                                                request.getBio(), request.getProfileImageURL(), treeHouse);
        Member savedMember = memberCommandAdapter.register(member);

        return MemberMapper.toRegister(request.getTreehouseId(), savedMember); // treehouseId는 관련 기능 구현 후 변경
    }
}
