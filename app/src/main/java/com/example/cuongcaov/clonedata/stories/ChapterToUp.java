package com.example.cuongcaov.clonedata.stories;

/**
 * ChapterToUp.
 *
 * @author CuongCV
 */

public class ChapterToUp {
    private int id;
    private int storyId;
    private int position;
    private String source;

    public ChapterToUp() {
    }

    public ChapterToUp(int storyId, int position, String source) {
        this.storyId = storyId;
        this.position = position;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoryId() {
        return storyId;
    }

    public int getPosition() {
        return position;
    }

    public String getSource() {
        return source;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
