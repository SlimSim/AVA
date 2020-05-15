


$( document ).ready(function() {

    var addTimeChanged = function( event ) {
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

});

