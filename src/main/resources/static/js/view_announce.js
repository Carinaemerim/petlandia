

function initComments() {
    const container = $('#comment');
    const comments = $('.direct-chat-msg', container);

    if (comments.length === 0) {
        return;
    }

    function setAction(modal, comment) {
        const form = modal.find('form');
        const url = form.data('action')
            .replace(':announce', comment.data('announce-id'))
            .replace(':comment', comment.data('comment-id'));
        form.attr('action', url);
    }

    const modalRemove = $('#modal-comment-remove');
    const modalReport = $('#modal-comment-report');
    modalRemove.on('hide.bs.modal', (e) => {
        modalRemove.find('form').attr('action', '#');
    });
    modalReport.on('hide.bs.modal', (e) => {
        modalReport.find('form').attr('action', '#');
    });

    comments.each(function _each() {
        const comment = $(this);
        const remove = comment.find('.btn-comment-remove');
        const report = comment.find('.btn-comment-report');

        if (remove.length) {
            remove.click((e) => {
                e.preventDefault();
                setAction(modalRemove, comment);
                modalRemove.modal('show');
            });
        }

        if (report.length) {
            report.click((e) => {
                e.preventDefault();
                setAction(modalReport, comment);
                modalReport.modal('show');
            });
        }
    });
}

$(document).ready(function() {
    initComments();
});
