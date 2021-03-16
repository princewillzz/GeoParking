let profileModal = document.getElementById("EditProfileInfoModal");

document.addEventListener("DOMContentLoaded", () => {
	profileModal = document.getElementById("EditProfileInfoModal");
	profileModal
		.querySelector(".profilePhotoContainer")
		.addEventListener("click", openFileDialog);
});

// profileModal
// 	.querySelector(".profile-pic")
// 	.addEventListener("click", openFileDialog);

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
		console.log("selected files to upload");
		// Append the selected files and keep them hidden

		console.log(inputElement);

		const file = inputElement.files[0];

		changeProfilePic(file);
	});

	// dispatch a click event to open the file dialog
	inputElement.dispatchEvent(new MouseEvent("click"));
}

function changeProfilePic(photo) {
	//if the file isn't a image nothing happens.
	//you are free to implement a fallback
	if (!photo || !photo.type.match(/image.*/)) return;

	console.log(photo);

	const formData = new FormData();
	formData.append("profile_photo", photo);

	const xhr = new XMLHttpRequest();
	const url = "/api/secured/customer/profile/image";

	xhr.open("GET", url, true);

	// const csrfEle = document.getElementById("csrfInput");
	// console.log(csrfEle.name, csrfEle.value);
	// formData.append(csrfEle.name, csrfEle.value);

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			console.log(this.response);
			document.getElementById(
				"customerProfilePictureId"
			).src = this.response;
		}
	};

	xhr.send(formData);
}
