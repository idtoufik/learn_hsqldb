package fr.learn.member;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.learn.dao.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByPseudo(String pseudo);
	Member findByEmail(String email);
}
