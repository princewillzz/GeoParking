/* eslint import/no-webpack-loader-syntax: off */

import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import { Button } from "@material-ui/core";
import mapboxgl from "mapbox-gl/dist/mapbox-gl-csp";
import React, { useCallback, useEffect, useRef, useState } from "react";
import MapboxWorker from "worker-loader!mapbox-gl/dist/mapbox-gl-csp-worker";
import "./mapbox.css";

import Geocoder from "@mapbox/mapbox-gl-geocoder";
// import Geocoder from "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions";
// import MapboxDirections from "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions";

// import "@mapbox/mapbox-gl-directions/dist/mapbox-gl-directions.css";

mapboxgl.workerClass = MapboxWorker;
mapboxgl.accessToken =
	"pk.eyJ1IjoicHJpbmNld2lsbHoiLCJhIjoiY2tvMzFqMWFpMG8yNTJ3czU4NWVjcG5kdyJ9.F20bN0FJ8byK6BxdoBFOfA";

let map;
export default function MapBoxMap({
	parkings,
	handleOpenBookSlotModal,
	fetchNearbyParking,
}) {
	const mapContainer = useRef();

	const loadMarkers = useCallback(
		(map) => {
			console.log("loadmarkers", parkings);

			const parkingMarker = [];

			parkings.forEach((parking, index) => {
				parkingMarker.push({
					type: "Feature",
					properties: {
						description: `<h2 style="overflow-wrap: break-word;">${parking.name}</h2>
					<p>${parking.address}</p>
                    <h3>Rs ${parking.hourlyRent}/hr</h3>
					<button class="mapboxPopupBtn" id="mapboxPopupBtn" value="${parking.id}" >book</button>`,
					},
					geometry: {
						type: "Point",
						coordinates: [
							parking.position.longitude,
							parking.position.latitude,
						],
					},
				});
			});

			if (!map.getSource("places")) {
				console.log("loading fresh");

				map.addSource("places", {
					type: "geojson",
					data: {
						type: "FeatureCollection",
						features: parkingMarker,
					},
				});
				map.addLayer({
					id: "places",
					type: "circle",
					source: "places",
					paint: {
						"circle-color": "#4264fb",
						"circle-radius": 8,
						"circle-stroke-width": 2,
						"circle-stroke-color": "#ffffff",
					},
				});
			} else {
				console.log("already exists");
				map.getSource("places").setData({
					type: "FeatureCollection",
					features: parkingMarker,
				});
			}

			// Add a layer showing the places.

			// Create a popup, but don't add it to the map yet.
			var popup = new mapboxgl.Popup({
				closeButton: true,
				closeOnClick: true,
			});

			map.on("mouseenter", "places", function (e) {
				// Change the cursor style as a UI indicator.
				map.getCanvas().style.cursor = "pointer";

				var coordinates = e.features[0].geometry.coordinates.slice();
				var description = e.features[0].properties.description;

				// Ensure that if the map is zoomed out such that multiple
				// copies of the feature are visible, the popup appears
				// over the copy being pointed to.
				while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
					coordinates[0] +=
						e.lngLat.lng > coordinates[0] ? 360 : -360;
				}

				// Populate the popup and set its coordinates
				// based on the feature found.
				popup.setLngLat(coordinates).setHTML(description).addTo(map);

				document
					.querySelector("#mapboxPopupBtn")
					.addEventListener("click", function () {
						console.log("Book slot", this);
						handleOpenBookSlotModal(this.value);
					});
			});

			// map.on("mouseleave", "places", function () {
			// 	map.getCanvas().style.cursor = "";
			// 	popup.remove();
			// });
		},
		[parkings, handleOpenBookSlotModal]
	);

	useEffect(() => {
		console.log("use effect render", map);

		let lng = -70.9;
		let lat = 42.35;
		let zoom = 8;

		// Creating an instance of map
		map = new mapboxgl.Map({
			container: mapContainer.current,
			style: "mapbox://styles/mapbox/streets-v11",
			center: [lng, lat],
			zoom: zoom,
			minZoom: 1,
			scrollZoom: true,
		});

		// Adding navbar to zoom in and out
		const nav = new mapboxgl.NavigationControl();
		map.addControl(nav);

		// Add markers to the map for parkings to book
		map.on("load", () => loadMarkers(map));

		// Add the control to the map.

		let geocoder = new Geocoder({
			accessToken: mapboxgl.accessToken,
			mapboxgl: mapboxgl,
		});

		geocoder.on("result", function (e) {
			console.log("getting result");

			console.log(e.result.center);

			// setLng(e.result.center[0]);
			// setLat(e.result.center[1]);

			fetchNearbyParking(e.result.center);
		});

		document
			.getElementById("parkingSearchGeocoder")
			.appendChild(geocoder.onAdd(map));

		// Fly to current location in map
		navigator.geolocation.getCurrentPosition(
			(position) => {
				console.log(
					position.coords.longitude,
					position.coords.latitude
				);

				map.flyTo({
					center: [
						position.coords.longitude,
						position.coords.latitude,
					],
					zoom: zoom,
				});
				new mapboxgl.Marker({
					color: "#191970",
					draggable: true,
				})
					.setLngLat([
						position.coords.longitude,
						position.coords.latitude,
					])
					.addTo(map);
			},
			() => {
				console.log("map error!!!!");
			},
			{
				enableHighAccuracy: true,
			}
		);

		// remove the map object on destroy
		return () => {
			map.remove();
			let searchBox = document.getElementById("parkingSearchGeocoder");
			if (searchBox) searchBox.innerHTML = "";
		};
	}, [fetchNearbyParking, loadMarkers]);

	const [isScrollEnabled, setIsScrollEnabled] = useState(true);

	const handleToggleScroll = () => {
		if (isScrollEnabled) {
			map["scrollZoom"].disable();
		} else {
			map["scrollZoom"].enable();
		}

		setIsScrollEnabled(!isScrollEnabled);
	};

	return (
		<>
			<div id="parkingSearchGeocoder" className="geocoder"></div>
			<div
				style={{
					position: "relative",
				}}
			>
				<Button
					variant="contained"
					color={isScrollEnabled ? "primary" : "inherit"}
					style={{
						position: "absolute",
						zIndex: 1000,
						// fontSize: 13,
					}}
					onClick={handleToggleScroll}
				>
					{isScrollEnabled ? "Disable" : "Enable"} Zoom on Scroll
				</Button>
				<div className="map-container" ref={mapContainer} />
			</div>
		</>
	);
}
