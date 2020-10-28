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
	ParticipantService participantService;

	@Autowired
	MeetingService meetingService;

	@Test
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	void testingGetSpeakerQueSorting() throws Exception{
		int meetingId = 1;
		Meeting meeting = new Meeting(meetingId, "TestMeeting" );
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

		participantService.addParticipant( p1, meetingId);
		participantService.addParticipant( p2, meetingId);

		List<Participant> meetingList = participantService.getSpeakerQue( meetingId );

		assertEquals(meetingList.size(), 5, "meetingList has not correct size" );
		assertEquals( meetingList.get(0).getId(), p2.getId(), "meetingList is not sorted correctly");
		assertEquals( meetingList.get(1).getId(), p1.getId(), "meetingList is not sorted correctly");
		assertEquals( meetingList.get(2).getId(), p1.getId(), "meetingList is not sorted correctly");
		assertEquals( meetingList.get(3).getId(), p1.getId(), "meetingList is not sorted correctly");
		assertEquals( meetingList.get(4).getId(), p2.getId(), "meetingList is not sorted correctly");


		assertTrue(meetingList.get(0).isBreakingQuestion(), "meetingList.get(0) is not BreakingQuestion!");
		assertTrue(meetingList.get(1).isBreakingQuestion(), "meetingList.get(1) is not BreakingQuestion!");
		assertTrue(meetingList.get(2).isInformation(), "meetingList.get(2) is not Information!");
		assertTrue(meetingList.get(3).isComment(), "meetingList.get(3) is not Comment!");
		assertTrue(meetingList.get(4).isComment(), "meetingList.get(4) is not Comment!");
	}

}
