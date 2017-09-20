package com.example.cuongcaov.clonedata.stories;

/**
 * StoryToUp.
 *
 * @author CuongCV
 */

public class StoryToUp {
    private int id;
    private String storyName;
    private String author;
    private String type;
    private int status;
    private int numOfChapters;
    private String source;

    public StoryToUp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoryName() {
        return storyName;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public int getNumOfChapters() {
        return numOfChapters;
    }

    public String getSource() {
        return source;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setNumOfChapters(int numOfChapters) {
        this.numOfChapters = numOfChapters;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String toString() {
        return this.storyName + "---" + this.author + "---" + this.numOfChapters + "---" + this.type + "---" + this.status + "---" + this.source;
    }
}
