package es.urjc.code.payment.infrastructure.adapter.repository.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
public class PolicyAccountEntity {

    @Id
    @GeneratedValue
    protected UUID id;
    
    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "policy_account_number")
    private String policyAccountNumber;

    @OneToMany(mappedBy = "policyAccount", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<AccountingEntryEntity> entries = new HashSet<>();
    
    @Column(name = "created")
    private LocalDate created;
    
    @Column(name = "updated")
    private LocalDate updated;
    
    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyAccountNumber() {
        return policyAccountNumber;
    }

    public Set<AccountingEntryEntity> getEntries() {
        return entries;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void addEntry(AccountingEntryEntity entry) {
    	entries.add(entry);
        entry.setPolicyAccount(this);
    }
 
    public void removeEntry(AccountingEntryEntity entry) {
    	entries.remove(entry);
        entry.setPolicyAccount(null);
    }    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PolicyAccountEntity)) return false;

        PolicyAccountEntity that = (PolicyAccountEntity) o;

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

        private final PolicyAccountEntity object;

        public Builder() {
            object = new PolicyAccountEntity();
        }

        public Builder withPolicyNumber(String value) {
            object.policyNumber = value;
            return this;
        }

        public Builder withPolicyAccountNumber(String value) {
            object.policyAccountNumber = value;
            return this;
        }

        public Builder withEntries(Set<AccountingEntryEntity> value) {
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

        public PolicyAccountEntity build() {
            return object;
        }

    }    
}
