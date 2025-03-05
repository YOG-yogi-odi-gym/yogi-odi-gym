document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("form").addEventListener("submit", validateForm);
});

function validateForm(event) {
    const checkboxes = document.querySelectorAll('input[name="bitDays"]:checked');
    if (checkboxes.length === 0) {
        alert("요일을 하나 이상 선택해주세요.");
        event.preventDefault(); // 폼 제출 방지
        return false;
    }
    return true;
}

function validateEndDate() {
    let startDay = document.querySelector("input[name='startDay']").value;
    let endDay = document.querySelector("input[name='endDay']").value;

    if (startDay && endDay && startDay > endDay) {
        alert("강의 종료일은 강의 시작일보다 이후여야 합니다.");
        document.querySelector("input[name='endDay']").value = "";
    }
}