$(document).ready(function () {
    var dataBook={};

    $(".btn-save-book").on("click", function () {
        if($("#full-name").val() === "" ||
            $("#phone-number").val() === "" ||
            $("#appointment-date").val() === "" ||
            $("#content").val() === "" ||
            $("#vehicle-brand").val()==="") {
            swal(
                'Lỗi',
                'Bạn phải điền đầy đủ',
                'error'
            );
            return;
        }

        dataBook.fullName = $('#full-name').val();
        dataBook.phoneNumber = $('#phone-number').val();
        dataBook.appointmentDate = $('#appointment-date').val();
        dataBook.vehicleBrand = $('#vehicle-brand').val();
        dataBook.content = $('#content').val();

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