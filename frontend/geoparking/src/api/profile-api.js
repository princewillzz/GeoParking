import { axiosInstance } from "./axios-config";

export const fetchProfileInfo = async () => {
	const token = localStorage.getItem("token");

	return axiosInstance
		.get("/auth/profile", {
			headers: {
				Authorization: "Bearer " + token,
			},
		})
		.then((response) => response.data);
};

export const updateBasicProfileInfo = async (profile) => {
	const token = localStorage.getItem("token");

	return axiosInstance
		.put("/auth/profile", profile, {
			headers: {
				Authorization: "Bearer " + token,
			},
		})
		.then((response) => response.data);
};
