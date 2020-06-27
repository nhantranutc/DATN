$(document).ready(function() {

    var dataOrder = {};

    $(".remove-order").on("click", function () {
        dataOrder.id = $(this).data("remove");
        console.log(dataOrder);
        NProgress.start();
        linkPost = "/api/book/remove/";
        axios.post(linkPost, dataOrder).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Thành công!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Lỗi rồi!!',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Lỗi rồi !!',
                'Không được rồi!!',
                'error'
            );
        })
    });

    $(".accept-order").on("click", function () {
        dataOrder.id = $(this).data("accept");
        console.log(dataOrder);
        NProgress.start();
        linkPost = "/api/book/accept/";
        axios.post(linkPost, dataOrder).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Thành công!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Lỗi rồi!!',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Lỗi rồi !!',
                'Không được rồi!!',
                'error'
            );
        })
    });

    $(".send-mail").on("click", function () {
        // dataOrder.email = $(this).data("email");

        console.log($(this).data("mail"));

        NProgress.start();

        axios.get("/api/mail1/" + $(this).data("mail") +"/"+$(this).data("name")+"/"+$(this).data("id")).then(function(res){
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

    $(".delete-book").on("click",function(){
        var pdInfo = $(this).data("id");
        dataOrder.id = pdInfo;

        NProgress.start();
        var linkGet = "/api/book/delete";
        axios.post(linkGet, dataOrder).then(function(res){
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
    });
});