package es.kairosds.reactive.practice.blog.api.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;


public class CommentDTO {

    @Id
    private String id;

    private String authorName;
    private String text;
    private Date date;
    

    protected CommentDTO() {
        // Used by SpringData
    }

    public CommentDTO(String authorName, String text, Date date) {
        this(null, authorName, text, date);
    }

    public CommentDTO(String id, String authorName, String text, Date date) {
        this.id = id;
        this.authorName = authorName;
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
    }


    public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
    public String toString() {
        return String.format("Comment[id=%d, authorName='%s', text='%s', date='%s']",
                id, authorName, text, date);
    }
}