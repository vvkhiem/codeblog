var reset = 0;
//random bg label
var randomBgClass = ['bg-dark', 'bg-danger', 'bg-primary', 'bg-warning', 'bg-success', 'bg-vio', 'bg-orange'];
var badgeClass = document.getElementsByClassName('badge_');
for (let i = 0; i < badgeClass.length; i++) {
    var rng = Math.floor(Math.random() * randomBgClass.length);
    badgeClass[i].classList.add(randomBgClass[rng]);
}

// post item of home page
function postItem(
    categoryName,
    postId,
    postTitle,
    postDescription,
    postDate,
    postView,
    postImage,
    account_avatar,
    account_fullname,
    bg_class
) {
    return `
        <!-- Card item START -->
        <div class="card border rounded-3 up-hover p-4 mb-4">
          <div class="row g-3">
            <div class="col-lg-5">
              <!-- Categories -->
              <a href="/danh-muc/${categoryName}" class="badge ${bg_class} mb-2"><i class="fas fa-circle me-2 small fw-bold"></i>${categoryName}</a>
              <!-- Title -->
              <h2 class="card-title">
                <a href="/bai-viet/${postId}" class="btn-link text-reset stretched-link">${postTitle}</a>
              </h2>
              <!-- Author info -->
              <div class="d-flex align-items-center position-relative mt-3">
                <div class="avatar me-2">
                  <img class="avatar-img rounded-circle" src="${account_avatar}" alt="avatar">
                </div>
                <div>
                  <h5 class="mb-1"><a href="#" class="stretched-link text-reset btn-link acc-name-style">${account_fullname}</a></h5>
                  <ul class="nav align-items-center small">
                    <li class="nav-item me-3">${postDate}</li>
                    <li class="nav-item"><i class="far fa-eye me-1"></i><span>${postView}</span> lượt xem</li>
                  </ul>
                </div>
              </div>
            </div>
            <!-- Detail -->
            <div class="col-md-6 col-lg-4">
              <p>${postDescription}</p>
            </div>
            <!-- Image -->
            <div class="col-md-6 col-lg-3">
              <img class="rounded-3" src="${postImage}" alt="${postTitle}">
            </div>
          </div>
        </div>
        <!-- Card item END -->
    `;
}

//post item of search-result page
function postItem_s(
    categoryName,
    postId,
    postTitle,
    postDescription,
    postDate,
    postView,
    postImage,
    account_avatar,
    account_fullname,
    bg_class
) {
    return `
        <!-- Card item START -->
        <div class="card border rounded-3 up-hover p-4 mb-4">
            <div class="row g-3">
                <div class="col-sm-9">
                    <!-- Categories -->
                    <a href="/danh-muc?c=${categoryName}" class="badge ${bg_class} mb-2"><i
                            class="fas fa-circle me-2 small fw-bold"></i>${categoryName}</a>
                    <!-- Title -->
                    <h3 class="card-title">
                        <a href="/bai-viet/${postId}" class="btn-link text-reset stretched-link">${postTitle}</a>
                    </h3>
                    <!-- Card info -->
                    <ul class="nav nav-divider align-items-center d-none d-sm-inline-block">
                        <li class="nav-item">
                            <div class="nav-link">
                                <div class="d-flex align-items-center position-relative">
                                    <div class="avatar avatar-xs">
                                        <img class="avatar-img rounded-circle"
                                             src="${account_avatar}" alt="avatar">
                                    </div>
                                    <span class="ms-3">by <a href="/gioi-thieu"
                                                             class="stretched-link text-reset btn-link">${account_fullname}</a></span>
                                </div>
                            </div>
                        </li>
                        <li class="nav-item">${postDate}</li>
                    </ul>
                </div>
                <!-- Image -->
                <div class="col-sm-3">
                    <img class="rounded-3" src="${postImage}" alt="Card image">
                </div>
            </div>
        </div>
        <!-- Card item END -->
    `;
}

// post item of category page
function postItem_c(
    categoryName,
    postId,
    postTitle,
    postDescription,
    postDate,
    postView,
    postImage,
    account_avatar,
    account_fullname,
    bg_class
) {
    return `
        
              <div class="card">
                  <!-- Card img -->
                  <div class="position-relative">
                      <img class="card-img" src="${postImage}" alt="${postTitle}">
                      <div class="card-img-overlay d-flex align-items-start flex-column p-3">
                          <!-- Card overlay bottom -->
                          <div class="w-100 mt-auto">
                              <!-- Card category -->
                              <a href="/danh-muc?c=${categoryName}" class="badge ${bg_class} mb-2"><i class="fas fa-circle me-2 small fw-bold"></i><span>${categoryName}</span></a>
                          </div>
                      </div>
                  </div>
                  <div class="card-body px-0 pt-3">
                      <h4 class="card-title">
                          <a href="/bai-viet/${postId}" class="btn-link text-reset fw-bold" >${postTitle}</a>
                      </h4>
                      <p class="card-text">
                        ${postDescription.substring(0,100)}
                      </p>
                      <!-- Card info -->
                      <ul class="nav nav-divider align-items-center d-none d-sm-inline-block">
                          <li class="nav-item">
                              <div class="nav-link">
                                  <div class="d-flex align-items-center position-relative">
                                      <div class="avatar avatar-xs">
                                          <img class="avatar-img rounded-circle" src="${account_avatar}" alt="${account_fullname}">
                                      </div>
                                      <span class="ms-3">by <a href="/gioi-thieu" class="stretched-link text-reset btn-link">${account_fullname}</a></span>
                                  </div>
                              </div>
                          </li>
                          <li class="nav-item">${postDate}</li>
                      </ul>
                  </div>
              </div>
    `;
}

// post item of tag page
function postItem_t(
    categoryName,
    postId,
    postTitle,
    postDescription,
    postDate,
    postView,
    postImage,
    account_avatar,
    account_fullname,
    bg_class
) {
    return `
        <!-- Card item START -->
            <div class="col-sm-6 col-lg-4 grid-item">
                <div class="card mb-4">
                    <!-- Card img -->
                    <div class="card-fold position-relative">
                        <img class="card-img" src="${postImage}" alt="Card image">
                    </div>
                    <div class="card-body px-0 pt-3">
                        <h4 class="card-title">
                            <a href="/bai-viet/${postId}" class="btn-link text-reset stretched-link fw-bold">${postTitle}</a>
                        </h4>
                        <p class="card-text" >${postDescription}</p>
                        <!-- Card info -->
                        <ul class="nav nav-divider align-items-center text-uppercase small">
                            <li class="nav-item">
                                <a href="/gioi-thieu" class="nav-link text-reset btn-link">${account_fullname}</a>
                            </li>
                            <li class="nav-item">${postDate}</li>
                        </ul>
                    </div>
                </div>
            </div>
        <!-- Card item END -->
    `;
}
/////////////////////////////////////////////////
//load more post item for home page
var index_ = 5;
$('#loadMore').click(function () {
    if(reset == 5){
        index_ = 5;
        reset = 0;
    }
    index_ = index_ + 5;
    apiGetPosts_loadMore(
        '/api/load-more/',
        index_,
        postItem
    );
});
//////////////////////////////////////////////////
//load more post item for search-result page
var index_s = 5;
$('#loadMore_s').click(function () {
    if(reset == 5){
        index_s = 5;
        reset = 0;
    }
    index_s = index_s + 5;
    apiGetPosts_loadMore(
        '/api/search/',
        index_s,
        postItem_s
    );
});
//////////////////////////////////////////////////


// api load more
function apiGetPosts_loadMore(url,index,func) {
    $.ajax({
        url: url + index,
        type: 'GET'
    }).done(function (data) {
        if (data.length != 0) {
            for (let i = 0; i < data.length; i++) {
                var rng = Math.floor(Math.random() * randomBgClass.length);
                var div = document.createElement('div');
                div.addClass('newItem');
                div.innerHTML = func(
                    data[i].category_categoryName,
                    data[i].postID,
                    data[i].postTitle,
                    data[i].postDescription,
                    data[i].postDate,
                    data[i].postViews,
                    data[i].postImage,
                    data[i].account_avatar,
                    data[i].account_fullname,
                    randomBgClass[rng]
                );
                $('#addItem').append(div);
            }
            $('#loadMoreTxt').html('Tải thêm');
            if (data.length < 5) {
                $('#loadMoreTxt').html('Thu gọn');
                $('#loadArrow').addClass('bi-arrow-up-circle ');
            }
        } else {
            $('#loadMoreTxt').html('Tải thêm');
            $('#loadArrow').removeClass('bi-arrow-up-circle ');
            $('#loadArrow').addClass('bi-arrow-down-circle ');
            $('.newItem').remove();
            reset = 5;
        }
    });
}


///////////////////////////////////////////////////////////////////////////////
var index_c = 6;
$('#newer').click(function () {
    $('.post_item').remove();
    index_c = index_c + 6;
    apiGetPosts_pagination(index_c);
    $('#older').css('display', 'block');
});

$('#older').click(function () {
    $('.post_item').remove();
    index_c = index_c - 6;
    apiGetPosts_pagination(index_c);
    if(index_c <= 6){
        index_c = 6;
        $('#older').css('display', 'none');
        $('#newer').css('display', 'block');
    }else {
        $('#older').css('display', 'block');
        $('#newer').css('display', 'block');
    }
})
//load more for pagination
function apiGetPosts_pagination(index) {
    $.ajax({
        url : '/api/category/' + index,
        type : 'GET'
    }).done(function (data) {
        for (let i = 0; i < data.length; i++) {
            var rng = Math.floor(Math.random() * randomBgClass.length);
            var div = document.createElement('div');
            div.addClass('col-sm-6');
            div.addClass('post_item');
            div.innerHTML = postItem_c(
                data[i].category_categoryName,
                data[i].postID,
                data[i].postTitle,
                data[i].postDescription,
                data[i].postDate,
                data[i].postViews,
                data[i].postImage,
                data[i].account_avatar,
                data[i].account_fullname,
                randomBgClass[rng]
            )
            $('#addItem').append(div);
        }
        if(data.length < 6){
            $('#newer').css('display', 'none');
            $('#older').css('display', 'block');
        }
    });
}