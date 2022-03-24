package com.example.casino.service;

import com.example.casino.model.Player;
import com.example.casino.model.Transaction;
import com.example.casino.repository.PlayerRepository;
import com.example.casino.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MainService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Transaction createTransaction(Transaction transaction) {

        Transaction t =  transactionRepository.save(transaction);
        return t;

    }

    public List<Player> getAllPLayers() {
        return this.playerRepository.findAll();
    }

    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }

    public Player getPlayerById(long playerID) throws Exception {
        Optional<Player> playerDB = this.playerRepository.findById(playerID);

        if(playerDB.isPresent()) {
            return playerDB.get();
        }else {
            throw new Exception("Record not found with id : " + playerID);
        }
    }


}
