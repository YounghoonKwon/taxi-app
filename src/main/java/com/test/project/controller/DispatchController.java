package com.test.project.controller;

import com.test.project.auth.SigninUser;
import com.test.project.domain.User;
import com.test.project.dto.DispatchRequest;
import com.test.project.dto.DispatchResponse;
import com.test.project.service.DispatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/taxi-requests")
@RequiredArgsConstructor
public class DispatchController {

    private final DispatchService dispatchService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DispatchResponse> findAll(@SigninUser User user) {
        return dispatchService.findAllDispatches(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DispatchResponse create(@SigninUser User user, @Valid DispatchRequest dispatchRequest) {
        return dispatchService.create(user, dispatchRequest);
    }

    @PutMapping("/{taxiRequestId}/accept")
    @ResponseStatus(HttpStatus.OK)
    public DispatchResponse update(@SigninUser User user, @PathVariable Integer taxiRequestId) {
        return dispatchService.update(user, taxiRequestId);
    }
}
