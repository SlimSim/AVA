


$( document ).ready(function() {
    var updateTime = 5000,

    addTimeChanged = function( event ) {
        console.log("addTimeChanged -> ");

        var millis = Date.now();

        $( event.target ).data( "time-changed", Date.now() );
    }

    submitForm = function( event ) {
        event.preventDefault();
        var data = {},
            $form = $( this ),
            url = $form.attr('action');

        $("#participantForm").find("button").each( (i, el) => {
            var $button = $( el );
            data[ $button.attr( "id" ) ] = $button.hasClass( "active" );
            data[ $button.attr( "id" ) + "Time" ] = $button.data( "time-changed" );
        });
        $("#participantForm").find("input").each( (i, el) => {
            var $button = $( el );
            data[ $button.attr( "id" ) ] = $button.val()
        });
        console.log("data", data);

        $.ajax({
            method: "PUT",
            url: url,
            contentType: "application/json",
            data: JSON.stringify( data )
        })
        .done( function( status ) { console.log("status", status)} );

    };

    $( "#participantForm" ).on( "submit", submitForm );

    $( "[data-time-changed]" ).on( "click", addTimeChanged)


/*
    getParticipants =  function() {
        console.log("getParticipants ->" )
        $.ajax({
            url: "/meeting/1/participants"
        })
        .done( repopulateParticipantTable );
    },

    repopulateParticipantTable = function( participants ) {
        console.log("repopulateParticipantTable -> participants", participants );
        $( "#participants" ).children().remove();
        console.log("repopulateParticipantTable: 2" );
        console.log("repopulateParticipantTable: $ table tbody: ", $( "table tbody" ) );
        $.each( participants, (index, participant) => {
            $( "#participants" ).append( $( "<tr>" )
                .append( $( "<td>" ).text(participant.id) )
                .append( $( "<td>" ).text(participant.name) )
                .append( $( "<td>" ).text(participant.handRaised) )
                .append( $( "<td>" ).text(participant.breakingQuestion) )
                .append( $( "<td>" ).text(participant.requestToSpeak) )
            );
        });
    },

    repeatedTasks = function(){
        getParticipants();

        setTimeout( repeatedTasks, updateTime );
    };

    setTimeout( repeatedTasks, updateTime );
*/
});

