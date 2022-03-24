package com.example.casino.model;

import com.example.casino.repository.TransactionType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionID;

    @CreationTimestamp
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "player")
    private Player player;

    public  Transaction(){

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType.valueOf(String.valueOf(transactionType));
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
