$(document).ready(function() {
    console.log("Got into map.js successfully");

    function initialize() {
        var mapOptions = {
            center: new google.maps.LatLng(-34.397, 150.644),
            zoom: 8
        };

        var map = new google.maps.Map(document.getElementById("view-area"),
            mapOptions);
    }

    initialize();

    //google.maps.event.addDomListener(window, 'load', initialize);
});