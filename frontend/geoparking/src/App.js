import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import "./App.css";
import UserPrivateRoute from "./authentication/UserPrivateRoute";
import { ProvideAuth } from "./authentication/ProvideAuth";
import Home from "./pages/Home";
import Login from "./pages/Login";
import PrivateTest from "./pages/PrivateTest";
import PrimarySearchAppBar from "./component/appbar/PrimarySearchAppBar";
import MyAccount from "./pages/MyAccount";
import MyBookings from "./pages/MyBookings";

function App() {
	return (
		<>
			<ProvideAuth>
				<BrowserRouter>
					<>
						<PrimarySearchAppBar />
						<Switch>
							<Route exact path="/">
								<Home />
							</Route>
							<Route exact path="/login">
								<Login />
							</Route>
							<UserPrivateRoute path="/private">
								<PrivateTest />
							</UserPrivateRoute>
							<UserPrivateRoute exact path="/my-account">
								<MyAccount />
							</UserPrivateRoute>
							<UserPrivateRoute exact path="/my-bookings">
								<MyBookings />
							</UserPrivateRoute>
						</Switch>
					</>
				</BrowserRouter>
			</ProvideAuth>
		</>
	);
}

export default App;
