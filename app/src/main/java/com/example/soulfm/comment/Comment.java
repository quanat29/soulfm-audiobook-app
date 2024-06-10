package com.example.soulfm.comment;

public class Comment {
    private String user_name;
    private String comment_time;
    private String content;
    private String num_star;
    private int num_comment;

    public Comment(String user_name, String comment_time, String content, String num_star, int num_comment) {
        this.user_name = user_name;
        this.comment_time = comment_time;
        this.content = content;
        this.num_star = num_star;
        this.num_comment = num_comment;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNum_star() {
        return num_star;
    }

    public void setNum_star(String num_star) {
        this.num_star = num_star;
    }

    public int getNum_comment() {
        return num_comment;
    }

    public void setNum_comment(int num_comment) {
        this.num_comment = num_comment;
    }
}
