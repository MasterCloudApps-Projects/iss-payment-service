package es.urjc.code.payment.application.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "AccountingEntry")
@Table(name = "accounting_entry", schema = "payment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,name = "entry_type")
public abstract class AccountingEntry {

    @Id
    @GeneratedValue
    protected UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_account_id")
    protected PolicyAccount policyAccount;
    
    @Column(name = "creation_date")
    protected LocalDate creationDate;
    
    @Column(name = "effective_date")
    protected LocalDate effectiveDate;
    
    @Column(name = "amount")
    protected BigDecimal amount;
    
    public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public PolicyAccount getPolicyAccount() {
		return policyAccount;
	}

	public void setPolicyAccount(PolicyAccount policyAccount) {
		this.policyAccount = policyAccount;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public abstract BigDecimal apply(BigDecimal state);

    boolean isEffectiveOn(LocalDate theDate) {
        return this.effectiveDate.isBefore(theDate) || this.effectiveDate.equals(theDate);
    }
}
