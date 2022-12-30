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

   echo: function (success, error) {
        cordova.exec(
            success,
            error,
            'BlockAppExit',
            'echo',
            []
        );
    }

}
module.exports = blockAppExit;
