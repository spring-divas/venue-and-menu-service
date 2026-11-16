package org.spring.divas.venue;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.spring.divas.rating.v1.RatingSyncResponse;
import org.spring.divas.rating.v1.RatingTarget;
import org.spring.divas.rating.v1.RatingUpdate;
import org.spring.divas.venue.feature.dish.DishService;
import org.spring.divas.venue.feature.rating.RatingSyncGrpcService;
import org.spring.divas.venue.feature.venue.VenueService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class RatingSyncGrpcServiceTests {

    private final DishService dishService = mock(DishService.class);
    private final VenueService venueService = mock(VenueService.class);
    private final RatingSyncGrpcService service = new RatingSyncGrpcService(dishService, venueService);

    @Test
    void appliesValidUpdates() {
        when(dishService.updateRating(anyLong(), anyFloat(), anyInt())).thenReturn(true);
        when(venueService.updateRating(anyLong(), anyFloat(), anyInt())).thenReturn(true);
        RecordingObserver response = new RecordingObserver();

        StreamObserver<RatingUpdate> requests = service.syncRatings(response);
        requests.onNext(update(RatingTarget.DISH, 4.5, 2));
        requests.onNext(update(RatingTarget.VENUE, 3.0, 1));
        requests.onCompleted();

        verify(dishService).updateRating(1L, 4.5f, 2);
        verify(venueService).updateRating(1L, 3.0f, 1);
        assertThat(response.updatedCount).isEqualTo(2);
        assertThat(response.completed).isTrue();
    }

    @Test
    void rejectsNegativeDishRating() {
        assertRejected(update(RatingTarget.DISH, -1.0, 2));
    }

    @Test
    void rejectsNegativeVenueReviewCount() {
        assertRejected(update(RatingTarget.VENUE, 4.0, -1));
    }

    private void assertRejected(RatingUpdate invalid) {
        RecordingObserver response = new RecordingObserver();

        StreamObserver<RatingUpdate> requests = service.syncRatings(response);
        requests.onNext(invalid);
        requests.onNext(update(RatingTarget.DISH, 4.0, 1));
        requests.onCompleted();

        assertThat(Status.fromThrowable(response.error).getCode()).isEqualTo(Status.Code.INVALID_ARGUMENT);
        assertThat(response.updatedCount).isNull();
        assertThat(response.completed).isFalse();
        verifyNoInteractions(dishService, venueService);
    }

    private static RatingUpdate update(RatingTarget target, double rating, int reviewCount) {
        return RatingUpdate.newBuilder()
                .setTarget(target)
                .setTargetId(1L)
                .setAverageRating(rating)
                .setReviewCount(reviewCount)
                .build();
    }

    private static final class RecordingObserver implements StreamObserver<RatingSyncResponse> {
        private Integer updatedCount;
        private Throwable error;
        private boolean completed;

        @Override
        public void onNext(RatingSyncResponse response) {
            updatedCount = response.getUpdatedCount();
        }

        @Override
        public void onError(Throwable throwable) {
            error = throwable;
        }

        @Override
        public void onCompleted() {
            completed = true;
        }
    }
}
