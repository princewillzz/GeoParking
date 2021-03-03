package xyz.willz.geoparking.service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import xyz.willz.geoparking.dto.PaymentDTORazorpay;
import xyz.willz.geoparking.model.Booking;
import xyz.willz.geoparking.model.Customer;
import xyz.willz.geoparking.model.RazorPayEntity;

@Service
@Qualifier("razorPayPaymentService")
@RequiredArgsConstructor
public class RazorpayPaymentService {
    private final Logger logger = LoggerFactory.getLogger(RazorpayPaymentService.class);

    private RazorpayClient razorpayClient;

    private final String RAZORPAY_API_KEY = "rzp_test_oO5Nlz03qtxInq";
    private final String RAZORPAY_SECRET_KEY = "aDCCVMEUiVoDkZXhdpE8cA75";

    // Other services required by this service
    private final BookingService bookingService;


    @PostConstruct
    void postConstruct() {
        try {
            this.razorpayClient = new RazorpayClient(this.RAZORPAY_API_KEY, this.RAZORPAY_SECRET_KEY);
        } catch (RazorpayException e) {
            logger.error(e.getMessage());

        }
    }

    // Form to make payment using razorpay. Get total amount from the database and
    // then make a request to create a order from the from the razorpay server
    @Transactional
    public Booking createRazorPayOrder(final Booking booking) throws RazorpayException {

        

        // If payment already done
        // if (booking.isPaymentDone()) {
        //     return booking;
        // }

        JSONObject options = new JSONObject();
        Double amountToPay = booking.getBill().getAmountToPay() * 100;
        options.put("amount", String.format("%.0f", amountToPay));
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1);

        final Order rzOrder = razorpayClient.Orders.create(options);

        booking.setRazorpayOrderId(rzOrder.get("id"));

        return booking;
    }

    public RazorPayEntity getRazorPayEntityForNewOrder(final Booking bookingDetail, final Customer customer) {

        RazorPayEntity razorPay = new RazorPayEntity();

        razorPay.setApplicationFee(String.valueOf(bookingDetail.getBill().getAmountToPay() * 100));
        razorPay.setCustomerName("customer");
        razorPay.setCustomerEmail("harsh@gmail.com");
        razorPay.setMerchantName("Merchant Name");
        razorPay.setPurchaseDescription("TEST PURCHASES");
        razorPay.setRazorpayOrderId(bookingDetail.getRazorpayOrderId());
        razorPay.setSecretKey(RAZORPAY_API_KEY);
        // razorPay.setImageURL("/logo")
        razorPay.setCustomerContact("9879879879");

        razorPay.setTheme("#F37254");
        razorPay.setNotes("notes" + bookingDetail.getRazorpayOrderId());

        return razorPay;
    }

    @Transactional
    public void saveCredentialOnPaymentSuccess(String razorpayOrderId, String razorpayPaymentId,
            String razorpaySignature) {

        final Booking booking = bookingService.getOrderWithRazorpayOrderId(razorpayOrderId);

        booking.setRazorpayOrderId(razorpayOrderId);
        booking.setRazorpayPaymentId(razorpayPaymentId);
        booking.setRazorpaySignature(razorpaySignature);
        booking.setPaymentDone(true);

    }

    public List<PaymentDTORazorpay> getAllPayments() throws RazorpayException {
        List<Payment> payments = razorpayClient.Payments.fetchAll();
        if (payments == null) {
            throw new RuntimeException("No Payments found");
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        return payments.parallelStream().map(eachPayment -> {
            try {
                PaymentDTORazorpay payment = objectMapper.readValue(eachPayment.toString(), PaymentDTORazorpay.class);
                return payment;
            } catch (Exception e) {
                return null;
            }
        }).filter(item -> item != null).collect(Collectors.toList());
    }

    public List<PaymentDTORazorpay> getAllPaidPayments() throws RazorpayException {
        List<Payment> payments = razorpayClient.Payments.fetchAll();

        final ObjectMapper objectMapper = new ObjectMapper();
        return payments.parallelStream().map(eachPayment -> {
            try {
                PaymentDTORazorpay payment = objectMapper.readValue(eachPayment.toString(), PaymentDTORazorpay.class);
                return payment;
            } catch (Exception e) {
                return null;
            }
        }).filter(payment -> payment != null && payment.getCaptured()).collect(Collectors.toList());
    }

}
