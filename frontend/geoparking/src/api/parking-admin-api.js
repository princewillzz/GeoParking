import { axiosInstance } from "./axios-config";

export const fetchAdminParkings = async (active) => {
	// const response = await axiosInstance.get()

	return axiosInstance
		.get("/api/parking-service/admin/my-parkings", {
			params: {
				active,
			},
		})
		.then((response) => response.data);
};

export const createParking = async (parkingData) => {
	return axiosInstance
		.post("/api/parking-service/admin/parking", parkingData)
		.then((response) => response.data);
};

export const updateParking = async (parkingData) => {
	return axiosInstance
		.put("/api/parking-service/admin/parking", parkingData)
		.then((response) => response.data);
};

export const deleteParking = async (parkingId) => {
	axiosInstance.delete("/api/parking-service/admin/parking/" + parkingId);
};
