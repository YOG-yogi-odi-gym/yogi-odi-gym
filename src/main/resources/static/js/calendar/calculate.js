function openSelectFoodWindow(foodId) {
    window.open(`/calendar/select_food?foodId=${foodId}`, "SelectFood", "width=600,height=400,scrollbars=yes,resizable=yes");
}

function openSelectExerciseWindow(exerciseId) {
    window.open(`/calendar/select_exercise?exerciseId=${exerciseId}`, "SelectExercise", "width=600,height=400,scrollbars=yes,resizable=yes");
}


function calculateCalories(id) {
    let time = $(`#exerciseTime_${id}`).val();
    let weight = $("#userWeight").val();
    let energyConsumption = $(`#energyConsumption_${id}`).val();

    if (time && weight && energyConsumption) {
        let calories = energyConsumption * 3.5 * weight * time * 0.005;
        $(`#exerciseCalories_${id}`).val(calories.toFixed(2));
    }
}

function updateEnergyConsumption(id) {
    let energyConsumptionValue = $("#selectedExerciseEnergy").val();

        $(`#energyConsumption_${id}`).val(energyConsumptionValue);
        calculateCalories(id);
}

function calculateFoodCalories(id) {
    let hundredGram = $(`#foodHundredGram_${id}`).val();
    let foodConsumption = $(`#foodConsumption_${id}`).val();

    if (hundredGram && foodConsumption) {
        let calories = (hundredGram * foodConsumption * 0.01);
        $(`#foodCalories_${id}`).val(calories.toFixed(2));
    }
}

function updateFoodConsumption(id) {
    let foodConsumptionValue = $("#selectedFoodEnergy").val();

    $(`#foodConsumption_${id}`).val(foodConsumptionValue);
    calculateFoodCalories(id);
}
