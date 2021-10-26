import axios from "axios";
import { axiosInstance } from "./axios-config";

// waking up all
export const wakeUpAllMicroservices = async () => {
	return axiosInstance.get("/api/awake/all").then(() => true);
};

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

	let uri = "https://geoparking-parking.herokuapp.com/internal/awake";

	if(new Date().getDate() >= 25) {
		uri = "https://parking-parking-service.herokuapp.com/internal/awake"
	}

	return axios
		.get(uri)
		.then(() => true)
		.catch(() => {
			throw new Error("Sleeping slothly");
		});
};

// waking profile service
export const wakeUpProfileService = async () => {
	let uri = "https://geoparking-profile.herokuapp.com/internal/awake";

	if(new Date().getDate() >= 25) {
		uri = "https://parking-profile-service.herokuapp.com/internal/awake"
	}

	return axios
		.get(uri)
		.then(() => true)
		.catch(() => {
			throw new Error("Sleeping slothly");
		});
};

// waking booking service
export const wakeUpBookingService = async () => {
	let uri = "https://geoparking-booking.herokuapp.com/internal/awake";

	if(new Date().getDate() >= 25) {
		uri = "https://parking-booking-service.herokuapp.com/internal/awake"
	}

	return axios
		.get(uri)
		.then(() => true)
		.catch(() => {
			throw new Error("Sleeping slothly");
		});
};
