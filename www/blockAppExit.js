var exec = require('cordova/exec');

/**
 * Block App exit.
 */
var blockAppExit = function (success, error) {
    cordova.exec(
        success,
        error,
        'BlockAppExit',
        'echo',
        []
    );
};
module.exports = blockAppExit;
