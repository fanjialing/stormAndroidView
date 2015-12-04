cordova.define("intbird-plugins-package.networksetting", function(require, exports, module) {

var exec = require('cordova/exec');

var exportsVar = function() {

   cordova.exec(resultSuccess,resultError,"NetworkSetting","netWorkSet",[]);
};

function resultSuccess(e){
};

function resultError(e){
};

module.exports = exportsVar;

});