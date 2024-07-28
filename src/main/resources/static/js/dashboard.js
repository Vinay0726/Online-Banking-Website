document.addEventListener('DOMContentLoaded', function() {
    // Parse the JSON data passed from the controller
    var dailyUserData = JSON.parse(dailyUserDataJson);

    // Extract the dates and user counts
    var dates = dailyUserData.map(function(data) { return data.date; });
    var userCounts = dailyUserData.map(function(data) { return data.userCount; });

    // Create the chart
    var ctx = document.getElementById('user-chart').getContext('2d');
    var userChart = new Chart(ctx, {
        type: 'line', // you can change this to 'bar', 'pie', etc.
        data: {
            labels: dates,
            datasets: [{
                label: 'Daily User Registrations',
                data: userCounts,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});
