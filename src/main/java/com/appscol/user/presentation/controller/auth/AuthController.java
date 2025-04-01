package com.appscol.user.presentation.controller.auth;

import com.appscol.constants.EndpointsConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth")
@RequiredArgsConstructor
@RequestMapping(path = EndpointsConstants.ENDPOINT_BASE_API)
public class AuthController {
}
