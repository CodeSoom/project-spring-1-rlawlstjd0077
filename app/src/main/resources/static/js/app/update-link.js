var main = {
    init : function () {
        var _this = this;
        $('#btn-update').on('click', function () {
            var id = $('#id').attr('value');
            _this.update(id);
        });
        $('#btn-cancel').on('click', function () {
             window.location.href = '/';
        });
    },
    update : function (id) {
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
                type: 'PATCH',
                url: '/links/' + id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('글이 수정되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
    }
};

main.init();
