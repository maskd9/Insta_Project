<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="height:70px;"></div>

<div align="center" style="min-height: 490px;">
	<p>
		<h4 align="left"><b>사람 찾기</b></h4><a href="/follow/index.do"><small align="right">추천 친구</small></a>
	</p>
		<c:forEach var="obj" items="${member }">
		<p style="float: left; width: 25%;">
			<c:choose>
				<c:when test="${empty obj.profile }">
					<img src="/image/insta.jpg" style="width: 30px; height: 30px; border-radius: 30px" id="preview">
				</c:when>
				<c:otherwise>
					<img src="${applicationScope.path }${obj.profile}"
						style="width: 30px; border-radius: 100%" id="profile" />
				</c:otherwise>
			</c:choose>

			<a href="/account/myPage.do?id=${obj.id}" name="id">${obj.id }</a>
			<c:set var="isFollowing" value="false" />
			<c:set var="doneLoop" value="false" />
			<c:forEach var="obj2" items="${following }">
				<c:if test="${not doneLoop }">
					<c:if test="${obj2.id } eq ${obj.id }">
						<input class="follower" type="button" name="${obj.id }" value="팔로잉" />
						<c:set var="isFollowing" value="true" />
						<c:set var="doneLoop" value="true" />
					</c:if>
				</c:if>
			</c:forEach>
			<c:if test="${not isFollowing }">
				<input class="follower" type="button" name="${obj.id }" value="팔로우" />
			</c:if>

		</p>
	</c:forEach>
</div>
	<script>

	$(".follower").click(function() {
		var owner = "${cookie.setId.value}";
		var src =$(this);
		var target = $(this).attr("name");
		
		if($(this).val() == "팔로잉"){
			$.ajax("/follow/delete.do",{
				"method" : "post",
				"async" : true,
				"data" :{
					"owner" : owner,
					"target" : target
				}
			}).done(function(btnFollowing){
				console.log("삭제 들어왔다.");
				src.val("팔로우");
				src.attr("name", target);
			});
			
		} else {
		$.ajax("/follow/insert.do",{
			"method" : "post",
			"async" : true,
			"data" :{
				"owner" : owner,
				"target" : target
			}
		}).done(function(btnFollow){
			console.log("들어왔다."+src);
			src.val("팔로잉");
			src.attr("name", target);
		});
		}
	});
	
</script>