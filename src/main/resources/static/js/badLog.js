
$( document ).ready(function() {
    const badLogAllUrl = thymeLeaf.contextPath + "badLog/api/all";
    const badLogClearUrl = thymeLeaf.contextPath + "badLog/api/clear";

    var getBadLogList = function() {
        $.ajax({
            url: badLogAllUrl
        })
        .done( repopulateBadLogList );
    },

    createBadLogRow = function( date, time, fileName, methodName, lineNumber, level, logInfo ) {
		const tr = $( "<tr>" ).addClass( level );

		tr.append( $( "<td>" ).addClass("date pr-2").text( date ) );
		tr.append( $( "<td>" ).addClass("time pr-2").text( time ) );
        tr.append( $( "<td>" ).addClass("fileName pr-2").text( fileName ) );
        tr.append( $( "<td>" ).addClass("methodName pr-2").text( methodName ) );
        tr.append( $( "<td>" ).addClass("lineNumber pr-2").text( lineNumber ) );
        tr.append( $( "<td>" ).addClass("level pr-2").text( level ) );
        tr.append( $( "<td>" ).addClass("logInfo pr-2").text( logInfo ) );

        return tr;
    },

    clearServerLogs = function() {
        $.ajax({
            url: badLogClearUrl
        })
        .done( () => { clearClientLogs(); } );
    },

    clearClientLogs = function() {
        $( "#badLogList" ).children().remove();
    },

    repopulateBadLogList = function( badLogList ) {

        clearClientLogs();

        $.each( badLogList, (index, badLog) => {
			var [date, time] = badLog.timeStamp.split("T");

            $( "#badLogList" ).append( createBadLogRow(
                date, time,  badLog.fileName, badLog.methodName,
                badLog.lineNumber, badLog.level,    badLog.logInfo
            ) );
        });

    };

    $( "#clearServer" ).on( "click", clearServerLogs );
    $( "#clearClient" ).on( "click", clearClientLogs );
    $( "#reloadData" ).on( "click", getBadLogList );

    $( ".hideData" ).on( "click", function( event ) {
        var $target = $( event.target ),
	        dataToHide = $target.data( "data-to-hide" );
        $target.toggleClass( "active" );
        $( "#badLogTable" ).toggleClass( dataToHide );
    });

    getBadLogList();
} );