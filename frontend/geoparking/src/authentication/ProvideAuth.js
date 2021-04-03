import React, { createContext, useContext } from "react";
import useProvideAuth from "./useProvideAuth";

const authContext = createContext();

export function ProvideAuth({ children }) {
	const auth = useProvideAuth();

	return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

export const useAuth = () => {
	return useContext(authContext);
};
