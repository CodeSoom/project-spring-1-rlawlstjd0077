function init () {
    $('#btn-update').on('click', function () {
        var id = $(this).find('#updateId').val();
        window.location.href = '/update-link/' + id;
    });

    $('#btn-delete').on('click', function () {
        var id = $(this).find('#deleteId').val();
        deleteLink(id);
    });
    $('.chips-placeholder').chips({
        placeholder: 'Enter a tag',
        secondaryPlaceholder: '+Tag',
    });

    $('.modal-trigger').on('click', function () {
        alert('설명은 아직 준비중이에요 ㅠ.ㅠ 노여워 마세요')
    });

    $('.btn-pagination').on('click', function () {
        var hrefLocation = $(this).parent().find('#page-value').val();
        var filterUrl = getFilterURL();

        location.href = hrefLocation + "&" + filterUrl;
    });

    $('.like-review').on('click', function () {
        if (document.getElementById("user") == null) {
            alert("로그인이 필요한 기능입니다!");
            return;
        }
        var linkId = $(this).parent().parent().find('#id').attr('value');
        var counter =  $(this).find('span')[0];
        addLike(linkId, counter);
    });

    initUser();
    initCategory();
    initType();
    initTag();
}

function addLike (linkId, counter) {
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
}


function initCategory () {
    $.get('/links/categories', (tagArr) => {
        var dataArr = {};
        tagArr.forEach(key => dataArr[key] = null)

        var arr = $('#category-init').find('input').toArray().map(e => e.value)
        var chips_arr = arr.map(e => ({tag: e}));

        $('#category-filter').chips({
            onChipAdd : () => { searchData(); },
            onChipDelete : () => { searchData(); },
            data: chips_arr,
            autocompleteOptions: {
              data: dataArr,
            }
        });
    },'json');
}

function initType() {
    $.get('/links/types', function(typeArr){
        var dataArr = {};
        typeArr.forEach(key => dataArr[key] = null)

        var arr = $('#type-init').find('input').toArray().map(e => e.value)
        var chips_arr = arr.map(e => ({tag: e}));

        $('#type-filter').chips({
            onChipAdd : () => { searchData(); },
            onChipDelete : () => { searchData(); },
            data: chips_arr,
            autocompleteOptions: {
              data: dataArr,
            }
        });
    },'json');
}

function initTag() {
    $.get('/links/tags', function(tagArr){
        var dataArr = {};
        tagArr.forEach(key => dataArr[key] = null)

        var arr = $('#tag-init').find('input').toArray().map(e => e.value)
        var chips_arr = arr.map(e => ({tag: e}));

        $('#tag-filter').chips({
            onChipAdd : () => { searchData(); },
            onChipDelete : () => { searchData(); },
            data: chips_arr,
            autocompleteOptions: {
              data: dataArr,
            }
        });
    },'json');
}

function initUser() {
      $.get('/users', function(userArr){
          var dataArr = {};
          userArr.forEach(key => dataArr[key] = null)

          var arr = $('#user-init').find('input').toArray().map(e => e.value)
          var chips_arr = arr.map(e => ({tag: e}));

          $('#user-filter').chips({
              onChipAdd : () => { searchData(); },
              onChipDelete : () => { searchData(); },
              data: chips_arr,
              autocompleteOptions: {
                data: dataArr,
              }
          });
      },'json');
}

function searchData() {
    var url = getFilterURL();
    location.href = "/?" + url;
}

function getFilterURL() {
    var categories =
        M.Chips.getInstance($('#category-filter')).chipsData.map(e => e.tag);
    var types =
        M.Chips.getInstance($('#type-filter')).chipsData.map(e => e.tag);
    var tags =
        M.Chips.getInstance($('#tag-filter')).chipsData.map(e => e.tag);
    var users =
        M.Chips.getInstance($('#user-filter')).chipsData.map(e => e.tag);
    var value = {
            categories,
            types,
            tags,
            users
    }

    return 'filter=' + encodeURIComponent(JSON.stringify(value));
}

function deleteLink(id) {
    $.ajax({
        type: 'DELETE',
        url: '/links/' + id
    }).done(function() {
        alert('글이 삭제 되었습니다.');
        window.location.href = '/';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

init();
