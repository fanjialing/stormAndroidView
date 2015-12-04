cordova.define("intbird-plugins-package.showtoast", function(require, exports, module) {

   var exec = require('cordova/exec');

   var exportsVar = function(message) {
       exec(null,null,"intbirdShowToast","toast",[message]);
   };

   module.exports = exportsVar;
 });