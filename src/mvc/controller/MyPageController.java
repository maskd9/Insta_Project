package mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mvc.model.AccountDTO;
import mvc.service.AccountDAO;
import mvc.service.FollowDAO;
import mvc.service.MessengerDAO;
import mvc.service.PostDAO;
import mvc.service.ReplyDAO;

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
	MessengerDAO mDAO;

	@RequestMapping("/index.do")
	public String MyPageHandle(@CookieValue(name = "setId", required = false) String setId, ModelMap modelMap) {
		System.out.println("[SERVER]: MyPage success" + setId);
		String id = setId;
		// 계정 정보
		AccountDTO aDTO = aDAO.selectOneAccountre(id);
		modelMap.put("aDTO", aDTO);

		// 이전에 쓴 모든 게시물 정보
		List<Map> myPost = pDAO.findAllPost();
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
		
		modelMap.put("following", followingList);
		modelMap.put("follower", followerList);

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

		// 팔로워 - 나를 구독하는 사람들
		List<AccountDTO> followerList = new ArrayList<>();
		followerList = aDAO.selectAllAccountFollower(id);
		
		modelMap.put("following", followingList);
		modelMap.put("follower", followerList);

		return "myPage_following";
	}
	
	@RequestMapping("/insert.do")
	public String insertHandle(@RequestParam MultiValueMap<String,String> vmap) {
		String me = vmap.getFirst("me");
		String target = vmap.getFirst("target");
		System.out.println("[SERVER]: insert follow, me->"+me+" | target->"+target);
		
		int r= fDAO.insertFollow(me, target);
		if(r==0) {
			System.out.println("[SERVER]: follow failed "+r);
			return "insta_follow";
		}
		System.out.println("[SERVER]: follow success");
		
		return "redirect:/mypage/follower.do";
	}
	
	@RequestMapping("/delete.do")
	public String deleteHandle(@RequestParam MultiValueMap<String,String> vmap) {
		String me = vmap.getFirst("me");
		String target = vmap.getFirst("target");
		System.out.println("[SERVER]: delete follow, me->"+me+" | target->"+target);
		
		int r= fDAO.deleteFollow(me, target);
		if(r==0) {
			System.out.println("[SERVER]: delete failed "+r);
			return "insta_follow";
		}
		System.out.println("[SERVER]: delete success");
		
		return "redirect:/mypage/following.do";
	}

}