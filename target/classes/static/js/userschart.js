document.addEventListener('DOMContentLoaded', (event) => {
            const dataParsed = JSON.parse(dailyUserData || '[]');

            const labels = dataParsed.map(data => data.date);
            const data = dataParsed.map(data => data.userCount);

            const ctx = document.getElementById('dailyUsersChart').getContext('2d');
            const dailyUsersChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Daily Users Added',
                        data: data,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
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