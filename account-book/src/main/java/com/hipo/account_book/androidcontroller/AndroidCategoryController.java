package com.hipo.account_book.androidcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hipo.account_book.androidservice.AndroidCategoryService;
import com.hipo.account_book.vo.OptionVo;

@Controller
@RequestMapping("/android/{id}")
public class AndroidCategoryController {

	@Autowired
	AndroidCategoryService aCategoryService;

	@RequestMapping("/addCategory")
	public void addCategory(HttpServletRequest request, @PathVariable String id) {
		String category = request.getParameter("category");
		if (aCategoryService.addCategoryStraight(category, id)) {
			System.out.println("성공!");
		} else {
			System.out.println("실패!");
		} /*
			 * OptionVo optionVo = aCategoryService.getCategoryId(category);
			 * 
			 * if (optionVo == null) {
			 * System.out.println("option null in CategoryController");
			 * aCategoryService.addCategory(category); optionVo =
			 * aCategoryService.getCategoryId(category); }
			 * optionVo.setCategory(category); optionVo.setId(id);
			 * System.out.println("Option Checking : " + optionVo.toString());
			 * 
			 * if (aCategoryService.addUsersCategory(optionVo)) {
			 * System.out.println("성공!!"); }
			 */
	}

	@RequestMapping("/getCategoryList")
	@ResponseBody
	public Map<String, String> getCategoryList(@PathVariable String id) {
		List<OptionVo> optionList = aCategoryService.getCategoryList(id);
		Map<String, String> categoryMap = new HashMap<>();
		for (int i = 0; i < optionList.size(); i++) {
			categoryMap.put("category" + (i + 1), optionList.get(i).getCategory());
		}
		return categoryMap;
	}

	@RequestMapping("/deleteCategory")
	public void deleteCategory(HttpServletRequest request, @PathVariable String id) {
		String category = request.getParameter("category");
		OptionVo optionVo = aCategoryService.getCategoryId(category);
		optionVo.setId(id);
		if (aCategoryService.deleteCategory(optionVo))
			;
		System.out.println("지우기성공");
	}

	@RequestMapping("/setLimitValue")
	public void setLimitValue(HttpServletRequest request, @PathVariable String id) {
		Map<String, String> params = new HashMap<>();
		params.put("category", request.getParameter("category"));
		params.put("id", id);
		params.put("moneyLimit", request.getParameter("limit"));
		params.put("categoryId", aCategoryService.getCategoryId(params.get("category")).getCategoryId() + "");
		System.out.println("CategoryController setLimitValue MapValueChecking : " + params.toString());
		aCategoryService.updateMoneyLimit(params);
	}

}
