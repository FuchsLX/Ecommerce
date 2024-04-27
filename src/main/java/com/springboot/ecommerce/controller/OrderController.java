package com.springboot.ecommerce.controller;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.exception.EmptyUserMetaException;
import com.springboot.ecommerce.entities.cart.Cart;
import com.springboot.ecommerce.services.*;
import com.springboot.ecommerce.entities.order.Order;
import com.springboot.ecommerce.entities.transaction.Transaction;
import com.springboot.ecommerce.entities.transaction.TransactionType;
import com.springboot.ecommerce.utils.PaypalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

import static com.springboot.ecommerce.entities.transaction.TransactionType.*;
import static com.springboot.ecommerce.constants.PaymentUrl.*;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;
    private final UserMetaService userMetaService;
    private final PaypalService paypalService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);



    @GetMapping("/order/checkout/payment")
    public String orderProductsList(HttpSession session,
                                    @AuthenticationPrincipal UserDetails user,
                                    Model model){
        User currentUser = userService.findByEmail(user.getUsername());
        if (currentUser.getUserMeta() == null){
            throw new EmptyUserMetaException();
        } else {
            int countChangeInCartBeforeOrder = orderService.checkCartBeforeOrder(currentUser, session);
            if (countChangeInCartBeforeOrder != 0){
                return "redirect:/cart";
            }
            Cart activeCart = cartService.getActiveCartBySession(session);
//            List<TransactionMode> transactionModes = Arrays.asList(CASH_ON_DELIVERY, CHEQUE, WIRED, DRAFT);
            List<TransactionType> transactionTypes = Arrays.asList(CASH_ON_DELIVERY, CHECKOUT_VIA_PAYPAL);
            model.addAttribute("cart",activeCart);
            model.addAttribute("userMeta",userMetaService.getUserMetaByCurrentUser(currentUser.getId()));
            model.addAttribute("transaction", new Transaction());
            model.addAttribute("transactionTypes", transactionTypes);
//            model.addAttribute("transactionModes", transactionModes);
            return "order-payment";
        }
    }

    @PostMapping("/order/processing")
    public RedirectView processingOrder(HttpSession session,
                                        @ModelAttribute("transaction") Transaction transaction,
                                        @AuthenticationPrincipal UserDetails user){
            User currentUser = userService.findByEmail(user.getUsername());
            var order = orderService.processingNewOrder(transaction, currentUser, session);
            if (transaction.getType().equals(CHECKOUT_VIA_PAYPAL)) {
                try {
                    Payment payment = paypalService.createPayment(
                            order.getSubTotal().doubleValue(),
                            "USD",
                            "paypal",
                            "sale",
                            "Payment description",
                            PAYMENT_CANCEL_URL,
                            PAYMENT_SUCCESS_URL
                    );
                    for (Links links: payment.getLinks()) {
                        if (links.getRel().equals("approval_url")) {
                            return new RedirectView(links.getHref());
                        }
                    }
                } catch (PayPalRESTException ex) {
                    logger.error("Error occurred:: ", ex);
                }
            }
        return new RedirectView("/order/history");
    }

    @GetMapping("/order/buy-again/{orderId}")
    public String buyAgainOrder(@PathVariable("orderId") String orderId,
                                HttpSession session){
        orderService.buyAgainHandler(orderId, session);
        return "redirect:/cart";
    }


    @GetMapping("/order-management")
    public String getAllOrder(Model model){
        model.addAttribute("allOrderList", orderService.getAllOrder());
        return "management-order";
    }

    @GetMapping("/order-management/order-detail/{orderId}")
    public String getOrderDetail(
            @PathVariable("orderId") String orderId,
            Model model
    ){
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("transaction", order.getTransaction());
        model.addAttribute("order", order);
        return "order-detail";
    }


    @GetMapping("/order-management/update-delivered-order/{orderId}")
    public String updateDeliveredOrder(@PathVariable("orderId") String orderId,
                                       RedirectAttributes redirectAttributes){
        orderService.setDeliveredOrder(orderId);
        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/order-management/order-detail/{orderId}";
    }

    @GetMapping("/order/update-cancelled-order/{orderId}")
    public String updateCancelledOrder(@PathVariable("orderId") String orderId,
                                        RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal UserDetails user) {
        User currentUser = userService.findByEmail(user.getUsername());
        orderService.setCancelledOrder(orderId);
        if (currentUser.getRole().getName().equals(BootstrapRole.ADMIN.getName())){
            redirectAttributes.addAttribute("orderId", orderId);
            return "redirect:/order-management/order-detail/{orderId}";
        } else {
            return "redirect:/order/history";
        }

    }

    @GetMapping("/order-management/update-completed-order/{orderId}")
    public String updateCompletedOrder(@PathVariable("orderId") String orderId,
                                       RedirectAttributes redirectAttributes){
        orderService.setCompletedOrder(orderId);
        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/order-management/order-detail/{orderId}";
    }


    @GetMapping("/order/history")
    public String getAllOrderByCurrentUser(Model model,
                                           @AuthenticationPrincipal UserDetails user){
        User currentUser = userService.findByEmail(user.getUsername());
        model.addAttribute("processingOrderList",
                orderService.getProcessingOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("deliveredOrderList",
                orderService.getDeliveredOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("completedOrderList",
                orderService.getCompletedOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("cancelledOrderList",
                orderService.getCancelledOrderByCurrentUser(currentUser.getId()));
        model.addAttribute("allOrderList",
                orderService.getAllOrderByCurrentUser(currentUser.getId()));
        return "order-history";
    }
}
