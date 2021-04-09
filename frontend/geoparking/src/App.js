import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import "./App.css";
import AdminPrivateRoute from "./authentication/AdminPrivateRoute";
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

							<AdminPrivateRoute exact path="/admin">
								<AdminHome />
							</AdminPrivateRoute>

							<AdminPrivateRoute exact path="/admin/bookings">
								<AdminBookings />
							</AdminPrivateRoute>

							<AdminPrivateRoute exact path="/admin/my-account">
								<MyAccount />
							</AdminPrivateRoute>
						</Switch>
					</>
				</BrowserRouter>
			</ProvideAuth>
		</>
	);
}

export default App;
