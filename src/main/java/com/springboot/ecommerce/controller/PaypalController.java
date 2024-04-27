package com.springboot.ecommerce.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.ecommerce.utils.PaypalService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class PaypalController {

    private final PaypalService paypalService;
    private static final Logger logger = LoggerFactory.getLogger(PaypalController.class);
//
//    @PostMapping("/payment/create")
//    public RedirectView createPayment(@RequestBody ) {
//        try {
//            String cancelUrl = "";
//            String successUrl = "";
//            Payment payment = paypalService.createPayment(
//                    10.0,
//                    "USD",
//                    "paypal",
//                    "sale",
//                    "Payment description",
//                    cancelUrl,
//                    successUrl
//            );
//            for (Links links: payment.getLinks()) {
//                if (links.getRel().equals("approval_url")) {
//                    return new RedirectView(links.getHref());
//                }
//            }
//        } catch (PayPalRESTException ex) {
//            logger.error("Error occurred:: ", ex);
//        }
//        return new RedirectView("/payment/error");
//    }

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId,
                                 @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) return "payment-success";
        } catch (PayPalRESTException ex) {
            logger.error("Error occurred:: ", ex);
        }
        return "payment-success";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "payment-error";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "payment-cancel";
    }
}
