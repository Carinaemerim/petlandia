const URL = window.URL || window.webkitURL;

function InitCropper(id, options) {
    const container = $(id);
    const $image = $(".crop-image", container);
    const $file = $('.crop-input', container);
    const $input_x = $(".input-x", container);
    const $input_y = $(".input-y", container);
    const $input_width = $(".input-width", container);
    const $input_height = $(".input-height", container);
    const $input_rotate = $(".input-rotate", container);
    const $input_scaleX = $(".input-scaleX", container);
    const $input_scaleY = $(".input-scaleY", container);
    const $input_remove = $(".input-remove", container);

    const $modal = $('.modal', container).modal({
        backdrop: true,
        show: false,
    });
    const $btn_submit = $('.btn-submit', container);
    const $btn_cancel = $('.btn-cancel', container);
    const $btn_select = $('.btn-select', container);
    const $btn_remove = $('.btn-remove', container);


    const backup = $image.attr('src');
    let uploadedImageName = 'cropped.jpg';
    let uploadedImageType = 'image/jpeg';
    let uploadedImageURL;

    const args = {
        width: 512,
        height: 288,
    };

    const config = {
        preview: '#' + container.attr('id') + ' .img-preview',
        aspectRatio: 16 / 9,
        maintainAspectRatio: true,
        initialAspectRatio: 16 / 9,
        crop: function _crop(event) {
            $input_x.val(event.detail.x);
            $input_y.val(event.detail.y);
            $input_width.val(event.detail.width);
            $input_height.val(event.detail.height);
            $input_rotate.val(event.detail.rotate);
            $input_scaleX.val(event.detail.scaleX);
            $input_scaleY.val(event.detail.scaleY);
        },
        viewMode: 3,
        responsive: true,
        autoCrop: true,
    };


    Object.assign(args, options || {});
    Object.assign(config, args);


    function getFile(instance) {
        const files = instance.files;

        if (files && files[0]) {
            const file = files[0];

            if (/^image\/\w+$/.test(file.type)) {
                return file;
            }
        }
    }

    function destroy() {
        if ($image.data('cropper')) {
            $image.cropper('destroy');
        }
    }

    function reset() {
        destroy();
        $image.attr('src', backup);
    }

    function getImageFromFile(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = function () {
                const img = new Image;

                img.onload = function () {
                    resolve(img);
                };

                img.onerror = function (err) {
                    reject(err);
                }

                img.src = reader.result;
            };

            reader.onerror = function (err) {
                reject(err);
            }

            reader.readAsDataURL(file);
        });
    }

    $file.change(async function _change() {
        if ($file.val() === '') {
            reset();
            return;
        }

        const file = getFile(this);
        const image = await getImageFromFile(file);

        if (image.width < args.width || image.height < args.height) {
            $file.val('');
            reset();
            alert(`Tamanho mínimo: ${args.width}x${args.height}`);
            return;
        }

        uploadedImageName = file.name;
        uploadedImageType = file.type;

        if (uploadedImageURL) {
            URL.revokeObjectURL(uploadedImageURL);
        }

        uploadedImageURL = URL.createObjectURL(file);

        destroy();

        $image.attr('src', uploadedImageURL)
            .cropper(config);
    });

    $btn_select.click((e) => {
        e.preventDefault();
        $modal.modal('show');
    });

    $btn_cancel.click((e) => {
        e.preventDefault();
        $modal.modal('hide');
    });

    $btn_submit.click((e) => {
        e.preventDefault();
        $modal.modal('hide');
    });

    $btn_remove.click((e) => {
        e.preventDefault();
        const remove = $input_remove.val() === 'true';
        if (!remove) {
            container.addClass('cropper-remove');
            $input_remove.val('true');
        } else {
            container.removeClass('cropper-remove');
            $input_remove.val('false');
        }
    });

    return $image.data('cropper');
}


function initPagination(container) {
    const baseUrl = `${window.location.pathname}?`;
    const search = new URLSearchParams(window.location.search);

    $('.page-link', container).each(function _each() {
        const link = $(this);

        search.delete('page');
        search.append('page', link.data('page'));
        link.attr('href', `${baseUrl}${search}`);
    });
}

$(document).ready(function () {
    initPagination();

    $(".image-placeholder").each(function _each() {
        const element = $(this);
        const img = $('img', element);
        const field = $('input', element);

        field.change(function _change() {
            const [input] = field;
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    img.attr('src', e.target.result);
                }

                reader.readAsDataURL(input.files[0]);
            }
        });
    });

    $(".cropper-init").each(function _each() {
        InitCropper(this);
    });

    $('#pagination-list').each(function _each() {
        InitCropper(this);
    });

    $('.mask-zipcode').mask('00000-000');
    $('.mask-cpf').mask('000.000.000-00', {reverse: true});
    $('.mask-celphone').mask('(00) 0000-00009');


    setTimeout(() => {
        $('.alert.alert-success').fadeOut();
    }, 5000)

});