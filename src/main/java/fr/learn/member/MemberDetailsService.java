package fr.learn.member;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.learn.dao.Member;
import fr.learn.dao.Role;

@Service
public class MemberDetailsService implements UserDetailsService{
	@Autowired
	private MemberService memberService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
		
		Member member = memberService.findByPseudo(pseudo);
		
		if(member == null)
		{
			System.out.println("user not found");
			throw new UsernameNotFoundException("username not found");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		System.out.println("roles de l'utilisateurs");
		
		for(Role role : member.getRoles())
		{
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return new org.springframework.security.core.userdetails.User
				(member.getPseudo(), member.getPassword(), grantedAuthorities);
		
	}

}
