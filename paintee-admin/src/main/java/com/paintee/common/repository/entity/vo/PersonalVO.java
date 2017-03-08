/**
@file PersonalVO.java
@section 파일생성정보
|    항  목       |      내  용       |
| :-------------: | -------------   |
| File name | PersonalVO.java |    
| Package | com.paintee.common.repository.entity.vo |    
| Project name | paintee-admin |    
| Type name | PersonalVO |    
| Company | Paintee | 
| Create Date | 2016 2016. 2. 27. 오후 6:25:26 |
| Author | Administrator |
| File Version | v1.0 |
*/
package com.paintee.common.repository.entity.vo;

import com.paintee.common.repository.entity.Painting;

/**
@class PersonalVO
com.paintee.common.repository.entity.vo \n
   ㄴ PersonalVO.java
 @section 클래스작성정보
    |    항  목       |      내  용       |
    | :-------------: | -------------   |
    | Company | Paintee |
    | Author | Administrator |
    | Date | 2016. 2. 27. 오후 6:25:26 |
    | Class Version | v1.0 |
    | 작업자 | Administrator |
 @section 상세설명
 - 상세설명 은 여기에 기입해 주세요.
 -# 여기는 리스트로 표시됩니다.
*/
public class PersonalVO extends PaintingVO {
	
	private String fileId;
	private Integer uploadCount;
	private String artistName;
	private String introduce;
	private Integer followCnt;
	private String artistId;
	/**
	 * 그림의 로그인한 사용자의 좋아요 카운트
	 */
	private Integer loginLikeCnt;  
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Integer getUploadCount() {
		return uploadCount;
	}

	public void setUploadCount(Integer uploadCount) {
		this.uploadCount = uploadCount;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Integer getFollowCnt() {
		return followCnt;
	}

	public void setFollowCnt(Integer followCnt) {
		this.followCnt = followCnt;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	public Integer getLoginLikeCnt() {
		return loginLikeCnt;
	}

	public void setLoginLikeCnt(Integer loginLikeCnt) {
		this.loginLikeCnt = loginLikeCnt;
	}
	
}