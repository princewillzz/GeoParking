document.addEventListener("DOMContentLoaded", () => {
    document
        .getElementById("checkAvailabilityModalForm")
        .addEventListener("submit", function (e) {
            e.preventDefault();

            const parkingId = document
                .getElementById("parkingAvailabilityCheckModal")
                .querySelector("#ParkingIdInputAvailableModal").value;

            checkAvailability(parkingId);
        });
});

function setParkingIdInAvailabilityModal(parkingId) {
    const modal = document.getElementById("parkingAvailabilityCheckModal");
    modal.querySelector("#ParkingIdInputAvailableModal").value = parkingId;

    // Hide green tick
    handleGreenTickOnCheckAvailable(false);
}

function checkAvailability(parkingId) {
    const arrivalDate = $("#InputArrivalDate").val();
    const arrivalTime = $("#InputArrivalTime").val();

    const departureDate = $("#InputDepartureDate").val();
    const departureTime = $("#InputDepartureTime").val();

    const data = {
        parkingId: parkingId,
        arrivalDate: arrivalDate,
        arrivalTime: arrivalTime,
        departureDate: departureDate,
        departureTime: departureTime,
    };

    console.log(data);

    // API call
    // const xhr = new XMLHttpRequest();
    // const url = "/api/parking/is-available";
    // xhr.open("POST", url, true);

    // xhr.onreadystatechange = function () {
    //     if (this.readyState === 4 && this.status === 200) {
    //     } else if (this.readyState === 4) {
    //     }
    // };

    // xhr.send(JSON.stringify(data));

    // End of API call

    // -------------Temporary

    // Close the modal
    // $("#parkingAvailabilityCheckModal .close").click();
    // // Fix this
    // const parkingCard = document.querySelectorAll(
    //     "#FeatchedParkingCard" + parkingId
    // );

    // parkingCard.forEach(
    //     (parking) =>
    //         (parking.querySelector(
    //             ".checkBookingSlotAvailabilityBtn"
    //         ).hidden = true)
    // );
    // parkingCard.forEach(
    //     (parking) =>
    //         (parking.querySelector(".payForBookingSlotBtn").hidden = false)
    // );

    handleGreenTickOnCheckAvailable(true);
}

function handleGreenTickOnCheckAvailable(status) {
    console.log(status);
    if (status) {
        document.querySelector(
            "#parkingAvailabilityCheckModal #GreenTickCheckAvailability"
        ).style.display = "inline";
    } else {
        document.querySelector(
            "#parkingAvailabilityCheckModal #GreenTickCheckAvailability"
        ).style.display = "none";
    }
}
