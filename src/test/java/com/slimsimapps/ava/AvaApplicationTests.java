package com.slimsimapps.ava;

import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.dto.model.ParticipantDto;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.MeetingService;
import com.slimsimapps.ava.model.Participant;
import com.slimsimapps.ava.service.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AvaApplicationTests {

	@Autowired
	BadLogService log;

	@Autowired
	ParticipantService participantService;

	@Autowired
	MeetingService meetingService;

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	void testingGetAllParticipants() throws Exception {

		MeetingDto meeting = new MeetingDto();
		meetingService.addMeeting(meeting);

		ParticipantDto p1 = new ParticipantDto().setName( "Participant1" );
		ParticipantDto p2 = new ParticipantDto().setName( "Participant2" );

		participantService.addParticipant( p1, meeting.getId());
		List<ParticipantDto> meetingList1 = participantService.getAllParticipantDtos( meeting.getId() );

		participantService.addParticipant( p2, meeting.getId());
		List<ParticipantDto> meetingList2 = participantService.getAllParticipantDtos( meeting.getId() );

		assertEquals(meetingList1.size(), 1, "meetingList1 has not correct size" );
		assertEquals(meetingList2.size(), 2, "meetingList2 has not correct size" );
	}

	@Test
	void testingGetSpeakerQueSorting() throws Exception{
		MeetingDto meeting = new MeetingDto();
		meetingService.addMeeting(meeting);

		ParticipantDto p1 = new ParticipantDto()
									.setId(1)
				                    .setName( "Participant1")
									.setComment(true)
									.setCommentTime(2000)
									.setBreakingQuestion(true)
									.setBreakingQuestionTime(4000)
									.setInformation(true)
									.setInformationTime(5000);

		ParticipantDto p2 = new ParticipantDto()
									.setId(2)
				                    .setName( "Participant2")
									.setBreakingQuestion(true)
									.setBreakingQuestionTime(3000)
									.setComment(true)
									.setCommentTime(6000);

		participantService.addParticipant( p1, meeting.getId());
		participantService.addParticipant( p2, meeting.getId());

		List<ParticipantDto> speakerQue = participantService.getSpeakerQue( meeting.getId() );

		for( int i = 0; i<speakerQue.size(); i++) {
			log.i(i + " : " + speakerQue.get( i ) );
		}
		log.i("p1: " + p1 );
		log.i("p2: " + p2 );

		assertEquals(speakerQue.size(), 5, "speakerQue has not correct size" );
		assertEquals( speakerQue.get(0).getId(), p2.getId(), "speakerQue is not sorted correctly");
		assertEquals( speakerQue.get(1).getId(), p1.getId(), "speakerQue is not sorted correctly");
		assertEquals( speakerQue.get(2).getId(), p1.getId(), "speakerQue is not sorted correctly");
		assertEquals( speakerQue.get(3).getId(), p1.getId(), "speakerQue is not sorted correctly");
		assertEquals( speakerQue.get(4).getId(), p2.getId(), "speakerQue is not sorted correctly");


		assertTrue(speakerQue.get(0).isBreakingQuestion(), "speakerQue.get(0) is not BreakingQuestion!");
		assertTrue(speakerQue.get(1).isBreakingQuestion(), "speakerQue.get(1) is not BreakingQuestion!");
		assertTrue(speakerQue.get(2).isInformation(), "speakerQue.get(2) is not Information!");
		assertTrue(speakerQue.get(3).isComment(), "speakerQue.get(3) is not Comment!");
		assertTrue(speakerQue.get(4).isComment(), "speakerQue.get(4) is not Comment!");
	}

}
