$(document).ready(function () {
    var dataBook={};

    $(".btn-save-book").on("click", function () {
        if($("#full-name").val() === "" ||
            $("#phone-number").val() === "" ||
            $("#appointment-date").val() === "" ||
            $("#content").val() === "" ||
            $("#email").val() === "" ||
            $("#action-type").val() === "" ||
            $("#vehicle-brand").val()==="") {
            swal(
                'Lỗi',
                'Bạn phải điền đầy đủ',
                'error'
            );
            return;
        }
        const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if( !re.test( $("#email").val() )) {
            swal(
                'Lỗi',
                'Email không đúng ( abc@gmail.com )',
                'error'
            );
            return;
        }

        dataBook.fullName = $('#full-name').val();
        dataBook.phoneNumber = $('#phone-number').val();
        dataBook.appointmentDate = $('#appointment-date').val();
        dataBook.vehicleBrand = $('#vehicle-brand').val();
        dataBook.content = $('#content').val();
        dataBook.actionType = $('#action-type').val();
        dataBook.email = $('#email').val();

        console.log(dataBook.appointmentDate);

        NProgress.start();
        var linkPost = "/api/book/create";

        axios.post(linkPost, dataBook).then(function(res){
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