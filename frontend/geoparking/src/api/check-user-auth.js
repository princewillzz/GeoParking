export const checkUserAuth = () => {
	const token = localStorage.getItem("token");

	if (!token) return false;

	const username = "harshawa";
	const role = "USER";

	return { username, role };
};
