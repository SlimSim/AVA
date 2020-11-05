package com.slimsimapps.ava.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeetingDto {

	private int id;
	private String name;

	private boolean showBreakingQuestion;
	private boolean showInformation;
	private boolean showComment;
	private boolean showRequestToSpeak;
	private boolean showVoteYes;
	private boolean showVoteNo;

}
