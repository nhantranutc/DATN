$(document).ready(function () {
    var dataCartProduct = {};

    $(".chang-amount").change(function () {
        dataCartProduct = {};
        dataCartProduct.id = $(this).data("id");
        dataCartProduct.amount = $(this).val();

        NProgress.start();

        var linkPost = "/api/cart/update";

        axios.post(linkPost, dataCartProduct).then(function(res){
            NProgress.done();
            if(res.data.success) {
                location.reload();
            } else {
                swal(
                    'Lỗi',
                    res.data.message,
                    'error'
                ).then(function() {
                    location.reload();
                });
            }
        }, function(err){
            NProgress.done();
            swal(
                'Lỗi',
                'Lỗi',
                'error'
            );
        });
    });

    $(".delete-cart").on("click",function(){
        var pdInfo = $(this).data("id");
        dataCartProduct.id = pdInfo;

        NProgress.start();
        var linkGet = "/api/cart/delete";
        axios.post(linkGet, dataCartProduct).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Thành Công',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Lỗi',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Lỗi',
                'Lỗi',
                'error'
            );
        });
    })
});