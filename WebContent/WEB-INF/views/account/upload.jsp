<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div style="margin-top: 20px" align="center">
	<h2>게시물 등록</h2>
	<small>사진을 선택한 후, 멘트를 달아주세요.</small>
	<form action="/account/uploadp.do" method="post" enctype="multipart/form-data">
		<div>
			<h2><b style="font-size: 15px; ">이미지 미리보기</b></h2>
			<div class="imgs">
				<a href="javascript:" onclick="uploadAction();" id="upload">파일 선택</a>
				<input type="file" name="photo" id="photo" accept="image/*"
				 	 style="display: none;" multiple />
			</div>		
		</div>

		<div>
			<div class="content">
				<img id="img">
			</div>
		</div>
		<div>
			<textarea name="comment" style="resize: none; width: 294px; height: 70px; padding: 2px; font-family: 맑은고딕"></textarea>
		</div>

		<p>
			<button type="submit" id="bt2">업로드</button>
		</p>
	</form>
</div>
<script>
	var select_files = [];	//선택한 파일들을 모아놓은 배열
	var capacity = 10;		//업로드 가능한 파일의 최대 개수
	
	$(document).ready(function() {
		$("#photo").on("change", handleImgSelect);
	});
	
	function uploadAction() {
		$("#photo").trigger("click");
	}
	
	function handleImgSelect(e) {
		select_files = [];
		$(".content").empty();	//디폴트로 이미지들을 비워놓기 위해
		
		//console.log(this.files[0]);
		var files = e.target.files;	//
		var filesArr = Array.prototype.slice.call(files);	//files 배열에서 call한 부분을 복사한 배열
		
		var index = 0;	//미리보기 배열의 인덱스와 연결할 변수
		filesArr.forEach(function(f) {
			if (!f.type.match("image.*")) {
				window.alert("이미지만 선택 가능 합니다.");
				return;
			}
			
			var result = select_files.push(f);	//f를 select_files에 집어넣는다
			console.log(result);
			
			if (select_files.length > capacity) {
				window.alert("이미지는 최대 10장까지 등록 가능합니다.");
				return;
			}	
			
			var reader = new FileReader();
			console.log(reader);
			reader.onload = function(e) {
				var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImgAction("
							+index+")\" id=\"img_id_"+index+"\"><img src=\"" 
							+ e.target.result + "\" style=\"width:120px; height:120px; padding: 10px;\" data-file='"
							+f.name+"' class='selProductFile' title='Click to remove'></a>";
				//$("#preview").attr("src", this.result);
				$(".content").append(html);
				index++;
			}
			reader.readAsDataURL(f);
		});
	}
	
	function deleteImgAction(index) {
		select_files.splice(index, 1);
		var rst = window.confirm("정말 삭제하시겠습니까?");
		if(rst) {
			var img_id = "#img_id_" + index;
			$(img_id).remove();
		}
		
		console.log(select_files);
	}
	
</script>