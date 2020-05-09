
$( document ).ready(function() {
    var updateTime = 250,

    getParticipants =  function() {
        $.ajax({
            url: "/meeting/1/participants"
        })
        .done( repopulateParticipantTable );
    },

    killButton = function( text, value ) {

        if( !value ) {
            return null;
        }
        $( "<button>" )
            .addClass( "btn").addClass


    },

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
    },

    repeatedTasks = function(){
        getParticipants();

        setTimeout( repeatedTasks, updateTime );
    };

    setTimeout( repeatedTasks, updateTime );

});

