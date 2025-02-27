let memberId = document.body.dataset.memberId
let lessonId = document.body.dataset.lessonId

function enrollLesson() {
    fetch(`/api/lesson/enroll`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ memberId, lessonId }),
    })
        .then(response => response.json())
        .then(data => {
            alert("수강 신청이 완료되었습니다.");
            location.reload();
        })
        .catch(error => console.error("Error:", error));
}

function cancelEnrollment() {
    fetch(`/api/lesson/cancel/${memberId}/${lessonId}`, {
        method: "DELETE",
    })
        .then(response => response.json())
        .then(data => {
            alert("수강이 취소되었습니다.");
            location.reload();
        })
        .catch(error => console.error("Error:", error));
}

function enterChatRoom() {
    location.href = `/chatroom/${lessonId}`;
}

function updateLesson() {
    location.href = `/lesson/${lessonId}/edit`;
}

function openModal() {
    let modal = document.getElementById("mapModal");
    modal.style.display = "block";

    let kakaoMap = document.getElementById("kakaoMap");
    kakaoMap.innerHTML = "";

    let latitude = event.target.dataset.latitude;
    let longitude = event.target.dataset.longitude;

    let mapOption = {
        center: new kakao.maps.LatLng(latitude, longitude),
        level: 3
    };

    let map = new kakao.maps.Map(kakaoMap, mapOption);

    let marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(latitude, longitude)
    });

    marker.setMap(map);
}

function closeModal() {
    document.getElementById("mapModal").style.display = "none";
}

function toggleEnrollment(lessonId, isEnrolled, button, tdCurrent) {
    let url = isEnrolled
        ? `/api/lesson/cancel/${memberId}/${lessonId}`
        : `/api/lesson/enroll`;

    let method = isEnrolled ? "DELETE" : "POST";
    let body = isEnrolled ? null : JSON.stringify({ memberId, lessonId });

    let currentCount = parseInt(tdCurrent.textContent.split(" / ")[0]);
    let maxCount = parseInt(tdCurrent.textContent.split(" / ")[1]);
    let newCount = isEnrolled ? currentCount - 1 : currentCount + 1;

    tdCurrent.textContent = `${newCount} / ${maxCount}`;

    if (newCount >= maxCount) {
        button.textContent = "마감";
        button.disabled = true;
    } else {
        button.textContent = isEnrolled ? "신청" : "취소";
    }

    fetch(url, {
        method: method,
        headers: { "Content-Type": "application/json" },
        body: body
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                button.onclick = () => toggleEnrollment(lessonId, !isEnrolled, button, tdCurrent);
            } else {
                tdCurrent.textContent = `${currentCount} / ${maxCount}`;
                button.textContent = isEnrolled ? "취소" : "신청";
                button.disabled = false;
            }
        })
        .catch(() => {
            tdCurrent.textContent = `${currentCount} / ${maxCount}`;
            button.textContent = isEnrolled ? "취소" : "신청";
            button.disabled = false;
        });
}

window.onload = function() {
    const lessonMasterId = document.body.dataset.lessonMasterId
    const lessonMax = document.body.dataset.lessonMax
    const lessonCurrent = document.body.dataset.lessonCurrent

    const chatRoomBtn = document.getElementById("chatRoomBtn");
    const updateBtn = document.getElementById("updateBtn");
    const enrollBtn = document.getElementById("enrollBtn");
    const cancelEnrollBtn = document.getElementById("cancelEnrollBtn");

    const bitmask = document.body.dataset.lessonCurrent.lessonDays
    document.getElementById("lessonDays").textContent = bitmaskToDays(parseInt(bitmask));

    if (lessonMasterId === memberId) {
        chatRoomBtn.style.display = "block"
        updateBtn.style.display = "block"
    } else {
        if (lessonCurrent >= lessonMax) {
            enrollBtn.textContent = "마감"
            enrollBtn.disabled = true
        } else {
            fetch(`/api/lesson/enrolled?lessonId=${lessonId}&memberId=${memberId}`)
                .then(response => response.json())
                .then(isEnrolled => {
                    if (isEnrolled) {
                        cancelEnrollBtn.style.display = "block"
                        chatRoomBtn.style.display = "block"
                        enrollBtn.style.display = "none"
                    } else {
                        enrollBtn.style.display = "block"
                        cancelEnrollBtn.style.display = "none"
                    }
                })
                .catch(error => console.error('Error checking enrollment:', error))
        }
    }
}