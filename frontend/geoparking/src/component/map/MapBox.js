// import "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions.css";
// import { makeStyles } from "@material-ui/core";
// import React, { useEffect } from "react";
// import "./mapbox.css";
// const mapboxGl = require("mapbox-gl");
// const MapboxDirections = require("@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions");

// mapboxGl.accessToken =
// 	"pk.eyJ1IjoicHJpbmNld2lsbHoiLCJhIjoiY2tvMzFqMWFpMG8yNTJ3czU4NWVjcG5kdyJ9.F20bN0FJ8byK6BxdoBFOfA";

// const useStyles = makeStyles((theme) => ({
// 	root: {
// 		height: "70vh",
// 		marginBottom: "1rem",
// 	},
// }));
// let map;
// function MapBox({ parkings, handleOpenBookSlotModal }) {
// 	const classes = useStyles();

// 	const loadMarkers = () => {
// 		console.log("loadmarkers", parkings);

// 		const parkingMarker = [];

// 		parkings.forEach((parking, index) => {
// 			parkingMarker.push({
// 				type: "Feature",
// 				properties: {
// 					description: `<strong>${parking.name}</strong>
// 					<p>Make it Mount Pleasant is a handmade and vintage market and afternoon of live entertainment and kids activities. 12:00-6:00 p.m.</p>
// 					<button name="${index}" class="mapboxPopupBtn" id="mapboxPopupBtn" value="${parking.id}" >book</button>`,
// 				},
// 				geometry: {
// 					type: "Point",
// 					coordinates: [87.5 + Math.random(), 22 + Math.random()],
// 				},
// 			});
// 		});

// 		if (!map.getSource("places")) {
// 			console.log("loading fresh");

// 			map.addSource("places", {
// 				type: "geojson",
// 				data: {
// 					type: "FeatureCollection",
// 					features: parkingMarker,
// 				},
// 			});
// 			map.addLayer({
// 				id: "places",
// 				type: "circle",
// 				source: "places",
// 				paint: {
// 					"circle-color": "#4264fb",
// 					"circle-radius": 8,
// 					"circle-stroke-width": 2,
// 					"circle-stroke-color": "#ffffff",
// 				},
// 			});
// 		} else {
// 			console.log("already exists");
// 			map.getSource("places").setData({
// 				type: "FeatureCollection",
// 				features: parkingMarker,
// 			});
// 		}

// 		// Add a layer showing the places.

// 		// Create a popup, but don't add it to the map yet.
// 		var popup = new mapboxGl.Popup({
// 			closeButton: true,
// 			closeOnClick: false,
// 		});

// 		map.on("mouseenter", "places", function (e) {
// 			// Change the cursor style as a UI indicator.
// 			map.getCanvas().style.cursor = "pointer";

// 			var coordinates = e.features[0].geometry.coordinates.slice();
// 			var description = e.features[0].properties.description;

// 			// Ensure that if the map is zoomed out such that multiple
// 			// copies of the feature are visible, the popup appears
// 			// over the copy being pointed to.
// 			while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
// 				coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
// 			}

// 			// Populate the popup and set its coordinates
// 			// based on the feature found.
// 			popup.setLngLat(coordinates).setHTML(description).addTo(map);

// 			document
// 				.querySelector("#mapboxPopupBtn")
// 				.addEventListener("click", function () {
// 					console.log("Book slot", this);
// 					handleOpenBookSlotModal(this.value);
// 				});
// 		});

// 		// map.on("mouseleave", "places", function () {
// 		// 	map.getCanvas().style.cursor = "";
// 		// 	popup.remove();
// 		// });
// 	};

// 	const setupMap = (center) => {
// 		console.log("map setup", parkings);
// 		const directions = new MapboxDirections({
// 			accessToken: mapboxGl.accessToken,
// 		});

// 		if (!map) {
// 			map = new mapboxGl.Map({
// 				container: "mapboxContainer",
// 				style: "mapbox://styles/mapbox/streets-v11",
// 				center: center,
// 				zoom: 8,
// 			});
// 			const nav = new mapboxGl.NavigationControl();
// 			map.addControl(nav);
// 			map.addControl(directions, "top-left");
// 		}

// 		new mapboxGl.Marker({
// 			color: "blue",
// 			draggable: true,
// 		})
// 			.setLngLat(center)
// 			.addTo(map);

// 		map.on("load", loadMarkers);
// 	};

// 	useEffect(() => {
// 		console.log("effect", parkings);
// 		navigator.geolocation.getCurrentPosition(
// 			(position) => {
// 				console.log(
// 					position.coords.longitude,
// 					position.coords.latitude
// 				);
// 				setupMap([position.coords.longitude, position.coords.latitude]);
// 			},
// 			() => {
// 				console.log("map error!!!!");
// 				setupMap([-2.24, 53.45]);
// 			},
// 			{
// 				enableHighAccuracy: true,
// 			}
// 		);
// 	}, [parkings]);

// 	return (
// 		<>
// 			<button className="mapboxPopupBtn">Load</button>

// 			<div className={classes.root} id="mapboxContainer"></div>
// 		</>
// 	);
// }

// export default MapBox;
