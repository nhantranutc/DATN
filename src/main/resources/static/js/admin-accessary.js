$(document).ready(function () {
    var dataProduct={};

    function readURL(input) {
        if(input.files && input.files[0]) {
            var reader =new FileReader();
            reader.onload= function (e) {
                $('#preview-product-img').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }

    }

    $("#change-product-mainImage").change(function () {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('file', $("#change-product-mainImage")[0].files[0]);
        axios.post("/api/upload/upload-image", formData).then(function (res) {
            NProgress.done();
            if (res.data.success) {
                $('.product-main-image').attr('src', res.data.link);

            }
        }, function(err) {
            NProgress.done();
        });
    });

    $("#new-accessary").on("click", function () {
        dataProduct = {};
        $('#input-accessary-name').val("");
        $('#input-accessary-category').val("");
        $("#input-accessary-price").val("");
        $("#input-accessary-origin-price").val("");
        $('#input-accessary-promotion-price').val("");
        $('#input-accessary-quantity').val("");
        $('#input-accessary-warranty').val("");
        $('#input-accessary-origin').val("");
        $('#input-accessary-desc').val("");
        $('.product-main-image').attr('src', 'https://dapp.dblog.org/img/default.jpg');

    });

    $(".edit-accessary").on("click", function () {
        var pdInfo = $(this).data("accessary");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/accessary/detail/" + pdInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataProduct.id = res.data.data.id;
                $('#input-accessary-name').val(res.data.data.name);
                $('#input-accessary-category').val(res.data.data.accessaryTypeId);
                $("#input-accessary-price").val(res.data.data.price);
                $("#input-accessary-origin-price").val(res.data.data.originalPrice);
                $('#input-accessary-promotion-price').val(res.data.data.promotionPrice);
                $('#input-accessary-quantity').val(res.data.data.quantity);
                $('#input-accessary-warranty').val(res.data.data.warranty);
                $('#input-accessary-origin').val(res.data.data.origin);
                $('#input-accessary-desc').val(res.data.data.description);
                if(res.data.data.image != null) {
                    $('.product-main-image').attr('src', res.data.data.image);
                }
            }else {
                console.log("Lỗi");
            }
        }, function(err){
            NProgress.done();
        })
    });

    $(".btn-save-accessary").on("click", function () {
        if($("#input-accessary-name").val() === ""
            || $("#input-accessary-desc").val() === ""
            || $("#input-accessary-price").val()===""
            || $("#input-accessary-origin-price").val()===""
            || $("#input-accessary-promotion-price").val()===""
            || $("#input-accessary-quantity").val()===""
            || $("#input-accessary-warranty").val()===""
            || $("#input-accessary-origin").val()==="") {
            swal(
                'Lỗi',
                'Bạn phải điền đầy đủ',
                'error'
            );
            return;
        }

        dataProduct.image = $('.product-main-image').attr('src');
        dataProduct.name = $('#input-accessary-name').val();
        dataProduct.accessaryTypeId = $('#input-accessary-category').val();
        dataProduct.price = $('#input-accessary-price').val();
        dataProduct.originalPrice = $('#input-accessary-origin-price').val();
        dataProduct.promotionPrice = $('#input-accessary-promotion-price').val();
        dataProduct.quantity = $('#input-accessary-quantity').val();
        dataProduct.warranty = $('#input-accessary-warranty').val();
        dataProduct.origin = $('#input-accessary-origin').val();
        dataProduct.description = $('#input-accessary-desc').val();


        NProgress.start();
        console.log(dataProduct.id);
        var linkPost = "/api/accessary/create";
        if(dataProduct.id) {
            linkPost = "/api/accessary/update/" + dataProduct.id;
        }

        axios.post(linkPost, dataProduct).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Thành Công!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Lỗi Rồi',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Lỗi Rồi',
                'Không được rồi!',
                'error'
            );
        })
    });

});