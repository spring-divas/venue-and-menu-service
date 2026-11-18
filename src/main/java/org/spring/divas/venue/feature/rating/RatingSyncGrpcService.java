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
    public StreamObserver<RatingUpdate> syncDishRatings(StreamObserver<RatingSyncResponse> responseObserver) {
        return new StreamObserver<>() {
            private int updatedCount;
            private boolean closed;

            @Override
            public void onNext(RatingUpdate update) {
                if (closed) {
                    return;
                }
                if (isInvalid(update)) {
                    closed = true;
                    responseObserver.onError(invalidRating());
                    return;
                }
                if (dishService.updateRating(
                        update.getTargetId(),
                        (float) update.getAverageRating(),
                        update.getReviewCount())) {
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

    @Override
    public void updateVenueRating(RatingUpdate update, StreamObserver<RatingSyncResponse> responseObserver) {
        if (isInvalid(update)) {
            responseObserver.onError(invalidRating());
            return;
        }
        boolean updated = venueService.updateRating(
                update.getTargetId(),
                (float) update.getAverageRating(),
                update.getReviewCount());
        responseObserver.onNext(RatingSyncResponse.newBuilder()
                .setUpdatedCount(updated ? 1 : 0)
                .build());
        responseObserver.onCompleted();
    }

    private boolean isInvalid(RatingUpdate update) {
        return update.getAverageRating() < 0 || update.getReviewCount() < 0;
    }

    private RuntimeException invalidRating() {
        return Status.INVALID_ARGUMENT
                .withDescription("Rating and review count must not be negative")
                .asRuntimeException();
    }
}
