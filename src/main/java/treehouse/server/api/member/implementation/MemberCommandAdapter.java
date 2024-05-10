package treehouse.server.api.member.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.member.persistence.MemberRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.member.Member;

@Adapter
@Slf4j
@RequiredArgsConstructor
public class MemberCommandAdapter {

    private final MemberRepository memberRepository;

    public Member register(Member member){
        return memberRepository.save(member);
    }
}
