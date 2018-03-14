package mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mvc.model.AccountDTO;
import mvc.service.AccountDAO;

@Controller
public class IndexController {

	@Autowired
	AccountDAO aDAO;
		//회원가입 페이지
	@RequestMapping("/register.do")
	public String registerHandle() {
		
		return "insta_register";
	}
	//로그인 페이지
	@RequestMapping("/login.do")
	public String loginHandle(@RequestParam MultiValueMap<String, String> vmap, ModelMap modelMap) {
		String id = vmap.getFirst("id");
		String pass = vmap.getFirst("pass");
		
		AccountDTO aDTO = aDAO.selectOneAccount(id, pass);
		if (aDTO == null) {
			System.out.println("[SERVER]: login failed");
			return "insta_login";
		}
		
		modelMap.put("aDTO", aDTO);
		System.out.println("[SERVER]: login success");
		return "insta_login";
	}
	//게시물 업로드 페이지
	@RequestMapping("/upload.do")
	public String uploadHandle() {
		
		return "insta_upload";
	}
	//메인 페이지
	@RequestMapping("/main.do")
	public String mainHandle() {
		
		return"insta_main";
	}
}
