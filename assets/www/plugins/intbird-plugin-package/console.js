/*global cordova, module*/

cordova.define("intbird-plugins-package.console", function(require, exports, module) {

   var exec = require('cordova/exec');

   var exportsVar = {
    tagName: "nativeLogger",
    setTag: function (newTag) {
        if (newTag) {
            this.tagName = newTag;
        }
    },
    info: function (message, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "nativeLogger", "info", [this.tagName, message]);
    },
    warn: function (message, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "nativeLogger", "warn", [this.tagName, message]);
    },
    error: function (message, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "nativeLogger", "error", [this.tagName, message]);
    },
    debug: function (message, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "nativeLogger", "debug", [this.tagName, message]);
    },
    log: function (message, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "nativeLogger", "verbose", [this.tagName, message]);
    }
};

   module.exports = exportsVar;
 });
 

