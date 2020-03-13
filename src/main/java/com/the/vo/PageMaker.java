package com.the.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;

@ToString(exclude = "pageList")
@Getter
public class PageMaker<T> {
	private Page<T> result;
	
	private Pageable pervPage;
	private Pageable nextPage;
	
	private int currentPageNum;
	private int totalPageNum;
	
	private Pageable currentPage;
	private List<Pageable> pageList;
	
	public PageMaker(Page<T> result) {
		this.result=result;
		this.currentPage=result.getPageable();
		this.currentPageNum=currentPage.getPageNumber()+1;
		this.totalPageNum=result.getTotalPages();
		this.pageList=new ArrayList<>();
		calcPages();
	}
	
	private void calcPages() {
		//페이지 표현개수 : 10개 기준
		
		//페이지 마지막번호
		int tempEndNum=(int)(Math.ceil(this.currentPageNum/10.0)*10);
		// 1~10 :  Math.ceil(1/10.0) * 10 -> Math.ceil(0.1) * 10 -> (1*10) => 10
		// 11~20: Math.ceil(11/10.0) * 10 -> Math.ceil(1.1) * 10 -> (2*10) => 20
		
		//페이지 시작번호
		int startNum= tempEndNum-9;
		Pageable startPage=this.currentPage;
		System.out.println("currentPageNum : "+currentPageNum);
		//System.out.println("startPage : "+startPage);
		
		//시작Pageable 구하기
		for(int i=startNum; i< this.currentPageNum; i++) {
		//	//System.out.println("previousOrFirst:"+startPage.previousOrFirst());
			startPage=startPage.previousOrFirst();
			//이전 페이지 또는 처음 페이지로 이동
		}
		
		//이전페이지구하기
		//시작Pageable.getPageNumber() 0이면 이전페이지는 미존재
		//예:만약 시작Pageable이 11페이지이면 10페이지로
		this.pervPage=startPage.getPageNumber()<=0?null: startPage.previousOrFirst();
		

		//tempEndNum값이 총페이지 범위를 넘어가면..
		if(this.totalPageNum < tempEndNum) {
			tempEndNum = this.totalPageNum;//마직막페이지번호로 세팅
			this.nextPage=null;//표현할 다음페이지 없음
		}
		
		
		//화면에  표현된 시작페이지 정보~마지막페이지까지 정보를 List에 저장
		Pageable addPage=startPage;
		for(int i=startNum; i<=tempEndNum; i++) {
			pageList.add(addPage);
			addPage=addPage.next();
		}
		//System.out.println("addPage : "+addPage);
	
		//addPage는 현재표현된 제일마지막 페이지+1
		System.out.printf("%d<%d\n",addPage.getPageNumber()+1 , totalPageNum);
		this.nextPage=addPage.getPageNumber()+1 < totalPageNum? addPage: null;
	}
}
