package com.matchub.api.matchub_api.dto;

import com.matchub.api.matchub_api.domain.enums.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HubUserDTOBase {
//    private Long id;
    @Schema(description = "", example = "null")
    private String nickname;
    @Schema(description = "", example = "null")
    private String firstname;
    @Schema(description = "", example = "null")
    private String lastname;
    @Schema(description = "", example = "null")
    private String email;
    @Schema(description = "", example = "null")
    private Region region;
//    private String username;
//    private Role role = Role.HUBUSER;
//    private Hability abilityLevel;
//    private Champion mastery;
//    private LocalDateTime creation;
//    private LocalDateTime update;
//    private SortedSet<Comment> comments = new TreeSet<>();
//    private Set<Evaluation> evaluations = new HashSet<>();
}
