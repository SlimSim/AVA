


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
    sendRequest = function(participantId, participantName, typeOfRequest, active) {
        const obj = {
            "participantId": participantId,
            "participantName" : participantName,
            "typeOfRequest" : typeOfRequest,
            "active" : active
        };

        console.log( "obj", obj );

        stompClient.send(
            "/app/request",
            {},
            JSON.stringify( obj )
        );
    },


    addTimeChanged = function( event ) {
        $( event.target ).data( "time-changed", Date.now() );
    },

    submitForm = function( event ) {
        console.log("submitForm -> ");
        event.preventDefault();
    },

    submitForm2 = function( event ) {
        event.preventDefault();
        console.log( "event", event );
        console.log( "this", this );

        var data = {},
            $target = $( event.target ),
            name = $( event.originalEvent.submitter ).text(),
            typeOfRequest = $target.attr( "id" ),
            participantId = $("#participantId").val(),
            participantName = $("#participantName").text(),
            active = !$target.hasClass( "active" ),
            $form = $( this ),
            url = $form.attr('action');
        console.log("participantId", participantId);
        console.log("participantName", participantName);
        console.log("typeOfRequest", typeOfRequest);
        console.log("active", active);
        console.log("name", name);
        console.log("event.originalEvent.submitter", event.originalEvent.submitter);

        /*
        $("#participantForm").find("button").each( (i, el) => {
            var $button = $( el );
            data[ $button.attr( "id" ) ] = $button.hasClass( "active" );
            data[ $button.attr( "id" ) + "Time" ] = $button.data( "time-changed" );
        });
        $("#participantForm").find("input").each( (i, el) => {
            var $button = $( el );
            data[ $button.attr( "id" ) ] = $button.val()
        });
        */



        sendRequest(participantId, participantName, typeOfRequest, active);
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

    //$( "#participantForm" ).on( "submit", submitForm );

    $( ".request-button" ).on( "click", submitForm2 );

    $( "[data-time-changed]" ).on( "click", addTimeChanged);

});

