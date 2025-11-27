
// --- UTILITY: Get Input Values ---
function getEmployeeForm() {
    return {
        empid: document.getElementById('inp-id').value,
        empname: document.getElementById('inp-name').value,
        empsalary: document.getElementById('inp-salary').value,
        empage: document.getElementById('inp-age').value,
        empcity: document.getElementById('inp-city').value
    };
}

// --- UI HELPER: Render Table ---
function renderTable(data) {
    const tbody = document.getElementById('table-body');
    tbody.innerHTML = ''; // Clear current rows
    const status = document.getElementById('status-msg');

    if (!data || (Array.isArray(data) && data.length === 0)) {
        tbody.innerHTML = `<tr><td colspan="5" style="text-align:center; padding:2rem;">No Records Found</td></tr>`;
        status.textContent = "Empty Result";
        status.style.background = "#f3f4f6";
        status.style.color = "#6b7280";
        return;
    }

    // Handle single object vs array
    const dataArray = Array.isArray(data) ? data : [data];

    dataArray.forEach(emp => {
        const row = `
    <tr>
        <td><strong>#${emp.empid}</strong></td>
        <td style="font-weight:500; color:var(--primary-dark)">${emp.empname}</td>
        <td>${emp.empsalary}</td>
        <td>${emp.empage}</td>
        <td><span style="background:#e0e7ff; color:#3730a3; padding:2px 8px; border-radius:4px; font-size:0.8rem">${emp.empcity}</span></td>
    </tr>
    `;
        tbody.innerHTML += row;
    });

    status.textContent = `Loaded ${dataArray.length} Row(s)`;
    status.style.background = "#d1fae5";
    status.style.color = "#065f46";
}

// ==========================================
//  YOUR SPRING BOOT INTEGRATION FUNCTIONS
// ==========================================
// 1. CREATE EMPLOYEE
async function createEmployee() {
    const empData = getEmployeeForm();

    // Remove empid (because backend auto-generates it)
    delete empData.empid;

    // VALIDATION: Only validate the fields user must enter
    if (!empData.empname || !empData.empsalary || !empData.empage || !empData.empcity) {
        alert("Please fill all fields except ID.");
        return;
    }

    console.log("SENDING TO BACKEND (POST):", empData);

    try {
        const response = await fetch('http://localhost:8080/api/employee', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(empData)
        });

        if (!response.ok) {
            alert("Error creating employee!");
            return;
        }

        alert("Employee Created!");
        getAllEmployees(); // Refresh table

    } catch (error) {
        console.error('Error:', error);
        alert("Failed to connect to backend!");
    }
}


// 2. GET ALL EMPLOYEES
async function getAllEmployees() {
    console.log("FETCHING ALL EMPLOYEES...");


    const response = await fetch('http://localhost:8080/api/employee');
    const data = await response.json();
    renderTable(data);



}

// 2b. GET BY ID
async function getById() {
    const id = document.getElementById('search-id').value;
    if (!id) return alert("Enter ID");

    console.log(`FETCHING ID: ${id}`);


    const response = await fetch(`http://localhost:8080/api/employee/${id}`);
    const data = await response.json();
    renderTable(data);



}

// 3. UPDATE EMPLOYEE
async function updateEmployee() {
    const empData = getEmployeeForm();
    if (!empData.empid) return alert("Employee ID is required for Update");

    console.log(`UPDATING ID ${empData.empid} WITH:`, empData);


    await fetch(`http://localhost:8080/api/employee/${empData.empid}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(empData)
    });
    getAllEmployees();

    alert("Update Request Sent");
}

// 4. DELETE ALL
async function deleteAll() {
    if (!confirm("Are you sure you want to delete ALL employees?"))
        return;

    console.log("DELETING ALL EMPLOYEES");


    await fetch('http://localhost:8080/api/employee', { method: 'DELETE' });
    renderTable([]);

    renderTable([]); // Clear table UI
}

// 4b. DELETE BY ID
async function deleteById() {
    const id = document.getElementById('delete-id').value;
    if (!id) return alert("Enter ID to delete");

    console.log(`DELETING ID: ${id}`);


    await fetch(`http://localhost:8080/api/employee/${id}`, { method: 'DELETE' });
    getAllEmployees();

    alert(`Delete request for ID ${id} sent.`);
}

// 5. GET BY CITY
async function getByCity() {
    const city = document.getElementById('search-city').value;
    if (!city) return alert("Enter City");

    console.log(`FETCHING CITY: ${city}`);


    const response = await fetch(`http://localhost:8080/api/employee/city/${city}`);
    const data = await response.json();
    renderTable(data);



}

