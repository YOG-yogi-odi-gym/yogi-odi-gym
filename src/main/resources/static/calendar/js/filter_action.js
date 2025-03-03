function saveMemo(memoId, selectedDate, memberId) {
    let title = $(`#memoTitle_${memoId}`).val();
    let context = $(`#memoContent_${memoId}`).val();

    let requestData = {
        title: title,
        context: context,
        date: selectedDate,
        memberId: memberId
    };

    console.log(requestData);


    $.ajax({
        url: "/memo/date/post",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-MemoButton-button").click();
        },
        error: function() {
            alert("메모 저장 실패");
        }
    });
}

function updateMemo(memoId, selectedDate, memberId) {
    let title = $(`#memoTitle_${memoId}`).val(); // 각 메모의 ID를 가져오기
    let context = $(`#memoContent_${memoId}`).val();

    let requestData = {
        id: memoId,
        title: title,
        context: context,
        date: selectedDate,
        memberId: memberId
    };

    console.log(requestData);


    $.ajax({
        url: "/memo/date/put",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-MemoButton-button").click();
        },
        error: function(xhr) {
            alert("메모 업데이트 실패: " + xhr.responseText);
        }
    });
}

function deleteMemo(memoId) {

    $.ajax({
        url: "/memo/date/delete/"+memoId,
        method: "DELETE",
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-MemoButton-button").click();
        },
        error: function(xhr) {
            alert("메모 삭제 실패: " + xhr.responseText);
        }
    });
}

function saveFood(foodId, selectedDate, memberId) {
    let name = $(`#foodName_${foodId}`).val();
    let hundredGram = $(`#foodHundredGram_${foodId}`).val();
    let calories = $(`#foodCalories_${foodId}`).val();

    let requestData = {
        name: name,
        hundredGram: hundredGram,
        calories: calories,
        date: selectedDate,
        memberId: memberId
    };

    $.ajax({
        url: "/food/date/post",
        method: "POST",
        contentType: "application/json",  // content-type을 application/json으로 설정
        data: JSON.stringify(requestData),  // 데이터를 JSON 형식으로 변환하여 전송
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-FoodButton-button").click();
        },
        error: function() {
            alert("식단 저장 실패");
        }
    });
}

function updateFood(foodId, selectedDate, memberId) {
    let name = $(`#foodName_${foodId}`).val(); // 각 메모의 ID를 가져오기
    let hundredGram = $(`#foodHundredGram_${foodId}`).val();
    let calories = $(`#foodCalories_${foodId}`).val();

    let requestData = {
        id: foodId,
        name: name,
        hundredGram: hundredGram,
        calories: calories,
        date: selectedDate,
        memberId: memberId
    };


    $.ajax({
        url: "/food/date/put",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-FoodButton-button").click();
        },
        error: function(xhr) {
            alert("식단 업데이트 실패: " + xhr.responseText);
        }
    });
}

function deleteFood(foodId) {

    $.ajax({
        url: "/food/date/delete/"+foodId,
        method: "DELETE",
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-FoodButton-button").click();
        },
        error: function(xhr) {
            alert("식단 삭제 실패: " + xhr.responseText);
        }
    });
}


function saveExercise(exerciseId, selectedDate, memberId) {
    let name =$(`#exerciseName_${exerciseId}`).val();
    let time = $(`#exerciseTime_${exerciseId}`).val()
    let calories = $(`#exerciseCalories_${exerciseId}`).val();

    let requestData = {
        name: name,
        time: time,
        calories: calories,
        date: selectedDate,
        memberId: memberId
    };

    $.ajax({
        url: "/exercise/date/post",
        method: "POST",
        contentType: "application/json",  // content-type을 application/json으로 설정
        data: JSON.stringify(requestData),  // 데이터를 JSON 형식으로 변환하여 전송
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-ExerButton-button").click();
        },
        error: function() {
            alert("운동 저장 실패");
        }
    });
}

function updateExercise(exerciseId, selectedDate, memberId) {
    let name = $(`#exerciseName_${exerciseId}`).val(); // 각 메모의 ID를 가져오기
    let time = $(`#exerciseTime_${exerciseId}`).val();
    let calories = $(`#exerciseCalories_${exerciseId}`).val();

    let requestData = {
        id: exerciseId,
        name: name,
        time: time,
        calories: calories,
        date: selectedDate,
        memberId: memberId
    };

    $.ajax({
        url: "/exercise/date/put",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-ExerButton-button").click();
        },
        error: function(xhr) {
            alert("운동 업데이트 실패: " + xhr.responseText);
        }
    });
}

function deleteExercise(exerciseId) {

    $.ajax({
        url: "/exercise/date/delete/"+exerciseId,
        method: "DELETE",
        success: function(response) {
            console.log(response);

            $("#openModalBtn").click();
            $(".fc-ExerButton-button").click();
        },
        error: function(xhr) {
            alert("운동 삭제 실패: " + xhr.responseText);
        }
    });
}

function getLesson() {
    let memberId = document.getElementById('memberId').value;

    $.ajax({
        url: '/calendar/lesson',
        method: 'GET',
        data: { memberId: memberId },
        success: function (response) {

            let events = response.data.map(item => ({
                title: item.title,
                start: new Date(item.date),
                end: new Date(new Date(item.date).setHours(new Date(item.date).getHours() + 2)),
                description: `${item.startTime} - ${item.endTime}`,
                allDay: true
            }));

            calendar.removeAllEvents();
            calendar.addEventSource(events);
        },
        error: function () {
            alert('일정을 불러오는 데 실패했습니다.');
        }
    });
}

function getLessonByDate(date) {
    let selectedDate = date.toISOString().split('T')[0]; // 'YYYY-MM-DD' 형식으로 변환
    let memberId = document.getElementById('memberId').value;
    console.log("선택한 날짜:", selectedDate);

    $.ajax({
        url: '/calendar/lesson/date',
        method: 'GET',
        data: {
            memberId: memberId,
            date: selectedDate
        },
        success: function (response) {

            let content = `<strong>${selectedDate}의 강의 일정</strong><br>`;
            if (response.data.length > 0) {
                response.data.forEach(item => {
                    content += `<strong>${item.title}</strong> (${item.startTime} ~ ${item.endTime})`;
                    content += `<input type="button" class="lessonDetailBtn btn btn-info mt-2" value="강의 상세보기" data-id="${item.id}" /><br>`;
                });
            } else {
                content += "해당 날짜에 일정이 없습니다.";
            }

            $("#modalContent").html(content);
            $("#openModalBtn").click(); // 모달 버튼 클릭 이벤트 트리거

            $(".lessonDetailBtn").on("click", function () {
                let id = $(this).data("id");
                window.location.href = `/lesson/${id}`; // 강의 상세 페이지 이동
            });
        },
        error: function () {
            $("#modalContent").html("일정을 불러오는 데 실패했습니다.");
            $("#openModalBtn").click(); // 모달 버튼 클릭 이벤트 트리거
        }
    });
}



function getMemo() {
    let memberId = document.getElementById('memberId').value;

    console.log(memberId);

    $.ajax({
        url: '/memo',
        method: 'GET',
        data: { memberId: memberId },
        success: function (response) {
            let events = response.data.map(item => ({
                title: item.title,
                start: new Date(item.date),
                description: item.context || "",
                allDay: true
            }));
            calendar.removeAllEvents();
            calendar.addEventSource(events);
        },
        error: function () {
            alert('메모를 불러오는 데 실패했습니다.');
        }
    });
}
function getMemoByDate(date) {
    let selectedDate = date.toISOString().split('T')[0];
    let memberId = document.getElementById('memberId').value;

    $.ajax({
        url: '/memo/date',
        method: 'GET',
        data: {
            date: selectedDate,
            memberId: memberId
        },
        success: function (response) {

            let content = `<strong>${selectedDate}의 메모</strong><br>`;

            content += `<button onclick="addMemoRow('${selectedDate}', ${memberId})">+ 추가</button>`;

            if (response.data.length > 0) {
                response.data.forEach(item => {
                    content += `<div id="memo_${item.id}">`;
                    content += `<strong>메모 수정</strong><br>`;
                    content += `제목 : <input type="text" id="memoTitle_${item.id}" value="${item.title}" /><br>`;
                    content += `내용 : <textarea id="memoContent_${item.id}">${item.context}</textarea><br>`;
                    content += `<button onclick="updateMemo(${item.id}, '${selectedDate}', ${memberId})">수정</button>`;
                    content += `<button onclick="deleteMemo(${item.id})">삭제</button></div>`;
                });
            } else {

            }

            $("#modalContent").html(content);
            $("#openModalBtn").click();

        },
        error: function () {
            $("#modalContent").html("메모를 불러오는 데 실패했습니다.");
            $("#openModalBtn").click(); 
        }
    });
}


function createMemoRow(selectedDate, memberId) {
    let memoId = new Date().getTime();
    return `<div id="memo_${memoId}">
            제목 : <input type="text" id="memoTitle_${memoId}"/><br>
            내용 : <textarea id="memoContent_${memoId}" placeholder="내용 입력"></textarea> <br>        
            <button onclick="saveMemo('${memoId}','${selectedDate}', ${memberId})">저장</button>
            <button onclick="deleteMemoRow('${memoId}')">취소</button><div>`
}

function addMemoRow(selectedDate, memberId) {
    $("#modalContent").append(createMemoRow(selectedDate,memberId));
}

function deleteMemoRow(memoId) {
    $(`#memo_${memoId}`).remove();
}



function getExercise() {
    let memberId = document.getElementById('memberId').value;

    $.ajax({
        url: '/exercise',  // 실제 API URL로 변경
        method: 'GET',
        data: { memberId: memberId },
        success: function (response) {
            let events = response.data.map(item => ({
                title: item.name,
                start: new Date(item.date),
                description: item.context || "",
                allDay: true
            }));
            calendar.removeAllEvents();
            calendar.addEventSource(events);
        },
        error: function () {
            alert('일정을 불러오는 데 실패했습니다.');
        }
    });
}
function getExerciseByDate(date) {
    let selectedDate = date.toISOString().split('T')[0];
    let memberId = document.getElementById('memberId').value;

    $.ajax({
        url: '/exercise/date',
        method: 'GET',
        data: {
            date: selectedDate,
            memberId: memberId
        },

        success: function (response) {

            let content = `<strong>${selectedDate}의 운동</strong><br>`;
            let energyConsumptionValue = $("#selectedExerciseEnergy").val(); // 기본값 설정

            content += `<button onclick="addExerciseRow('${selectedDate}', ${memberId})">+ 추가</button>`;

            if (response.data.length > 0) {
                response.data.forEach(item => {
                    content += `<div id="exercise_${item.id}">`;
                    content += `<strong>운동 수정</strong><br>`;
                    content += `운동명 : <input type="text" id="exerciseName_${item.id}" value="${item.name}" onkeyup="updateEnergyConsumption(${item.id})" disabled/><br>`;
                    content += `운동시간 : <input type="text" id="exerciseTime_${item.id}" value="${item.time}" onkeyup="calculateCalories(${item.id})" onfocus="calculateCalories(${item.id})"/>`;
                    content += `<input type="text" id="energyConsumption_${item.id}" value="${energyConsumptionValue}" disabled/><br>`;
                    content += `운동 칼로리 : <input type="text" id="exerciseCalories_${item.id}" value="${item.calories}" onkeyup="calculateCalories(${item.id})" disabled /><br>`;
                    content += `<button onclick="updateExercise(${item.id}, '${selectedDate}', ${memberId})">수정</button>`;
                    content += `<button onclick="deleteExercise(${item.id})">삭제</button>`;
                    content += `<button onClick="openSelectExerciseWindow('${item.id}')">운동 선택</button></div>`;

                    $("#modalContent").html(content);

                    let selectedExerciseName = $(`#exerciseName_${item.id}`).val();

                    $.ajax({
                        url: "/api/exercise/search",
                        method: "GET",
                        data: { name: selectedExerciseName },

                        success: function (responseData) {

                            if (responseData && responseData.length > 0) {
                                let energyConsumption = responseData[0].energyConsumption;
                                console.log(energyConsumption);

                                $(`#energyConsumption_${item.id}`).val(energyConsumption);
                            } else {
                                // 값이 없으면 빈 값 처리
                                $(`#energyConsumption_${item.id}`).val('');
                            }

                        },
                        error: function (error) {
                            console.error("Error fetching energy consumption:", error);
                        }
                    });
                });

            } else {
            }

            $("#modalContent").html(content);
            $("#openModalBtn").click(); // 모달 버튼 클릭 이벤트 트리거
        },
        error: function () {
            $("#modalContent").html("일정을 불러오는 데 실패했습니다.");
            $("#openModalBtn").click(); // 모달 버튼 클릭 이벤트 트리거
        }
    });
}

function createExerciseRow(selectedDate, memberId) {
    let exerciseId = new Date().getTime(); // 새 음식 ID 생성
    return `<div id="exercise_${exerciseId}">
            운동명 : <input type="text" id="exerciseName_${exerciseId}" onkeyup="updateEnergyConsumption(${exerciseId})" disabled/><br>
            운동시간 : <input type="text" id="exerciseTime_${exerciseId}" onkeyup="calculateCalories(${exerciseId})" onfocus="calculateCalories(${exerciseId})"/>
            <input type="text" id="energyConsumption_${exerciseId}" disabled/><br>
            운동 칼로리 : <input type="text" id="exerciseCalories_${exerciseId}" onkeyup="calculateCalories(${exerciseId})" disabled /><br>        
            <button onClick="openSelectExerciseWindow('${exerciseId}')">운동선택</button>
            <button onclick="saveExercise('${exerciseId}','${selectedDate}', ${memberId})">저장</button>
            <button onclick="deleteExerciseRow('${exerciseId}')">취소</button><div> `
}

function addExerciseRow(selectedDate, memberId) {
    $("#modalContent").append(createExerciseRow(selectedDate,memberId));
}

function deleteExerciseRow(exerciseId) {
    $(`#exercise_${exerciseId}`).remove();
}


function getFood() {
    let memberId = document.getElementById('memberId').value;

    $.ajax({
        url: '/food',  // 실제 API URL로 변경
        method: 'GET',
        data: {memberId: memberId},
        success: function (response) {
                let events = response.data.map(item => ({
                    title: item.name,
                    start: new Date(item.date),
                    description: item.context || "",
                    allDay: true
                }));
                calendar.removeAllEvents();
                calendar.addEventSource(events);
            } ,
        error: function () {
            alert('일정을 불러오는 데 실패했습니다.');
        }
    });
}

function getFoodByDate(date) {
    let selectedDate = date.toISOString().split('T')[0];
    let memberId = document.getElementById('memberId').value;

    $.ajax({
        url: '/food/date',
        method: 'GET',
        data: {
            date: selectedDate,
            memberId: memberId
        },
        success: function (response) {

            let content = `<strong>${selectedDate}의 식단</strong><br>`;
            let foodConsumptionValue = $("#selectedFoodEnergy").val(); // 기본값 설정

            content += `<button onclick="addFoodRow('${selectedDate}', ${memberId})">+ 추가</button>`;

            if (response.data.length > 0) {
                response.data.forEach(item => {
                    content += `<div id="food_${item.id}">`
                    content += `<strong>식단 수정</strong><br>`;
                    content += `음식명 : <input type="text" id="foodName_${item.id}" value="${item.name}" onkeyup="updateFoodConsumption(${item.id})" disabled /><br>`;
                    content += `섭취량 : <input type="text" id="foodHundredGram_${item.id}" value="${item.hundredGram}" onkeyup="calculateFoodCalories(${item.id})" onfocus="calculateFoodCalories(${item.id})"/>`;
                    content += `<input type="text" id="foodConsumption_${item.id}" value="${foodConsumptionValue}" disabled/><br>`;
                    content += `섭취 칼로리 : <input type="text" id="foodCalories_${item.id}" value="${item.calories}" onkeyup="calculateFoodCalories(${item.id})" disabled /><br>`;
                    content += `<button onClick="openSelectFoodWindow('${item.id}')">음식 선택</button>`;
                    content += `<button onclick="updateFood(${item.id}, '${selectedDate}', ${memberId})">수정</button>`;
                    content += `<button onclick="deleteFood(${item.id})">삭제</button></div>`

                    $("#modalContent").html(content);

                    let selectedFoodName = $(`#foodName_${item.id}`).val();

                    $.ajax({
                        url: "/api/food/search",
                        method: "GET",
                        data: { name: selectedFoodName },
                        success: function (responseData) {

                            if (responseData && responseData.length > 0) {
                                let foodConsumption = responseData[0].calories;
                                console.log(foodConsumption);

                                $(`#foodConsumption_${item.id}`).val(foodConsumption);
                            } else {

                                $(`#foodConsumption_${item.id}`).val('');
                            }
                        },
                        error: function (error) {
                            console.error("Error fetching energy consumption:", error);
                        }
                    });
                });

            } else {
            }


            // 모달에 기본 데이터 추가
            $("#modalContent").html(content);
            $("#openModalBtn").click(); // 모달 열기
        },
        error: function () {
            $("#modalContent").html("일정을 불러오는 데 실패했습니다.");
            $("#openModalBtn").click(); // 모달 버튼 클릭 이벤트 트리거
        }
    });
}


function createFoodRow(selectedDate, memberId) {
    let foodId = new Date().getTime(); // 새 음식 ID 생성
    return `<div id="food_${foodId}">
            음식명: <input type="text" id="foodName_${foodId}" onkeyup="updateFoodConsumption(${foodId})" disabled /><br>
            섭취량: <input type="text" id="foodHundredGram_${foodId}" onkeyup="calculateFoodCalories(${foodId})" onfocus="calculateFoodCalories(${foodId})"/>
            <input type="text" id="foodConsumption_${foodId}" disabled/><br>
            섭취 칼로리: <input type="text" id="foodCalories_${foodId}" onkeyup="calculateFoodCalories(${foodId})" disabled /><br>
            <button onClick="openSelectFoodWindow('${foodId}')">음식 선택</button>
            <button onclick="saveFood('${foodId}','${selectedDate}', ${memberId})">저장</button>
            <button onclick="deleteFoodRow('${foodId}')">취소</button></div>`
}

function addFoodRow(selectedDate, memberId) {
    $("#modalContent").append(createFoodRow(selectedDate,memberId));
}

function deleteFoodRow(foodId) {
    $(`#food_${foodId}`).remove();
}