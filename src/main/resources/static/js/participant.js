$( document ).ready(function() {

    var stompClient = null,

    connect = function() {
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
        });
    },

    sendRequest = function(participantId, participantName, typeOfRequest, active) {
        stompClient.send(
            "/app/request",
            {},
            JSON.stringify( {
                "participantId": participantId,
                "participantName" : participantName,
                "typeOfRequest" : typeOfRequest,
                "active" : active
            } )
        );
    },

    addTimeChanged = function( event ) {
        $( event.target ).data( "time-changed", Date.now() );
    },

    submitForm = function( event ) {
        event.preventDefault();

        var $target = $( event.target ),
            typeOfRequest = $target.attr( "id" ),
            participantId = $("#participantId").val(),
            participantName = $("#participantName").text(),
            active = !$target.hasClass( "active" );

        sendRequest(participantId, participantName, typeOfRequest, active);
    };

    connect();

    $( ".request-button" ).on( "click", submitForm );

    $( "[data-time-changed]" ).on( "click", addTimeChanged);

});

