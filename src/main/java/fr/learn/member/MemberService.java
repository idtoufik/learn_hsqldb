package fr.learn.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.learn.dao.Member;

@Service
public class MemberService {
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	public boolean register(Member member)
	{
		member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
		try
		{
			memberRepository.save(member);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	public Member findByPseudo(String pseudo)
	{
		return memberRepository.findByPseudo(pseudo);
	}
	
	
	 public List<Member> findAll()
	 {
		 List<Member> members = new ArrayList<>();
		 memberRepository.findAll().forEach(members::add);
		 return members;
	 }
	 
	 public void delete(long id)
	 {
		 memberRepository.delete(id);
	 }
	 
	 public Member getMember(long memberId)
	 {
		 return memberRepository.findOne(memberId);
	 }
	 
	 public boolean updateMember(Member member)
	 { 
		 try
			{
				memberRepository.save(member);
			}
			catch(Exception e)
			{
				return false;
			}
			
			return true;
	 }

	public Member findOne(long idMember) {
		
		return memberRepository.findOne(idMember);
	}
	 
	public Member getMemberFromAuthentification(Authentication auth)
	{
	    if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken))
	    {
	    	return findByPseudo(auth.getName());
	    }
	    else
	    {
	    	return null;
	    }
	}

	public void changePasswordForMember(Member loggedIn, String newPassword) {
		//TODO test if i have to change the useDetals model
		loggedIn.setPassword(bCryptPasswordEncoder.encode(newPassword));
		memberRepository.save(loggedIn);
		
	}
	 
	public Member findByEmail(String email)
	{
		return memberRepository.findByEmail(email);
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
