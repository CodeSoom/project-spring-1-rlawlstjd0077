var main = {
    init : function () {
        var _this = this;
        $('#btn-update').on('click', function () {
            var id = $(this).find('#updateId').attr('value');
            window.location.href = '/update-link/' + id;
        });

        $('#btn-delete').on('click', function () {
            var id = $(this).find('#deleteId').attr('value');
            _this.delete(id);
        });
    },
    delete : function (id) {
        $.ajax({
            type: 'DELETE',
            url: '/links/' + id
        }).done(function() {
            alert('글이 삭제 되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};

main.init();
