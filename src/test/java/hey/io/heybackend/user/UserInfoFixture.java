package hey.io.heybackend.user;

import hey.io.heybackend.user.dtos.request.FollowShowListRequest;
import hey.io.heybackend.user.dtos.request.FollowShowRequest;
import hey.io.heybackend.user.dtos.response.FollowShowResponse;

public class UserInfoFixture {

    public static FollowShowRequest getFollowShowRequestInfo() {
        FollowShowRequest request = FollowShowRequest.builder()
                .userId(1l)
                .showId(1L)
                .build();

        return request;
    }

    public static FollowShowListRequest getFollowShowListRequestInfo() {
        FollowShowListRequest request = FollowShowListRequest.builder()
                .userId(1l)
                .build();

        return request;
    }

    public static FollowShowResponse getFollowShowResponseInfo(FollowShowRequest request) {
        FollowShowResponse response = FollowShowResponse.builder()
                .userId(request.getUserId())
                .showId(request.getShowId())
                .build();

        return response;
    }
}
