import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import React, { useEffect, useState } from "react";
import Lottie from "react-lottie";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import * as animationData from "./28880-database-connection-transfer-data-on-remote-cloud-storage-server-rack.json";
import {
	wakeUpAllMicroservices,
	wakeUpApiGateway,
	wakeUpBookingService,
	wakeUpParkingService,
	wakeUpProfileService,
} from "./api/setupMicroservices";
import "./App.css";
import AdminPrivateRoute from "./authentication/AdminPrivateRoute";
import { ProvideAuth } from "./authentication/ProvideAuth";
import UserPrivateRoute from "./authentication/UserPrivateRoute";
import PrimarySearchAppBar from "./component/appbar/PrimarySearchAppBar";
import { ENV, ENVIRONMENTS } from "./config";
import AdminBookings from "./pages/AdminBookings";
import AdminHome from "./pages/AdminHome";
import Home from "./pages/Home";
import Login from "./pages/Login";
import MyAccount from "./pages/MyAccount";
import MyBookings from "./pages/MyBookings";

function App() {
	const defaultOptions = {
		loop: true,
		autoplay: true,
		animationData: animationData.default,
		rendererSettings: {
			preserveAspectRatio: "xMidYMid slice",
		},
	};

	const [isPreLoadingDone, setIsPreLoadingDone] = useState(false);

	useEffect(() => {
		// // API Gateway
		const tryWakeUpAllMicroservicesService = async () => {
			wakeUpAllMicroservices()
				.then(() => {
					console.log("App loaded");
					// setIsPreLoadingDone(true);
				})
				.catch(() => {
					console.log("App loading");
					setTimeout(() => {
						tryWakeUpAllMicroservicesService();
					}, 10000);
				});
		};

		let count = 0;
		let numberOfServers = 3;
		// API Gateway
		const tryWakeUpGatewayService = async () => {
			wakeUpApiGateway()
				.then(() => {
					count++;
					if (count >= numberOfServers) {
						setIsPreLoadingDone(true);
					}
				})
				.catch(() => {
					setTimeout(() => {
						if (count < 10) tryWakeUpGatewayService();
					}, 200);
				});
		};

		// parking service
		const tryWakeUpParkingService = async () => {
			wakeUpParkingService()
				.then(() => {
					count++;
					if (count >= numberOfServers) {
						setIsPreLoadingDone(true);
					}
				})
				.catch(() => {
					setTimeout(() => {
						if (count < 10) tryWakeUpParkingService();
					}, 200);
				});
		};

		// profile service
		const tryWakeUpProfileService = async () => {
			wakeUpProfileService()
				.then(() => {
					count++;
					if (count >= numberOfServers) {
						setIsPreLoadingDone(true);
					}
				})
				.catch(() => {
					setTimeout(() => {
						if (count < 10) tryWakeUpProfileService();
					}, 200);
				});
		};

		// booking service
		const tryWakeUpBookingService = async () => {
			wakeUpBookingService()
				.then(() => {
					count++;
					if (count >= numberOfServers) {
						setIsPreLoadingDone(true);
					}
				})
				.catch(() => {
					setTimeout(() => {
						if (count < 10) tryWakeUpBookingService();
					}, 200);
				});
		};

		if (ENV === ENVIRONMENTS.PRODUCTION) {
			tryWakeUpAllMicroservicesService();
			tryWakeUpGatewayService();
			tryWakeUpParkingService();
			tryWakeUpProfileService();
			tryWakeUpBookingService();

			setTimeout(() => {
				setIsPreLoadingDone(true);
			}, 2000);

			setTimeout(() => {
				count += 10;
			}, 60 * 1000);
		} else {
			setIsPreLoadingDone(true);
		}
	}, []);

	return (
		<>
			{!isPreLoadingDone ? (
				<div className="preloader-container">
					<Snackbar
						open={!isPreLoadingDone}
						anchorOrigin={{ vertical: "top", horizontal: "center" }}
					>
						<Alert severity="success">
							Starting the ideal microservices...Please wait!
						</Alert>
					</Snackbar>
					<Lottie
						options={defaultOptions}
						height={"100%"}
						width={"100%"}
					/>

					<div className="animationCreator">
						<a
							target="_blank"
							rel="noopener noreferrer"
							href="https://lottiefiles.com/BojanMitevskii"
						>
							Animation by Chris Gannon on LottieFiles
						</a>
					</div>
				</div>
			) : (
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

									<AdminPrivateRoute
										exact
										path="/admin/bookings"
									>
										<AdminBookings />
									</AdminPrivateRoute>

									<AdminPrivateRoute
										exact
										path="/admin/my-account"
									>
										<MyAccount />
									</AdminPrivateRoute>
								</Switch>
							</>
						</BrowserRouter>
					</ProvideAuth>
				</>
			)}
		</>
	);
}

export default App;
