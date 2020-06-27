$(document).ready(function() {

    $(".add-accessary").on("click", function () {
        var dataCart = {};
        var pdInfo = $(this).data("accessary");
        dataCart.amount = 1;
        dataCart.accessaryId = pdInfo;
        console.log(dataCart);

        NProgress.start();

        var linkPost = "/api/cart/add-accessary";

        axios.post(linkPost, dataCart).then(function(res){
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
                document.getElementById('getAmount').value =1;
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


    $(".add-service").on("click", function () {
        var dataCart = {};
        var pdInfo = $(this).data("services");
        dataCart.amount = 1;
        dataCart.serviceId = pdInfo;
        console.log(dataCart);

        NProgress.start();

        var linkPost = "/api/cart/add-service";

        axios.post(linkPost, dataCart).then(function(res){
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
                document.getElementById('getAmount').value =1;
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
});