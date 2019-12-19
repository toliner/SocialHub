package net.socialhub.apis;

import net.socialhub.SocialAuthUtil;
import net.socialhub.model.Account;
import net.socialhub.model.service.Comment;
import net.socialhub.model.service.Pageable;
import net.socialhub.model.service.Paging;
import net.socialhub.model.service.Thread;
import org.junit.Test;

public class MessageTimelineTest extends AbstractTimelineTest {

    @Test
    public void testMessageTimelineMastodon() {

        Account account = SocialAuthUtil.getMastodonAccount();
        Pageable<Thread> threads = account.action().getMessageThread(new Paging(50L));

        if (threads.getEntities().size() > 0) {
            Pageable<Comment> comments = account.action().getMessageTimeLine(
                    threads.getEntities().get(0), new Paging(50L));
            printTimeline("Message", comments);
        }
    }
}

