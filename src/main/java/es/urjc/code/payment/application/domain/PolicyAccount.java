package es.urjc.code.payment.application.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class PolicyAccount {

    private UUID id;
    private String policyNumber;
    private String policyAccountNumber;
    private Set<AccountingEntry> entries = new HashSet<>();
    private LocalDate created;
    private LocalDate updated;
    
    public UUID getId() {
        return id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyAccountNumber() {
        return policyAccountNumber;
    }

    public Set<AccountingEntry> getEntries() {
		return entries;
	}

	public LocalDate getCreated() {
        return created;
    }

    public LocalDate getUpdated() {
        return updated;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyAccount)) return false;

        PolicyAccount that = (PolicyAccount) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(policyNumber, that.policyNumber)
                .append(policyAccountNumber, that.policyAccountNumber)
                .append(entries, that.entries)
                .append(created, that.created)
                .append(updated, that.updated)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(policyNumber)
                .append(policyAccountNumber)
                .append(entries)
                .append(created)
                .append(updated)
                .toHashCode();
    }    

    public static final class Builder {

        private final PolicyAccount object;

        public Builder() {
            object = new PolicyAccount();
        }

        public Builder withId(UUID value) {
            object.id = value;
            return this;
        }

        public Builder withPolicyNumber(String value) {
            object.policyNumber = value;
            return this;
        }

        public Builder withPolicyAccountNumber(String value) {
            object.policyAccountNumber = value;
            return this;
        }
        
        public Builder withEntries(Set<AccountingEntry> value) {
        	object.entries = value;
        	return this;
        }

        public Builder withCreated(LocalDate value) {
            object.created = value;
            return this;
        }

        public Builder withUpdated(LocalDate value) {
            object.updated = value;
            return this;
        }

        public PolicyAccount build() {
            return object;
        }

    }

    public void expectedPayment(BigDecimal amount, LocalDate dueDate) {
        entries.add(new ExpectedPayment.Builder().withPolicyAccount(this).withCreationDate(LocalDate.now()).withEffectiveDate(dueDate).withAmount(amount).build());
    }

    public void inPayment(BigDecimal amount, LocalDate incomeDate) {
        entries.add(new InPayment.Builder().withPolicyAccount(this).withCreationDate(LocalDate.now()).withEffectiveDate(incomeDate).withAmount(amount).build());
    }

    public void outPayment(BigDecimal amount, LocalDate paymentReleaseDate) {
        entries.add(new OutPayment.Builder().withPolicyAccount(this).withCreationDate(LocalDate.now()).withEffectiveDate(paymentReleaseDate).withAmount(amount).build());
    }

    public BigDecimal balanceAt(LocalDate effectiveDate) {
        List<AccountingEntry> effectiveEntries = entries.stream()
                .sorted(Comparator.comparing(AccountingEntry::getCreationDate))
                .filter(e -> e.isEffectiveOn(effectiveDate))
                .collect(Collectors.toList());

        BigDecimal balance = BigDecimal.ZERO;
        for (AccountingEntry entry : effectiveEntries) {
            balance = entry.apply(balance);
        }

        return balance;
    }
}
