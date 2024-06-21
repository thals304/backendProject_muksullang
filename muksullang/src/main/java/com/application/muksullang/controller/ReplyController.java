package com.application.muksullang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.muksullang.dto.ReplyDTO;
import com.application.muksullang.service.ReplyService;
/*
 * reply 안한 부분
 * - 리뷰 수정하기, 리뷰 삭제하기 마무리
 * */
@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
    private ReplyService replyService;
	
	@PostMapping("/createReply")
	@ResponseBody
	public String creatReply(@ModelAttribute ReplyDTO replyDTO) {
		replyService.creatReply(replyDTO);
		
		String jsScript = "";
		jsScript += "<script>";
		jsScript += "alert('리뷰가 등록되었습니다.');";
		jsScript += "location.href='/post/bestOfDetail?postId=" + replyDTO.getPostId() + "';";
		jsScript += "</script>";
		
		return jsScript;
	}
	
	@GetMapping("/updateReply")
	public String updateReply(@RequestParam("replyId") long replyId, Model model) {
		
		model.addAttribute("replyDTO", replyService.getReplyDetail(replyId));
		
		return "reply/updateReply";
	}
		
}
