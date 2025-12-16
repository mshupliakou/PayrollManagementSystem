// changing tabs function
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

    // --- NAPRAWA BŁĘDU ---
    // Jeśli nie znaleziono elementu o danym ID, przerwij funkcję, żeby nie było błędu w konsoli
    if (!targetTab) {
        console.warn("Nie znaleziono zakładki o ID: " + tabName);
        return;
    }
    // ---------------------

    // Logika wyświetlania (flex vs block)
    if (targetTab.classList.contains('management-flex-container')) {
        targetTab.style.display = "flex";
    } else {
        targetTab.style.display = "block";
    }

    // Dodaj klasę active do klikniętego przycisku
    if (evt && evt.currentTarget) {
        evt.currentTarget.className += " active";
    }

    // Obsługa sidebarów (Menu boczne)
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

// Inicjalizacja przy ładowaniu strony
document.addEventListener("DOMContentLoaded", function() {

    // Sprawdzamy, czy w URL jest parametr ?tab=...
    const urlParams = new URLSearchParams(window.location.search);
    const activeTabFromUrl = urlParams.get('tab');

    // Jeśli jest parametr w URL, spróbuj otworzyć tę zakładkę
    if (activeTabFromUrl) {
        // Mapowanie parametrów URL na ID przycisków
        let btnId = null;
        if (activeTabFromUrl === 'work_hours') btnId = 'work_hours-tab-btn';
        else if (activeTabFromUrl === 'salary') btnId = 'my_salary-tab-btn'; // Poprawka ID
        else if (activeTabFromUrl === 'projects') btnId = 'projects-tab-btn';
        else if (activeTabFromUrl === 'management' || ['roles', 'users', 'departments'].includes(activeTabFromUrl)) {
            btnId = 'management-tab-btn';
        }
        else if (activeTabFromUrl === 'salaries' || ['types', 'statuses'].includes(activeTabFromUrl)) {
            btnId = 'salaries-tab-btn';
        }

        const btn = document.getElementById(btnId);
        if (btn) {
            btn.click();
            return; // Kończymy, żeby nie otwierać domyślnej
        }
    }

    // Jeśli nie ma parametru w URL, otwórz pierwszą dostępną zakładkę
    const firstAvailableTabButton = document.querySelector('.tablinks');
    if (firstAvailableTabButton) {
        const onClickAttr = firstAvailableTabButton.getAttribute('onclick');
        // Wyciągamy ID z tekstu onclick="openTab(event, 'ID_ZAKLADKI')"
        const match = onClickAttr.match(/'([^']+)'/);

        if (match && match[1]) {
            const defaultTabId = match[1];
            // Wywołujemy funkcję bezpiecznie
            openTab(null, defaultTabId);
            firstAvailableTabButton.classList.add("active");
        }
    }
});

// Funkcje do Modali
function openModal(modalId) {
    var modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = "block";
        // Fix dla DataTables w modalu
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