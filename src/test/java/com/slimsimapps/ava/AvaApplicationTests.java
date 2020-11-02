package com.slimsimapps.ava;

import com.slimsimapps.ava.badlog.BadLogService;
import com.slimsimapps.ava.meeting.Meeting;
import com.slimsimapps.ava.meeting.MeetingService;
import com.slimsimapps.ava.participant.Participant;
import com.slimsimapps.ava.participant.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Part;
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

		Meeting meeting = new Meeting();
		meetingService.addMeeting(meeting);

		Participant p1 = new Participant( "Participant1");
		Participant p2 = new Participant( "Participant2");

		participantService.addParticipant( p1, meeting.getId());
		List<Participant> meetingList1 = participantService.getAllParticipants( meeting.getId() );

		participantService.addParticipant( p2, meeting.getId());
		List<Participant> meetingList2 = participantService.getAllParticipants( meeting.getId() );

		assertEquals(meetingList1.size(), 1, "meetingList1 has not correct size" );
		assertEquals(meetingList2.size(), 2, "meetingList2 has not correct size" );
	}

	@Test
	void testingGetSpeakerQueSorting() throws Exception{
		Meeting meeting = new Meeting();
		meetingService.addMeeting(meeting);

		Participant p1 = new Participant( "Participant1");
		p1.setComment(true);
		p1.setCommentTime(2000);
		p1.setBreakingQuestion(true);
		p1.setBreakingQuestionTime(4000);
		p1.setInformation(true);
		p1.setInformationTime(5000);

		Participant p2 = new Participant( "Participant2");
		p2.setBreakingQuestion(true);
		p2.setBreakingQuestionTime(3000);
		p2.setComment(true);
		p2.setCommentTime(6000);

		participantService.addParticipant( p1, meeting.getId());
		participantService.addParticipant( p2, meeting.getId());

		List<Participant> speakerQue = participantService.getSpeakerQue( meeting.getId() );

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
