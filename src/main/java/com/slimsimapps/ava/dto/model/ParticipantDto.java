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
public class ParticipantDto {

	private int id;
	private String name;
	private boolean breakingQuestion;
	private long breakingQuestionTime;
	private boolean information;
	private long informationTime;
	private boolean comment;
	private long commentTime;
	private boolean requestToSpeak;
	private long requestToSpeakTime;
	private boolean voteYes;
	private long voteYesTime;
	private boolean voteNo;
	private long voteNoTime;
	private boolean showBreakingQuestion;
	private boolean showInformation;
	private boolean showComment;
	private boolean showRequestToSpeak;
	private boolean showVoteYes;
	private boolean showVoteNo;

}
