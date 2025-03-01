function openSelectFoodWindow() {
    window.open("/calendar/select_food", "SelectFood", "width=600,height=400,scrollbars=yes,resizable=yes");
}

function openSelectExerciseWindow() {
    window.open("/calendar/select_exercise", "SelectExercise", "width=600,height=400,scrollbars=yes,resizable=yes");
}


function calculateCalories(id = null) {
    let time = id ? $(`#exerciseTime_${id}`).val() : $("#exerciseTime").val();
    let weight = $("#userWeight").val();
    let energyConsumption = id ? $(`#energy_consumption_${id}`).val() : $("#energy_consumption").val();

    if (time && weight && energyConsumption) {
        let calories = energyConsumption * 3.5 * weight * time * 0.005;
        if (id) {
            $(`#exerciseCalories_${id}`).val(calories.toFixed(2));
        } else {
            $("#exerciseCalories").val(calories.toFixed(2));
        }
    }
}

// 🔹 운동 선택 시 energy_consumption 업데이트
function updateEnergyConsumption(id = null) {
    let energyConsumptionValue = $("#selectedExerciseEnergy").val();
    if (id) {
        $(`#energy_consumption_${id}`).val(energyConsumptionValue);
        calculateCalories(id);
    } else {
        $("#energy_consumption").val(energyConsumptionValue);
        calculateCalories();
    }
}

function calculateFoodCalories(id = null) {
    let hundredGram = id ? $(`#foodHundredGram_${id}`).val() : $("#foodHundredGram").val();
    let foodConsumption = id ? $(`#food_consumption_${id}`).val() : $("#food_consumption").val();

    if (hundredGram && foodConsumption) {
        let calories = (hundredGram * foodConsumption * 0.01);
        console.log("칼로리 계산 : " + calories);
        if (id) {
            $(`#foodCalories_${id}`).val(calories.toFixed(2));
        } else {
            $("#foodCalories").val(calories.toFixed(2));
        }
    }
}

function updateFoodConsumption(id = null) {
    let foodConsumptionValue = $("#selectedFoodEnergy").val();

    console.log("foodConsumptionValue: ", foodConsumptionValue);

    if (id) {
        $(`#food_consumption_${id}`).val(foodConsumptionValue); // foodConsumption 값 갱신
        calculateFoodCalories(id);  // foodConsumption 값으로 칼로리 계산
    } else {
        $("#food_consumption").val(foodConsumptionValue);
        calculateFoodCalories();  // foodConsumption 값으로 칼로리 계산
    }
}
