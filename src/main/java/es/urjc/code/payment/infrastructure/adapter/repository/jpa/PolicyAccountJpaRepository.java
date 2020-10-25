package es.urjc.code.payment.infrastructure.adapter.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.urjc.code.payment.infrastructure.adapter.repository.entity.PolicyAccountEntity;
@Repository
public interface PolicyAccountJpaRepository extends JpaRepository<PolicyAccountEntity, UUID> {
	
	@Query("SELECT p FROM PolicyAccountEntity p LEFT JOIN FETCH p.entries where p.policyNumber=:policyNumber")
	Optional<PolicyAccountEntity> findByPolicyNumber(@Param("policyNumber") String policyNumber);
	
	@Query("SELECT p FROM PolicyAccountEntity p LEFT JOIN FETCH p.entries where p.policyAccountNumber=:policyAccountNumber")
	Optional<PolicyAccountEntity> findByPolicyAccountNumber(@Param("policyAccountNumber") String policyAccountNumber);

}
