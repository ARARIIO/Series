package com.example.jspdemo.controller;

import com.example.jspdemo.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService; // Assuming you have a service class for payment operations

    @PostMapping("/createPayment")
    public String createPayment(@RequestParam("amount") String amount, RedirectAttributes redirectAttributes) {
        try {
            Payment createdPayment = paymentService.createPayment(amount);
            List<Links> links = createdPayment.getLinks();
            for (Links link : links) {
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    String approvalLink = link.getHref();
                    // Execute the payment by redirecting the user to the approval link
                    return "redirect:" + approvalLink;
                }
            }
        } catch (PaymentException | PayPalRESTException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Ошибка при создании платежа");
            return "redirect:/viewAnimeList";
        }
        return "redirect:/viewAnimeList";
    }

}
