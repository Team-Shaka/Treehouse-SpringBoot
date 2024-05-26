package treehouse.server.api.member.implementation;

import lombok.RequiredArgsConstructor;
import treehouse.server.api.member.persistence.MemberRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.MemberException;

@Adapter
@RequiredArgsConstructor
public class MemberQueryAdapter {

    private final MemberRepository memberRepository;

    public Member getMember(User user){

        return memberRepository.findByUser(user).orElseThrow(()-> new MemberException(GlobalErrorCode.USER_NOT_FOUND));
    }

    public Member findByUserAndTreehouse(User user, TreeHouse treehouse) {
        return memberRepository.findByUserAndTreeHouse(user, treehouse).orElseThrow(() -> new MemberException(GlobalErrorCode.USER_NOT_FOUND));
    }
}
