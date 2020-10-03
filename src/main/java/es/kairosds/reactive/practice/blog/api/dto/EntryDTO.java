package es.kairosds.reactive.practice.blog.api.dto;

import java.util.List;

import org.springframework.data.annotation.Id;


public class EntryDTO {

    @Id
    private String id;

    private String authorName;
    private String title;
    private String text;
    private List<CommentDTO> comments;
    
    public EntryDTO(String id, String authorName, String title, String text, List<CommentDTO> comments) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.text = text;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	@Override
    public String toString() {
        return String.format("EntryDTO[id=%d, authorName='%s', title='%s', text='%s', comments='%s']",
                id, authorName, title, text, comments);
    }
}