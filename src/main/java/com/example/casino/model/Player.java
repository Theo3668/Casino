package com.example.casino.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long playerID;

    @Column(name = "userName")
    private String userName;

    @Column(name = "balance")
    private double balance;

    @Column(name = "promoUsage")
    private int promoUsage;

    @CreationTimestamp
    private Date createdDate;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    List<Transaction> transactions = new ArrayList<>();

    public Player(){

    }

    public Player(String userName, double balance, Date createdDate) {
        super();
        this.userName = userName;
        this.balance = balance;
        this.createdDate = createdDate;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setPromoUsage(int promoUsage) {
        this.promoUsage = promoUsage;
    }

    public int getPromoUsage() {
        return promoUsage;
    }
}
