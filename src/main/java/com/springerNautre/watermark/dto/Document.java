package com.springerNautre.watermark.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

/**
 * Created by ganeshnagarajan on 1/12/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {
    private Content content;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    private String watermark;
    private String topic;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark() {
        Optional optional = Optional.ofNullable(topic);
        String watermark = content + " | " + title + " | " + author;
        if (optional.isPresent()) {
            this.watermark = watermark + "|" + title;
        } else {
            this.watermark = watermark;
        }
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String toString(){
        return "Content: " + content + ", title : " + title + ", author: " + author + ", watermark : " + watermark + ", topic : " + topic;
    }
}
