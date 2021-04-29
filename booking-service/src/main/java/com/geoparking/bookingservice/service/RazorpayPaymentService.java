package com.geoparking.bookingservice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoparking.bookingservice.model.Booking;
import com.geoparking.bookingservice.model.BookingStatus;
import com.geoparking.bookingservice.model.Customer;
import com.geoparking.bookingservice.model.PaymentRazorpay;
import com.geoparking.bookingservice.model.RazorPayEntity;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("razorPayPaymentService")
public class RazorpayPaymentService {
    private final Logger logger = LoggerFactory.getLogger(RazorpayPaymentService.class);

    private RazorpayClient razorpayClient;

    private final String RAZORPAY_API_KEY = "rzp_test_oO5Nlz03qtxInq";
    private final String RAZORPAY_SECRET_KEY = "aDCCVMEUiVoDkZXhdpE8cA75";

    // Other services required by this service
    private final BookingService bookingService;

    @Autowired
    public RazorpayPaymentService(@Qualifier("bookingService") final BookingService bookingService) {
        this.bookingService = bookingService;
    }

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
        if (booking.isPaymentDone()) {
            return booking;
        }

        JSONObject options = new JSONObject();
        final double amountToPay = booking.getBill().getAmountToPay() * 100;
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
        razorPay.setCustomerName(customer.getFirstName() + customer.getLastName());
        razorPay.setCustomerEmail(customer.getEmail());
        razorPay.setMerchantName("GeoParking");
        razorPay.setPurchaseDescription("Book Slot");
        razorPay.setRazorpayOrderId(bookingDetail.getRazorpayOrderId());
        razorPay.setSecretKey(RAZORPAY_API_KEY);
        // razorPay.setImageURL("/logo")
        razorPay.setCustomerContact(customer.getMobile());

        razorPay.setTheme("#F37254");
        razorPay.setNotes("notes" + bookingDetail.getRazorpayOrderId());

        return razorPay;
    }

    /**
     * update booking model to persists details returned from razorpay on payment
     * completion
     * 
     * @param razorpayOrderId
     * @param razorpayPaymentId
     * @param razorpaySignature
     * @throws RazorpayException
     */
    @Transactional
    public void saveCredentialOnPaymentSuccess(String razorpayOrderId, String razorpayPaymentId,
            String razorpaySignature) throws RazorpayException {

        JSONObject options = new JSONObject();

        if (StringUtils.isNotBlank(razorpayPaymentId) && StringUtils.isNotBlank(razorpaySignature)
                && StringUtils.isNotBlank(razorpayOrderId)) {

            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_signature", razorpaySignature);

            boolean isEqual = Utils.verifyPaymentSignature(options, this.RAZORPAY_SECRET_KEY);

            if (isEqual) {
                final Booking booking = bookingService.getOrderWithRazorpayOrderId(razorpayOrderId);

                booking.setRazorpayOrderId(razorpayOrderId);
                booking.setRazorpayPaymentId(razorpayPaymentId);
                booking.setRazorpaySignature(razorpaySignature);

                booking.setPaymentDone(true);
                booking.setBookingStatus(BookingStatus.PENDING);
            }

        }

    }

    /**
     * Retrieve all payments from the razorpay api
     * 
     * @return List of PaymentRazorpay models
     * @throws RazorpayException
     */
    public List<PaymentRazorpay> getAllPayments() throws RazorpayException {
        List<Payment> payments = razorpayClient.Payments.fetchAll();
        if (payments == null) {

            throw new RuntimeException("No Payments found");
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        return payments.parallelStream().map(eachPayment -> {
            try {
                PaymentRazorpay payment = objectMapper.readValue(eachPayment.toString(), PaymentRazorpay.class);
                return payment;
            } catch (Exception e) {
                return null;
            }
        }).filter(item -> item != null).collect(Collectors.toList());
    }

    /**
     * Retrive a list of payments that are paid
     * 
     * @return list of PaymentRazorpay models
     * @throws RazorpayException
     */
    public List<PaymentRazorpay> getAllPaidPayments() throws RazorpayException {
        List<Payment> payments = razorpayClient.Payments.fetchAll();

        final ObjectMapper objectMapper = new ObjectMapper();
        return payments.parallelStream().map(eachPayment -> {
            try {
                PaymentRazorpay payment = objectMapper.readValue(eachPayment.toString(), PaymentRazorpay.class);
                return payment;
            } catch (Exception e) {
                return null;
            }
        }).filter(payment -> payment != null && payment.getCaptured()).collect(Collectors.toList());
    }

}