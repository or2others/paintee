// 0: hide, 1:head, 2:contents
var newsStatus = 0;
var newslist = [];


    var likecount = 0;
    var commentcount = 0;
    var postcount = 0;
    var followcount = 0;

$(document).ready(function () {
	// 페이지 로딩 시 Tuesday 스와이프 홈 화면 정보구성
  if (userInfo != null) {
	   initNews();
   }
});

function initNews(){
    var controller = new NewsController();
    controller.getListData();
}

function NewsController() {
	// this.startRow =0;
}
NewsController.prototype = {
	getListData: function () {
		var controller = this;
		AjaxCall.call(apiUrl + "/notify/list",
			null,
			"GET",
			function (result) {
				controller.getListDataRes(result);
			}
		);
	},
	getListDataRes: function (result) {

    likecount     = 0;
    postcount     = 0;
    followcount   = 0;
    commnetcount  = 0;
    newslist = result;
    for (var i = 0 ; i < newslist.length ; i++){

      var data = newslist[newslist.length - i - 1];

      if(data.type == 1 && data.readYn == 'N'){
        likecount++;
      }
      if(data.type == 2 && data.readYn == "N"){
        postcount++;
      }
      if(data.type == 3 && data.readYn == "N"){
        followcount++;
      }
      if(data.type == 4 && data.readYn == "N"){
        commentcount++;
      }
		}

    if(likecount == 0){
      $("#news_like").html('<img class="header_ico" src="ico/like_deact.png">');
    }
    else{
      $("#news_like").html('<img class="header_ico" src="ico/like.png">'+likecount);
    }

    if(commentcount == 0){
      $("#news_comment").html('<img class="header_ico" src="ico/comment_deact.png">');
    }
    else{
      $("#news_comment").html('<img class="header_ico" src="ico/comment.png">'+commentcount);
    }

    if(postcount == 0){
      $("#news_post").html('<img class="header_ico" src="ico/post_deact.png">');
    }
    else{
      $("#news_post").html('<img class="header_ico" src="ico/post.png">'+postcount);
    }

    if(followcount == 0){
      $("#news_follow").html('<img class="header_ico" src="ico/follows_deact.png">');
    }
    else{
      $("#news_follow").html('<img class="header_ico" src="ico/follows.png">'+followcount);
    }

	},
};

function showNewsHead(){
    if(newsStatus==0){
        newsStatus = 1;
        $(".news_container").css("top", mainHeight-60);
        $(".news_box").css("top", "0px");
    }
}

function hideNewsHead(){
    if(newsStatus==1){
        $(".news_container").css("top", "100%");
        $(".news_box").css("top", "0px");
        newsStatus = 0;
    }
}

function showNewsContents(){
    if(newsStatus==1){

        for (var i = 0 ; i < newslist.length ; i++){

          var data = newslist[newslist.length - i - 1];
          console.log(data);
          addNews(data.sender,data.type);

        }
        newsStatus = 2;
        $(".news_container").css(
            {   "top": 0,
                "background-color": "rgba(0,0,0,0.8)"
            });

        $(".news_box").css(
            {   "top": "10%",
                "width": "80%",
                "background-color": "rgb(255,255,255)",
                "color": "rgb(80,80,80)"
        });

        $("#news_like").find(".header_ico").attr("src", "ico/like_dark.png");
        $("#news_comment").find(".header_ico").attr("src", "ico/comment_dark.png");
        $("#news_post").find(".header_ico").attr("src", "ico/post_dark.png");
        $("#news_follow").find(".header_ico").attr("src", "ico/follows_dark.png");
        // setReadNewses();
        exeTranslation('.news_contents', lang);
    }
}
function setReadNewses(){
        if(userInfo!=null){
            AjaxCall.call(apiUrl + "/notify/read",
                null,
                "POST",
                function (result) {
                    followcount = 0;
                    commentcount = 0;
                    postcount = 0;
                    likecount = 0;
                }
            );
        }
        clearNews();
}
function hideNewsContents(){
    if(newsStatus==2){
        $(".news_container").css(
            {   "top": mainHeight-60,
                "background-color": "rgba(0,0,0,0)"
        });
        $(".news_box").css(
            {   "top": "0px",
                "width": "288px",
                "background-color": "rgba(25,25,25,0.2)",
                "color": "rgb(255,255,255)"
        });
        setReadNewses();
        newsStatus = 1;
    }
}

$(".news_header").click(function(e){
    showNewsContents();
    e.stopPropagation();
})

$(".news_container").click(function(){
    // clearNews();
    $(this).find(".news_contents").empty();
    hideNewsContents();
})

/**
 * 모든 알림을 확인한것으로 초기화
 **/
function clearNews(){
    // 아이콘 수정

        if(likecount == 0){
          $("#news_like").html('<img class="header_ico" src="ico/like_deact.png">');
        }
        else{
          $("#news_like").html('<img class="header_ico" src="ico/like.png">'+likecount);
        }

        if(commentcount == 0){
          $("#news_comment").html('<img class="header_ico" src="ico/comment_deact.png">');
        }
        else{
          $("#news_comment").html('<img class="header_ico" src="ico/comment.png">'+commentcount);
        }

        if(postcount == 0){
          $("#news_post").html('<img class="header_ico" src="ico/post_deact.png">');
        }
        else{
          $("#news_post").html('<img class="header_ico" src="ico/post.png">'+postcount);
        }

        if(followcount == 0){
          $("#news_follow").html('<img class="header_ico" src="ico/follows_deact.png">');
        }
        else{
          $("#news_follow").html('<img class="header_ico" src="ico/follows.png">'+followcount);
        }
}

/**
 * 새로운 소식 목록
 */

function addNews(name, type) {
	var adder = new News();
	$(adder.build(name, type)).appendTo($(".news_contents"));
	delete adder;
}

function News() {
    this.list = $("<div>").addClass("news_list");
	this.name = $("<span>").addClass("news_list_name");
	this.like = "<span data-i18n='news.like'></span>";
    this.comment = "<span data-i18n='news.comment'></span>";
    this.post = "<span data-i18n='news.post'></span>";
    this.follow = "<span data-i18n='news.follow'></span>";
	this.build = function(name, type) {
		$(this.name).html(name).click(function () {
			showPersonal(name);
		});
      $(this.list).append(this.name);
		if(type == 1) {
			$(this.list).append(this.like);
		}else if(type == 4) {
			$(this.list).append(this.comment);
		}else if(type == 2) {
			$(this.list).append(this.post);
		}else if(type == 3) {
			$(this.list).append(this.follow);
		}

		return this.list;
	}
}
