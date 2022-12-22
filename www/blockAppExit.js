var exec = require('cordova/exec');

/**
 * Block App exit.
 */
var blockAppExit = {
    enable: function (success, error) {
        cordova.exec(
            success,
            error,
            'BlockAppExit',
            'enable',
            []
        );
    },
    disable: function (success, error) {
        cordova.exec(
            success,
            error,
            'BlockAppExit',
            'disable',
            []
        );
    },
} ;

module.exports = blockAppExit;
