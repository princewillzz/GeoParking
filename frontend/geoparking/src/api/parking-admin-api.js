import { axiosInstance } from "./axios-config";

export const fetchAdminParkings = async (active) => {
	// const response = await axiosInstance.get()

	const token = localStorage.getItem("token");
	console.log("admin's parkings", token);
	const response = await axiosInstance.get(
		"/api/parking-service/admin/my-parkings",
		{
			params: {
				active,
			},
			headers: {
				Authorization: "Bearer " + token,
			},
		}
	);

	return response.data;
};

export const createParking = async (parkingData) => {
	const token = localStorage.getItem("token");

	return axiosInstance
		.post("/api/parking-service/admin/parking", parkingData, {
			headers: {
				Authorization: "Bearer " + token,
			},
		})
		.then((response) => response.data);
};

export const updateParking = async (parkingData) => {
	const token = localStorage.getItem("token");

	return axiosInstance
		.put("/api/parking-service/admin/parking", parkingData, {
			headers: {
				Authorization: "Bearer " + token,
			},
		})
		.then((response) => response.data);
};

export const deleteParking = async (parkingId) => {
	const token = localStorage.getItem("token");

	axiosInstance.delete("/api/parking-service/admin/parking/" + parkingId, {
		headers: {
			Authorization: "Bearer " + token,
		},
	});
};
