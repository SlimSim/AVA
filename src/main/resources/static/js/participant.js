


/*
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
});
*/




$( document ).ready(function() {



    var stompClient = null,


    connect = function() {
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
        });
    },

    /*
    sendName = function( name ) {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': name}));
    },
    sendRequest2 = function(participantId, typeOfRequest) {
        stompClient.send(
            "/app/request2",
            {},
            //JSON.stringify({'name': participantId + " & " + typeOfRequest})
            JSON.stringify({
                'name': participantId,
                'typeOfRequest' : typeOfRequest
            })
        );
    },*/
    sendRequest = function(participantId, typeOfRequest, active) {
        stompClient.send(
            "/app/request",
            {},
            JSON.stringify({
                "participantId": participantId,
                "typeOfRequest" : typeOfRequest,
                "active" : active
            })
        );
    },


    addTimeChanged = function( event ) {
        $( event.target ).data( "time-changed", Date.now() );
    },

    submitForm = function( event ) {
        event.preventDefault();
        console.log( "event", event );
        var data = {},
            name = $( event.originalEvent.submitter ).text(),
            typeOfRequest = $( event.originalEvent.submitter ).attr( "id" ),
            participantId = $("#participantId").val(),
            active = $( event.originalEvent.submitter ).hasClass( "active" )
            $form = $( this ),
            url = $form.attr('action');
        console.log("typeOfRequest", typeOfRequest);
        console.log("participantId", participantId);
        console.log("active", active);

        $("#participantForm").find("button").each( (i, el) => {
            var $button = $( el );
            data[ $button.attr( "id" ) ] = $button.hasClass( "active" );
            data[ $button.attr( "id" ) + "Time" ] = $button.data( "time-changed" );
        });
        $("#participantForm").find("input").each( (i, el) => {
            var $button = $( el );
            data[ $button.attr( "id" ) ] = $button.val()
        });



        sendRequest(participantId, typeOfRequest, active);
/*
        $.ajax({
            method: "PUT",
            url: url,
            contentType: "application/json",
            data: JSON.stringify( data )
        })
        .done( function( status ) {
        } );
*/
    };

    connect();

    $( "#participantForm" ).on( "submit", submitForm );

    $( "[data-time-changed]" ).on( "click", addTimeChanged);

});

