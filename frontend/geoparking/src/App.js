import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import "./App.css";
import { ProvideAuth } from "./authentication/ProvideAuth";
import UserPrivateRoute from "./authentication/UserPrivateRoute";
import PrimarySearchAppBar from "./component/appbar/PrimarySearchAppBar";
import AdminBookings from "./pages/AdminBookings";
import AdminHome from "./pages/AdminHome";
import Home from "./pages/Home";
import Login from "./pages/Login";
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

							<UserPrivateRoute exact path="/my-account">
								<MyAccount />
							</UserPrivateRoute>
							<UserPrivateRoute exact path="/my-bookings">
								<MyBookings />
							</UserPrivateRoute>

							<Route exact path="/admin">
								<AdminHome />
							</Route>
							<Route exact path="/admin/bookings">
								<AdminBookings />
							</Route>
						</Switch>
					</>
				</BrowserRouter>
			</ProvideAuth>
		</>
	);
}

export default App;
