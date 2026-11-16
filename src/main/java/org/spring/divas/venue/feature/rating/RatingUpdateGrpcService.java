package org.spring.divas.venue.feature.rating;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.spring.divas.rating.v1.DishRatingUpdateRequest;
import org.spring.divas.rating.v1.RatingUpdateServiceGrpc;
import org.spring.divas.venue.feature.dish.DishService;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class RatingUpdateGrpcService extends RatingUpdateServiceGrpc.RatingUpdateServiceImplBase {

    private final DishService dishService;

    @Override
    public void updateDishRating(DishRatingUpdateRequest request, StreamObserver<Empty> responseObserver) {
        dishService.updateRating(
                request.getId(),
                request.getRating(),
                request.getReviewCount()
        );
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}