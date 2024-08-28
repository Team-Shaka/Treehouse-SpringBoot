package treehouse.server.api.member.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.member.persistence.MemberRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.MemberException;

@Slf4j
@Adapter
@RequiredArgsConstructor
public class MemberQueryAdapter {

    private final MemberRepository memberRepository;

    public Member getMember(User user){
        return memberRepository.findByUser(user).orElseThrow(()-> new MemberException(GlobalErrorCode.USER_NOT_FOUND));
    }

    public Member findByUserAndTreehouse(User user, TreeHouse treehouse) {
        log.info("user: {}, treehouse: {}", user.getId(), treehouse.getId());
        return memberRepository.findByUserAndTreeHouse(user, treehouse).orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
    }
}
