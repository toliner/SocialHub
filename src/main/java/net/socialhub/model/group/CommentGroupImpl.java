package net.socialhub.model.group;

import net.socialhub.model.service.Comment;
import net.socialhub.model.service.Pageable;
import net.socialhub.model.service.Paging;
import net.socialhub.service.action.group.CommentGroupAction;
import net.socialhub.service.action.group.CommentGroupActionImpl;
import net.socialhub.service.action.request.CommentsRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Whole Accounts Comments
 */
public class CommentGroupImpl implements CommentGroup {

    /** Comments Request Group */
    private CommentsRequestGroup requestGroup;

    /** Entity of Comments related to Request */
    private Map<CommentsRequest, Pageable<Comment>> entities;

    /** Max Date (include) **/
    private Date maxDate;

    /** Since Date (not include) */
    private Date sinceDate;

    /**
     * {@inheritDoc}
     */
    @Override
    public Pageable<Comment> getComments() {

        Stream<Comment> stream = entities.values().stream() //
                .flatMap((e) -> e.getEntities().stream());

        long size = entities.values().stream()
                .filter(e -> (e.getPaging() != null))
                .map(e -> e.getPaging().getCount())
                .min(Long::compareTo).orElse(0L);

        if (maxDate != null) {
            stream = stream.filter((e) -> e.getCreateAt().getTime() <= maxDate.getTime());
        }
        if (sinceDate != null) {
            stream = stream.filter((e) -> e.getCreateAt().getTime() > sinceDate.getTime());
        }

        stream = stream.sorted(Comparator.comparing(Comment::getCreateAt).reversed());
        List<Comment> comments = stream.collect(Collectors.toList());
        Pageable<Comment> result = new Pageable<>();

        result.setEntities(comments);
        result.setPaging(new Paging(size));
        return result;
    }

    /**
     * Marge Prev Comments when New Request
     * 最新リクエストの場合の結合処理
     */
    public void margeWhenNewPageRequest(CommentGroupImpl prev) {
        if (prev.getMaxDate() == null) {
            return;
        }

        // MaxDate を変更
        sinceDate = prev.getMaxDate();

        entities.forEach((acc, page) -> {
            if (prev.getEntities().containsKey(acc)) {

                List<Comment> comments = new ArrayList<>(page.getEntities());
                comments.addAll(prev.getEntities().get(acc).getEntities().stream() //
                        .filter((e) -> e.getCreateAt().getTime() > sinceDate.getTime()) //
                        .collect(Collectors.toList()));

                comments.sort(Comparator.comparing(Comment::getCreateAt).reversed());
                page.getPaging().setCount((long) comments.size());
                page.setEntities(comments);
            }
        });
    }

    /**
     * Marge Prev Comments when Past Request
     * 遡りリクエストの場合の結合処理
     */
    public void margeWhenPastPageRequest(CommentGroupImpl prev) {
        if (prev.getSinceDate() == null) {
            return;
        }

        // MaxDate を変更
        maxDate = prev.getSinceDate();

        entities.forEach((acc, page) -> {
            if (prev.getEntities().containsKey(acc)) {

                List<Comment> comments = new ArrayList<>(page.getEntities());
                comments.addAll(prev.getEntities().get(acc).getEntities().stream() //
                        .filter((e) -> e.getCreateAt().getTime() <= maxDate.getTime()) //
                        .collect(Collectors.toList()));

                comments.sort(Comparator.comparing(Comment::getCreateAt).reversed());
                page.getPaging().setCount((long) comments.size());
                page.setEntities(comments);
            }
        });
    }

    /**
     * MaxDate の計算
     */
    public void setMaxDateFromEntities() {
        maxDate = entities.values().stream() //
                .filter((e) -> e.getEntities() != null) //
                .filter((e) -> !e.getEntities().isEmpty()) //
                .map((e) -> e.getEntities().get(0)) //
                .map(Comment::getCreateAt) //
                .min(Date::compareTo) //
                .orElse(null); //
    }

    /**
     * SinceDate の計算
     */
    public void setSinceDateFromEntities() {
        sinceDate = entities.values().stream() //
                .filter((e) -> e.getEntities() != null) //
                .filter((e) -> !e.getEntities().isEmpty()) //
                .map((e) -> e.getEntities().get(e.getEntities().size() - 1)) //
                .map(Comment::getCreateAt) //
                .max(Date::compareTo) //
                .orElse(null); //

        // +1ms (for not include)
        if (sinceDate != null) {
            sinceDate = new Date(sinceDate.getTime() - 1);
        }
    }

    @Override
    public CommentGroupAction action() {
        return new CommentGroupActionImpl(this);
    }

    //region // Getter&Setter
    @Override
    public CommentsRequestGroup getRequestGroup() {
        return requestGroup;
    }

    public void setRequestGroup(CommentsRequestGroup requestGroup) {
        this.requestGroup = requestGroup;
    }

    @Override
    public Map<CommentsRequest, Pageable<Comment>> getEntities() {
        return entities;
    }

    public void setEntities(Map<CommentsRequest, Pageable<Comment>> entities) {
        this.entities = entities;
    }

    @Override
    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    @Override
    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }
    //endregion
}
