import { axiosInstance } from "./axios-config";

export const fetchParkingsWithAddress = async (address) => {
	const response = await axiosInstance.get(
		"/api/parking-service/parking/search",
		{
			params: {
				address,
			},
		}
	);
	return response.data;
};

export const fetchFeaturedParkings = async () => {
	const response = await axiosInstance.get(
		"/api/parking-service/parking/featured"
	);

	return response.data;
};
