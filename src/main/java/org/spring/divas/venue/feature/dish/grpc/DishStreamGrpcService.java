package org.spring.divas.venue.feature.dish.grpc;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.spring.divas.dish.v1.DishChange;
import org.spring.divas.dish.v1.DishStreamServiceGrpc;
import org.spring.divas.dish.v1.SubscriptionRequest;
import org.spring.divas.venue.feature.dish.DishRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@ConditionalOnProperty(name = "dish.sync.transport", havingValue = "grpc")
@RequiredArgsConstructor
public class DishStreamGrpcService extends DishStreamServiceGrpc.DishStreamServiceImplBase {
    private final DishRepository dishRepository;
    private final DishProtoMapper dishProtoMapper;
    private final DishStreamPublisher dishStreamPublisher;

    @Override
    public void subscribeDishes(
            SubscriptionRequest request,
            StreamObserver<DishChange> responseObserver
    ) {
        dishRepository.findAll().stream()
                .map(dishProtoMapper::toChange)
                .forEach(responseObserver::onNext);
        dishStreamPublisher.subscribe(responseObserver);
        if (responseObserver instanceof ServerCallStreamObserver<DishChange> observer) {
            observer.setOnCancelHandler(() -> dishStreamPublisher.unsubscribe(responseObserver));
        }
    }
}
