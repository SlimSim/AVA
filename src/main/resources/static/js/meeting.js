

$( document ).ready(function() {
    const meetingId = $( "#meetingId" ).val(),
        speakerQueUrl = thymeLeaf.contextPath + "meeting/" + meetingId + "/speakerQue",
        participantsUrl = thymeLeaf.contextPath + "meeting/" + meetingId + "/participants";

    var stompClient = null,


    getSpeakerQue = function() {

        $.ajax({
            url: speakerQueUrl
        })
        .done( repopulateSpeakerQue );
    },

    connect = function() {
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/newParticipant', function (response) {
                addParticipant( JSON.parse( response.body ) );
            });
            stompClient.subscribe('/topic/request', function (response) {
                addRequest( JSON.parse( response.body ) );
            });
        });
    },

    getParticipants = function() {
        $.ajax({
            url: participantsUrl
        })
            .done( repopulateParticipantList );
    },

    killRequest = function( event ) {
        const individual = $( event.target ).closest( "[participant-id]" );
        const participantId = individual.attr("participant-id");
        const typeOfRequest = individual.attr("type-of-request");

        stompClient.send(
            "/app/request",
            {},
            JSON.stringify( {
                "participantId": participantId,
                "typeOfRequest" : typeOfRequest,
                "participantName" : "null",
                "active" : false
            } )
        );
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
        $("#speakerQue").append("<tr><td>" + request.participantName + " " + request.typeOfRequest + "</td></tr>");

        if( request.active ) {
            createSpeakerQueRow( request.participantId, request.participantName, request.typeOfRequest );
        } else {
            removeSpeakerQueRow( request.participantId, request.typeOfRequest );
        }
    },

    removeSpeakerQueRow = function( participantId, typeOfRequest ) {

        let currentQue = "";
        if( typeOfRequest == "breakingQuestion" )  {
            currentQue = "#speakerQueBreakingQuestion";
            decrementHtmlCounter( "#breakingQuestionCounter" );
        }
        if( typeOfRequest == "information" )       {
            currentQue = "#speakerQueInformation";
            decrementHtmlCounter( "#informationCounter" );
        }
        if( typeOfRequest == "comment" )           {
            currentQue = "#speakerQueComment";
            decrementHtmlCounter( "#commentCounter" );
        }
        if( typeOfRequest == "requestToSpeak" )    {
            currentQue = "#speakerQueRequestToSpeak";
            decrementHtmlCounter( "#requestToSpeakCounter" );
        }
        if( typeOfRequest == "voteYes" )        {
            currentQue = "#speakerQueVoteYes";
            decrementHtmlCounter( "#voteYesCounter" );
        }
        if( typeOfRequest == "voteNo" )        {
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

    createSpeakerQueRow = function( participantId, participantName, typeOfRequest ) {

        const individual = $( "<div>" )
            .attr("participant-id", participantId)
            .attr("type-of-request", typeOfRequest)
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

        if( typeOfRequest == "breakingQuestion" )  {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Ordningsfråga" ) );
            card.addClass( "bg-danger text-white" );
            currentQue = "#speakerQueBreakingQuestion";
            incrementHtmlCounter( "#breakingQuestionCounter" );
        }
        if( typeOfRequest == "information" )       {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Sakupplysning" ) );
            card.addClass( "bg-warning" );
            currentQue = "#speakerQueInformation";
            incrementHtmlCounter( "#informationCounter" );
        }
        if( typeOfRequest == "comment" )           {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Kommentar" ) );
            card.addClass( "bg-info text-white" );
            currentQue = "#speakerQueComment";
            incrementHtmlCounter( "#commentCounter" );
        }
        if( typeOfRequest == "requestToSpeak" )    {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Begär ordet" ) );
            card.addClass( "bg-white" );
            currentQue = "#speakerQueRequestToSpeak";
            incrementHtmlCounter( "#requestToSpeakCounter" );
        }
        if( typeOfRequest == "voteYes" )        {
            statusArea.append( $( "<p>" ).addClass("m-0").text( "Rösta JA" ) );
            card.addClass( "bg-yes" );
            currentQue = "#speakerQueVoteYes";
            incrementHtmlCounter( "#voteYesCounter" );
        }
        if( typeOfRequest == "voteNo" )        {
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
        $( "#speakerQue" ).children().remove();

        $.each( speakerRequests, (index, participant) => {

            createSpeakerQueRow( participant.id, participant.name, participant.typeOfRequest );

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

    };

    connect();

    getSpeakerQue();
    getParticipants();

});

