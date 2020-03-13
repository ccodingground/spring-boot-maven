package com.the;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.the.domain.WebBoard;
import com.the.persistence.WebBoardRepository;

import lombok.extern.java.Log;

@Commit
@Log
@SpringBootTest
public class WebBoardRepositoryTests {
	
	@Autowired
	WebBoardRepository repo;
	
	//@Test
	public void insertBoardDummy() {
		IntStream.range(1,301).forEach(i->{
			WebBoard board=new WebBoard();
			board.setTitle("제목.."+i);
			board.setContent("내용.."+i);
			board.setWriter("user.."+i%10);
			
			repo.save(board);
		});
	}
	
	//@Test
	public void testList1() {
		
		Pageable page=PageRequest.of(0,10, Sort.Direction.DESC, "bno");
		
		Page<WebBoard> result=repo.findAll(repo.makePredicate(null, null), page);
		
		log.info("PAGE :" +result.getPageable());
		log.info("-----------------------");
		result.getContent().forEach(board->{
			log.info(""+board);
		});
		
		//select webboard0_.bno as bno1_0_, webboard0_.content as content2_0_, webboard0_.regdate as regdate3_0_, webboard0_.title as title4_0_, webboard0_.updatedate as updateda5_0_, webboard0_.writer as writer6_0_ 
		//from tbl_webboard webboard0_ 
		//where webboard0_.bno>? order by webboard0_.bno desc limit ?
		
		//select count(webboard0_.bno) as col_0_0_ 
		//from tbl_webboard webboard0_ where webboard0_.bno>?
	}
	
	//@Test
	public void testList2() {
		Pageable page=PageRequest.of(0,20, Sort.Direction.DESC, "bno");
		
		Page<WebBoard> result=repo.findAll(repo.makePredicate("t", "10"), page);
		
		log.info("PAGE :" +result.getPageable());
		log.info("-----------------------");
		result.getContent().forEach(board->{
			log.info(""+board);
		});
		
		//select webboard0_.bno as bno1_0_, webboard0_.content as content2_0_, webboard0_.regdate as regdate3_0_, webboard0_.title as title4_0_, webboard0_.updatedate as updateda5_0_, webboard0_.writer as writer6_0_ 
		//from tbl_webboard webboard0_ 
		//where webboard0_.bno>? and (webboard0_.title like ? escape '!') 
		//order by webboard0_.bno desc limit ?
	}
}











