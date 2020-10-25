package es.urjc.code.payment.application.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public abstract class AccountingEntry {

    protected UUID id;
    protected PolicyAccount policyAccount;
    protected LocalDate creationDate;
    protected LocalDate effectiveDate;
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
