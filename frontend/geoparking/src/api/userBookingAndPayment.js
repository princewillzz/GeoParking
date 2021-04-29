import { axiosInstance } from "./axios-config";

export const initiateBookingAndFetchPaymentOptions = async (
	checkAvailabilityForm
) => {
	return axiosInstance
		.post("api/booking/user/initiate/payment", checkAvailabilityForm)
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
	return axiosInstance
		.put("/api/booking/user/payment/success", paymentInfo)
		.then((response) => response.status)
		.catch((error) => error.response.status);
};

export const fetchMyBookings = async () => {
	return axiosInstance
		.get("/api/booking/user/my")
		.then((response) => response)
		.catch((error) => error.response);
};

export const cancelMyBookings = async (bookingId) => {
	return axiosInstance
		.delete("/api/booking/user/booking/" + bookingId)
		.then((response) => response);
};
