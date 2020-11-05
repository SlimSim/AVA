package com.slimsimapps.ava.dto.mapper;

import com.slimsimapps.ava.dto.model.ParticipantDto;
import com.slimsimapps.ava.model.Participant;

public class ParticipantMapper {
	public static ParticipantDto toParticipantDto(Participant participant ) {
		return new ParticipantDto()
				.setId( participant.getId() )
				.setName( participant.getName() )
				.setBreakingQuestion( participant.isBreakingQuestion() )
				.setBreakingQuestionTime( participant.getBreakingQuestionTime() )
				.setInformation( participant.isInformation() )
				.setInformationTime( participant.getInformationTime() )
				.setComment( participant.isComment() )
				.setCommentTime( participant.getCommentTime() )
				.setRequestToSpeak( participant.isRequestToSpeak() )
				.setRequestToSpeakTime( participant.getRequestToSpeakTime() )
				.setVoteYes( participant.isVoteYes() )
				.setVoteYesTime( participant.getVoteYesTime() )
				.setVoteNo( participant.isVoteNo() )
				.setVoteNoTime( participant.getVoteNoTime() )
				.setShowBreakingQuestion( participant.isShowBreakingQuestion() )
				.setShowInformation( participant.isShowInformation() )
				.setShowComment( participant.isShowComment() )
				.setShowRequestToSpeak( participant.isShowRequestToSpeak() )
				.setShowVoteYes( participant.isShowVoteYes() )
				.setShowVoteNo( participant.isShowVoteNo() );
	}

	public static Participant toParticipant( ParticipantDto participantDto ) {
		return new Participant()
				.setId( participantDto.getId() )
				.setName( participantDto.getName() )
				.setBreakingQuestion( participantDto.isBreakingQuestion() )
				.setBreakingQuestionTime( participantDto.getBreakingQuestionTime() )
				.setInformation( participantDto.isInformation() )
				.setInformationTime( participantDto.getInformationTime() )
				.setComment( participantDto.isComment() )
				.setCommentTime( participantDto.getCommentTime() )
				.setRequestToSpeak( participantDto.isRequestToSpeak() )
				.setRequestToSpeakTime( participantDto.getRequestToSpeakTime() )
				.setVoteYes( participantDto.isVoteYes() )
				.setVoteYesTime( participantDto.getVoteYesTime() )
				.setVoteNo( participantDto.isVoteNo() )
				.setVoteNoTime( participantDto.getVoteNoTime() )
				.setShowBreakingQuestion( participantDto.isShowBreakingQuestion() )
				.setShowInformation( participantDto.isShowInformation() )
				.setShowComment( participantDto.isShowComment() )
				.setShowRequestToSpeak( participantDto.isShowRequestToSpeak() )
				.setShowVoteYes( participantDto.isShowVoteYes() )
				.setShowVoteNo( participantDto.isShowVoteNo() );
	}
}
