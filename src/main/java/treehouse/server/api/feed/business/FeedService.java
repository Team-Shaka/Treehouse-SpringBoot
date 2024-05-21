package treehouse.server.api.feed.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.feed.implement.FeedCommandAdapter;
import treehouse.server.api.feed.implement.FeedQueryAdapter;
import treehouse.server.api.feed.presentation.dto.FeedResponseDto;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.feed.Feed;

@Service
@AllArgsConstructor
@Slf4j
public class FeedService {

    private final FeedCommandAdapter feedCommandAdapter;
    private final FeedQueryAdapter feedQueryAdapter;

    /**
     * 게시글 상세조회
     * @param user
     * @param feedId
     * @param treehouseId - 게시글 정보에 표시할 memberBranch을 계산하고 감정표현의 상태를 반환하기 위해 user와 treehouseId 사용
     * @return FeedResponseDto.getFeedDetails
     */
    @Transactional(readOnly = true)
    public FeedResponseDto.getFeedDetails getFeedDetails(User user, Long feedId, Long treehouseId){
        Feed feed = feedQueryAdapter.findById(feedId);
        return FeedMapper.toGetFeedDetails(feed);
    }
}
