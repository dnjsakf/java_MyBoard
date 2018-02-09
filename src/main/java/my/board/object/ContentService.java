package my.board.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class ContentService implements ContentDAO {

	@Inject
	DataSource dataSource;
	
	Connection conn;
	
	int pageNumber = 1;
	int viewContentCount = 10;
	
	// Connection: just Connection
	public ContentService() {
		try {
			conn = dataSource.getConnection();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	// Connection: set page number
	public ContentService(int pageNumber, int viewContentCount) {
		this.pageNumber = pageNumber;
		this.viewContentCount = viewContentCount;
		try {
			conn = dataSource.getConnection();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	// 연결이 제대로 됐는지 확인
	public boolean checkConnection() {
		System.out.println("[Connection] " + conn);
		System.out.println("[DataSource] " +dataSource);
		return this.conn == null;
	}
	/**
	 * 마지막 게시글의 아이디 가져오기
	 * @return 성공 id, 실패 0, 에러 -1
	 */
	public int getLastContentId() {
		String SQL = "SELECT bbsID FROM bbs ORDER BY bbsID DESC LIMIT 1";
		try {
			PreparedStatement pd = conn.prepareStatement(SQL);
			ResultSet result = pd.executeQuery();
			if( result.next() ) {
				return result.getInt(1);
			}
			return 0;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/** Select
	 * 게시글 가져오기
	 * @param pageNumber
	 * @return 성공 ArrayList<ContentDTO>.size() > 0,
	 * 			실패 ArrayList<ContentDTO.size() = 0
	 * 			에러 null
	 */
	@Override
	public ArrayList<ContentDTO> getList(int pageNumber){
		String SQL = "SELECT * FROM bbs ORDER BY bbsID DESC LIMIT ?, ?";
		int startNumber = ( pageNumber - 1 ) * viewContentCount;
		try {
			PreparedStatement pd = conn.prepareStatement(SQL);
			pd.setInt(1, startNumber);
			pd.setInt(2, viewContentCount);
			ResultSet result = pd.executeQuery();
			
			ArrayList<ContentDTO> contents = new ArrayList<ContentDTO>();
			while( result.next() ) {
				ContentDTO content = new ContentDTO(
											result.getInt(1),
											result.getString(2),
											result.getString(3),
											result.getString(4),
											result.getString(5),
											result.getInt(6)
											);
				contents.add(content);
				System.out.println("[Get] " + content.toString());
			}
			return contents;
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
	/** Insert
	 * 게시글 작성
	 * @param content
	 * @return 성공 id, 실패 0, 에러 -1
	 */

	@Override
	public int insert(ContentDTO content) {
		String SQL = "INSERT INTO bbs VALUES(?, ?, ?, ?, ?, ?)";
		int lastContentId = this.getLastContentId();
		if( lastContentId < 1 ) return 0;
		try {
			PreparedStatement pd = conn.prepareStatement(SQL);
			pd.setInt(1, lastContentId + 1 );
			pd.setString(2, content.getTitle());
			pd.setString(3, content.getWriter());
			pd.setString(4, content.getDate());
			pd.setString(5, content.getContent());
			pd.setInt(6, content.getAvailable());
			return pd.executeUpdate();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return -1;
	}
		
	/** Update
	 * 게시글 수정
	 * 1. 기존 데이터랑 비교해서 수정된 부분만 수정하고 싶지만, 우선은 수정 기능만을 구현하는 것이므로,
	 *    수정 데이터 외에 기존 데이터도 모두 수정해버리자
	 * @param updated
	 * @return 성공 1, 실패 0, 에러 -1
	 */
	@Override
	public int update(ContentDTO content) {
		String SQL = "UPDATE bbs SET bbsTitle=?, userID=?, bbsDate=?, bbsContent=? WHERE bbsID = ?";
		try {
			PreparedStatement pd = conn.prepareStatement(SQL);
			pd.setString(1, content.getTitle());
			pd.setString(2, content.getWriter());
			pd.setString(3, content.getDate());	// 이건 현지 시간으로
			pd.setString(4, content.getContent());
			pd.setInt(5, content.getId());
			return pd.executeUpdate();
		} catch( Exception e ) {
			e.printStackTrace();
		}		
		return -1;
	}
	
	/** Update ( role: delete )
	 * 게시글 삭제
	 * 1. 이름은 삭제이지만, available을 1에서 0으로 수정함으로써 리스트에는 표시되지 않게함.
	 * @param contentId
	 * @return 성공 1, 실패 0, 에러 -1
	 */
	@Override
	public int delete(int contentID) {
		//String SQL = "DELETE bbs WHERE bbsID = ?";
		String SQL = "UPDATE bbs SET bbsAvailable = 0 WHERE bbsID = ?";
		try {
			PreparedStatement pd = conn.prepareStatement(SQL);
			pd.setInt(1, contentID);
			return pd.executeUpdate();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Getters and Setters
	 */
	public void setViewContentCount(int viewContentCount) {
		this.viewContentCount = viewContentCount;
	}
}
