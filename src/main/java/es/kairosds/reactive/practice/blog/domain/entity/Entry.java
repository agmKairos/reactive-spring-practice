package es.kairosds.reactive.practice.blog.domain.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Entry {

    @Id
    private String _id;

    private String authorName;
    private String title;
    private String text;
    
    private List<Comment> comments;
    

    protected Entry() {
        // Used by SpringData
    }

    public Entry(String authorName, String title, String text, List<Comment> comments) {
        this(null, authorName, title, text, comments);
    }

    public Entry(String id, String authorName, String title, String text, List<Comment> comments) {
        this._id = id;
        this.authorName = authorName;
        this.title = title;
        this.text = text;
        this.comments = comments;
    }

    public String getId() {
        return _id;
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
    public String toString() {
        return String.format("Entry[id=%d, authorName='%s', title='%s', text='%s', comments='%s']",
                _id, authorName, title, text, comments);
    }
}