package fr.learn.member;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.learn.dao.Member;
import fr.learn.dao.Role;

@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	
	@RequestMapping(value = "/resources/members", method = RequestMethod.POST)
	public boolean registerMember(@RequestBody Member member)
	{
		member.setId(null);
		member.setDateOfRegistration(new Date());
		if(member.getPassword() == null || member.getPassword().length() <6)
		{
			return false;
		}
		return memberService.register(member);
	}

	@RequestMapping(value = "/resources/members", method = RequestMethod.GET)
	public List<Member> getAllMembers()
	{
		
		List<Member> members = memberService.findAll();
		members.forEach(t -> t.setCourses(null));
		return members;
	}
	
	
	
	@RequestMapping(value = "/resources/members/{idMember}", method = RequestMethod.DELETE)
	public boolean deleteMember(@PathVariable("idMember") Long idMember)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member loggedIn = memberService.getMemberFromAuthentification(auth);
		if (loggedIn == null)
			return false;
		
		if(loggedIn.getId().equals(idMember))
			return false;
		for(Role role : loggedIn.getRoles())
		{
			if(role.getName().equals("root"))
			{
				memberService.delete(idMember);
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/resources/members/{idMember}", method = RequestMethod.GET)
	public Member getMember(@PathVariable("idMember") long idMember)
	{
		Member member = memberService.getMember(idMember);
		return member;
	}
	
	
	
	@RequestMapping(value = "/resources/members/{idMember}", method = RequestMethod.PUT)
	public boolean updateMember(@PathVariable("idMember") Long idMember, @RequestBody Member member)
	{
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member loggedIn = memberService.getMemberFromAuthentification(auth);
		if(loggedIn != null && loggedIn.getId().equals(idMember))
		{
			member.setId(idMember);
			member.setPassword(memberService.findOne(idMember).getPassword());
			if(memberService.updateMember(member))
			{
				member = memberService.findOne(idMember);
				Authentication authentication = new UsernamePasswordAuthenticationToken(member.getPseudo(), 
						member.getPassword(), auth.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return true;
			}
			
		}
		
		return false;
		
		
	}

	@RequestMapping(value = "/resources/members/loggedIn", method = RequestMethod.GET)
	public Member getLoggedInMember()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member loggedIn = memberService.getMemberFromAuthentification(auth);
		if(loggedIn != null)
			loggedIn.setCourses(null);
		else
		{
			loggedIn = new Member();
			loggedIn.setPseudo("__notLogged__");
		}
		
		return loggedIn;
	}
	
	@RequestMapping(value = "/resources/members/changePassword", method = RequestMethod.GET)
	public boolean changeMemberPassword(@RequestBody String password)
	{
		if(password == null || password.length() < 6)
		{
			return false;
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Member loggedIn = memberService.getMemberFromAuthentification(auth);
		
		if(loggedIn == null)
			return false;
		
		memberService.changePasswordForMember(loggedIn, password);
		return true;
	}
	
	@RequestMapping(value="/resources/members/pseudo/validate")
	public ResponseEntity<String> pseudoExists(@RequestParam("pseudo") String pseudo)
	{
		if(pseudo == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		if(memberService.findByPseudo(pseudo) == null)
			return new ResponseEntity<>("", HttpStatus.OK);
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

	}
	
	@RequestMapping(value="/resources/members/email/validate")
	public ResponseEntity<String> mailExists( @RequestParam("email") String email)
	{
		
		if(email == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		if(memberService.findByEmail(email) == null)
			return new ResponseEntity<>("", HttpStatus.OK);
		return new ResponseEntity<String>("",HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
