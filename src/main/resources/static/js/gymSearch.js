window.onload = function () {
    gymSearch();
};

let currentPage = 0;
const pageSize = 10;

let memberLat = parseFloat(document.getElementById("memberLat").value);
let memberLon = parseFloat(document.getElementById("memberLon").value);

function gymSearch(page = 0) {
    currentPage = page;

    let gymKeyword = document.getElementById("gymKeyword").value;
    let searchColumn = document.getElementById("searchColumn").value;

    fetch(`/api/search?gymKeyword=${gymKeyword}&page=${page}&size=${pageSize}&searchColumn=${searchColumn}`)
        .then(response => response.json())
        .then(gyms => {
            let resultSearch = document.getElementById("resultSearch");
            resultSearch.innerHTML = "";

            let gymNo = page * 10 + 1;

            gyms.content.forEach(gym => {
                let gymTrRow = document.createElement("tr");
                let gymTdNo = document.createElement("td");
                let gymTdName = document.createElement("td");
                let gymTdOldAddress = document.createElement("td");
                let gymTdDistance = document.createElement("td");

                let modalLink = document.createElement("a");
                modalLink.href = "#";
                modalLink.textContent = gym.name;
                modalLink.onclick = () => openModal(gym);
                gymTdName.appendChild(modalLink);

                gymTdNo.textContent = gymNo++;
                gymTdOldAddress.textContent = gym.oldAddress;

                let distance = calculateDistance(memberLat, memberLon, gym.latitude, gym.longitude);
                gymTdDistance.textContent = `${distance.toFixed(2)} Km`;

                gymTrRow.appendChild(gymTdNo);
                gymTrRow.appendChild(gymTdName);
                gymTrRow.appendChild(gymTdOldAddress);
                gymTrRow.appendChild(gymTdDistance);

                resultSearch.appendChild(gymTrRow);
            });

            setupPagination(gyms.totalPages);
        });
}

function setupPagination(totalPages) {
    let pagination = document.getElementById("pagination");
    pagination.innerHTML = "";

    let startPage = Math.floor(currentPage / 10) * 10;
    let endPage = Math.min(totalPages, startPage + 10);

    let prevBtn = document.createElement("button");
    prevBtn.textContent = "이전";
    prevBtn.onclick = () => gymSearch(Math.max(0, startPage - 10));
    prevBtn.disabled = startPage === 0;
    pagination.appendChild(prevBtn);

    for (let i = startPage; i < endPage; i++) {
        let pageBtn = document.createElement("button");
        pageBtn.textContent = i + 1;
        pageBtn.onclick = () => gymSearch(i);
        if (i === currentPage) {
            pageBtn.style.fontWeight = "bold";
        }
        pagination.appendChild(pageBtn);
    }

    let nextBtn = document.createElement("button");
    nextBtn.textContent = "다음";
    nextBtn.onclick = () => gymSearch(Math.min(totalPages - 1, startPage + 10));
    nextBtn.disabled = endPage >= totalPages;
    pagination.appendChild(nextBtn);
}

function openModal(gym) {
    let modal = document.getElementById("mapModal");
    modal.style.display = "block";

    let kakaoMap = document.getElementById("kakaoMap");
    kakaoMap.innerHTML = "";

    document.getElementById("modalContent").innerHTML = `
        <strong>이름:</strong> ${gym.name} <br>
        <strong>주소:</strong> ${gym.oldAddress} <br>
        <strong>위도:</strong> ${gym.latitude} <br>
        <strong>경도:</strong> ${gym.longitude} <br>
    `;

    let mapOption = {
        center: new kakao.maps.LatLng(gym.latitude, gym.longitude),
        level: 3
    };

    let map = new kakao.maps.Map(kakaoMap, mapOption);

    let marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(gym.latitude, gym.longitude)
    });

    marker.setMap(map);
}

function closeModal() {
    document.getElementById("mapModal").style.display = "none";
}

function calculateDistance(memberLat, memberLon, gymLat, gymLon) {
    const R = 6371;
    const dLat = (gymLat - memberLat) * Math.PI / 180;
    const dLon = (gymLon - memberLon) * Math.PI / 180;

    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(memberLat * Math.PI / 180) * Math.cos(gymLat * Math.PI / 180) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);

    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}
