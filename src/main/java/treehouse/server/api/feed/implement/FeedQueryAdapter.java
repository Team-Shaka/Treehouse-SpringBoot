package treehouse.server.api.feed.implement;

import lombok.RequiredArgsConstructor;
import treehouse.server.api.feed.persistence.FeedRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.feed.Feed;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.FeedException;

@Adapter
@RequiredArgsConstructor
public class FeedQueryAdapter {

    private final FeedRepository feedRepository;

    public Feed findById(Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(() -> new FeedException(GlobalErrorCode.POST_NOT_FOUND));
    }
}
