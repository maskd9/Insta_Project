package mvc.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import mvc.model.AccountDTO;
import mvc.service.AccountDAO;
import mvc.service.DeleteAccountDAO;
import mvc.service.ExtractService;
import mvc.service.FollowDAO;
import mvc.service.MessengerDAO;
import mvc.service.PostDAO;
import mvc.service.ReplyDAO;
import mvc.service.SearchDAO;
import mvc.service.UploadService2;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

	@Autowired
	FollowDAO fDAO;
	@Autowired
	AccountDAO aDAO;
	@Autowired
	PostDAO pDAO;
	@Autowired
	ReplyDAO rDAO;
	@Autowired
	SearchDAO sDAO;
	@Autowired
	MessengerDAO mDAO;
	@Autowired
	DeleteAccountDAO daDAO;
	@Autowired
	UploadService2 us2;
	@Autowired
	ExtractService es;
	@Autowired
	ServletContext ctx;
	@Autowired
	Gson gson;

	@RequestMapping("/index.do")
	public String MyPageHandle(@CookieValue(name = "setId", required = false) String setId, ModelMap modelMap) {
		System.out.println("[SERVER]: MyPage success" + setId);
		String id = setId;
		// 계정 정보
	
		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);

		// 이전에 쓴 모든 게시물 정보
		List<Map> myPost = sDAO.findSearchTag(id);
		if (myPost != null)
			modelMap.put("myPost", myPost);

		// 팔로잉 - 내가 구독한 사람들
		List<AccountDTO> followingList = new ArrayList<>();
		followingList = aDAO.selectAllAccountFollowing(id);

		// 팔로워 - 나를 구독하는 사람들
		List<AccountDTO> followerList = new ArrayList<>();
		followerList = aDAO.selectAllAccountFollower(id);

		modelMap.put("following", followingList);
		modelMap.put("follower", followerList);

		return "insta_myPage";
	}

	@RequestMapping(path="/markBoard.do")
	public String markBoardHandle(@CookieValue(name = "setId", required = false) String setId,ModelMap modelMap) {
		System.out.println("[SERVER]: MyPage success" + setId);
		String id = setId;
		List<Map> result = new ArrayList<>();
		result=	rDAO.markBoardFind(id);
		List<Map> result2=rDAO.AllmarkBoardFind(result);
		
		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);

		// 이전에 쓴 모든 게시물 정보
		List<Map> myPost = sDAO.findSearchTag(id);
		if (myPost != null)
			modelMap.put("myPost", myPost);

		// 팔로잉 - 내가 구독한 사람들
		List<AccountDTO> followingList = new ArrayList<>();
		followingList = aDAO.selectAllAccountFollowing(id);

		// 팔로워 - 나를 구독하는 사람들
		List<AccountDTO> followerList = new ArrayList<>();
		followerList = aDAO.selectAllAccountFollower(id);

		modelMap.put("following", followingList);
		modelMap.put("follower", followerList);
		modelMap.put("myPost", result2);
		return "insta_myPage";
	}
/*	@RequestMapping(path="/markSearch.do", produces="application/json;charset=utf-8")
	@ResponseBody
	public String markSearchHandle(@CookieValue(name = "setId", required = false) String setId, @RequestParam MultiValueMap<String,String> vmap) {
		System.out.println("[SERVER]: MyPage success" + vmap.get("boardId[]"));
		String id = setId;
		List<Map> result = rDAO.AllmarkBoardFind(vmap);
		Gson gson = new Gson();
		return gson.toJson(result);
	}
*/
	@RequestMapping("/follower.do")
	public String followerHandle(@CookieValue(name = "setId", required = false) String setId, ModelMap modelMap) {
		System.out.println("[SERVER]: MyPage success" + setId);
		String id = setId;
		// 계정 정보

		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);

		// 팔로잉 - 내가 구독한 사람들
		List<AccountDTO> followingList = new ArrayList<>();
		followingList = aDAO.selectAllAccountFollowing(id);

		// 팔로워 - 나를 구독하는 사람들
		List<AccountDTO> followerList = new ArrayList<>();
		followerList = aDAO.selectAllAccountFollower(id);

		// 팔로워 - 나를 구독하는 사람들조인 문
		List<Map> followerListJoin = new ArrayList<>();
		followerListJoin = fDAO.selectFollwerProfileId(id);

		modelMap.put("following", followingList);
		modelMap.put("follower", followerList);
		modelMap.put("followerJoin", followerListJoin);

		return "myPage_follower";
	}

	@RequestMapping("/following.do")
	public String followingHandle(@CookieValue(name = "setId", required = false) String setId, ModelMap modelMap) {
		System.out.println("[SERVER]: MyPage success" + setId);
		String id = setId;
		// 계정 정보

		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);

		// 팔로잉 - 내가 구독한 사람들
		List<AccountDTO> followingList = new ArrayList<>();
		followingList = aDAO.selectAllAccountFollowing(id);

		// 팔로잉 - 내가 구독한 사람들
		List<Map> followingJoin = new ArrayList<>();
		followingJoin = fDAO.selectFollwingProfileId(id);

		// 팔로워 - 나를 구독하는 사람들
		List<AccountDTO> followerList = new ArrayList<>();
		followerList = aDAO.selectAllAccountFollower(id);

		modelMap.put("following", followingList);
		modelMap.put("follower", followerList);
		modelMap.put("followingJoin", followingJoin);

		return "myPage_following";
	}

	@RequestMapping("/insert.do")
	public String insertHandle(@RequestParam MultiValueMap<String, String> vmap) {
		String me = vmap.getFirst("me");
		String target = vmap.getFirst("target");
		System.out.println("[SERVER]: insert follow, me->" + me + " | target->" + target);

		int r = fDAO.insertFollow(me, target);
		if (r == 0) {
			System.out.println("[SERVER]: follow failed " + r);
			return "insta_follow";
		}
		System.out.println("[SERVER]: follow success");

		return "redirect:/mypage/follower.do";
	}

	@RequestMapping("/delete.do")
	public String deleteHandle(@RequestParam MultiValueMap<String, String> vmap) {
		String me = vmap.getFirst("me");
		String target = vmap.getFirst("target");
		System.out.println("[SERVER]: delete follow, me->" + me + " | target->" + target);

		int r = fDAO.deleteFollow(me, target);
		if (r == 0) {
			System.out.println("[SERVER]: delete failed " + r);
			return "insta_follow";
		}
		System.out.println("[SERVER]: delete success");

		return "redirect:/mypage/following.do";
	}

	// 기존의 계정 정보 넘길 때
	@RequestMapping(path = "/edit.do", method = RequestMethod.GET)
	public String editHandle(@RequestParam Map param, ModelMap modelMap,
			@CookieValue(name = "setId", required = false) String setId) {
		System.out.println("[SERVER]: login success" + setId);
		String id = setId;

		// 계정 정보
		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);

		System.out.println("[SERVER]: login success");
		return "mypage_edit";
	}

	// 수정한 계정 정보 넘길 떄
	@RequestMapping(path = "/edit.do", method = RequestMethod.POST)
	public String editUpdateHandle(@RequestParam Map param, @CookieValue(name = "setId", required = false) String setId,
			ModelMap modelMap) {
		System.out.println("[SERVER]: login success" + setId);
		System.out.println(param.get("name") + "/" + param.get("intro") + "/"
				+ param.get("email") + "/" + param.get("phone") + "/" 
				+ param.get("birth") + "/" + param.get("gender")
				+ "/" + param.get("privateAccount"));

		String id = setId;

		// 공개범위(scope) 처리하기
		String flag = (String) param.get("privateAccount");
		param.put("scope", flag == null ? 0 : 1);
		// 계정 정보
		param.put("id", id);
		int r = aDAO.updateAccount(param);
		pDAO.updateProfilebirthgender(param,setId);
		if (r > 0) {
			AccountDTO aDTO = aDAO.selectOneAccountre(id);
			modelMap.put("aDTO", aDTO);
		}
		
		System.out.println("[SERVER]: login success");
		return "redirect:/mypage/index.do";
	}

	// 기존의 비밀번호 정보 넘길 때
	@RequestMapping(path = "/pass.do", method = RequestMethod.GET)
	public String passHandle(ModelMap modelMap, @CookieValue(name = "setId", required = false) String setId) {
		System.out.println("[SERVER]: login success" + setId);
		String id = setId;
		// 계정 정보
		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);

		System.out.println("[SERVER]: login success");
		return "mypage_pass";
	}

	// 수정한 비밀번호 정보 넘길 때
	@RequestMapping(path = "/pass.do", method = RequestMethod.POST)
	public String passUpdateHandle(@RequestParam Map param, ModelMap modelMap,
			@CookieValue(name = "setId", required = false) String setId) {
		System.out.println("[SERVER]: login success" + setId);
		System.out.println(param.get("oldPass") + " / " + param.get("newPass") + " / " + param.get("confirmPass"));
		String id = setId;
		String pass = (String) param.get("newPass");
		String oldPass=(String)param.get("oldPass");
		// 계정 정보
		param.put("id", id);
		param.put("pass", pass);
		AccountDTO dto= aDAO.selectOneAccountre(id);
		int r=0;
		if(dto.getPass().equals(oldPass)) {
			r = aDAO.updatePassword(param);
			
		}else {
			AccountDTO aDTO = aDAO.selectOneAccountre(id);
			modelMap.put("aDTO", aDTO);
			modelMap.put("check", "이전 비밀번호와 같아야합니다.");
			return "mypage_pass";
		}
		if (r > 0) {
			AccountDTO aDTO = aDAO.selectOneAccountre(id);
			modelMap.put("aDTO", aDTO);
			modelMap.put("checkOk", "비밀번호가 변경되었습니다.");
		}
		
		System.out.println("[SERVER]: login success");
		return "mypage_pass";
	}

	@RequestMapping(path = "/uploadProfile.do", method = RequestMethod.POST)
	public String uploadProfileHandle(@RequestParam(name = "profile") MultipartFile file,
			@CookieValue(name = "setId", required = false) String setId, ModelMap modelMap) throws Exception {
		System.out.println("[SERVER]:프로필 사진 넘겼다" + setId);
		System.out.println("얘가 받아야한다." + file);
		String id = setId;
		File target = us2.uploadImage(file);
		String fileName = "/" + target.getName();
		System.out.println(fileName);
		Map param = new HashMap<>();
		param.put("id", id);
		param.put("profile", fileName);
		int r = aDAO.updateProfile(param);
		if (r > 0) {
			AccountDTO aDTO = aDAO.selectOneAccountre(id);
			modelMap.put("aDTO", aDTO);
		}
		pDAO.updateProfile(param);
		return "redirect:/mypage/index.do";
	}
	
	@RequestMapping(path="/uploadProfileByAjax.do", method = RequestMethod.POST)
	@ResponseBody
	public String uploadProfileHandleByAjax(@RequestParam Map param) {
		
		return "";
	}
	
	@RequestMapping("/deletePage.do")
	public String deletePageHandle(@CookieValue(name="setId") String id, ModelMap modelMap) {
		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);
		return "delete_account";
	}
	
	@RequestMapping(path = "/deleteAccount.do", method = RequestMethod.GET, produces="application/json;charset=utf-8")
	@ResponseBody
	public String deleteAccountHandle(@RequestParam Map param, 
				@CookieValue(name="setId", required=false) String setId,
				HttpServletResponse resp, HttpServletRequest req,HttpSession se) {
		HttpSession session = req.getSession();
		AccountDTO dto=aDAO.selectOneAccountre(setId);
		System.out.println("비밀번호 확인"+dto.getPass());
		pDAO.deletePostAllById(setId);
		String id = setId;
		String deleteReason = (String) param.get("reason");
		String password = (String) param.get("password");
		System.out.println(id + " / " + deleteReason + " / " + password);
		
		if (!password.equals(dto.getPass())) {
			System.out.println("[SERVER] 등록 실패...");
			return "{\"result\" : false}";
		}
		Cookie[] cookies = req.getCookies();

		if (cookies != null) {

			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("setId")) {
					System.out.println(cookies[i].getName());
					cookies[i].setPath("/"); // 유효시간을 0으로 설정
					cookies[i].setMaxAge(0);
					resp.addCookie(cookies[i]); // 응답 헤더에 추가
				}

			}

		}
		se.invalidate();	
		aDAO.deleteAccount(id, password);
		return "{\"result\" : true}";
	}
}
