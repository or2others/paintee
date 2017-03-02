/**
@file PaintingVO.java
@section 파일생성정보
|    항  목       |      내  용       |
| :-------------: | -------------   |
| File name | PaintingVO.java |    
| Package | com.paintee.common.repository.entity.vo |    
| Project name | paintee-admin |    
| Type name | PopularVO |    
| Company | Paintee | 
| Create Date | 2016 2016. 3. 5. 오후 12:00:44 |
| Author | Administrator |
| File Version | v1.0 |
*/
package com.paintee.common.repository.entity.vo;

import com.paintee.common.repository.entity.FileInfo;
import com.paintee.common.repository.entity.Painting;

/**
@class PaintingVO
com.paintee.common.repository.entity.vo \n
   ㄴ PopularSearchVO.java
 @section 클래스작성정보
    |    항  목       |      내  용       |
    | :-------------: | -------------   |
    | Company | Paintee |
    | Author | Administrator |
    | Date | 2016. 3. 5. 오후 12:00:44 |
    | Class Version | v1.0 |
    | 작업자 | Administrator |
 @section 상세설명
 - 상세설명 은 여기에 기입해 주세요.
 -# 여기는 리스트로 표시됩니다.
*/
public class PaintingVO extends Painting {
	
	private String artistName;
	private FileInfo fileInfo;
	private Boolean followed;
	private Integer likeCnt;
	private Integer liked;

	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public FileInfo getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
	public Boolean getFollowed() {
		return followed;
	}
	public void setFollowed(Boolean followed) {
		this.followed = followed;
	}
	public Integer getLikeCnt() {
		return likeCnt;
	}
	public Integer getLiked() {
		return liked;
	}
	public void setLiked(Integer liked) {
		this.liked = liked;
	}
}
