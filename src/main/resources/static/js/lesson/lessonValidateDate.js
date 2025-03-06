document.addEventListener("DOMContentLoaded", function () {
    function validateEndDate() {
        let startDay = document.querySelector("input[name='startDay']").value
        let endDay = document.querySelector("input[name='endDay']").value

        if (startDay && endDay && startDay > endDay) {
            alert("강의 종료일은 강의 시작일보다 이후여야 합니다.")
            document.querySelector("input[name='endDay']").value = ""
        }
    }

    document.querySelector("input[name='startDay']").addEventListener("change", validateEndDate)
    document.querySelector("input[name='endDay']").addEventListener("change", validateEndDate)
})

function validateForm() {
    let bitDays = document.querySelectorAll('input[name="bitDays"]:checked');
    if (bitDays.length === 0) {
        alert("강의 요일을 하나 이상 선택해야 합니다.");
        return false;
    }

    let latitude = document.getElementById("latitude").value;
    let longitude = document.getElementById("longitude").value;
    if (!latitude || !longitude) {
        alert("위도와 경도가 비어 있습니다. 주소를 다시 검색해주세요.");
        return false;
    }

    return true;
}
