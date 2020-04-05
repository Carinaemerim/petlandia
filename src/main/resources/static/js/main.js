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

    const $modal = $('.modal', container);
    const $btn_submit = $('.btn-submit', container);
    const $btn_cancel = $('.btn-cancel', container);
    const $btn_select = $('.btn-select', container);

    let uploadedImageName = 'cropped.jpg';
    let uploadedImageType = 'image/jpeg';
    let uploadedImageURL;

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

    Object.assign(config, options || {});

    $file.change(function _change() {
        const files = this.files;

        if (files && files[0]) {
            const file = files[0];

            if (/^image\/\w+$/.test(file.type)) {
                uploadedImageName = file.name;
                uploadedImageType = file.type;

                if (uploadedImageURL) {
                    URL.revokeObjectURL(uploadedImageURL);
                }

                uploadedImageURL = URL.createObjectURL(file);

                if ($image.data('cropper')) {
                    $image.cropper('destroy');
                }

                $image.attr('src', uploadedImageURL)
                    .cropper(config);
            }
        }
    });

    $btn_select.click((e) => {
      e.preventDefault();
      $modal.show();
    });

    $btn_cancel.click((e) => {
        e.preventDefault();
        $modal.hide();
    });

    $btn_submit.click((e) => {
        e.preventDefault();
        $modal.hide();
    });



    return $image.data('cropper');
}

$(document).ready(function() {
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

    $('.mask-zipcode').mask('00000-000');
    $('.mask-cpf').mask('000.000.000-00', {reverse: true});
    $('.mask-celphone').mask('(00) 0000-00009');
});