package my.board.object;

import java.util.ArrayList;

public interface ContentDAO {
	public ArrayList<ContentDTO> getList(int pageNumber);
	public int insert(ContentDTO content);
	public int update(ContentDTO content);
	public int delete(int contentID);
}
