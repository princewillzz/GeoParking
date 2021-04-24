import axios from "axios";

export const initiateBookingAndFetchPaymentOptions = async (
	checkAvailabilityForm
) => {
	return axios
		.post(
			"http://localhost:8301/user/initiate/payment",
			checkAvailabilityForm,
			{
				headers: {
					Authorization: "Bearer " + localStorage.getItem("token"),
				},
			}
		)
		.then((response) => {
			return {
				status: response.status,
				data: response.data,
			};
		})
		.catch((error) => {
			return { status: error.response.status, data: error.response.data };
		});
};

export const callbackOnSuccessfullPayment = async (paymentInfo) => {
	return axios
		.put("http://localhost:8301/user/payment/success", paymentInfo)
		.then((response) => response.status)
		.catch((error) => error.response.status);
};
