package com.slimsimapps.ava.dto.mapper;

import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.model.Meeting;
import org.springframework.stereotype.Component;

@Component
public class MeetingMapper {
	public static MeetingDto toMeetingDto(Meeting meeting ) {
		return new MeetingDto()
				       .setId( meeting.getId() )
				       .setName( meeting.getName() )
				       .setShowBreakingQuestion( meeting.isShowBreakingQuestion() )
				       .setShowInformation( meeting.isShowInformation() )
				       .setShowComment( meeting.isShowComment() )
				       .setShowRequestToSpeak( meeting.isShowRequestToSpeak() )
				       .setShowVoteYes( meeting.isShowVoteYes() )
				       .setShowVoteNo( meeting.isShowVoteNo() );
	}

	public static Meeting toMeeting(MeetingDto meetingDto ) {
		return new Meeting()
				       .setId( meetingDto.getId() )
				       .setName( meetingDto.getName() )
				       .setShowBreakingQuestion( meetingDto.isShowBreakingQuestion() )
				       .setShowInformation( meetingDto.isShowInformation() )
				       .setShowComment( meetingDto.isShowComment() )
				       .setShowRequestToSpeak( meetingDto.isShowRequestToSpeak() )
				       .setShowVoteYes( meetingDto.isShowVoteYes() )
				       .setShowVoteNo( meetingDto.isShowVoteNo() );
	}

}
