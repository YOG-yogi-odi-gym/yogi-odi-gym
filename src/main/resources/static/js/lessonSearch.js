window.onload = function () {
    lessonSearch();
}

let currentPage=0
const pageSize=10

let memberLat = document.body.dataset.memberLat;
let memberLon = document.body.dataset.memberLon;
let memberId = document.body.dataset.memberId;

function lessonSearch(page = 0){
    currentPage = page;

    let lessonKeyword = document.getElementById("lessonKeyword").value
    let searchColumn = document.getElementById("searchColumn").value

    let selectedCategories = document.querySelectorAll("input[name='categories']:checked");
    let categoryList = Array.from(selectedCategories).map(checkbox => checkbox.value);

    let selectedDays = document.querySelectorAll("input[name='days']:checked");
    let daysBitmask = Array.from(selectedDays).reduce((acc, checkbox) => acc + parseInt(checkbox.value), 0);

    let queryParams = new URLSearchParams({
        lessonKeyword: lessonKeyword,
        searchColumn: searchColumn,
        page: page,
        size: pageSize
    });

    if (categoryList.length > 0) {
        queryParams.append("categories", categoryList.join(","));
    }

    if (daysBitmask > 0) {
        queryParams.append("days", daysBitmask);
    }

    fetch(`/api/lesson/search?${queryParams.toString()}`)
        .then(response => response.json())
        .then(data => {
            console.log(data)

            const lessons = data.content;
            const totalPages = data.totalPages;

            console.log(lessons)
            console.log(totalPages)

            let resultSearch = document.getElementById("resultSearch")
            resultSearch.innerHTML=""

            let lessonNo = page * 10 + 1;

            lessons.forEach(lesson => {
                let trRow = document.createElement("tr")
                let tdNo = document.createElement("td")
                let tdTitle = document.createElement("td")
                let tdMaster = document.createElement("td")
                let tdLessonDays = document.createElement("td")
                let tdLessonTime = document.createElement("td")
                let tdCurrent = document.createElement("td")
                let tdOldAddress = document.createElement("td")
                let tdDistance = document.createElement("td")
                let tdBtn = document.createElement("td")

                let detailLink = document.createElement("a")
                detailLink.href=`/lesson/${lesson.id}`
                detailLink.textContent = `[${lesson.categoryName}] ${lesson.title}`
                tdTitle.appendChild(detailLink)

                tdNo.textContent = lessonNo++
                tdMaster.textContent = lesson.masterName
                tdLessonDays.textContent = bitmaskToDays(lesson.days);
                tdLessonTime.textContent = `${lesson.startTime} ~ ${lesson.endTime}`
                tdCurrent.textContent = `${lesson.current} / ${lesson.max}`
                tdOldAddress.textContent = lesson.location

                let distance = calculateDistance(memberLat, memberLon, lesson.latitude, lesson.longitude)
                tdDistance.textContent = `${distance.toFixed(2)} Km`

                if (lesson.masterId === memberId) {
                    // 현재 사용자가 강의 생성자일 경우 버튼 없음
                    tdBtn.textContent = "내 강의";
                } else {
                    let lessonBtn = document.createElement("button");

                    if (lesson.current >= lesson.max) {
                        lessonBtn.textContent = "마감";
                        lessonBtn.disabled = true;
                    } else {
                        fetch(`/api/lesson/enrolled?lessonId=${lesson.id}&memberId=${memberId}`)
                            .then(response => response.json())
                            .then(isEnrolled => {
                                lessonBtn.textContent = isEnrolled ? "취소" : "신청";
                                lessonBtn.onclick = () => toggleEnrollment(lesson.id, isEnrolled, lessonBtn, tdCurrent);
                            });
                    }

                    tdBtn.appendChild(lessonBtn);
                }

                trRow.appendChild(tdNo)
                trRow.appendChild(tdTitle)
                trRow.appendChild(tdMaster)
                trRow.appendChild(tdLessonDays)
                trRow.appendChild(tdLessonTime)
                trRow.appendChild(tdCurrent)
                trRow.appendChild(tdOldAddress)
                trRow.appendChild(tdDistance)
                trRow.appendChild(tdBtn)

                resultSearch.appendChild(trRow)
            })

            let pagination = document.getElementById("pagination")
            pagination.innerHTML = ""

            let startPage = Math.floor(currentPage / 10) * 10
            let endPage = Math.min(totalPages, startPage + 10)

            let prevBtn = document.createElement("button")
            prevBtn.textContent = "이전"
            prevBtn.onclick = () => lessonSearch(Math.max(0, startPage - 10))
            prevBtn.disabled = startPage === 0
            pagination.appendChild(prevBtn)


            for (let i = startPage; i< endPage; i++){
                let pageBtn = document.createElement("button")
                pageBtn.textContent = i + 1
                pageBtn.onclick = () => lessonSearch(i)
                if (i === currentPage) {
                    pageBtn.style.fontWeight = "bold"
                }
                pagination.appendChild(pageBtn)
            }

            let nextBtn = document.createElement("button")
            nextBtn.textContent = "다음"
            nextBtn.onclick = () => lessonSearch(Math.min(totalPages - 1, startPage + 10))
            nextBtn.disabled = endPage >= totalPages
            pagination.appendChild(nextBtn)

        })
}

function openModal(lesson){
    let modal = document.getElementById("gymModal")
    console.log(kakao)
    console.log(kakao.maps)
    modal.style.display = "block"

    let kakaoMap = document.getElementById("kakaoMap")
    kakaoMap.innerHTML = "";

    document.getElementById("modalContent").innerHTML = `
                <strong>이름:</strong> ${lesson.name} <br>
                <strong>주소:</strong> ${lesson.oldAddress} <br>
                <strong>위도:</strong> ${lesson.latitude} <br>
                <strong>경도:</strong> ${lesson.longitude} <br>
            `

    kakao.maps.load(function() {
        let mapOption = {
            center: new kakao.maps.LatLng(lesson.latitude, lesson.longitude),
            level: 3
        }

        let map = new kakao.maps.Map(kakaoMap, mapOption)

        let marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(lesson.latitude, lesson.longitude)
        });

        marker.setMap(map)
    })

}

function closeModal(){
    document.getElementById("gymModal").style.display = "none"
}

function calculateDistance(memberLat, memberLon, lat, lon) {
    const R = 6371;
    const dLat = (lat - memberLat) * Math.PI / 180;
    const dLon = (lon - memberLon) * Math.PI / 180;

    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(memberLat * Math.PI / 180) * Math.cos(lat * Math.PI / 180) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);

    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}

function bitmaskToDays(bitmask) {
    if (bitmask === 31){
        return "평일"
    } else if (bitmask === 96){
        return "주말"
    }

    const days = ["월", "화", "수", "목", "금", "토", "일"]
    let result = []

    for (let i = 0; i < days.length; i++) {
        if (bitmask & (1 << i)) {
            result.push(days[i])
        }
    }
    return result.join(", ")
}

function toggleEnrollment(lessonId, isEnrolled, button, tdCurrent) {
    let url = isEnrolled
        ? `/api/lesson/cancel/${memberId}/${lessonId}`
        : `/api/lesson/enroll`;

    let method = isEnrolled ? "DELETE" : "POST";
    let body = isEnrolled ? null : JSON.stringify({ memberId: memberId, lessonId: lessonId });

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

function limitCheckboxSelection(checkbox) {
    let checkboxes = document.querySelectorAll("input[name='categories']");

    checkboxes.forEach(function(item) {
        if (item !== checkbox) {
            item.checked = false;
        }
    });
}