
    function showAccount(id) {
    fetch('/accounts/' + id)
        .then(response => response.json()) // Parse response as JSON
        .then(data => {
            // Handle JSON data here
            console.log(data); // Example: assuming data is an array of accounts
            // Process data as needed
        })
        .catch(error => {
            console.error('Error fetching accounts:', error);
        });
        fetch('/accounts/' + id)
            .then(response => response.json())
            .then(data => {
                const accountsTableBody = document.getElementById('accountsTableBody');
                accountsTableBody.innerHTML = ''; // Clear existing rows
                data.forEach(account => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${account.name}</td>
                        <td>${account.accountType}</td>
                        <td>${account.balance}</td>
                        <td>${account.accountNumber}</td>
                        <td>${account.rateOfInterest}</td>
                        <td>${account.branchId}</td>
                        <td>${account.openingDate}</td>
                        <td>${account.closingDate}</td>
                        <td><span class="badge bg-label-primary me-1">Active</span></td>
                        <td>
                            <div class="dropdown">
                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                    <i class="bx bx-dots-vertical-rounded"></i>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="javascript:void(0);"><i class="bx bx-edit-alt me-1"></i> Edit</a>
                                    <a class="dropdown-item" href="javascript:void(0);"><i class="bx bx-trash me-1"></i> Delete</a>
                                </div>
                            </div>
                        </td>
                    `;
                    accountsTableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error:', error));
    }
