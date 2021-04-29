import { axiosInstance } from "./axios-config";

export const fetchBookingsOfParking = async (parkingId) => {
	return axiosInstance
		.get("/api/booking/admin/parking/" + parkingId)
		.then((response) => response.data);
};

export const fetchCustomerInfoForBooking = async (bookingId, customerId) => {
	return axiosInstance
		.get("/api/booking/admin/customer/info", {
			params: {
				customerId,
			},
		})
		.then((response) => response.data);
};
