$(document).ready(function () {
    var dataService={};


    function readURL(input) {
        if(input.files && input.files[0]) {
            var reader =new FileReader();
            reader.onload= function (e) {
                $('#preview-product-img').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }

    }

    $("#change-service-mainImage").change(function () {
        readURL(this);
        var formData = new FormData();
        NProgress.start();
        formData.append('file', $("#change-service-mainImage")[0].files[0]);
        axios.post("/api/upload/upload-image", formData).then(function (res) {
            NProgress.done();
            if (res.data.success) {
                $('.service-main-image').attr('src', res.data.link);

            }
        }, function(err) {
            NProgress.done();
        });
    });

    $("#new-service").on("click", function () {
        dataService = {};
        $('#input-service-name').val("");
        $('#input-service-desc').val("");
        $("#input-service-content").val("");
        $('.service-main-image').attr('src', 'https://dapp.dblog.org/img/default.jpg');

    });

    $(".edit-service").on("click", function () {
        var pdInfo = $(this).data("service");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/service/detail/" + pdInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                dataService.id = res.data.data.id;
                $("#input-service-name").val(res.data.data.name);
                $("#input-service-desc").val(res.data.data.description);
                $("#input-service-content").val(res.data.data.content);
                if(res.data.data.image != null) {
                    $('.service-main-image').attr('src', res.data.data.image);
                }
            }else {
                console.log("Lỗi");
            }
        }, function(err){
            NProgress.done();
        })
    });

    $(".btn-save-service").on("click", function () {
        if($("#input-service-name").val() === "" ||
            $("#input-service-desc").val() === "" ||
            $("#input-service-content").val()==="") {
            swal(
                'Lỗi',
                'Bạn phải điền đầy đủ',
                'error'
            );
            return;
        }


        dataService.name = $('#input-service-name').val();
        dataService.description = $('#input-service-desc').val();
        dataService.image = $('.service-main-image').attr('src');
        dataService.content = $("#input-service-content").val();
        NProgress.start();
        console.log(dataService.id);
        var linkPost = "/api/service/create";
        if(dataService.id) {
            linkPost = "/api/service/update/" + dataService.id;
        }

        axios.post(linkPost, dataService).then(function(res){
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