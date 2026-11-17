package org.spring.divas.venue.feature.rating;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.spring.divas.rating.v1.RatingSyncResponse;
import org.spring.divas.rating.v1.RatingSyncServiceGrpc;
import org.spring.divas.rating.v1.RatingUpdate;
import org.spring.divas.venue.feature.dish.DishService;
import org.spring.divas.venue.feature.venue.VenueService;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class RatingSyncGrpcService extends RatingSyncServiceGrpc.RatingSyncServiceImplBase {
    private final DishService dishService;
    private final VenueService venueService;

    @Override
    public StreamObserver<RatingUpdate> syncRatings(StreamObserver<RatingSyncResponse> responseObserver) {
        return new StreamObserver<>() {
            private int updatedCount;
            private boolean closed;

            @Override
            public void onNext(RatingUpdate update) {
                if (closed) {
                    return;
                }
                if (update.getAverageRating() < 0 || update.getReviewCount() < 0) {
                    closed = true;
                    responseObserver.onError(Status.INVALID_ARGUMENT
                            .withDescription("Rating and review count must not be negative")
                            .asRuntimeException());
                    return;
                }
                if (apply(update)) {
                    updatedCount++;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                closed = true;
            }

            @Override
            public void onCompleted() {
                if (closed) {
                    return;
                }
                responseObserver.onNext(RatingSyncResponse.newBuilder()
                        .setUpdatedCount(updatedCount)
                        .build());
                responseObserver.onCompleted();
            }
        };
    }

    private boolean apply(RatingUpdate update) {
        float rating = (float) update.getAverageRating();
        return switch (update.getTarget()) {
            case DISH -> dishService.updateRating(update.getTargetId(), rating, update.getReviewCount());
            case VENUE -> venueService.updateRating(update.getTargetId(), rating, update.getReviewCount());
            default -> false;
        };
    }
}
