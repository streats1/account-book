package com.hipo.account_book.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hipo.account_book.dto.JSONResult;
import com.hipo.account_book.service.FrontService;
import com.hipo.account_book.vo.UserVo;

@Controller
public class FrontController {
	@Autowired
	private FrontService frontService;
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@ResponseBody
	@RequestMapping("/fbjoin")
	public JSONResult fbjoin(@RequestBody Map<String, Object> map){
		UserVo uservo = frontService.fblogin(map);
		if(uservo==null){
			frontService.fbjoin(map);
		}
		return JSONResult.success(map.get("email"));
	}
	
	@ResponseBody
	@RequestMapping("/checkid")
	public JSONResult checkid(@RequestBody Map<String, Object> map){
		if(frontService.checkId(map)=="fail"){
			return JSONResult.fail("");
		}
		return JSONResult.success(frontService.checkId(map));
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo uservo, BindingResult result, Model model){
		if (result.hasErrors()) {
			model.addAttribute("result", result.getModel());
			return "redirect:/login";
		}
		frontService.join(uservo);
		return "redirect:/login";
	}
}
