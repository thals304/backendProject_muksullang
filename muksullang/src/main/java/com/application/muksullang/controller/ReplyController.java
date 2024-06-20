package com.application.muksullang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.application.muksullang.service.ReplyService;

@Controller
public class ReplyController {

	@Autowired
    private ReplyService replyService;
}
