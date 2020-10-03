package es.kairosds.reactive.practice.blog.domain.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {

    @Id
    private String _id;

    private String authorName;
    private String text;
    private Date date;
    

    protected Comment() {
        // Used by SpringData
    	this._id = UUID.randomUUID().toString();
    }

    public Comment(String authorName, String text, Date date) {
        this(null, authorName, text, date);
    }

    public Comment(String id, String authorName, String text, Date date) {
    	if(id == null) {
    		this._id = UUID.randomUUID().toString();
    	} else {    		
    		this._id = id;    		
    	}
        this.authorName = authorName;
        this.text = text;
        this.date = date;
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
                _id, authorName, text, date);
    }
}