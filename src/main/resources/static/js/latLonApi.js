function searchAddress(location) {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById(location).value = data.address;
            getLatLng(data.address);
        }
    }).open();
}

function getLatLng(address) {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.addressSearch(address, function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            document.getElementById("latitude").value = result[0].y;
            document.getElementById("longitude").value = result[0].x;
        } else {
            alert("주소 변환에 실패했습니다.");
        }
    });
}