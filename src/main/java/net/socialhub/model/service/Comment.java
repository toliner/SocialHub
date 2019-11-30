package net.socialhub.model.service;

import net.socialhub.model.common.AttributedString;
import net.socialhub.model.error.NotImplimentedException;
import net.socialhub.model.request.CommentForm;
import net.socialhub.service.action.AccountAction;
import net.socialhub.service.action.CommentAction;
import net.socialhub.service.action.CommentActionImpl;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SNS コメント情報
 * SNS Comment Model
 */
public class Comment extends Identify {

    /** Text */
    private AttributedString text;

    /** Date of text created */
    private Date createAt;

    /** User who create this text */
    private User user;

    /**
     * Files which attached with this text
     * if no media with this tweet, return empty list.
     */
    private List<Media> medias;

    /**
     * Shared text
     * (ReTweeted or Quoted text in Twitter Term)
     * (Null if text shared any text)
     */
    private Comment sharedComment;

    /**
     * Is possibly sensitive?
     * NSFW in mastodon term.
     */
    private Boolean possiblySensitive;

    /**
     * Application which user used
     * (with application link)
     */
    private Application application;

    /**
     * Is direct message comment?
     * (Comment and message are same model)
     */
    private Boolean directMessage;

    /**
     * Get many kind of reactions
     * (like, share, :+1:, and so on)
     */
    public List<Reaction> getReactions() {
        return new ArrayList<>();
    }

    public Comment(Service service) {
        super(service);
    }

    /**
     * Get Action
     */
    @Nonnull
    public CommentAction action() {
        AccountAction action = getService().getAccount().action();
        return new CommentActionImpl(action).comment(this);
    }

    /**
     * Get comment should be shown
     * (Use return object to display)
     */
    public Comment getDisplayComment() {
        return this;
    }

    /**
     * Get Reply Form
     * 返信用のフォームを取得
     */
    public CommentForm getReplyForm() {
        throw new NotImplimentedException();
    }

    //region // Getter&Setter
    public AttributedString getText() {
        return text;
    }

    public void setText(AttributedString text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Comment getSharedComment() {
        return sharedComment;
    }

    public void setSharedComment(Comment sharedComment) {
        this.sharedComment = sharedComment;
    }

    public Boolean getPossiblySensitive() {
        return possiblySensitive;
    }

    public void setPossiblySensitive(Boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Boolean getDirectMessage() {
        return directMessage;
    }

    public void setDirectMessage(Boolean directMessage) {
        this.directMessage = directMessage;
    }
    //endregion
}
