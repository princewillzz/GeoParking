import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import "./App.css";
import UserPrivateRoute from "./authentication/UserPrivateRoute";
import { ProvideAuth } from "./authentication/ProvideAuth";
import Home from "./pages/Home";
import Login from "./pages/Login";
import PrivateTest from "./pages/PrivateTest";
import PrimarySearchAppBar from "./component/appbar/PrimarySearchAppBar";

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
						</Switch>
					</>
				</BrowserRouter>
			</ProvideAuth>
		</>
	);
}

export default App;
