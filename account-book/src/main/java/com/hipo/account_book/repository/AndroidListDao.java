package com.hipo.account_book.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hipo.account_book.vo.ListVo;

@Repository
public class AndroidListDao {

	@Autowired
	private SqlSession sqlSession;

	public boolean addList(ListVo vo) {
		return ((sqlSession.insert("android.add", vo)) == 1);
	}

	public List<ListVo> getList(String id) {
		System.out.println("idididid : "+id);
		List<ListVo> list = sqlSession.selectList("android.getListById", id);
		System.out.println(list.toString());
		return list;
	}

}
