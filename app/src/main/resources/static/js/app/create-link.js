var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-cancel').on('click', function () {
            window.location.href = '/';
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            linkURL: $('#linkURL').val(),
            description: $('#description').val(),
            category: $('#category').val(),
            type: $('#type').val(),
            tags: $('#tag').map(function() {
                   return $(this).val();
                 }).get()
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
    }
};

main.init();
