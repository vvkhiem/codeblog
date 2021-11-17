function post_item(
    postID,
    postImage,
    postTitle,
    categoryName,
    postDate,

) {
    return `
        
                            <td>
                                <a href="#" >#${postID}</a>
                            </td>
                            <td class="text-center">
                                <a href="#">
                                    <img src="${postImage}" class="rounded" width="40"
                                         alt="...">
                                </a>
                            </td>
                            <td class="text-center" >${postTitle}</td>
                            <td class="text-center">
                                ${categoryName}
                            </td>
                            <td class="text-center" >${postDate}</td>
                            <td class="text-center">
                                <div class="d-flex">
                                    <div class="dropdown ms-auto">
                                        <a href="#" data-bs-toggle="dropdown"
                                           class="btn btn-floating"
                                           aria-haspopup="true" aria-expanded="false">
                                            <i class="bi bi-three-dots"></i>
                                        </a>
                                        <div class="dropdown-menu dropdown-menu-end">
                                            <a href="/admin/bai-viet/?pid=${postID}" class="dropdown-item">Cập nhật</a>
                                            <a href="#" class="dropdown-item text-danger delete">Xóa</a>
                                            <input type="hidden" value="${postID}" class="p_ID">
                                        </div>
                                    </div>
                                </div>
                            </td>
                        
    `;
}

function user_item(
    accountID,
    avatar,
    fullName,
    userName,
    email,
    auth_provider,
    role,
    active
) {
    return `
                
                    <td class="text-center">
                        <a href="#">
                            <figure class="avatar me-3">
                                <img src="${avatar}"
                                     class="rounded-circle" alt="avatar">
                            </figure>
                        </a>
                    </td>
                    <td class="text-center">${fullName}</td>
                    <td class="text-center">${userName}</td>
                    <td class="text-center">${email}</td>
                    <td class="text-center">${auth_provider}</td>
                    <td class="text-center">${role}</td>
                    <td class="text-center">
                        <span class="badge ${active == true ? 'bg-success' : 'bg-danger'}">${active == true ? 'Active' : 'Blocked'}</span>
                    </td>
                    <td class="text-center">
                        <div class="dropdown">
                            <a href="#" data-bs-toggle="dropdown"
                               class="btn btn-floating"
                               aria-haspopup="true" aria-expanded="false">
                                <i class="bi bi-three-dots"></i>
                                <input type="hidden" class="u_ID" value="${accountID}">
                            </a>
                            <div class="dropdown-menu dropdown-menu-end">
                                <a href="/admin/xem-binh-luan/${accountID}" class="dropdown-item">Xem bình luận</a>
                                <a href="#" class="dropdown-item status-user">${active == true ? 'Block' : 'Active'}</a>
                                <a href="#" class="dropdown-item text-danger delete-user">Delete</a>
                            </div>
                        </div>
                    </td>
               
    `;
}

function contact_item(
    contactID,
    contactStatus,
    contactName,
    contactDate,
    contactSubject,
    contactEmail,
    avatar_rnd
) {
    return `
                                <div class="d-none d-sm-block flex-shrink-0">
                                    <i class="add-star bi fs-5 me-3 ${contactStatus == true ? 'text-success bi-eye-fill' : 'text-danger bi-eye-slash-fill'}"></i>
                                </div>
                                <div class="d-none d-sm-block flex-shrink-0">
                                    <figure class="avatar ${avatar_rnd} me-3">
                                        <span class="avatar-text rounded-circle" >${contactName.substring(0,1)}</span>
                                    </figure>
                                </div>
                                <div class="flex-grow-1">
                                    <div class="mb-1 d-flex justify-content-between align-items-center">
                                        <div class="align-items-center d-flex">
                                            <h6 class="text-truncate mb-0" >${contactName}</h6>
                                            <span class="text-nowrap text-muted d-none d-sm-block" style="margin-left: 10px;">&#60;${contactEmail}&#62;</span>
                                        </div>
                                        <div class="ps-3 d-flex">
                                            <span class="text-nowrap text-muted">${contactDate}</span>
                                            <div class="dropdown ms-3">
                                                <a href="#" class="btn btn-floating btn-sm" data-bs-toggle="dropdown">
                                                    <i class="bi bi-three-dots"></i>
                                                    <input type="hidden" class ="ct_ID" value="${contactID}">
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-end">
                                                    <a class="dropdown-item" href="#">Xem chi tiết</a>
                                                    <a class="dropdown-item text-danger delete-item-contact" href="#">Xóa</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-muted d-flex justify-content-between">
                                        <div class="text-truncate">
                                            ${contactSubject}
                                        </div>
                                    </div>
                                </div>
    `;
}


$('.back').click(function () {
    window.history.back();
})

//load more post - admin page
var index = 5;
$('#load-more').click(function () {
    index = index + 5;
    $.ajax({
        url : '/admin/load-more/' + index,
        type : 'GET'
    }).done(function (data){
        if(data.length != 0){
            for (let i = 0; i < data.length ; i++) {
                var tr = document.createElement('tr');
                tr.classList.add('new-item','item');
                tr.innerHTML = post_item(
                    data[i].postID,
                    data[i].postImage,
                    data[i].postTitle,
                    data[i].category_categoryName,
                    data[i].postDate
                )
                $('#tbody').append(tr);
            }
            $('#load-more').html('Xem thêm');
            if(data.length < 5){
                $('#load-more').html('Thu gọn');
            }
        } else {
            $('#load-more').html('Xem thêm');
            $('.new-item').remove();
            index = 5;
        }
    });
})

// load more user - admin page
var index2 = 5;
$('#load-more-user').click(function () {
    index2 = index2 + 5;
    $.ajax({
        url : '/admin/quan-ly-user/load-more-user/' + index2,
        type : 'GET'
    }).done(function (data){
        if(data.length != 0){
            for (let i = 0; i < data.length ; i++) {
                var tr = document.createElement('tr');
                tr.classList.add('new-item','item');
                tr.innerHTML = user_item(
                    data[i].accountID,
                    data[i].avatar,
                    data[i].fullName,
                    data[i].username,
                    data[i].email,
                    data[i].auth_provider,
                    data[i].role,
                    data[i].active
                )
                $('#tbody').append(tr);
            }
            $('#load-more-user').html('Xem thêm');
            if(data.length < 5){
                $('#load-more-user').html('Thu gọn');
            }
        } else {
            $('#load-more-user').html('Xem thêm');
            $('.new-item').remove();
            index2 = 5;
        }
    });
})


// delete post
$(document).on('click','.delete',function (event) {
    var conf = confirm('Bạn đang thực hiện xóa 1 bài viết. Click OK để xác nhận. ');
    event.preventDefault();
    if (conf == true) {
        var index = $(this).index('.delete');
        var id = document.getElementsByClassName('p_ID')[index].value;
        $.ajax({
            url : "/admin/bai-viet/" + id,
            type : 'DELETE',
        }).done(function (message) {
            if(message == true) {
                $('.item')[index].remove();
            }
        })
    }
});

//delete user
$(document).on('click','.delete-user',function (e) {
    var conf = confirm('Bạn đang thực hiện xóa 1 user. Click OK để xác nhận. ');
    e.preventDefault();
    if (conf == true ) {
        var index = $(this).index('.delete-user');
        var id = document.getElementsByClassName('u_ID')[index].value;
        $.ajax({
            url : "/admin/quan-ly-user/xoa-user/" + id,
            type : 'DELETE',
        }).done(function (message) {
            if(message == true) {
                $('.item')[index].remove();
            }
        })
    }
});

// update status user
$(document).on('click','.status-user',function (e) {
    e.preventDefault();
    var index = $(this).index('.status-user');
    var status = document.getElementsByClassName('status-user')[index].innerHTML;
    var conf = confirm('Bạn đang thực hiện ' + status + ' tài khoản này. Click Ok để thực hiện.');
    var id = document.getElementsByClassName('u_ID')[index].value;
    if (conf == true) {
        $.ajax({
            url : '/admin/quan-ly-user/trang-thai' ,
            type : 'GET' ,
            data : {
                tt : status,
                id : id
            }
        }).done(function(st) {
            if (st == true) {
                document.getElementsByClassName('status-user')[index].innerHTML = 'Block';
                document.getElementsByClassName('badge')[index].classList.remove('bg-danger');
                document.getElementsByClassName('badge')[index].classList.add('bg-success');
                document.getElementsByClassName('badge')[index].innerHTML = 'Active';
            } else {
                document.getElementsByClassName('status-user')[index].innerHTML = 'Active';
                document.getElementsByClassName('badge')[index].classList.remove('bg-success');
                document.getElementsByClassName('badge')[index].classList.add('bg-danger');
                document.getElementsByClassName('badge')[index].innerHTML = 'Blocked';
            }
        });
    }
});

// delete comment
$('.delete-cmt').click(function (e) {
    e.preventDefault();
    var index = $(this).index('.delete-cmt');
    var c_ID = document.getElementsByClassName('c_ID')[index].value;
    $.ajax({
        url : '/admin/xoa-binh-luan/' + c_ID,
        type : 'DELETE'
    }).done(function(message){
        if(message == true) {
            $('.item')[index].remove();
        }
    });
});
// delete all comments
$('#delete-all-cmt').click(function (e) {
    var conf = confirm('Bạn đang thực hiện xóa tất cả bình luận. Click Ok để xác nhận !');
    if (conf == true) {
        $.ajax({
            url : '/admin/xoa-tat-ca-binh-luan',
            type : 'DELETE'
        }).done(function (message) {
            if(message == true) {
                $('.item').remove();
            }
        });
    }
});


// load more contact - admin page
var index3 = 5;
var arr = ['avatar-info','avatar-danger','avatar-warning','avatar-secondary','avatar-success'];
var avatar = document.getElementsByClassName('avatar-user');
$('#load-more-contact').click(function (e) {
    e.preventDefault();
    index3 = index3 + 5;
    $.ajax({
        url : '/admin/lien-lac/load-more-contact/' + index3 ,
        type : 'GET'
    }).done(function (data) {
        if(data.length != 0) {
            for (let i = 0; i < data.length ; i++) {
                var rnd = Math.floor(Math.random() * avatar.length);
                var li = document.createElement('li');
                li.classList.add('list-group-item', 'text-success' , 'new-item', 'item');
                li.innerHTML = contact_item(
                    data[i].contactID,
                    data[i].contactStatus,
                    data[i].contactName,
                    data[i].contactDate,
                    data[i].contactSubject,
                    data[i].contactEmail,
                    arr[rnd]
                )
                $('#addItems').append(li);
            }
            $('#load-more-contact').html('Xem thêm');
            if(data.length < 5){
                $('#load-more-contact').html('Thu gọn');
            }
        } else {
            $('#load-more-contact').html('Xem thêm');
            $('.new-item').remove();
            index3 = 5;
        }
    });
});

// delete contact
$(document).on('click','.delete-item-contact', function (e) {
    e.preventDefault();
    var index = $(this).index('.delete-item-contact');
    var ct_ID = document.getElementsByClassName('ct_ID')[index].value;
    $.ajax({
        url : '/admin/lien-lac/xoa/' + ct_ID,
        type : 'DELETE'
    }).done(function (message) {
        if(message == true) {
            $('.item')[index].remove();
        }
    });
});
// delete all contacts
$('#delete-all-contact').click(function (e) {
    e.preventDefault();
    var conf = confirm('Bạn đang thực hiện xóa tất cả tin nhắn. Click OK để xác nhận !');
    if (conf == true) {
        window.location.href = 'https://localhost:2501/admin/lien-lac/xoa-tat-ca';
    }
});


/////
$('#menu-btn').click(function () {
    $('#show-menu').addClass('open');
})
