import { Button, makeStyles } from "@material-ui/core";
import React, { useEffect } from "react";
import {
	callbackOnSuccessfullPayment,
	initiateBookingAndFetchPaymentOptions,
} from "../../api/userBookingAndPayment";
import { loadRazorPay } from "./loadRazorPay";

const useStyles = makeStyles((theme) => ({
	payToBookBtn: {
		color: "#fff",
		textTransform: "none",
		fontSize: 15,
		marginInline: 5,
		backgroundColor: "#28a745",
		"&:hover": {
			backgroundColor: "green",
		},
	},
}));

function PayByRazorPay({
	checkAvailabilityForm,
	handleCloseBookSlotModal,
	handleErrorMessageAlert,
	setIsLoading,
}) {
	const classes = useStyles();

	const handlePaymentSuccess = ({
		razorpay_payment_id,
		razorpay_order_id,
		razorpay_signature,
	}) => {
		console.log(razorpay_signature, razorpay_order_id, razorpay_payment_id);

		setIsLoading(true);
		callbackOnSuccessfullPayment({
			razorpay_payment_id,
			razorpay_order_id,
			razorpay_signature,
		})
			.then((status) => {
				if (status === 200) {
					handleCloseBookSlotModal();
				} else {
					handleErrorMessageAlert("Something went wrong!");
				}
			})
			.catch((error) => {
				handleErrorMessageAlert("Payment Pending... Check Later!");
			})
			.finally(() => {
				setTimeout(() => {
					setIsLoading(false);
				}, 500);
			});
	};

	const options = {
		key: "",
		amount: "", //  = INR 1
		name: "",
		description: "",
		order_id: "",
		// image: "https://cdn.razorpay.com/logos/7K3b6d18wHwKzL_medium.png",
		handler: handlePaymentSuccess,
		prefill: {
			name: "",
			contact: "",
			email: "",
		},
		notes: {
			address: "",
		},
		theme: {
			color: "blue",
			hide_topbar: false,
		},
	};

	const openPayModal = () => {
		let rzp1 = new window.Razorpay(options);
		rzp1.on("payment.failed", function (response) {
			console.log(response.error.code);
			console.log(response.error.description);
			console.log(response.error.source);
			console.log(response.error.step);
			console.log(response.error.reason);
			console.log(response.error.metadata.order_id);
			console.log(response.error.metadata.payment_id);

			handleErrorMessageAlert("Payment failed...!");
		});

		rzp1.open();
	};

	const initiatePayToBook = () => {
		// console.log(checkAvailabilityForm);
		setIsLoading(true);

		initiateBookingAndFetchPaymentOptions(checkAvailabilityForm)
			.then(({ status, data }) => {
				if (status === 200) {
					options.key = data.secretKey;
					options.order_id = data.razorpayOrderId;
					options.amount = data.applicationFee;
					options.name = data.merchantName;
					options.prefill.name = data.customerName;
					options.prefill.contact = data.customerContact;
					options.prefill.email = data.customerEmail;
					options.description = data.purchaseDescription;
					options.notes = data.notes.substr(15);

					openPayModal();
					handleErrorMessageAlert();
				} else if (status === 417) {
					handleErrorMessageAlert("Slot Unavailable...!");
				} else if (status === 400) {
					handleErrorMessageAlert("Unable to book slot", data);
				}
			})
			.catch((error) => {
				handleErrorMessageAlert("Check your connection!");
			})
			.finally(() => {
				setTimeout(() => {
					setIsLoading(false);
				}, 500);
			});

		// .catch((error) => {
		// 	console.log(error.response.data);
		// 	console.log(error.response.status);
		// 	console.log(error.response.headers);
		// });
	};

	useEffect(() => {
		const loadRZPay = async () => {
			const res = await loadRazorPay(
				"https://checkout.razorpay.com/v1/checkout.js"
			);
			if (!res) {
				handleErrorMessageAlert(
					"Razorpay unable to load. Are you online?"
				);
			}
		};
		loadRZPay();
	}, [handleErrorMessageAlert]);

	return (
		<Button
			className={classes.payToBookBtn}
			color="primary"
			variant="contained"
			onClick={initiatePayToBook}
		>
			Pay to Book
		</Button>
	);
}

export default PayByRazorPay;
