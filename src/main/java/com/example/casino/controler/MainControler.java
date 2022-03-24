package com.example.casino.controler;

import com.example.casino.model.Player;
import com.example.casino.model.RequestBodyTransaction;
import com.example.casino.model.Transaction;
import com.example.casino.repository.TransactionType;
import com.example.casino.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainControler {

    @Autowired
    private MainService mainService;

    @PostMapping("/transactions/{playerID}")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestBody String password, @PathVariable Long playerID) {
        if (password == null && password.equals("Swordfish")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            Player player = mainService.getPlayerById(playerID);
            List<Transaction> transactions = player.getTransactions();
            transactions.sort((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()));

            if (transactions.size() < 10) {
                return ResponseEntity.ok().body(transactions);
            }


            return ResponseEntity.ok().body(transactions.subList(0, 10));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<Transaction>());
        }

    }

    //populate table with test data
    @PostMapping("/seed")
    public ResponseEntity<Boolean> populateTbl() throws Exception{

        Player player1 = new Player();
        player1.setUserName("MrJohnDoe");
        player1.setBalance(5000.92);
        mainService.updatePlayer(player1);

        Player player2 = new Player();
        player2.setUserName("MrsJaneDoe");
        player2.setBalance(0);
        mainService.updatePlayer(player2);

        return ResponseEntity.ok().body(true);
    }

    // For Testing
    @GetMapping("/player")
    public ResponseEntity<List<Player>> allPLayers() throws Exception{
        return ResponseEntity.ok().body(mainService.getAllPLayers());
    }


    @GetMapping("/balance/{id}")
    public ResponseEntity<Double> getBalance(@PathVariable long id) throws Exception{
        return ResponseEntity.ok(mainService.getPlayerById(id).getBalance());
    }

    // For Testing
    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> allTransaction() throws Exception {
        return ResponseEntity.ok().body(mainService.getAllTransactions());
    }

    @PostMapping("/wager")
    public ResponseEntity<String> newWager(@RequestBody RequestBodyTransaction requestBody) throws Exception {

        // Create new transaction with transaction Type as WAGER
        // todo: check for the requestbody for promo code - if found, set a promocode value as 4 and do not
        //  change the balance - In player


        try {

            Player player = mainService.getPlayerById(requestBody.getId());

            boolean isNewPromo = requestBody.getPromoCode() != null && requestBody.getPromoCode().equals("paper");
            boolean canUsePromo = player.getPromoUsage() > 0;
            if (!canUsePromo && !isNewPromo && player.getBalance() < requestBody.getAmount()) {
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No funds :(");
            }

            Transaction transaction = new Transaction();
            transaction.setAmount(requestBody.getAmount());
            transaction.setTransactionType(TransactionType.WAGER);

            if (isNewPromo || canUsePromo) {
                if (isNewPromo) {
                    player.setPromoUsage(4);

                } else {
                    player.setPromoUsage(player.getPromoUsage() - 1);
                }

            } else {

                player.setBalance(player.getBalance() - requestBody.getAmount());

            }

            transaction.setPlayer(player);
            mainService.createTransaction(transaction);
            List<Transaction> newTransactions = player.getTransactions();
            newTransactions.add(transaction);
            player.setTransactions(newTransactions);
            mainService.updatePlayer(player);

            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PostMapping("/win")
    public ResponseEntity<String> newWin(@RequestBody RequestBodyTransaction requestBody) {
        try {

            Player player = mainService.getPlayerById(requestBody.getId());

            Transaction transaction = new Transaction();
            transaction.setAmount(requestBody.getAmount());
            transaction.setTransactionType(TransactionType.WIN);

            player.setBalance(player.getBalance() + requestBody.getAmount());

            transaction.setPlayer(player);
            mainService.createTransaction(transaction);
            List<Transaction> newTransactions = player.getTransactions();
            newTransactions.add(transaction);
            player.setTransactions(newTransactions);
            mainService.updatePlayer(player);

            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}
