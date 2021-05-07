import axios from "axios";
import { axiosInstance } from "./axios-config";

// waking up gateway
export const wakeUpApiGateway = async () => {
	return axiosInstance
		.get("/api/awake")
		.then(() => true)
		.catch(() => {
			throw new Error("Sleeping slothly");
		});
};

// waking parknig service
export const wakeUpParkingService = async () => {
	return axios
		.get("https://geoparking-parking.herokuapp.com/internal/awake")
		.then(() => true)
		.catch(() => {
			throw new Error("Sleeping slothly");
		});
};

// waking profile service
export const wakeUpProfileService = async () => {
	return axios
		.get("https://geoparking-profile.herokuapp.com/internal/awake")
		.then(() => true)
		.catch(() => {
			throw new Error("Sleeping slothly");
		});
};

// waking booking service
export const wakeUpBookingService = async () => {
	return axios
		.get("https://geoparking-booking.herokuapp.com/internal/awake")
		.then(() => true)
		.catch(() => {
			throw new Error("Sleeping slothly");
		});
};
