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
