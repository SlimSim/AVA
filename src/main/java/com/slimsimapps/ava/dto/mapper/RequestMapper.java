package com.slimsimapps.ava.dto.mapper;

import com.slimsimapps.ava.dto.model.RequestDto;
import com.slimsimapps.ava.model.Request;

public class RequestMapper {
	public static Request toRequest( RequestDto requestDto ) {
		return new Request()
				.setParticipantId( requestDto.getParticipantId() )
				.setParticipantName( requestDto.getParticipantName() )
				.setTypeOfRequest( requestDto.getTypeOfRequest() )
				.setActive( requestDto.isActive() );
	}

}
