var main = {
    init : function () {
        var _this = this;
        $('#btn-sign').on('click', function () {
            _this.clickSignUp();
        });
    },
    clickSignUp : function () {
            var data = {
                email: $('#email').val(),
                name: $('#name').val(),
                password: $('#password').val(),
            };

            $.ajax({
                type: 'POST',
                url: '/users',
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('회원 가입이 완료되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
    }
};

main.init();
