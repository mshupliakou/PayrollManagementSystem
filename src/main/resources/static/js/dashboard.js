// --- FUNKCJA ZMIANY ZAKŁADEK ---
function openTab(evt, tabName) {
    let i, tabcontent, tablinks;

    // 1. Ukryj wszystkie treści zakładek
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // 2. Usuń klasę "active" z przycisków
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // 3. Znajdź docelową zakładkę
    const targetTab = document.getElementById(tabName);

    // Zabezpieczenie przed brakiem elementu
    if (!targetTab) {
        console.warn("Nie znaleziono zakładki o ID: " + tabName);
        return;
    }

    // Logika wyświetlania (flex vs block)
    if (targetTab.classList.contains('management-flex-container')) {
        targetTab.style.display = "flex";
    } else {
        targetTab.style.display = "block";
    }

    // Dodaj klasę active do klikniętego przycisku (jeśli funkcja wywołana kliknięciem)
    if (evt && evt.currentTarget) {
        evt.currentTarget.className += " active";
    }

    // --- OBSŁUGA SIDEBARÓW (MENU BOCZNE) ---
    const oldSidebar = document.getElementById("managementSidebar");
    if (oldSidebar) {
        oldSidebar.style.display = (tabName === "Management") ? "block" : "none";
    }

    const projectSidebar = document.getElementById("projectSidebar");
    if (projectSidebar) {
        projectSidebar.style.display = (tabName === "Projects") ? "block" : "none";
    }

    const salariesSidebar = document.getElementById("salariesSidebar");
    if (salariesSidebar) {
        salariesSidebar.style.display = (tabName === "Salaries") ? "block" : "none";
    }
}

// --- INICJALIZACJA PRZY ŁADOWANIU STRONY ---
document.addEventListener("DOMContentLoaded", function() {

    // 1. Pobieramy parametr ?tab=... z URL
    const urlParams = new URLSearchParams(window.location.search);
    const activeTab = urlParams.get('tab'); // Używamy zmiennej 'activeTab' wszędzie

    if (activeTab) {
        // === A. ZAKŁADKA: GODZINY PRACY (Work Hours) ===
        if (activeTab === 'work_hours') {
            const btn = document.getElementById('work_hours-tab-btn');
            if (btn) btn.click();

            setTimeout(function() {
                const table = document.getElementById('myWorkHoursTable');
                if (table) table.scrollIntoView({ behavior: 'smooth' });
            }, 100);
        }

        // === B. ZAKŁADKA: PROJEKTY I TYPY PRACY ===
        else if (['projects', 'work_types', 'approvals'].includes(activeTab)) {
            const btn = document.getElementById('projects-tab-btn');
            if (btn) btn.click();

            setTimeout(function() {
                if (activeTab === 'projects') {
                    const el = document.getElementById('section-projects'); // lub projectsTable
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
                if (activeTab === 'work_types') {
                    const el = document.getElementById('section-work_types'); // lub workTypesTable
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
                if (activeTab === 'approvals') {
                    const el = document.getElementById('section-approvals'); // lub approvalsTable
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
            }, 100);
        }

        // === C. ZAKŁADKA: WYPŁATY KSIĘGOWEGO (Salaries / Types / Statuses) ===
        else if (['salaries', 'types', 'statuses'].includes(activeTab)) {
            const btn = document.getElementById('salaries-tab-btn');
            if (btn) btn.click();

            setTimeout(function() {
                if (activeTab === 'types') {
                    const el = document.getElementById('section-payment_types');
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
                if (activeTab === 'statuses') {
                    const el = document.getElementById('section-payment_statuses');
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
                // Dla samego 'salaries' nie trzeba przewijać, lub można do góry
            }, 100);
        }

        // === D. ZAKŁADKA: ZARZĄDZANIE (Management: Roles, Users, Depts) ===
        else if (['management', 'roles', 'users', 'departments', 'positions'].includes(activeTab)) {
            const btn = document.getElementById('management-tab-btn');
            if (btn) btn.click();

            setTimeout(function() {
                if (activeTab === 'roles') {
                    const el = document.getElementById('myRolesTable');
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
                if (activeTab === 'departments') {
                    const el = document.getElementById('myDepartmentsTable');
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
                if (activeTab === 'positions') {
                    const el = document.getElementById('myPositionsTable');
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
                if (activeTab === 'users') {
                    const el = document.getElementById('myUsersTable');
                    if (el) el.scrollIntoView({ behavior: 'smooth' });
                }
            }, 100);
        }

        // === E. ZAKŁADKA: MOJE WYPŁATY (User) ===
        else if (activeTab === 'salary') {
            const btn = document.getElementById('my_salary-tab-btn');
            if (btn) btn.click();
        }

    } else {
        // === BRAK PARAMETRU W URL - OTWIERAMY DOMYŚLNĄ ===
        const firstAvailableTabButton = document.querySelector('.tablinks');
        if (firstAvailableTabButton) {
            const onClickAttr = firstAvailableTabButton.getAttribute('onclick');
            // Wyciągamy ID z tekstu onclick="openTab(event, 'ID_ZAKLADKI')"
            const match = onClickAttr.match(/'([^']+)'/);

            if (match && match[1]) {
                const defaultTabId = match[1];
                openTab(null, defaultTabId);
                firstAvailableTabButton.classList.add("active");
            }
        }
    }
});

// --- FUNKCJE MODALI ---
function openModal(modalId) {
    var modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = "block";
        // Fix dla DataTables w modalu - przeliczenie szerokości
        setTimeout(function() {
            if (typeof $ !== 'undefined') {
                $(modal).find('table.dataTable').DataTable().columns.adjust();
            }
        }, 100);
    } else {
        console.error("Nie znaleziono modala o ID: " + modalId);
    }
}

function closeModal(modalId) {
    var modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = "none";
    }
}

// Zamykanie modala przy kliknięciu w tło
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = "none";
    }
}