
$( document ).ready(function() {
    const meetingId = $( "#meetingId" ).val(),
        participantUrl = "/meeting/" + meetingId + "/participants",
        updateTime = 250;

    var getParticipants = function() {

        $.ajax({
            url: participantUrl
        })
        .done( repopulateParticipantTable );
    },

        /*
    killButton = function( text, value ) {

        if( !value ) {
            return null;
        }
        $( "<button>" )
            .addClass( "btn").addClass


    },*/

    repopulateParticipantTable = function( participants ) {

        $( "#participants" ).children().remove();
        let breakingQuestionCounter = 0;
        let informationCounter = 0;
        let commentCounter = 0;
        let requestToSpeakCounter = 0;
        let handRaisedCounter = 0;


        $.each( participants, (index, participant) => {


            const individual = $( "<div>" ).addClass().addClass( "pb-1" );
            const card = $( "<div>" ).addClass().addClass( "card" );
            const cardBody = $( "<div>" ).addClass( "row" ).addClass( "card-body" ).addClass( "p-1 m-0" );
            const nameArea = $( "<div>" ).addClass( "col" );
            const name = $( "<p>" ).addClass("m-0").text( participant.name );

            const statusArea = $( "<div>" ).addClass( "col" );


            if( participant.breakingQuestion )  {
                breakingQuestionCounter++;
                statusArea.append( $( "<p>" ).addClass("m-0").text( "Ordningsfråga" ) );
            }
            if( participant.information )       {
                informationCounter++;
                statusArea.append( $( "<p>" ).addClass("m-0").text( "Sakupplysning" ) );
            }
            if( participant.comment )           {
                commentCounter++;
                statusArea.append( $( "<p>" ).addClass("m-0").text( "Kommentar" ) );
            }
            if( participant.requestToSpeak )    {
                requestToSpeakCounter++;
                statusArea.append( $( "<p>" ).addClass("m-0").text( "Begär ordet" ) );
            }
            if( participant.handRaised )        {
                handRaisedCounter++;
                statusArea.append( $( "<p>" ).addClass("m-0").text( "Rösta JA" ) );
            }

            statusArea.children().eq(0).addClass( "font-weight-bold" );

            nameArea.append( name );
            card.append( cardBody );
            cardBody.append(nameArea).append(statusArea);
            individual.append(card);
            $( "#participants" ).append( individual );
        });


        $( "#participantCounter" ).text( participants.length );
        $( "#breakingQuestionCounter" ).text( breakingQuestionCounter );
        $( "#informationCounter" ).text( informationCounter );
        $( "#commentCounter" ).text( commentCounter );
        $( "#requestToSpeakCounter" ).text( requestToSpeakCounter );
        $( "#handRaisedCounter" ).text( handRaisedCounter );


    },

    repeatedTasks = function(){
        getParticipants();

        setTimeout( repeatedTasks, updateTime );
    };

    setTimeout( repeatedTasks, updateTime );

});

