setTimeout(() => {
	const xhr = new XMLHttpRequest();
	xhr.open("PUT", "/", true);
	xhr.onreadystatechange = function () {
		// User is authenticated
		if (this.readyState === 4 && this.status === 200) {
			document.getElementById(
				"navbarDropdownAuthenticated"
			).style.display = "block";
			document.getElementById("navbarUnAuthenticated").style.display =
				"none";

			// Setting profile info
			const response = JSON.parse(this.response);
			const modal = document.getElementById("EditProfileInfoModal");
			modal.querySelector("#profileInfoFirstName").value =
				response.firstName;
			modal.querySelector("#profileInfoLastName").value =
				response.lastName;
			modal.querySelector("#profileInfoMobileNumber").value =
				response.mobile;
			modal.querySelector(
				".profilePhoto"
			).style.backgroundImage = `url(${response.profilePicture})`;
		} else if (this.readyState === 4) {
			// User is unauthenticated
		}
	};
	xhr.send(null);
}, 0);

function sendTokenForMobileChange() {
	const mobile = document.getElementById("profileInfoMobileNumber").value;

	if (mobile.length < 10) {
		alert("Enter valid mobile number...!");
		return false;
	}
	updatingProfileSpinner(true);

	const url = "/api/secured/customer/mobile/sendtoken?mobile=" + mobile;
	const xhr = new XMLHttpRequest();
	xhr.open("GET", url, true);

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			updatingProfileSpinner(false);

			alert("Verification token sent to your email...");
		} else if (this.readyState === 4) {
			updatingProfileSpinner(false);
			alert("Check your Info...!");
		}
	};

	xhr.send();
}

// update profile Info
function updateProfileInfo() {
	const modal = document.getElementById("EditProfileInfoModal");
	updatingProfileSpinner(true);
	const data = {
		firstName: modal.querySelector("#profileInfoFirstName").value,
		lastName: modal.querySelector("#profileInfoLastName").value,
	};

	const xhr = new XMLHttpRequest();
	const url = "/api/secured/user/profile";
	xhr.open("PUT", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			console.log("Profile updated");

			updatingProfileSpinner(false);
		} else if (this.readyState === 4) {
			alert("Unable to update info...");
			updatingProfileSpinner(false);
		}
	};

	xhr.send(JSON.stringify(data));
}

function updatingProfileSpinner(flag) {
	const modal = document.getElementById("EditProfileInfoModal");
	if (flag) {
		modal.querySelector(".modal-footer .spinner-border").style.display =
			"inline-block";
		modal.querySelector(".modal-footer .save-profile-btn").disabled = true;
	} else {
		setTimeout(() => {
			modal.querySelector(".modal-footer .spinner-border").style.display =
				"none";
			modal.querySelector(
				".modal-footer .save-profile-btn"
			).disabled = false;
		}, 1000);
	}
}
