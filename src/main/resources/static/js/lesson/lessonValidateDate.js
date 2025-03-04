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