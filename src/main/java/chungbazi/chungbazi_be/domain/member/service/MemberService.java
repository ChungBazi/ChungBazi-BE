package chungbazi.chungbazi_be.domain.member.service;


import chungbazi.chungbazi_be.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

}
