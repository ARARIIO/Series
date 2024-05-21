package com.example.jspdemo.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private String clientId = "AabTx5Of58eV_fMwsukS5RZBmKTSm79rUWNvQr-uBRbxs52ThBjpwk549usyWF68s_9ikBrDKDZ1RhlZ";

    private String clientSecret = "EMrnIL9NDEeSnPHZBOEZ9f-cbTTBo0bKbr22xBKpRZzQAzM9PmNRPST931tIP7Px3XGGsiemJODkKBTz";

    public Payment createPayment(String amount) throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");

        Amount paymentAmount = new Amount();
        paymentAmount.setCurrency("USD");
        paymentAmount.setTotal(amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(paymentAmount);
        transaction.setDescription("Payment description");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8091/viewAnimeList");
        redirectUrls.setReturnUrl("http://localhost:8091/viewAnimeList");
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

}
