package com.the.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.the.domain.WebBoard;
import com.the.persistence.WebBoardRepository;
import com.the.vo.PageMaker;
import com.the.vo.PageVO;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/board/")
@Log
public class WebBoardController {
	
	@Autowired
	private WebBoardRepository repo;

	@GetMapping("/list")
	public void list(@ModelAttribute("pageVO") PageVO vo, Model model) {

		Pageable page=vo.makePabeable(0, "bno");
		Page<WebBoard> result=repo.findAll(repo.makePredicate(vo.getType(), vo.getKeyword()), page);
		log.info(""+ page);
		log.info(""+result);
		
		model.addAttribute("pageMaker", new PageMaker(result));
	}
}















