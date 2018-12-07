package com.github.fangming.springboot.jdbc.model;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Account {

    private Long id;
    private String name;
    private BigDecimal balance;
    private LocalDate insertDt;
    private LocalDateTime lastUpdateTs;

    private Account(Long id, String name, BigDecimal balance, LocalDate insertDt, LocalDateTime lastUpdateTs) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.insertDt = insertDt;
        this.lastUpdateTs = lastUpdateTs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDate getInsertDt() {
        return insertDt;
    }

    public LocalDateTime getLastUpdateTs() {
        return lastUpdateTs;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static Builder builder(Account source){
        Builder builder = new Builder();
        builder.setId(source.getId())
            .setName(source.getName())
            .setBalance(source.getBalance())
            .setInsertDt(source.getInsertDt())
            .setLastUpdateTs(source.getLastUpdateTs());
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
            Objects.equals(name, account.name) &&
            Objects.equals(balance, account.balance) &&
            Objects.equals(insertDt, account.insertDt) &&
            Objects.equals(lastUpdateTs, account.lastUpdateTs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance, insertDt, lastUpdateTs);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("name", name)
            .add("balance", balance)
            .add("insertDt", insertDt)
            .add("lastUpdateTs", lastUpdateTs)
            .toString();
    }

    public static class Builder {
        private Long id;
        private String name;
        private BigDecimal balance;
        private LocalDate insertDt;
        private LocalDateTime lastUpdateTs;

        private Builder(){
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder setInsertDt(LocalDate insertDt) {
            this.insertDt = insertDt;
            return this;
        }

        public Builder setLastUpdateTs(LocalDateTime lastUpdateTs) {
            this.lastUpdateTs = lastUpdateTs;
            return this;
        }

        public Account build(){
            return new Account(id, name, balance, insertDt, lastUpdateTs);
        }
    }
}
