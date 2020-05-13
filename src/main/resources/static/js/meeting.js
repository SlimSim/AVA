
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
        $.each( participants, (index, participant) => {
            var row = $( "<tr>" )
                .append( $( "<td>" ).text(index) )
                .append( $( "<td>" ).text(participant.name) )

                //.append( $( "<td>" ).append( killButton( "Ordningsfråga", participant.breakingQuestion ) ) )
                .append( $( "<td>" ).text(participant.breakingQuestion?"Ordningsfråga":"") )
                .append( $( "<td>" ).text(participant.information?"Sakupplysning":"") )
                .append( $( "<td>" ).text(participant.comment?"Kommentar":"") )
                .append( $( "<td>" ).text(participant.requestToSpeak?"Begär ordet":"") )
                .append( $( "<td>" ).text(participant.handRaised?"Rösta JA":"") );

            $( "#participants" ).append( row );
        });


        // new:

        /*
        $( "#participants2" ).children().remove();
        $.each( participants, (index, participant) => {
            var status = "";


            if( participant.breakingQuestion )  {
                status += " Ordningsfråga";
            }
            if( participant.information )  {
                status += " Sakupplysning";
            }
            if( participant.comment )  {
                status += " Kommentar";
            }
            if( participant.requestToSpeak )  {
                status += " Begär ordet";
            }
            if( participant.handRaised )  {
                status += " Rösta JA";
            }


            var row = $( "<tr>" )
                .append( $( "<td>" ).text() )
                .append( $( "<td>" ).text(index + ", " + participant.name) )
                .append( $( "<td>" ).text(status) );

            $( "#participants2" ).append( row );
        });
        */


    },

    repeatedTasks = function(){
        getParticipants();

        setTimeout( repeatedTasks, updateTime );
    };

    setTimeout( repeatedTasks, updateTime );

});

