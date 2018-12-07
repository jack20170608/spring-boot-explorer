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

    public Account(Long id) {
        this.id = id;
    }

    public Account(Long id, String name, BigDecimal balance, LocalDate insertDt, LocalDateTime lastUpdateTs) {
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

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(LocalDate insertDt) {
        this.insertDt = insertDt;
    }

    public LocalDateTime getLastUpdateTs() {
        return lastUpdateTs;
    }

    public void setLastUpdateTs(LocalDateTime lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
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
}
