var options = {
    key: "", // Enter the Key ID generated from the Dashboard
    amount: "50000", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    currency: "INR",
    name: "Acme Corp",
    description: "Test Transaction",
    image: "https://example.com/your_logo",
    order_id: "", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
    handler: function (response) {
        handlePaymentSuccess(response);
    },
    prefill: {
        name: "Harsh Kumar",
        email: "harsh.kumar@example.com",
        contact: "9999999999",
    },
    notes: {
        address: "Razorpay Corporate Office",
    },
    theme: {
        color: "#3399cc",
    },
};

function payToBookParkingSlot() {
    // Api call
    const parkingData = getCheckAvailabilityFormData();
    const xhr = new XMLHttpRequest();
    const url = "/api/booking/book-slot";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log(this.response);
            const response = JSON.parse(this.response);
            // create the option json object to make it possible to pay using razorpay
            options.amount = response.applicationFee;
            options.order_id = response.razorpayOrderId;
            options.key = response.secretKey;
            var rzp1 = new Razorpay(options);

            rzp1.open();

            rzp1.on("payment.failed", function (response) {
                // alert(response.error.code);
                // alert(response.error.description);
                // alert(response.error.source);
                // alert(response.error.step);
                // alert(response.error.reason);
                // alert(response.error.metadata.order_id);
                // alert(response.error.metadata.payment_id);

                alert("Unable to make payment try again...");
            });

            // Initiaze payment
        }
    };

    xhr.send(JSON.stringify(parkingData));
    // End of API call
}

function handlePaymentSuccess(response) {
    const data = {
        payment_id: response.razorpay_payment_id,
        order_id: response.razorpay_order_id,
        signature: response.razorpay_signature,
    };

    const xhr = new XMLHttpRequest();
    const url = "/api/payment/success";
    xhr.open("PUT", url, true);
    xhr.setRequestHeader("content-Type", "Application/json");

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("Payment Done...");

            // On success api call
            // Making the payment button visible
            // const parkingCard = document.querySelectorAll(
            //     "#FeatchedParkingCard" + parkingData.parkingId
            // );

            // parkingCard.forEach(
            //     (parking) =>
            //         (parking.querySelector(
            //             ".checkBookingSlotAvailabilityBtn"
            //         ).hidden = true)
            // );
            // parkingCard.forEach(
            //     (parking) =>
            //         (parking.querySelector(".payForBookingSlotBtn").hidden = false)
            // );
            // Close the modal
            $("#parkingAvailabilityCheckModal .close").click();
        }
    };

    xhr.send(JSON.stringify(data));
}
