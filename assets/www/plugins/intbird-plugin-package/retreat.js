cordova.define("intbird-plugins-package.retreat", function(require, exports, module) {

   var exec = require('cordova/exec');

   var exportsVar = function() {
       exec(null,null,"RetreatActivity","Retreat",[]);
   };

   module.exports = exportsVar;
 });