

$( document ).ready(function() {
    const meetingId = $( "#meetingId" ).val(),
        socketUrl = thymeLeaf.contextPath + 'gs-guide-websocket',
		requestUrl = thymeLeaf.contextPath + "ajax/request",
        speakerQueUrl = thymeLeaf.contextPath + "meeting/" + meetingId + "/speakerQue",
        participantsUrl = thymeLeaf.contextPath + "meeting/" + meetingId + "/participants";

    var stompClient = null,


    getSpeakerQue = function( callback ) {
        $.ajax({
            url: speakerQueUrl
        })
        .done( ( response ) => {
            if( response.status != "OK" ) {
                console.error( "error on get " + speakerQueUrl + ": ", response );
                alert( "Fel vid hämtnign av deltagarkö, försök igen" );
                return;
            }
	        repopulateSpeakerQue( response.payload );
	        callback && callback();
        } );
    },

    getParticipants = function( callback ) {
        console.log("participantsUrl", participantsUrl);
        $.ajax({
            url: participantsUrl
        })
        .done( ( response ) => {
            if( response.status != "OK" ) {
                console.error( "error on get " + participantsUrl + ": ", response );
                alert( "Fel vid hämtnign av deltagare, försök igen" );
                return;
            }
            console.log("response", response);
            repopulateParticipantList( response.payload );
	        callback && callback();
        } );
    },

    connect = function() {
        const funk = "connect";
        console.log( "*** " + funk + ": socketUrl", socketUrl );
        var socket = new SockJS( socketUrl );
        console.log( "*** " + funk + ": socket", socket );
        stompClient = Stomp.over(socket);
        console.log( "*** " + funk + ": stompClient", stompClient );
        stompClient.connect({}, function (frame) {
            console.log( "*** " + funk + ": stompClient.connect function -> frame", frame );
            stompClient.subscribe('/topic/newParticipant', function (response) {
	            console.log( "*** " + funk + ": stompClient.subscribe 'topic/newParticipant' -> response:", response );
                addParticipant( JSON.parse( response.body ) );
            });
            stompClient.subscribe('/topic/request', function (response) {
	            console.log( "*** " + funk + ": stompClient.subscribe 'topic/request' -> response:", response );
                addRequest( JSON.parse( response.body ) );
            });
        }, function( error ) {
            console.log( "*** " + funk + ": error", error );
	        continuouslyPullForSpeakerQue();
            continuouslyPullForParticipants();
        });
    },

    killRequest = function( event ) {
        const individual = $( event.target ).closest( "[participant-id]" );
        const participantId = individual.attr("participant-id");
        const requestType = individual.attr("type-of-request");
		const data = JSON.stringify( {
			"participantId": participantId,
			"requestType" : requestType,
			"participantName" : "null",
			"active" : false
		} );

        if( stompClient != null && stompClient.connected ) {
	        stompClient.send( "/app/request", {}, data );
        } else {
	        $.ajax({
	            type : "POST",
		        contentType: "application/json",
	            url: requestUrl,
				data : data
	        })
	        .done( repopulateSpeakerQue );
        }

    },

    addParticipant = function( participant ) {

        const name = $( "<li>" )
            .addClass("m-0 p-1 border-bottom-0 list-group-item")
            .text( participant.name );

        $( "#participants" ).append( name );

        updateParticipantCounter();
    },

    updateParticipantCounter = function() {
        $( "#participantCounter" ).text( $( "#participants" ).children().length );
    },

    addRequest = function(request) {
        $("#speakerQue").append("<tr><td>" + request.participantName + " " + request.requestType + "</td></tr>");

        if( request.active ) {
            createSpeakerQueRow( request.participantId, request.participantName, request.requestType );
        } else {
            removeSpeakerQueRow( request.participantId, request.requestType );
        }
    },

    removeSpeakerQueRow = function( participantId, requestType ) {

        let currentQue = "";
        if( requestType == "breakingQuestion" )  {
            currentQue = "#speakerQueBreakingQuestion";
            decrementHtmlCounter( "#breakingQuestionCounter" );
        }
        if( requestType == "information" )       {
            currentQue = "#speakerQueInformation";
            decrementHtmlCounter( "#informationCounter" );
        }
        if( requestType == "comment" )           {
            currentQue = "#speakerQueComment";
            decrementHtmlCounter( "#commentCounter" );
        }
        if( requestType == "requestToSpeak" )    {
            currentQue = "#speakerQueRequestToSpeak";
            decrementHtmlCounter( "#requestToSpeakCounter" );
        }
        if( requestType == "voteYes" )        {
            currentQue = "#speakerQueVoteYes";
            decrementHtmlCounter( "#voteYesCounter" );
        }
        if( requestType == "voteNo" )        {
            currentQue = "#speakerQueVoteNo";
            decrementHtmlCounter( "#voteNoCounter" );
        }
        const row = $(currentQue).find( "[participant-id=" + participantId + "]" );

        row.remove();
    },

    incrementHtmlCounter = function( selector ) {
        $( selector ).text( Number( $( selector ).text() ) + 1 );
    },

    decrementHtmlCounter = function( selector ) {
        $( selector ).text( Number( $( selector ).text() ) - 1 );
    },

    createSpeakerQueRow = function( participantId, participantName, requestType ) {

        const individual = $( "<div>" )
            .attr("participant-id", participantId)
            .attr("type-of-request", requestType)
            .addClass( "pb-1" );
        const card = $( "<div>" ).addClass( "card" );
        const cardBody = $( "<div>" ).addClass( "row card-body p-1 m-0" );
        const nameArea = $( "<div>" ).addClass( "col" );
        const removeArea = $( "<div>" ).addClass( "col" );
        const name = $( "<p>" ).addClass("m-0").text( participantName );
        const removeButton = $( "<button>" ).addClass("btn p-0 float-right").append(
            $( "<i>" ).addClass( "fa fa-remove" )
        ).on( "click", killRequest );

        let currentQue = "";

        const statusArea = $( "<div>" ).addClass( "col" );

        if( requestType == "breakingQuestion" )  {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Ordningsfråga" ) );
            card.addClass( "bg-danger text-white" );
            currentQue = "#speakerQueBreakingQuestion";
            incrementHtmlCounter( "#breakingQuestionCounter" );
        }
        if( requestType == "information" )       {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Sakupplysning" ) );
            card.addClass( "bg-warning" );
            currentQue = "#speakerQueInformation";
            incrementHtmlCounter( "#informationCounter" );
        }
        if( requestType == "comment" )           {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Kommentar" ) );
            card.addClass( "bg-info text-white" );
            currentQue = "#speakerQueComment";
            incrementHtmlCounter( "#commentCounter" );
        }
        if( requestType == "requestToSpeak" )    {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Begär ordet" ) );
            card.addClass( "bg-white" );
            currentQue = "#speakerQueRequestToSpeak";
            incrementHtmlCounter( "#requestToSpeakCounter" );
        }
        if( requestType == "voteYes" )        {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Rösta JA" ) );
            card.addClass( "bg-yes" );
            currentQue = "#speakerQueVoteYes";
            incrementHtmlCounter( "#voteYesCounter" );
        }
        if( requestType == "voteNo" )        {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Rösta NEJ" ) );
            card.addClass( "bg-no" );
            currentQue = "#speakerQueVoteNo";
            incrementHtmlCounter( "#voteNoCounter" );
        }

        /*
        if( first ) {
            statusArea.children().eq(0).addClass( "font-weight-bold" );
            first = false;
        }
        */

        nameArea.append( name );
        removeArea.append( removeButton );
        card.append( cardBody );
        cardBody.append(nameArea).append(statusArea).append( removeArea );
        individual.append(card);
        $( currentQue ).append( individual );
    },

    repopulateSpeakerQue = function( speakerRequests ) {
        $( ".speakerQue" ).children().remove();
        $( ".counter" ).text( "0" );

        $.each( speakerRequests, (index, participant) => {

            createSpeakerQueRow( participant.id, participant.name, participant.requestType );

            if( participant.breakingQuestion )  {
                createSpeakerQueRow( participant.id, participant.name, "breakingQuestion" );
            } else if( participant.information )       {
                createSpeakerQueRow( participant.id, participant.name, "information" );
            } else if( participant.comment )           {
                createSpeakerQueRow( participant.id, participant.name, "comment" );
            } else if( participant.requestToSpeak )    {
                createSpeakerQueRow( participant.id, participant.name, "requestToSpeak" );
            } else if( participant.voteYes )        {
                createSpeakerQueRow( participant.id, participant.name, "voteYes" );
            } else if( participant.voteNo )        {
                createSpeakerQueRow( participant.id, participant.name, "voteNo" );
            }

        });

    },

    repopulateParticipantList = function( participants ) {
        $( "#participants" ).children().remove();

        $.each( participants, (index, participant) => {
            const name = $( "<li>" ).addClass("m-0 p-1 border-bottom-0 list-group-item").text( participant.name );
            $( "#participants" ).append( name );
        });

        $( "#participantCounter" ).text( participants.length );

    },

    continuouslyPullForSpeakerQue = function() {
        setTimeout( () => {
            getSpeakerQue( continuouslyPullForSpeakerQue );
        }, 1000 );
    },

    continuouslyPullForParticipants = function() {
        setTimeout( () => {
            getParticipants( continuouslyPullForParticipants );
        }, 9000 );
    },

    setJoinUrl = function() {
        const joinUrl = window.location.href.replace("/admin", "");
        $( "#joinUrl" ).attr( "href", joinUrl ).text( joinUrl );
    };


    setJoinUrl();

    //connect(); // webSockets
    continuouslyPullForSpeakerQue(); // noWebSockets
    continuouslyPullForParticipants(); // noWebSockets

    getSpeakerQue();
    getParticipants();

});

