package com.matchub.api.matchub_api.controller;

import com.matchub.api.matchub_api.dto.HubUserDTOLinks;
import com.matchub.api.matchub_api.security.dto.ChangePositionDTO;
import com.matchub.api.matchub_api.service.HubUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin", description = "")
@RestController
@RequestMapping(value = "/admins")
@RequiredArgsConstructor
public class AdminController {
    private final HubUserService hubUserService;

    @PutMapping(value = "/{hubUserid}")
    public ResponseEntity<HubUserDTOLinks> alterPositon(@PathVariable Long hubUserid,
                                                        @RequestBody ChangePositionDTO request){
        HubUserDTOLinks updatedHubUser = hubUserService.alterPosition(hubUserid, request);
        return ResponseEntity.ok().body(updatedHubUser);
    }

    @DeleteMapping(value = "/{hubUserId}")
    public ResponseEntity<Void> delete(@PathVariable Long hubUserId){
        hubUserService.delete(hubUserId);
        return ResponseEntity.noContent().build();
    }
}
