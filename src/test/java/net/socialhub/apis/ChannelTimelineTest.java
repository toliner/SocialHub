package net.socialhub.apis;

import net.socialhub.SocialAuthUtil;
import net.socialhub.model.Account;
import net.socialhub.model.service.Comment;
import net.socialhub.model.service.Identify;
import net.socialhub.model.service.Pageable;
import org.junit.Test;

public class ChannelTimelineTest extends AbstractApiTest {

    @Test
    public void testChannelTimeline() {
        Identify channel = new Identify(null, "CHANNEL_ID");
        Account account = SocialAuthUtil.getSlackAccount();

        Pageable<Comment> comments = account.action().getChannelTimeLine(channel, null);

        for (Comment comment : comments.getEntities()) {
            System.out.println(comment.getId() + ":" + comment.getText().getDisplayText());
            System.out.println(comment.getUser().getName());
        }
    }
}
