$(document).ready(function () {
    var data={};


    $("#new-accessary-type").on("click", function () {
        data = {};
        $('#input-accessary-type-name').val("");
        $('#input-accessary-type-desc').val("");
    });

    $(".edit-accessary-type").on("click", function () {
        var pdInfo = $(this).data("accessarytype");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/accessaryType/detail/" + pdInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                data.id = res.data.data.id;
                $("#input-accessary-type-name").val(res.data.data.name);
                $("#input-accessary-type-desc").val(res.data.data.description);
            }else {
                console.log("Lỗi");
            }
        }, function(err){
            NProgress.done();
        })
    });

    $(".btn-save-accessary-type").on("click", function () {
        if($("#input-accessary-type-name").val() === "" ||
            $("#input-accessary-type-desc").val() === "" ){
            swal(
                'Lỗi',
                'Bạn phải điền đầy đủ',
                'error'
            );
            return;
        }


        data.name = $('#input-accessary-type-name').val();
        data.description = $('#input-accessary-type-desc').val();
        NProgress.start();
        console.log(data.id);
        var linkPost = "/api/accessaryType/create";
        if(data.id) {
            linkPost = "/api/accessaryType/update/" + data.id;
        }

        axios.post(linkPost, data).then(function(res){
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