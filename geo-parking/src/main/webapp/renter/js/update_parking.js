const url = new URL(window.location.href);
const parkingId = url.searchParams.get("id");
loadParkingData(parkingId);

function loadParkingData(parkingId) {
	console.log(parkingId);
	const xhr = new XMLHttpRequest();
	const url = "/api/parking/" + parkingId;
	xhr.open("GET", url, true);

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			const data = JSON.parse(this.response);

			document.getElementById("parkingIdInput").value = data.uid;
			document.getElementById("parkingNameInput").value = data.name;
			document.getElementById("parkingAddressInput").value = data.address;
			document.getElementById("parkingTotalInput").value = data.total;
			document.getElementById("parkingOccupiedInput").value =
				data.occupied;
			document.getElementById("parkingVacantInput").value = data.vacant;
			document.getElementById("parkingHourlyRateInput").value =
				data.hourlyRate;
		} else if (this.readyState === 4) {
			alert("No Such parking found");
			window.location.replace("/renter");
		}
	};

	xhr.send(null);
}

// Upload parking btn
document
	.getElementById("uploadParkingImageBtn")
	.addEventListener("click", openFileDialog);

// upload images
function openFileDialog(accept) {
	// this function must be called from  a user
	// activation event (ie an onclick event)

	console.log(accept);
	// Create an input element
	var inputElement = document.createElement("input");
	inputElement.type = "file";
	// inputElement.hidden = true;
	// document.querySelector(".profile-pic").append(inputElement);
	// Set accept to the file types you want the user to select.
	// Include both the file extension and the mime type
	inputElement.accept = accept;

	// set onchange event to call selectedFiles when user has selected file
	inputElement.addEventListener("change", () => {
		// console.log("selected files to upload");
		// Append the selected files and keep them hidden

		// console.log(inputElement);

		const file = inputElement.files[0];

		// upload and display selected file in the list
		uploadParkingImage(file);
	});

	// dispatch a click event to open the file dialog
	inputElement.dispatchEvent(new MouseEvent("click"));
}

function uploadParkingImage(file) {
	console.log("Uploading file...");

	console.log(file);
	// upload then display on successfull upload

	displayImageInGallery(file);
}

function displayImageInGallery(file) {
	// display photo
	const liContainer = document.createElement("li");
	// Trash icon
	const trashElement = document.createElement("i");
	trashElement.className = "fas fa-trash-alt";
	liContainer.appendChild(trashElement);
	// image
	const imageElement = document.createElement("img");
	imageElement.src = URL.createObjectURL(file);
	// imageElement.src = "/image/img1.svg";
	liContainer.appendChild(imageElement);

	document.getElementById("parkingImagesContainer").appendChild(liContainer);
}
