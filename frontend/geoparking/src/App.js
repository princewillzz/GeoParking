import React, { useEffect, useState } from "react";
import Lottie from "react-lottie";
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

import * as animationData from "./28880-database-connection-transfer-data-on-remote-cloud-storage-server-rack.json";
import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";

import {
	wakeUpApiGateway,
	wakeUpProfileService,
	wakeUpBookingService,
	wakeUpParkingService,
	wakeUpAllMicroservices,
} from "./api/setupMicroservices";

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
					console.log("Done setting useeffect");
					setIsPreLoadingDone(true);
				})
				.catch(() => {
					console.log("In error useeffec");
					setTimeout(() => {
						tryWakeUpAllMicroservicesService();
					}, 200);
				});
		};

		tryWakeUpAllMicroservicesService();

		// let count = 0;
		// let numberOfServers = 4;
		// // API Gateway
		// const tryWakeUpGatewayService = async () => {
		// 	wakeUpApiGateway()
		// 		.then(() => {
		// 			count++;
		// 			if (count >= numberOfServers) {
		// 				console.log(count, "Done setting useeffect");
		// 				setIsPreLoadingDone(true);
		// 			}
		// 		})
		// 		.catch(() => {
		// 			console.log("In error useeffec");
		// 			setTimeout(() => {
		// 				tryWakeUpGatewayService();
		// 			}, 200);
		// 		});
		// };

		// // parking service
		// const tryWakeUpParkingService = async () => {
		// 	wakeUpParkingService()
		// 		.then(() => {
		// 			count++;
		// 			if (count >= numberOfServers) {
		// 				console.log(count, "Done setting useeffect");
		// 				setIsPreLoadingDone(true);
		// 			}
		// 		})
		// 		.catch(() => {
		// 			console.log("In error useeffec");
		// 			setTimeout(() => {
		// 				tryWakeUpParkingService();
		// 			}, 200);
		// 		});
		// };

		// // profile service
		// const tryWakeUpProfileService = async () => {
		// 	wakeUpProfileService()
		// 		.then(() => {
		// 			count++;
		// 			if (count >= numberOfServers) {
		// 				console.log(count, "Done setting useeffect");
		// 				setIsPreLoadingDone(true);
		// 			}
		// 		})
		// 		.catch(() => {
		// 			console.log("In error useeffec");
		// 			setTimeout(() => {
		// 				tryWakeUpProfileService();
		// 			}, 200);
		// 		});
		// };

		// // booking service
		// const tryWakeUpBookingService = async () => {
		// 	wakeUpBookingService()
		// 		.then(() => {
		// 			count++;
		// 			if (count >= numberOfServers) {
		// 				console.log(count, "Done setting useeffect");
		// 				setIsPreLoadingDone(true);
		// 			}
		// 		})
		// 		.catch(() => {
		// 			console.log("In error useeffec");
		// 			setTimeout(() => {
		// 				tryWakeUpBookingService();
		// 			}, 200);
		// 		});
		// };

		// tryWakeUpGatewayService();
		// tryWakeUpParkingService();
		// tryWakeUpProfileService();
		// tryWakeUpBookingService();
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
						style={
							{
								// backgroundColor
							}
						}
					/>
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
