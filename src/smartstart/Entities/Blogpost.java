/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartstart.Entities;

import java.util.Date;


public class Blogpost {
    private int postId;
        private int Author_id;
	private java.util.Date post_date;
	private String post_status;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
	private String post_type;
	private int post_comment_count;
	private String article_title;
	private String article_content;
	private String image;
        private String post_content;

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }
	private int post_likes_count;
	private int views;

	public int getAuthor_id(){
		return Author_id;
	}

	public void setAuthor_id(int Author_id){
		this.Author_id=Author_id;
	}

    public Blogpost(int Author_id, Date post_date, String post_status, String post_type, int post_comment_count, String article_title, String article_content, String image, int post_likes_count, int views) {
        this.Author_id = Author_id;
        this.post_date = post_date;
        this.post_status = post_status;
        this.post_type = post_type;
        this.post_comment_count = post_comment_count;
        this.article_title = article_title;
        this.article_content = article_content;
        this.image = image;
        this.post_likes_count = post_likes_count;
        this.views = views;
    }

    public Blogpost() {
    }

	public java.util.Date getPost_date(){
		return post_date;
	}

	public void setPost_date(java.util.Date post_date){
		this.post_date=post_date;
	}

	public String getPost_status(){
		return post_status;
	}

	public void setPost_status(String post_status){
		this.post_status=post_status;
	}

	public String getPost_type(){
		return post_type;
	}

	public void setPost_type(String post_type){
		this.post_type=post_type;
	}

	public int getPost_comment_count(){
		return post_comment_count;
	}

	public void setPost_comment_count(int post_comment_count){
		this.post_comment_count=post_comment_count;
	}

	public String getArticle_title(){
		return article_title;
	}

	public void setArticle_title(String article_title){
		this.article_title=article_title;
	}

	public String getArticle_content(){
		return article_content;
	}

	public void setArticle_content(String article_content){
		this.article_content=article_content;
	}

	public String getImage(){
		return image;
	}

	public void setImage(String image){
		this.image=image;
	}

	public int getPost_likes_count(){
		return post_likes_count;
	}

	public void setPost_likes_count(int post_likes_count){
		this.post_likes_count=post_likes_count;
	}

	public int getViews(){
		return views;
	}

	public void setViews(int views){
		this.views=views;
	}

    @Override
    public String toString() {
        return "Post Id"+views;
    }
           
}
