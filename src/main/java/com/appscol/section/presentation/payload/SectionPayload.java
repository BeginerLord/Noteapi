package com.appscol.section.presentation.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionPayload {
    private String sectionName;
    private Long gradeId;

}
