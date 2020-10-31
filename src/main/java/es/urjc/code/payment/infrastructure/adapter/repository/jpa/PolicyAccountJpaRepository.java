package es.urjc.code.payment.infrastructure.adapter.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.urjc.code.payment.application.domain.PolicyAccount;
@Repository
public interface PolicyAccountJpaRepository extends JpaRepository<PolicyAccount, UUID> {
	
	@Query("SELECT p FROM PolicyAccount p LEFT JOIN FETCH p.entries where p.policyNumber=:policyNumber")
	Optional<PolicyAccount> findByPolicyNumber(@Param("policyNumber") String policyNumber);
	
	@Query("SELECT p FROM PolicyAccount p LEFT JOIN FETCH p.entries where p.policyAccountNumber=:policyAccountNumber")
	Optional<PolicyAccount> findByPolicyAccountNumber(@Param("policyAccountNumber") String policyAccountNumber);

}
