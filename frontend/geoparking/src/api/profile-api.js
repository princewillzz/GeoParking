import { axiosInstance } from "./axios-config";

export const fetchProfileInfo = async () => {
	return axiosInstance.get("/auth/profile").then((response) => response.data);
};

export const updateBasicProfileInfo = async (profile) => {
	return axiosInstance
		.put("/auth/profile", profile)
		.then((response) => response.data);
};

export const registerProfile = async (profile, callback) => {
	axiosInstance.post("/auth/register", profile).then(callback);
};
