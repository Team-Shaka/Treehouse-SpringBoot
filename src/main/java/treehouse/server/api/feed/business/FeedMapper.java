package treehouse.server.api.feed.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import treehouse.server.api.feed.presentation.dto.FeedResponseDto;
import treehouse.server.api.member.business.MemberMapper;
import treehouse.server.global.common.TimeFormatter;
import treehouse.server.global.entity.feed.Feed;
import treehouse.server.global.entity.feed.FeedImage;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedMapper {

    public static FeedResponseDto.getFeedDetails toGetFeedDetails(Feed feed) {
        return FeedResponseDto.getFeedDetails.builder()
                .memberProfile(MemberMapper.toGetMember(feed.getWriter()))
                .postId(feed.getId())
                .context(feed.getContent())
                .pictureUrlList(feed.getFeedImageList().stream()
                        .map(FeedImage::getImageUrl).toList()
                )
//                .reactionList() // Reaction 기능 개발 이후 적용
                .postedAt(TimeFormatter.format(feed.getCreatedAt()))
                .build();
    }
}
