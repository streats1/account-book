package com.hipo.account_book.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hipo.account_book.repository.ListDao;
import com.hipo.account_book.vo.BoardVo;
import com.hipo.account_book.vo.ListVo;

@Service
public class ListService {
	@Autowired
	public ListDao dao;
	
	private static final int LIST_SIZE = 8;
	private static final int PAGE_SIZE = 10;

	public List<ListVo> getList(ListVo vo) {
		System.out.println("아이디 ! 불러옵니다" + vo);
		List<ListVo> list = dao.list(vo);
		System.out.println("리스트 불러옵니까????" + list);

		return list;
	}

	public boolean delete(ListVo vo) {
		return dao.delete(vo); 
		
	}

	public String add(ListVo vo) {
		System.out.println("서비스 보 다가지고 오냐??????????" + vo);
		int list = dao.add(vo);
		return null;
	}

	public boolean modify(ListVo vo) {
		return  dao.modify(vo);
		
	}
	
	public void boardadd(String id, BoardVo boardvo, List<MultipartFile> file){
		boardvo.setId(id);
		boardvo.setName(dao.usernameselect(id));
		dao.boardinsert(boardvo);
		boardvo.setBoardId(dao.boardidselect(boardvo));
		try{
			if(file.isEmpty() == true){
				return;
			}
			for(int i=0; i< file.size(); i++)
		    {
		        if(!file.get(i).isEmpty())
		        {
		            CommonsMultipartFile cm = (CommonsMultipartFile) file.get(i);
		            String originalFileName = cm.getOriginalFilename();
		            String extName = originalFileName.substring(originalFileName.lastIndexOf(".")+1,originalFileName.length());
					String saveFileName = generateSaveFileName(extName);
					
					writeFile(cm, saveFileName);
					
					boardvo.setPhoto(saveFileName);
					
					dao.imagesave(boardvo);
		        }
		        boardvo.setPhoto(generateSaveFileName("無"));
				dao.imagesave(boardvo);
		    }
		} catch(IOException e) {
			new RuntimeException("upload file:"+e);
		}
	}
	
	public void boardedit(String id, BoardVo boardvo, List<MultipartFile> file){
		boardvo.setId(id);
		dao.boardupdate(boardvo);
		deleteFile(boardvo.getBoardId());
		dao.imagedelete(boardvo.getBoardId());
		try{
			if(file.isEmpty() == true){
				return;
			}
			for(int i=0; i< file.size(); i++)
		    {
		        if(!file.get(i).isEmpty())
		        {
		            CommonsMultipartFile cm = (CommonsMultipartFile) file.get(i);
		            String originalFileName = cm.getOriginalFilename();
		            String extName = originalFileName.substring(originalFileName.lastIndexOf(".")+1,originalFileName.length());
					String saveFileName = generateSaveFileName(extName);
					
					writeFile(cm, saveFileName);
					
					boardvo.setPhoto(saveFileName);
					
					dao.imagesave(boardvo);
		        } else {
		        	boardvo.setPhoto(generateSaveFileName("無"));
					dao.imagesave(boardvo);
		        }
		    }
		} catch(IOException e) {
			new RuntimeException("upload file:"+e);
		}
	}
	
	private String generateSaveFileName(String extName){
		String fileName = "";
		
		Calendar calendar = Calendar.getInstance();
		
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("."+extName);
		return fileName;
	}
	
	private void writeFile(MultipartFile file, String saveFileName) throws IOException{
		byte[] data = file.getBytes();
		FileOutputStream fos = new FileOutputStream("/image/"+saveFileName);
		fos.write(data);
		fos.close();
	}
	
	private void deleteFile(int id){
		for(int i=0; i<dao.imagelist(id).size(); i++){
			File file = new File("C:/image/"+dao.imagelist(id).get(i).getPhoto());
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	public List<BoardVo> showboard(String search){
		return dao.boardselect(search);
	}
	
	public List<BoardVo> boardcontent(int num){
		return dao.contentselect(num);
	}
	
	public void boardremove(int num){
		dao.imagedelete(num);
		dao.boarddelete(num);
	}
	
	public Map<String, Object> getBoardList(int currentPage, String keyword){
		//3. 페이징을 위한 기본 데이터 계산
		int totalCount = dao.boardcount(keyword);
		int pageCount = (int)Math.ceil( (double)totalCount / LIST_SIZE );
		int blockCount = (int)Math.ceil( (double)pageCount / PAGE_SIZE );
		int currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		
		//4. 파라미터 page 값  검증
		if( currentPage < 1 ) {
			currentPage = 1;
			currentBlock = 1;
		} else if( currentPage > pageCount ) {
			currentPage = pageCount;
			currentBlock = (int)Math.ceil( (double)currentPage / PAGE_SIZE );
		}
		
		//5. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1)*PAGE_SIZE + 1;
		int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * PAGE_SIZE : 0;
		int nextPage = ( currentBlock < blockCount ) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + LIST_SIZE : pageCount;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put( "list", dao.getList(keyword, currentPage, LIST_SIZE) );
		map.put( "totalCount", totalCount );
		map.put( "listSize", LIST_SIZE );
		map.put( "currentPage", currentPage );
		map.put( "beginPage", beginPage );
		map.put( "endPage", endPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "keyword", keyword );
		
		return map;
	}

}
