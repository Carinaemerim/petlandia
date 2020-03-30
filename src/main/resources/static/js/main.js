

function InitCropper(id, options) {
    const config = {
        aspectRatio: 16 / 9,
        crop: function _crop(event) {
            $input_x.val(event.detail.x);
            $input_y.val(event.detail.y);
            $input_width.val(event.detail.width);
            $input_height.val(event.detail.height);
            $input_rotate.val(event.detail.rotate);
            $input_scaleX.val(event.detail.scaleX);
            $input_scaleY.val(event.detail.scaleY);
        },
        minContainerWidth: 500,
        minContainerHeight: 500,
        autoCrop: true,
    };

    Object.assign(config, options || {});

    const container = $(id);
    const $image = $(".crop-image", container);
    const $input_x = $(".input-x", container);
    const $input_y = $(".input-y", container);
    const $input_width = $(".input-width", container);
    const $input_height = $(".input-height", container);
    const $input_rotate = $(".input-rotate", container);
    const $input_scaleX = $(".input-scaleX", container);
    const $input_scaleY = $(".input-scaleY", container);

    $image.cropper(config);
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