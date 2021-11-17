
var stompClient = null;
var commentID = null;
var received = null;
var author = $('#author').val();
var post = $('#post').val();
var index = 0;
var level = 0;
//comment item
function commentItem(
    commentDate,
    commentContent,
    avatar,
    name,
    commentID,
    level,
    x
) {
    return `
            <div class="d-flex">
                <img class="avatar avatar-md rounded-circle float-start me-3"
                    src="${avatar}" alt="avatar">
                <div class="main-comment w-100">
                    <div class="mb-2">
                        <h5 class="m-0 userName">${name}</h5>
                        <span class="me-3 small">${commentDate}</span>
                        <a href="" class="text-body fw-normal reply">
                            ${x != null ? 'Reply' : ''}
                        </a>
                        <input type="hidden" value="${commentID}"
                                                   class="comment">
                        <input type="hidden" value="${level}" class="level">
                    </div>
                    <p>${commentContent.replace(/</g,'&lt;').replace(/>/g,'&gt;')}</p>
                </div>

            </div>
    `;
}
// reply form
function replyForm(
    commentID
) {
    return `
            <form id="replyForm">
                <div class="col-12">
                    <label class="form-label">Your Comment *</label>
                    <textarea class="form-control" rows="3" id="replyInput"></textarea>
                    <input type="hidden" value="${commentID}" id="replyComment">
                </div>
                <div class="col-12 mt-3">
                    <button type="submit" id="r" class="btn btn-primary">Post comment</button>
                </div>
            </form>
    `;
}

/* Adds new element  AFTER old element */
Element.prototype.appendAfter = function (element) {
    element.parentNode.insertBefore(this, element.nextSibling);
}, false;

//show reply form
$(document).on('click', '.reply', function (e) {
    e.preventDefault();
    $('#rep').remove();
    index = $(this).index('.reply');
    level = document.getElementsByClassName('level')[index].value;
    var comment = document.getElementsByClassName('comment')[index].value;
    var div = document.createElement('div');
    div.id = 'rep';
    div.innerHTML = replyForm(comment);
    $('.main-comment')[index].append(div);
});
//send comment
$('#commentForm').on('submit', function (event) {
    var commentInput = $('#commentInput').val();
    event.preventDefault();
    if(commentInput != ''){
        var comment = {
            accountID: author,
            postID: post,
            commentContent: commentInput,
            commentLevel: 1,
            index : index
        }
        stompClient.send('/app/binh-luan', {}, JSON.stringify(comment));
        $('#commentInput').val('');
    }
});


//send comment reply
$(document).on('submit', '#replyForm', function (e) {
    e.preventDefault();
    var replyUser = $('.userName')[index].innerHTML;
    var replyInput = $('#replyInput').val();
    var replyComment = $('#replyComment').val();
    //var level = document.getElementsByClassName('level')[index].value;
    if(replyInput != ''){
        if( level > 2){
            level = 2;
        }
        var reply = {
            reply: replyComment,
            accountID: author,
            postID: post,
            commentContent: '@' + replyUser + ' ' + replyInput,
            commentLevel: ++level,
            index : index
        }
        stompClient.send('/app/binh-luan', {}, JSON.stringify(reply));
    }
    $(this).remove();
});


//////////////////////////////////////////////

//connect and receive message
function connect() {
    var socket = new SockJS('/binh-luan');
    stompClient = Stomp.over(socket);
    stompClient.connect({},
        function () {
            stompClient.subscribe('/topic/binh-luan', function (payload) {
                received = JSON.parse(payload.body);
                var l_o = $('#l_o').val();
                var div = document.createElement('div');
                div.addClass('my-4');
                div.addClass('child');
                if(received.commentLevel == 3){
                    div.addClass('ps-2');
                    div.addClass('ps-md-6');
                    div.innerHTML = commentItem(
                        received.commentDate,
                        received.commentContent,
                        received.avatarUser,
                        received.nameUser,
                        received.reply,
                        received.commentLevel,
                        l_o
                    );
                    div.appendAfter(document.getElementsByClassName('child')[received.index]);
                } else {
                    div.innerHTML = commentItem(
                        received.commentDate,
                        received.commentContent,
                        received.avatarUser,
                        received.nameUser,
                        received.commentID,
                        received.commentLevel,
                        l_o
                    );
                    if(received.reply == 0){
                        div.appendAfter(document.getElementById('commentArea'));
                    }else {
                        div.addClass('ps-2');
                        div.addClass('ps-md-5');
                        div.appendAfter(document.getElementsByClassName('child')[received.index]);
                    }
                }
                document.getElementById('numberComments').innerHTML = received.numberOfComments + ' bình luận';
            });
        });
}

connect();