
    const csrfToken = $('meta[name="_csrf"]').attr('content');
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    const colorMap = {};

    function getColorForTitle(title) {

        if (colorMap[title]) {
            return colorMap[title];
        }

        const letters = '0123456789ABCDE';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }

        colorMap[title] = color;
        return color;
    }

    const color2Map = {};

    function getColorForId(id) {

        if (color2Map[id]) {
            return color2Map[id];
        }

        const letters = '0123456789ABCDE';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }

        color2Map[id] = color;
        return color;
    }

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
            url: "/api/calendar/memo/date",
            method: "POST",
            contentType: "application/json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            data: JSON.stringify(requestData),
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-MemoButton-button").click();
            },
            error: function () {
                alert("메모 저장에 실패했습니다.");
            }
        });
    }

    function updateMemo(memoId, selectedDate, memberId) {
        let title = $(`#memoTitle_${memoId}`).val();
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
            url: "/api/calendar/memo/date",
            method: "PUT",
            contentType: "application/json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            data: JSON.stringify(requestData),
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-MemoButton-button").click();
            },
            error: function () {
                alert("메모 업데이트에 실패했습니다.");
            }
        });
    }

    function deleteMemo(memoId) {

        $.ajax({
            url: "/api/calendar/memo/date/" + memoId,
            method: "DELETE",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-MemoButton-button").click();
            },
            error: function () {
                alert("메모 삭제에 실패했습니다.");
            }
        });
    }

    function saveFood(foodId, selectedDate, memberId) {
        let id = $(`#foodId_${foodId}`).val();
        let name = $(`#foodName_${foodId}`).val();
        let hundredGram = $(`#foodHundredGram_${foodId}`).val();
        let calories = $(`#foodCalories_${foodId}`).val();

        let requestData = {
            name: name,
            hundredGram: hundredGram,
            calories: calories,
            date: selectedDate,
            memberId: memberId,
            foodId: id
        };

        $.ajax({
            url: "/api/calendar/food/date",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestData),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-FoodButton-button").click();
            },
            error: function () {
                alert("식단 저장에 실패했습니다.");
            }
        });
    }

    function updateFood(foodId, selectedDate, memberId) {
        let id = $(`#foodId_${foodId}`).val();
        let name = $(`#foodName_${foodId}`).val();
        let hundredGram = $(`#foodHundredGram_${foodId}`).val();
        let calories = $(`#foodCalories_${foodId}`).val();

        let requestData = {
            id: foodId,
            name: name,
            hundredGram: hundredGram,
            calories: calories,
            date: selectedDate,
            memberId: memberId,
            foodId: id
        };


        $.ajax({
            url: "/api/calendar/food/date",
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(requestData),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-FoodButton-button").click();
            },
            error: function () {
                alert("식단 업데이트에 실패했습니다.");
            }
        });
    }

    function deleteFood(foodId) {

        $.ajax({
            url: "/api/calendar/food/date/" + foodId,
            method: "DELETE",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-FoodButton-button").click();
            },
            error: function () {
                alert("식단 삭제에 실패했습니다.");
            }
        });
    }


    function saveExercise(exerciseId, selectedDate, memberId) {
        let id = $(`#exerciseId_${exerciseId}`).val();
        let name = $(`#exerciseName_${exerciseId}`).val();
        let time = $(`#exerciseTime_${exerciseId}`).val()
        let calories = $(`#exerciseCalories_${exerciseId}`).val();

        let requestData = {
            name: name,
            time: time,
            calories: calories,
            date: selectedDate,
            memberId: memberId,
            exerciseId: id
        };

        $.ajax({
            url: "/api/calendar/exercise/date",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestData),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-ExerButton-button").click();
            },
            error: function () {
                alert("운동 저장에 실패했습니다.");
            }
        });
    }

    function updateExercise(exerciseId, selectedDate, memberId) {
        let id = $(`#exerciseId_${exerciseId}`).val();
        let name = $(`#exerciseName_${exerciseId}`).val();
        let time = $(`#exerciseTime_${exerciseId}`).val();
        let calories = $(`#exerciseCalories_${exerciseId}`).val();

        let requestData = {
            id: exerciseId,
            name: name,
            time: time,
            calories: calories,
            date: selectedDate,
            memberId: memberId,
            exerciseId: id
        };

        $.ajax({
            url: "/api/calendar/exercise/date",
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(requestData),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-ExerButton-button").click();
            },
            error: function () {
                alert("운동 업데이트에 실패했습니다.");
            }
        });
    }

    function deleteExercise(exerciseId) {

        $.ajax({
            url: "/api/calendar/exercise/date/" + exerciseId,
            method: "DELETE",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                console.log(response);

                $("#openModalBtn").click();
                $(".fc-ExerButton-button").click();
            },
            error: function () {
                alert("운동 삭제 실패했습니다.");
            }
        });
    }

    function getLesson() {
        let memberId = document.getElementById('memberId').value;

        $.ajax({
            url: '/api/calendar/lesson',
            method: 'GET',
            data: {memberId: memberId},
            success: function (response) {

                let events = response.data.map(item => ({
                    title: item.title,
                    start: new Date(item.date),
                    description: `${item.startTime} - ${item.endTime}`,
                    allDay: true,
                    color: getColorForTitle(item.title)
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
        let selectedDate = date.toISOString().split('T')[0];
        let memberId = document.getElementById('memberId').value;

        $.ajax({
            url: '/api/calendar/lesson/date',
            method: 'GET',
            data: {
                memberId: memberId,
                date: selectedDate
            },
            success: function (response) {

                document.getElementById('eventModalLabel').innerHTML = `<strong>${selectedDate}의 강의</strong>`;
                let content = ``;

                if (response.data.length > 0) {
                    response.data.forEach(item => {
                        content += `<div class="modal-container">`;
                        content += `<div class="modal-list">`;
                        content += `<div>`;
                        content += `<label for="lessonName_${item.id}">강의명</label>`;
                        content += `<input type="text" id="lessonName_${item.id}" value="${item.title}" disabled/>`;
                        content += `</div>`;
                        content += `<div>`;
                        content += `<label for="lessonStartTime_${item.id}">시작시간</label>`;
                        content += `<input type="text" id="lessonStartTime_${item.id}" value="${item.startTime}" disabled/>`;
                        content += `</div>`;
                        content += `<div>`;
                        content += `<label for="lessonEndTime_${item.id}">종료시간</label>`;
                        content += `<input type="text" id="lessonStartTime_${item.id}" value="${item.endTime}" disabled/>`;
                        content += `</div>`;
                        content += `<div>`;
                        content += `<input type="button" class="lessonDetailBtn btn btn-info mt-2" value="강의 상세보기" data-id="${item.id}" /><br>`;
                        content += `</div>`;
                        content += `</div>`;
                        content += `</div>`;
                    });
                } else {
                    content += "해당 날짜에 일정이 없습니다.";
                }

                $("#modalContent").html(content);
                $("#addbtn").html("");
                $("#openModalBtn").click();

                $(".lessonDetailBtn").on("click", function () {
                    let id = $(this).data("id");
                    window.location.href = `/lesson/${id}`;
                });
            },
            error: function () {
                $("#modalContent").html("일정을 불러오는 데 실패했습니다.");
                $("#openModalBtn").click();
            }
        });
    }


    function getMemo() {
        let memberId = document.getElementById('memberId').value;

        console.log(memberId);

        $.ajax({
            url: '/api/calendar/memo',
            method: 'GET',
            data: {memberId: memberId},
            success: function (response) {
                let events = response.data.map(item => ({
                    title: item.title,
                    start: new Date(item.date),
                    description: item.context || "",
                    allDay: true,
                    color: getColorForId(item.id)
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
            url: '/api/calendar/memo/date',
            method: 'GET',
            data: {
                date: selectedDate,
                memberId: memberId
            },
            success: function (response) {

                document.getElementById('eventModalLabel').innerHTML = `<strong>${selectedDate}의 메모</strong>`;
                let content = ``;

                if (response.data.length > 0) {
                    response.data.forEach(item => {
                        content += `<div id="memo_${item.id}">`;
                        content += `<div class="modal-container">`;
                        content += `<div class="modal-list">`;
                        content += `<div class="flex_row">`
                        content += `<label for="memoTitle_${item.id}">제목</label>`;
                        content += `<input type="text" id="memoTitle_${item.id}" class="form-control mb-2" value="${item.title}" placeholder="제목 입력"/>`;
                        content += `<button onClick="toggleMemo(${item.id})" class="btn btn-info mb-2" id="memoToggleOn_${item.id}">메모 보기</button>`
                        content += `<button style="display:none" onClick="toggleMemo(${item.id})" class="btn btn-secondary mb-2" id="memoToggleOff_${item.id}">메모 닫기</button>`
                        content += `</div>`;
                        content += `</div>`;
                        content += `<div class="modal-list">`;
                        content += `<div class="flex_row">`
                        content += `<label style="display:none" for="memoContent_${item.id}" class="memoContent_${item.id}">내용</label>`;
                        content += `<textarea style="display:none" id ="memoContent_${item.id}"  class="form-control mb-2 memoContent_${item.id}" rows="4" placeholder="내용 입력">${item.context}</textarea>`;
                        content += `</div>`;
                        content += `</div>`;
                        content += `<div class="modal-button">`;
                        content += `<button style="display:none" class="btn btn-success memoContent_${item.id}" onclick="updateMemo(${item.id}, '${selectedDate}', ${memberId})">수정</button>`;
                        content += `<button style="display:none" class="btn btn-warning memoContent_${item.id}" onclick="deleteMemo(${item.id})">삭제</button>`;
                        content += `</div>`;
                        content += `</div>`;
                        content += `</div>`;
                    });
                } else {}

                let addbtn = `<div class="add-btn-container">`;
                addbtn += `<div class="add-box" onclick="addMemoRow('${selectedDate}', ${memberId})">`;
                addbtn += `<button class="add-btn" ><i class="bi bi-plus"></i></button></div></div>`;

                $("#modalContent").html(content);
                $("#addbtn").html(addbtn);
                $("#openModalBtn").click();

            },
            error: function () {
                $("#modalContent").html("메모를 불러오는 데 실패했습니다.");
                $("#openModalBtn").click();
            }
        });
    }

    function toggleMemo(memoId) {
        let memoContents = document.getElementsByClassName(`memoContent_${memoId}`);

        for (let i = 0; i < memoContents.length; i++) {
            let memoContent = memoContents[i];
            if (memoContent.style.display === "none" || memoContent.style.display === "") {
                memoContent.style.display = "inline-block";
                document.getElementById(`memoToggleOn_${memoId}`).style.display = "none";
                document.getElementById(`memoToggleOff_${memoId}`).style.display = "inline-block";
            } else {
                memoContent.style.display = "none";
                document.getElementById(`memoToggleOn_${memoId}`).style.display = "inline-block";
                document.getElementById(`memoToggleOff_${memoId}`).style.display = "none";
            }
        }
    }


    function createMemoRow(selectedDate, memberId) {
        let memoId = new Date().getTime();
        return `<div id="memo_${memoId}">
            <div class="modal-container">
                <div class="modal-list">
                    <div class="flex_row">
                        <label for="memoTitle_${memoId}">제목</label>
                        <input type="text" id="memoTitle_${memoId}" class="form-control mb-2" placeholder="제목 입력"/>
                    </div>
                </div>
                <div class="modal-list">
                    <div class="flex_row">
                        <label for="memoContent_${memoId}" class="memoContent_${memoId}">내용</label>
                        <textarea id ="memoContent_${memoId}" class="form-control mb-2 memoContent_${memoId}" rows="4" placeholder="내용 입력"></textarea>
                    </div>
                </div>
                <div class="modal-button">
                    <button class="btn btn-primary memoContent_${memoId}" onclick="saveMemo('${memoId}','${selectedDate}', ${memberId})">저장</button>
                    <button class="btn btn-secondary memoContent_${memoId}" onclick="deleteMemoRow('${memoId}')">취소</button>
                </div>
            </div>
        </div>`
    }

    function addMemoRow(selectedDate, memberId) {
        $("#modalContent").append(createMemoRow(selectedDate, memberId));
    }

    function deleteMemoRow(memoId) {
        $(`#memo_${memoId}`).remove();
    }


    function getExercise() {
        let memberId = document.getElementById('memberId').value;

        $.ajax({
            url: '/api/calendar/exercise',
            method: 'GET',
            data: {memberId: memberId},
            success: function (response) {
                let events = response.data.map(item => ({
                    title: item.name,
                    start: new Date(item.date),
                    description: item.context || "",
                    allDay: true,
                    color: getColorForId(item.id)
                }));
                calendar.removeAllEvents();
                calendar.addEventSource(events);
            },
            error: function () {
                alert('일정을 불러오는 데 실패했습니다.');
            }
        });

        $("#fc-MemoButton-button").css("background-color", "green");
    }

    function getExerciseByDate(date) {
        let selectedDate = date.toISOString().split('T')[0];
        let memberId = document.getElementById('memberId').value;

        $.ajax({
            url: '/api/calendar/exercise/date',
            method: 'GET',
            data: {
                date: selectedDate,
                memberId: memberId
            },

            success: function (response) {

                document.getElementById('eventModalLabel').innerHTML = `<strong>${selectedDate}의 운동</strong>`;
                let content = ``;

                if (response.data.length > 0) {
                    response.data.forEach(item => {
                        content += `<div id="exercise_${item.id}">`;
                        content += `<div class="modal-container">`;
                        content += `<div class="modal-list">`;
                        content += `<div>`;
                        content += `<label for="exerciseName_${item.id}">운동명</label>`;
                        content += `<input type="text" id="exerciseName_${item.id}" value="${item.name}" onkeyup="updateEnergyConsumption(${item.id})" disabled/>`;
                        content += `</div>`;
                        content += `<div>`;
                        content += `<label for="exerciseCalories_${item.id}">소모 칼로리</label>`;
                        content += `<input type="text" id="exerciseCalories_${item.id}" value="${item.calories}" onkeyup="calculateCalories(${item.id})" disabled />`;
                        content += `</div>`;
                        content += `<div>`;
                        content += `<label for="exerciseTime_${item.id}">운동시간(분)</label>`;
                        content += `<input type="text" id="exerciseTime_${item.id}" value="${item.time}" onkeyup="calculateCalories(${item.id})" onfocus="calculateCalories(${item.id})"/>`;
                        content += `</div>`;
                        content += `<input type="hidden" id="energyConsumption_${item.id}" value="${item.energyConsumption}" disabled />`;
                        content += `<input type="hidden" id="exerciseId_${item.id}" value="${item.exerciseId}" disabled/>`;
                        content += `</div>`;
                        content += `<div class="modal-button">`;
                        content += `<button class="btn btn-dark" onClick="openSelectExerciseWindow('${item.id}')">운동 선택</button>`;
                        content += `<button class="btn btn-success" onclick="updateExercise(${item.id}, '${selectedDate}', ${memberId})">수정</button>`;
                        content += `<button class="btn btn-danger" onclick="deleteExercise(${item.id})">삭제</button>`;
                        content += `</div>`;
                        content += `</div>`;
                        content += `</div>`;

                        $("#modalContent").html(content);

                        let selectedExerciseName = $(`#exerciseName_${item.id}`).val();

                        $.ajax({
                            url: "/api/exercise/search",
                            method: "GET",
                            data: {name: selectedExerciseName},
                            success: function (responseData) {

                                if (responseData && responseData.length > 0) {
                                    let energyConsumption = responseData[0].energyConsumption;
                                    $(`#energyConsumption_${item.id}`).val(energyConsumption);
                                }
                            },
                            error: function (error) {
                                console.error("Error fetching energy consumption:", error);
                            }
                        });
                    });
                } else {}

                let addbtn= `<div class="add-btn-container">`;
                addbtn += `<div class="add-box" onclick="addExerciseRow('${selectedDate}', ${memberId})">`;
                addbtn += `<button class="add-btn"><i class="bi bi-plus"></i></button></div></div>`;

                $("#modalContent").html(content);
                $("#addbtn").html(addbtn);
                $("#openModalBtn").click();
            },
            error: function () {
                $("#modalContent").html("일정을 불러오는 데 실패했습니다.");
                $("#openModalBtn").click();
            }
        });
    }

    function createExerciseRow(selectedDate, memberId) {
        let exerciseId = new Date().getTime();
        return `
        <div id="exercise_${exerciseId}">
            <div class="modal-container">
                <div class="modal-list">
                    <div>
                        <label for="exerciseName_${exerciseId}">운동명</label>
                        <input type="text" id="exerciseName_${exerciseId}" onkeyup="updateEnergyConsumption(${exerciseId})" disabled/>
                    </div>
                    <div>
                        <label for="exerciseCalories_${exerciseId}">소모 칼로리</label>
                        <input type="text" id="exerciseCalories_${exerciseId}" onkeyup="calculateCalories(${exerciseId})" disabled />
                    </div>
                    <div>
                        <label for="exerciseTime_${exerciseId}">운동시간(분)</label>
                        <input type="text" id="exerciseTime_${exerciseId}" onkeyup="calculateCalories(${exerciseId})" onfocus="calculateCalories(${exerciseId})"/>
                    </div>
                    <input type="hidden" id="energyConsumption_${exerciseId}" disabled/>
                    <input type="hidden" id="exerciseId_${exerciseId}" disabled/>
                </div>

                <div class="modal-button">
                    <button class="btn btn-dark" onClick="openSelectExerciseWindow('${exerciseId}')">운동 선택</button>
                    <button class="btn btn-primary" onclick="saveExercise('${exerciseId}','${selectedDate}', ${memberId})">저장</button>
                    <button class="btn btn-secondary" onclick="deleteExerciseRow('${exerciseId}')">취소</button>
                </div>
            </div>
        </div>`;
    }

    function addExerciseRow(selectedDate, memberId) {
        $("#modalContent").append(createExerciseRow(selectedDate, memberId));
    }

    function deleteExerciseRow(exerciseId) {
        $(`#exercise_${exerciseId}`).remove();
    }


    function getFood() {
        let memberId = document.getElementById('memberId').value;

        $.ajax({
            url: '/api/calendar/food',
            method: 'GET',
            data: {memberId: memberId},
            success: function (response) {
                let events = response.data.map(item => ({
                    title: item.name,
                    start: new Date(item.date),
                    description: item.context || "",
                    allDay: true,
                    color: getColorForId(item.id)
                }));
                calendar.removeAllEvents();
                calendar.addEventSource(events);
            },
            error: function () {
                alert('일정을 불러오는 데 실패했습니다.');
            }
        });
    }

    function getFoodByDate(date) {
        let selectedDate = date.toISOString().split('T')[0];
        let memberId = document.getElementById('memberId').value;

        $.ajax({
            url: '/api/calendar/food/date',
            method: 'GET',
            data: {
                date: selectedDate,
                memberId: memberId
            },
            success: function (response) {

                document.getElementById('eventModalLabel').innerHTML = `<strong>${selectedDate}의 식단</strong>`;
                let content = ``;

                if (response.data.length > 0) {
                    response.data.forEach(item => {
                        console.log(item.foodId);
                        content += `<div id="food_${item.id}">`
                        content += `<div class="modal-container">`;
                        content += `<div class="modal-list">`;
                        content += `<div>`;
                        content += `<label for="foodName_${item.id}">음식명</label>`;
                        content += `<input type="text" id="foodName_${item.id}" value="${item.name}"  onKeyUp="updateFoodConsumption(${item.id})" disabled/>`;
                        content += `</div>`;
                        content += `<div>`
                        content += `<label for="foodCalorie_${item.id}">섭취 칼로리</label>`;
                        content += `<input type="text" id="foodCalories_${item.id}" value="${item.calories}" onkeyup="calculateFoodCalories(${item.id})" disabled />`;
                        content += `</div>`;
                        content += `<div>`;
                        content += `<label for="foodHundredGram_${item.id}">섭취량</label>`;
                        content += `<input type="text" id="foodHundredGram_${item.id}" value="${item.hundredGram}" onkeyup="calculateFoodCalories(${item.id})" onfocus="calculateFoodCalories(${item.id})"/>`;
                        content += `</div>`;
                        content += `<input type="hidden" id="foodConsumption_${item.id}" value="${item.cal}" disabled/>`;
                        content += `<input type="hidden" id="foodId_${item.id}" value="${item.foodId}" disabled/>`
                        content += `</div>`;
                        content += `<div class="modal-button">`;
                        content += `<button class="btn btn-dark" onClick="openSelectFoodWindow('${item.id}')">음식 선택</button>`;
                        content += `<button class="btn btn-success" onclick="updateFood(${item.id}, '${selectedDate}', ${memberId})">수정</button>`;
                        content += `<button class="btn btn-danger" onclick="deleteFood(${item.id})">삭제</button>`
                        content += `</div>`;
                        content += `</div>`;
                        content += `</div>`;

                        $("#modalContent").html(content);

                        let selectedFoodName = $(`#foodName_${item.id}`).val();

                        $.ajax({
                            url: "/api/food/search",
                            method: "GET",
                            data: {name: selectedFoodName},
                            success: function (responseData) {

                                if (responseData && responseData.length > 0) {
                                    let foodConsumption = responseData[0].calories;
                                    $(`#foodConsumption_${item.id}`).val(foodConsumption);
                                }
                            },
                            error: function (error) {
                                console.error("Error fetching energy consumption:", error);
                            }
                        });
                    });
                } else {
                }

                let addbtn = `<div class="add-btn-container">`;
                addbtn += `<div class="add-box" onclick="addFoodRow('${selectedDate}', ${memberId})">`;
                addbtn += `<button class="add-btn"><i class="bi bi-plus"></i></button></div></div>`

                $("#modalContent").html(content);
                $("#addbtn").html(addbtn);
                $("#openModalBtn").click();
            },
            error: function () {
                $("#modalContent").html("일정을 불러오는 데 실패했습니다.");
                $("#openModalBtn").click();
            }
        });
    }


    function createFoodRow(selectedDate, memberId) {
        let foodId = new Date().getTime();
        return `
        <div id="food_${foodId}">
            <div class="modal-container">
                <div class="modal-list">
                    <div>
                        <label for="foodName_${foodId}">음식명</label>
                        <input type="text" id="foodName_${foodId}" onkeyup="updateFoodConsumption(${foodId})" disabled />
                    </div>
                    <div>
                        <label for="foodCalories_${foodId}">섭취 칼로리</label>
                        <input type="text" id="foodCalories_${foodId}" onkeyup="calculateFoodCalories(${foodId})" disabled />
                    </div>
                    <div>
                        <label for="foodHundredGram_${foodId}">섭취량</label>
                        <input type="text" id="foodHundredGram_${foodId}" onkeyup="calculateFoodCalories(${foodId})" onfocus="calculateFoodCalories(${foodId})"/>
                    </div>   
                    <input type="hidden" id="foodConsumption_${foodId}" disabled/>
                    <input type="hidden" id="foodId_${foodId}" disabled/>
                </div>
                <div class="modal-button">
                    <button class="btn btn-dark" onClick="openSelectFoodWindow('${foodId}')">음식 선택</button>
                    <button class="btn btn-primary" onclick="saveFood('${foodId}','${selectedDate}', ${memberId})">저장</button>
                    <button class="btn btn-secondary" onclick="deleteFoodRow('${foodId}')">취소</button>
                </div>
            </div>
        </div>`;
    }

    function addFoodRow(selectedDate, memberId) {
        $("#modalContent").append(createFoodRow(selectedDate, memberId));
    }

    function deleteFoodRow(foodId) {
        $(`#food_${foodId}`).remove();
    }