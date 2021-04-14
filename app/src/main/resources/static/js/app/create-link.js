var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-cancel').on('click', function () {
            window.location.href = '/';
        });
        $('.chips-placeholder').chips({
            placeholder: 'Enter a tag',
            secondaryPlaceholder: '+Tag',
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            linkURL: $('#linkURL').val(),
            description: $('#description').val(),
            category: $('#category').val(),
            type: $('#type').val(),
            tags: M.Chips.getInstance($('.chips-placeholder')).chipsData.map(e => e.tag)
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
