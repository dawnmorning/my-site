<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">


function renderGuestbookEntry(entry, prepend = false) {
    var guestbookList = $('#list-guestbook');

    // 게스트북 항목을 위한 HTML 구조 생성
    var listItem = $("<li data-no='" + entry.no + "'></li>");
    listItem.append("<strong>" + entry.name + "</strong>");
    listItem.append(
        "<p>" + entry.contents + "<br>" +
       " <em>" + entry.regDate + "</em></p>"
    );

    // 삭제 링크 추가 (이벤트 핸들러를 연결해야 함)
    var deleteLink = $("<a href='#' data-no='" + entry.no + "'>삭제</a>");
    listItem.append(deleteLink);

    // prepend 매개변수가 true로 설정된 경우 항목을 목록의 시작 부분에 추가
    if (prepend) {
        guestbookList.prepend(listItem);
    } else {
        guestbookList.append(listItem);
    }

    // 삭제 링크에 클릭 이벤트 핸들러 바인딩
    // (이 부분은 '삭제' 기능의 구현에 따라 추가적인 로직이 필요할 수 있습니다)
    deleteLink.click(function(e) {
        e.preventDefault();
        var entryNo = $(this).data('no'); // 'no' 데이터 속성에서 항목 번호 가져오기

        // 삭제 전 사용자에게 확인 받기
        if (!confirm('정말로 삭제하시겠습니까?')) {
            return;
        }

        // 서버로 삭제 요청을 보내는 AJAX 호출
        $.ajax({
            type: "DELETE", // HTTP DELETE 메서드 사용
            url: "${pageContext.request.contextPath}/api/gb/" + entryNo, // 해당 번호의 게스트북 항목에 대한 URL
            success: function(response) {
                if (response.data) { // 서버가 성공적으로 처리했는지 확인
                    // 해당 항목 제거
                    listItem.remove();
                } else {
                    alert('삭제할 수 없습니다. 비밀번호를 확인해주세요.');
                }
            },
            error: function(e) {
                console.error("ERROR: ", e);
                alert('삭제 과정에서 오류가 발생했습니다.');
            }
        })
    });
}
function loadGuestbookEntries() {
    $.ajax({
        type: "get",
        url: "${pageContext.request.contextPath}/api/gb", // 이 URL 경로는 서버 설정에 따라 달라질 수 있습니다.
        dataType: "json",
        success: function(response) {
            // 서버에서 반환된 데이터를 확인합니다.
            console.log(response);

            // 가져온 엔트리들을 렌더링합니다.
            $.each(response.data, function(i, entry) {
                // 'false'는 항목을 목록의 끝에 추가하라는 의미입니다. 
                // 만약 새 항목을 목록의 시작 부분에 추가하고 싶다면 'true'를 전달하세요.
                renderGuestbookEntry(entry, false);
            });
        },
        error: function(e) {
            // 오류 발생 시 콘솔에 오류 로그 출력
            console.error("ERROR: ", e);
        }
    });
}
	$(document).ready(function(){
		loadGuestbookEntries();
		$('#add-form').submit(function(e){
			e.preventDefault()
			
			var formData = {
				name: $('#input-name').val(),
				password: $('#input-password').val(),
				contents: $('#tx-content').val()
			}
			$.ajax({
				type:"post",
				contentType: "application/json",
				url: "${pageContext.request.contextPath}/api/gb",
				data: JSON.stringify(formData),
				dataType: 'json',
				success : function(res){
					console.log(res)
				},
				error:function(e){
					console.log(formData)
					console.error("ERROR: ", e)
				}
			})
		})
	})
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>