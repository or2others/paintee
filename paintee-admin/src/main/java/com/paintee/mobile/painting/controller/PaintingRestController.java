/**
@file PaintingRestController.java
@section 파일생성정보
|    항  목       |      내  용       |
| :-------------: | -------------   |
| File name | PaintingRestController.java |    
| Package | com.paintee.mobile.painting.controller |    
| Project name | paintee-admin |    
| Type name | PaintingRestController |    
| Company | Paintee | 
| Create Date | 2016 2016. 3. 2. 오후 10:54:46 |
| Author | Administrator |
| File Version | v1.0 |
*/
package com.paintee.mobile.painting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paintee.common.file.service.FileInfoGenerator;
import com.paintee.common.repository.entity.CommentPainting;
import com.paintee.common.repository.entity.FileInfo;
import com.paintee.common.repository.entity.Painting;
import com.paintee.common.repository.entity.vo.LikeUserVO;
import com.paintee.common.repository.entity.vo.PaintingLikeVO;
import com.paintee.common.repository.entity.vo.PaintingVO;
import com.paintee.mobile.comment.service.CommentService;
import com.paintee.mobile.painting.service.PaintingService;
import com.paintee.mobile.support.obejct.LoginedUserVO;

/**
@class PaintingRestController
com.paintee.mobile.painting.controller \n
   ㄴ PaintingRestController.java
 @section 클래스작성정보
    |    항  목       |      내  용       |
    | :-------------: | -------------   |
    | Company | Paintee |
    | Author | Administrator |
    | Date | 2016. 3. 2. 오후 10:54:46 |
    | Class Version | v1.0 |
    | 작업자 | Administrator |
 @section 상세설명
 - 그림에 대한 RestController
*/
@RestController(value="com.paintee.mobile.painting.controller.PaintingRestController")
public class PaintingRestController {
	private final static Logger logger = LoggerFactory.getLogger(PaintingRestController.class);

	@Autowired
	private PaintingService paintingService;

	@Autowired
	private FileInfoGenerator fileInfoGenerator;

	@Autowired
	private CommentService commentService;

	/**
	 @fn detailPainting
	 @brief 함수 간략한 설명 : 그림에 대한 정보조회
	 @remark
	 - 함수의 상세 설명 : 그림에 대한 정보를 조회한다.
	 @return 
	*/
	@RequestMapping(value="/api/painting/{paintingId}", method={RequestMethod.GET})
	public PaintingVO detailPainting(@PathVariable String paintingId, LoginedUserVO loginedUserVO) throws Exception {

		return paintingService.getPaintingInfo(paintingId, loginedUserVO);
	}
	
	/**
	 @fn createPainting
	 @brief 함수 간략한 설명 : 옆서 그림 등록
	 @remark
	 - 함수의 상세 설명 : 옆서 그림 등록
	 @param testVO
	 @return
	 @throws Exception 
	*/
	@RequestMapping(value="/api/painting", method={RequestMethod.POST})
	public Map<String, Object> createPainting(PaintingCreateVO paintingCreateVO, LoginedUserVO loginedUserVO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		MultipartFile painteeFile = paintingCreateVO.getPainteeFile();

		FileInfo fileInfo = null;

		//첨부파일 업로드시
		if (painteeFile != null && !painteeFile.isEmpty()) {
			fileInfo = fileInfoGenerator.makePainteeFileInfo(painteeFile, null, null, paintingCreateVO);
			logger.debug("fileInfo:{}", fileInfo);
		}

		Painting painting = new Painting();
		BeanUtils.copyProperties(paintingCreateVO, painting);

		Map<String, Object> paintingInfoMap = paintingService.createPainting(painting, fileInfo, loginedUserVO);
		resultMap.put("painting", paintingInfoMap);

		resultMap.put("errorMsg", "");
		resultMap.put("errorNo", 0);

		return resultMap;
	}

	/**
	 @fn updatePainting
	 @brief 함수 간략한 설명 : painting 정보 수정
	 @remark
	 - 함수의 상세 설명 : painting 정보 수정
	 @param paintingId
	 @param painting
	 @param loginedUserVO
	 @return
	 @throws Exception 
	*/
	@RequestMapping(value="/api/painting/{paintingSeq}", method={RequestMethod.PUT})
	public Map<String, Object> updatePainting(@PathVariable Integer paintingSeq, @RequestBody Painting painting, LoginedUserVO loginedUserVO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if(paintingSeq != null && painting.getArtistId() != null && painting.getArtistId().equals(loginedUserVO.getUserId())) {
			painting.setSeq(paintingSeq);

			boolean result = paintingService.updatePainting(painting);

			if(result) {
				resultMap.put("errorMsg", "");
				resultMap.put("errorNo", 0);
			} else {
				resultMap.put("errorNo", 500);
			}
		} else {
			resultMap.put("errorNo", 405);
		}

		return resultMap;
	}

	/**
	 @fn createPainting
	 @brief 함수 간략한 설명 : 옆서 그림에 대한 코멘트 작성
	 @remark
	 - 함수의 상세 설명 : 옆서 그림에 대한 코멘트 작성
	 @param testVO
	 @return
	 @throws Exception 
	*/
	@RequestMapping(value="/api/painting/{paintingId}/comment", method={RequestMethod.POST})
	public Map<String, Object> createComment(@PathVariable String paintingId, @RequestBody CommentPainting commentPainting, LoginedUserVO loginedUserVO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		commentPainting.setPaintingId(paintingId);

		commentPainting = commentService.createCommentPainting(commentPainting, loginedUserVO);

		resultMap.put("commentPainting", commentPainting);

		resultMap.put("errorMsg", "");
		resultMap.put("errorNo", 0);

		return resultMap;
	}
	
	/**
	 @fn deleteComment
	 @brief 함수 간략한 설명 : 옆서 그림에 대한 코멘트 삭제
	 @remark
	 - 함수의 상세 설명 : 옆서 그림에 대한 코멘트 삭제
	 @param seq
	 @return
	 @throws Exception 
	 */
	@RequestMapping(value="/api/painting/{seq}/comment", method={RequestMethod.DELETE})
	public Map<String, Object> deleteComment(@PathVariable Integer seq, @RequestBody CommentPainting commentPainting) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		commentPainting.setSeq(seq);
		
		commentService.deleteCommentPainting(commentPainting);
		
		resultMap.put("errorMsg", "");
		resultMap.put("errorNo", 0);
		
		return resultMap;
	}

	/**
	@fn addPaintingLike
	@brief 함수 간략한 설명 : 옆서 그림 좋아요 추가
	@remark
	- 함수의 상세 설명 : 옆서 그림 등록
	@param testVO
	@return
	@throws Exception 
	*/
	@RequestMapping(value="/api/painting/like", method={RequestMethod.POST})
	public Map<String, Object> addPaintingLike(@RequestBody PaintingLikeVO paintingLike, LoginedUserVO loginedUserVO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		paintingLike.setUserId(loginedUserVO.getUserId());
		boolean result = paintingService.addPaintingLike(paintingLike);
		if(result) {
			resultMap.put("errorMsg", "");
			resultMap.put("errorNo", 0);
		} else {
			resultMap.put("errorNo", 500);
		}
		return resultMap;
	}	
	
	/**
	 @fn deletePaintingLike
	 @brief 함수 간략한 설명 : 옆서 그림 좋아요 취소
	 @remark
	 - 함수의 상세 설명 : 옆서 그림 등록
	 @param testVO
	 @return
	 @throws Exception 
	 */
	@RequestMapping(value="/api/painting/{paintingId}/like", method={RequestMethod.DELETE})
	public Map<String, Object> deletePaintingLike(@PathVariable String paintingId, @RequestBody PaintingLikeVO paintingLike, LoginedUserVO loginedUserVO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		paintingLike.setUserId(loginedUserVO.getUserId());
		paintingLike.setPaintingId(paintingId);
		boolean result = paintingService.cancelPaintingLike(paintingLike);
		if(result) {
			resultMap.put("errorMsg", "");
			resultMap.put("errorNo", 0);
		} else {
			resultMap.put("errorNo", 500);
		}
		return resultMap;
	}

	/**
	 * 해당 그림을 좋아요 한 사람의 목록
	 * 
	 * @param loginedUserVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value={"/api/painting/{paintingId}/like/users"}, method=RequestMethod.GET)
	public Map<String, Object> likeUserList(@PathVariable String paintingId, LoginedUserVO loginedUserVO) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		List<LikeUserVO> list = paintingService.getLikeUserList(paintingId, loginedUserVO.getUserId());
		resultMap.put("list", list);
		resultMap.put("errorNo", 0);
		resultMap.put("errorMsg", "");
		
		return resultMap;
	}
}
