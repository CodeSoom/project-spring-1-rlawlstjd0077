var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            alert("");
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            url: $('#url').val(),
            description: $('#description').val(),
            category: $('#category').val(),
            type: $('#type').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/links',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};

main.init();
