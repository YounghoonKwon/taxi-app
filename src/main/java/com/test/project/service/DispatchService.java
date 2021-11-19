package com.test.project.service;

import com.test.project.domain.Dispatch;
import com.test.project.domain.Status;
import com.test.project.domain.User;
import com.test.project.dto.DispatchRequest;
import com.test.project.dto.DispatchResponse;
import com.test.project.exception.ConflictException;
import com.test.project.exception.ForbiddenException;
import com.test.project.exception.NotFoundException;
import com.test.project.exception.UnAuthorizedException;
import com.test.project.repository.DispatchRepository;
import com.test.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DispatchService {

    private final DispatchRepository dispatchRepository;
    private final UserRepository userRepository;

    @Transactional
    public DispatchResponse create(User user, DispatchRequest dispatchRequest) {
        if (!user.isPassenger()) {
            throw new ForbiddenException("승객이 아닌 유저가 배차 요청하였습니다");
        }

        if (dispatchRepository.existsByPassengerIdAndStatus(user.getId(), Status.STAND_BY)) {
            throw new ConflictException("승객의 기존 배차 요청이 수락되지 않았습니다");
        }

        final Dispatch dispatch = dispatchRepository.save(
            Dispatch.builder()
                .address(dispatchRequest.getAddress())
                .passengerId(user.getId())
                .status(Status.STAND_BY)
                .build()
        );

        return DispatchResponse.from(dispatch);
    }

    public List<DispatchResponse> findAllDispatches(Integer userId) {
        final Optional<User> optionalUser = userRepository.findById(userId);
        final User user = optionalUser.orElseThrow(() -> new UnAuthorizedException("배차요청 하려는 유저가 저장소에 존재하지 않습니다"));

        if (user.isDriver()) {
            final List<Dispatch> dispatchesList = dispatchRepository.findAll();
            return DispatchResponse.from(dispatchesList);
        }

        if (user.isPassenger()) {
            final List<Dispatch> dispatchesList = dispatchRepository.findAllByPassengerId(userId);
            return DispatchResponse.from(dispatchesList);
        }
        return null;
    }

    @Transactional
    public DispatchResponse update(User user, int taxiRequestId) {
        if (!user.isDriver()) {
            throw new ForbiddenException("기사가 아닌 유저는 배차를 요청할 수 없습니다");
        }
        final Optional<Dispatch> optionalDispatch = dispatchRepository.findById(taxiRequestId);
        optionalDispatch.orElseThrow(() -> new NotFoundException("요청한 택시 배차 요청이 존재하지 않습니다"));
        final Dispatch dispatch = optionalDispatch.get();

        if (dispatch.isAcceptedStatus()) {
            throw new ConflictException("이미 다른 기사에 의해 배차 요청이 수락되었습니다");
        }

        final Dispatch updateDispatch = Dispatch.builder()
            .id(taxiRequestId)
            .address(dispatch.getAddress())
            .passengerId(dispatch.getPassengerId())
            .status(Status.ACCEPTED)
            .driverId(user.getId())
            .acceptedAt(LocalDateTime.now())
            .build();
        dispatch.update(updateDispatch);
        return DispatchResponse.from(dispatch);
    }
}
