document.addEventListener('DOMContentLoaded', (event) => {
    const selectElements = document.querySelectorAll('select[name="statusAposta"]');

    function updateSelectClass(select) {
        select.classList.remove('green-status', 'red-status', 'meio-green-status', 'meio-red-status', 'devolvida-status', 'cashout-status', 'pre-live-status');
        switch (select.value) {
            case 'Green':
                select.classList.add('green-status');
                break;
            case 'Red':
                select.classList.add('red-status');
                break;
            case 'Meio Green':
                select.classList.add('meio-green-status');
                break;
            case 'Meio Red':
                select.classList.add('meio-red-status');
                break;
            case 'Devolvida':
                select.classList.add('devolvida-status');
                break;
            case 'Cashout':
                select.classList.add('cashout-status');
                break;
            case 'Pré Live':
                select.classList.add('pre-live-status');
                break;
        }
    }

    selectElements.forEach(select => {
        updateSelectClass(select);
        select.addEventListener('change', function() {
            updateSelectClass(this);
            const form = this.closest('form');
            form.submit();
        });
    });
});
