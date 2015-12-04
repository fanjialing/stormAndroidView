cordova.define("intbird-plugins-package.console", function(require, exports, module) {

   var exec = require('cordova/exec');

   var exportsVar = function(message) {
       exec(null,null,"nativeLogger","info",['nativeLogger',message]);
       
   };

   module.exports = exportsVar;
 });