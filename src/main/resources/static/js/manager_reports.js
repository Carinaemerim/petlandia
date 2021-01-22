function initReportsModal() {
    const container = $('#report-container');
    const rows = $('.callout', container);

    if (rows.length === 0) {
        return;
    }

    function setAction(modal, report) {
        const form = modal.find('form');
        const url = form.data('action')
            .replace(':report', report.data('report-id'));
        form.attr('action', url);
    }

    const modalAccept = $('#modal-report-accept');
    const modalReject = $('#modal-report-reject');
    modalAccept.on('hide.bs.modal', (e) => {
        modalAccept.find('form').attr('action', '#');
    });
    modalReject.on('hide.bs.modal', (e) => {
        modalReject.find('form').attr('action', '#');
    });

    rows.each(function _each() {
        const report = $(this);
        const accept = report.find('.btn-accept');
        const reject = report.find('.btn-reject');

        if (accept.length) {
            accept.click((e) => {
                e.preventDefault();
                setAction(modalAccept, report);
                modalAccept.modal('show');
            });
        }

        if (reject.length) {
            reject.click((e) => {
                e.preventDefault();
                setAction(modalReject, report);
                modalReject.modal('show');
            });
        }
    });
}

$(document).ready(function () {
    initReportsModal();
});
