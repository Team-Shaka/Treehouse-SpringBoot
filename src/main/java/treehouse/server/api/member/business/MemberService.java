package treehouse.server.api.member.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.member.implementation.MemberCommandAdapter;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.member.presentation.dto.MemberRequestDTO;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;

@Service
@AllArgsConstructor
@Slf4j
public class MemberService {

    private final MemberQueryAdapter memberQueryAdapter;

    private final MemberCommandAdapter memberCommandAdapter;

    /**
     * 회원가입
     * @param user
     * @param request
     * @return
     */
    @Transactional
    public MemberResponseDTO.registerMember register(User user, MemberRequestDTO.registerMember request){
        Member member = MemberMapper.toMember(user, request.getMemberName(),
                                                request.getBio(), request.getProfileImageURL());
        Member savedMember = memberCommandAdapter.register(member);

        return MemberMapper.toRegister(request.getTreehouseId(), savedMember); // treehouseId는 관련 기능 구현 후 변경
    }

    public void checkAlreadyMember(Long treeHouseId, String phoneNum){
        memberQueryAdapter.existsByTreeHouseAndPhoneNum(treeHouseId, phoneNum);
    }
}
