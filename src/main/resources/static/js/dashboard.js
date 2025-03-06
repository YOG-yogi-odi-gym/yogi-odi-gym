document.addEventListener('DOMContentLoaded', function() {
    const exerciseChartCanvas = document.getElementById('exerciseChart').getContext('2d');
    const myExerciseData = myExerciseTimeAverages;
    const exerciseAverageData = exerciseTimeAverages;

    function labels() {
        const labels = [];
        const today = new Date();

        for (let i = 4; i > 0; i--) {
            const date = new Date(today);
            date.setDate(today.getDate() - i);

            const formattedDate = `${date.getMonth() + 1}/${date.getDate()}`;
            labels.push(formattedDate);
        }

        return labels;
    }

    new Chart(exerciseChartCanvas, {
        type: 'line',
        data: {
            labels: labels(),
            datasets: [
                {
                    label: '내 운동량',
                    data: myExerciseData,
                    borderColor: '#914DF0',
                    backgroundColor: 'rgba(106, 90, 205, 0.1)',
                    borderWidth: 2.5,
                    pointRadius: 5,
                    pointBackgroundColor: '#914DF0',
                    pointHoverRadius: 7,
                    tension: 0
                },
                {
                    label: '회원 평균',
                    data: exerciseAverageData,
                    borderColor: '#3FAEA3',
                    backgroundColor: 'rgba(32, 178, 170, 0.1)',
                    borderWidth: 2.5,
                    pointRadius: 5,
                    pointBackgroundColor: '#3FAEA3',
                    pointHoverRadius: 7,
                    tension: 0
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        font: {
                            size: 12
                        }
                    }
                },
                tooltip: {
                    backgroundColor: 'rgba(0,0,0,0.7)',
                    titleFont: {
                        size: 13
                    },
                    bodyFont: {
                        size: 12
                    }
                }
            },
            scales: {
                y: {
                    min: 0,
                    max: 120,
                    ticks: {
                        stepSize: 30,
                        font: {
                            size: 11
                        }
                    },
                    grid: {
                        color: 'rgba(200, 200, 200, 0.2)',
                        borderDash: [5, 5]
                    }
                },
                x: {
                    ticks: {
                        color: '#666',
                        font: {
                            size: 11
                        }
                    },
                    grid: {
                        display: false
                    }
                }
            },
            interaction: {
                mode: 'nearest',
                intersect: false
            },
            height: 330
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const calorieChartCanvas = document.getElementById('calorieChart').getContext('2d');
    const myCalorieData = myCalories;
    const calorieAverageData = calorieAverages;

    function labels() {
        const labels = [];
        const today = new Date();

        for (let i = 4; i > 0; i--) {
            const date = new Date(today);
            date.setDate(today.getDate() - i);

            const formattedDate = `${date.getMonth() + 1}/${date.getDate()}`;
            labels.push(formattedDate);
        }

        return labels;
    }

    new Chart(calorieChartCanvas, {
        type: 'line',
        data: {
            labels: labels(),
            datasets: [
                {
                    label: '내 칼로리',
                    data: myCalorieData,
                    borderColor: '#914DF0',
                    backgroundColor: 'rgba(106, 90, 205, 0.1)',
                    borderWidth: 2.5,
                    pointRadius: 5,
                    pointBackgroundColor: '#914DF0',
                    pointHoverRadius: 7,
                    tension: 0
                },
                {
                    label: '회원 평균',
                    data: calorieAverageData,
                    borderColor: '#F4A26C',
                    backgroundColor: 'rgba(255, 245, 235, 0.3)',
                    borderWidth: 2.5,
                    pointRadius: 5,
                    pointBackgroundColor: '#F4A26C',
                    pointHoverRadius: 7,
                    tension: 0
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        font: {
                            size: 12
                        }
                    }
                },
                tooltip: {
                    backgroundColor: 'rgba(0,0,0,0.7)',
                    titleFont: {
                        size: 13
                    },
                    bodyFont: {
                        size: 12
                    }
                }
            },
            scales: {
                y: {
                    min: 0,
                    max: 1000,
                    ticks: {
                        stepSize: 250,
                        font: {
                            size: 11
                        }
                    },
                    grid: {
                        color: 'rgba(200, 200, 200, 0.2)',
                        borderDash: [5, 5]
                    }
                },
                x: {
                    ticks: {
                        color: '#666',
                        font: {
                            size: 11
                        }
                    },
                    grid: {
                        display: false
                    }
                }
            },
            interaction: {
                mode: 'nearest',
                intersect: false
            },
            height: 330
        }
    });
});