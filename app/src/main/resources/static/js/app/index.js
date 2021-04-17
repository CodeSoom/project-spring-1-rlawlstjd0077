var main = {
    init : function () {
        var _this = this;
        $('#btn-update').on('click', function () {
            var id = $(this).find('#updateId').val();
            window.location.href = '/update-link/' + id;
        });

        $('#btn-delete').on('click', function () {
            var id = $(this).find('#deleteId').val();
            _this.delete(id);
        });
        $('.chips-placeholder').chips({
            placeholder: 'Enter a tag',
            secondaryPlaceholder: '+Tag',
        });

        $('.like-review').on('click', function () {
            if (document.getElementById("user") == null) {
                alert("로그인이 필요한 기능입니다!");
                return;
            }
            var linkId = $(this).parent().parent().find('#id').attr('value');
            var counter =  $(this).find('span')[0];
            _this.addLike(linkId, counter);
        });

        _this.initCategory();
        _this.initType();
        _this.initTag();
    },

    addLike : function(linkId, counter) {
        likes = counter.innerHTML;

        $.post('/links/like/' + linkId, function(status){
            if (!status) {
                alert("이미 좋아요를 누른 링크입니다!");
                return;
            }
            likes++;
            counter.innerText = likes
            $(this).children('.fa-heart').addClass('animate-like');
        },'json');


    },

    initCategory : function () {
        $.get('/links/categories', function(tagArr){
            var dataArr = {};
            tagArr.forEach(key => dataArr[key] = null)

            $('#category-filter').chips({
                autocompleteOptions: {
                  data: dataArr,
                }
            });
        },'json');
    },

    initType: function() {
        $.get('/links/types', function(typeArr){
            var dataArr = {};
            typeArr.forEach(key => dataArr[key] = null)

            $('#type-filter').chips({
                autocompleteOptions: {
                  data: dataArr,
                }
            });
        },'json');
    },

    initTag: function() {
        $.get('/links/tags', function(tagArr){
            var dataArr = {};
            tagArr.forEach(key => dataArr[key] = null)

            $('#tag-filter').chips({
                autocompleteOptions: {
                  data: dataArr,
                }
            });
        },'json');
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

/* 좋아요 버튼 이벤트 처리 */
