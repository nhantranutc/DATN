$(document).ready(function() {

    var dataOrder = {};

    $(".send-mail").on("click", function () {

        for(var i=0;i<vm.sendMailList.length;i++) {
            console.log(vm.sendMailList[i].name);
            NProgress.start();

            axios.get("/api/mail/" + vm.sendMailList[i].email +"/"+vm.sendMailList[i].name).then(function(res){
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
                    'Lỗi Rồi',
                    'Không được rồi!',
                    'error'
                );
            })
        }
    });
});