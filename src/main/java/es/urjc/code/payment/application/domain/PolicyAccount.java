package es.urjc.code.payment.application.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "policy_account", schema = "payment")
public class PolicyAccount {

    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(name = "policy_number")
    private String policyNumber;
    
    @Column(name = "policy_account_number")
    private String policyAccountNumber;
    
    @OneToMany(mappedBy = "policyAccount", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<AccountingEntry> entries = new HashSet<>();
    
    @Column(name = "created")
    private LocalDate created;
    
    @Column(name = "updated")
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
    
    public void addEntry(AccountingEntry entry) {
    	entries.add(entry);
        entry.setPolicyAccount(this);
    }
 
    public void removeEntry(AccountingEntry entry) {
    	entries.remove(entry);
        entry.setPolicyAccount(null);
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
    	addEntry(new ExpectedPayment.Builder().withCreationDate(LocalDate.now()).withEffectiveDate(dueDate).withAmount(amount).build());
    }

    public void inPayment(BigDecimal amount, LocalDate incomeDate) {
    	addEntry(new InPayment.Builder().withCreationDate(LocalDate.now()).withEffectiveDate(incomeDate).withAmount(amount).build());
    }

    public void outPayment(BigDecimal amount, LocalDate paymentReleaseDate) {
    	addEntry(new OutPayment.Builder().withCreationDate(LocalDate.now()).withEffectiveDate(paymentReleaseDate).withAmount(amount).build());
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
