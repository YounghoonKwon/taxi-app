package com.test.project.service;

import com.test.project.domain.Dispatch;
import com.test.project.domain.Status;
import com.test.project.domain.User;
import com.test.project.dto.DispatchRequest;
import com.test.project.dto.DispatchResponse;
import com.test.project.exception.UnAuthorizeException;
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
        System.out.println(user);
        final Dispatch build = Dispatch.builder()
            .address(dispatchRequest.getAddress())
            .passengerId(user.getId())
            .status(Status.STAND_BY)
            .build();
        final Dispatch dispatch = dispatchRepository.save(build);
        dispatchRepository.flush();

        System.out.println(dispatch.getId());

        return DispatchResponse.from(dispatch);
    }


    public List<DispatchResponse> findAllDispatches(Integer userId) {
        final Optional<User> optionalUser = userRepository.findById(userId);
        final User User = optionalUser.orElseThrow(() -> new UnAuthorizeException("유저가 없습니다."));

        if (User.isDriver()) {
            final List<Dispatch> dispatchesList = dispatchRepository.findAll();
            return DispatchResponse.from(dispatchesList);
        }

        if (User.isPassenger()) {
            final List<Dispatch> dispatchesList = dispatchRepository.findAllByPassengerId(userId);
            return DispatchResponse.from(dispatchesList);
        }
        return null;
    }

    @Transactional
    public DispatchResponse update(User user, int taxiRequestId) {
        final Optional<Dispatch> optionalDispatch = dispatchRepository.findById(taxiRequestId);
        optionalDispatch.orElseThrow(() -> new UnAuthorizeException("Taxi Request가 없습니다."));
        final Dispatch dispatch = optionalDispatch.get();
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

