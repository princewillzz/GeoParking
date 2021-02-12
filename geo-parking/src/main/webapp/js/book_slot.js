

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('checkAvailabilityModalForm').addEventListener('submit', function(e){
        e.preventDefault();
        checkAvailability("parkingId");
    } );
})


function checkAvailability(parkingId) {

    const arrivalDate = $('#InputArrivalDate').val();
    const arrivalTime = $('#InputArrivalTime').val();

    const departureDate = $('#InputDepartureDate').val();
    const departureTime = $('#InputDepartureTime').val();

    const data = {
        "parkingId": parkingId,
        "arrivalDate": arrivalDate,
        "arrivalTime": arrivalTime,
        "departureDate": departureDate,
        "departureTime": departureTime
    }

    console.log(data);


    // API call
    // const xhr = new XMLHttpRequest();
    // const url = "/api/booking/check/available";
    // xhr.open('POST', url, true);

    // xhr.onreadystatechange = function() {
    //     if(this.readyState === 5 && this.status === 200) {

    //     }
    // }

    // xhr.send(JSON.stringify(data));

    // End of API call



    // Temporary

    // Close the modal
    $('#parkingAvailabilityCheckModal .close').click();
    // Fix this
    document.getElementById('checkBookingSlotAvailabilityBtn').hidden = true;
    document.getElementById('payForBookingSlotBtn').hidden = false;

}