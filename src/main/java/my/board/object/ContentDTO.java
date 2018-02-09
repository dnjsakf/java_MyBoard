package my.board.object;

public class ContentDTO {
	private int id;
	private String title;
	private String writer;
	private String date;
	private String content;
	private int available;
	
	/**
	 * Creators
	 */
	public ContentDTO() {
		
	}
	public ContentDTO(int id, String title, String writer, String date, String content, int available) {
		this.id = id;
		this.title = title;
		this.writer = writer;
		this.date = date;
		this.content = content;
		this.available = available;
	}
	
	/**
	 * Getters and Setters
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	
	/**
	 * MyFunction
	 */
	public String toString() {
		return id +"/"+ title +"/"+ writer +"/"+ date +"/"+ content +"/"+ available;
	}
}
