cordova.define("intbird-plugins-package.callactivity", function(require, exports, module) {

var exec = require('cordova/exec');

var exportsVar = {

   local:function (url,data,successCallback, errorCallback) {
       cordova.exec(successCallback,errorCallback,"intbirdCallActivity","callLocal",[url,data]);

   },
   remote:function (url,data,successCallback, errorCallback) {
	   cordova.exec(successCallback,errorCallback,"intbirdCallActivity","callRemote",[url,data]);
   },
   main:function (url,data,successCallback, errorCallback) {
	   cordova.exec(successCallback,errorCallback,"intbirdCallActivity","callMain",[url,data]);
   },
   login:function (url,data,successCallback, errorCallback) {
	   cordova.exec(successCallback,errorCallback,"intbirdCallActivity","callLogin",[url,data]);
   }, 
   reg:function (url,data,successCallback, errorCallback) {
	   cordova.exec(successCallback,errorCallback,"intbirdCallActivity","callReg",[url,data]);
   },
   forgotPwd:function (url,data,successCallback, errorCallback) {
	   cordova.exec(successCallback,errorCallback,"intbirdCallActivity","callForgotPwd",[url,data]);
   },
   accredItation:function (url,data,successCallback, errorCallback) {
	   cordova.exec(successCallback,errorCallback,"intbirdCallActivity","callAccreditation",[url,data]);
   }
};

function successCallback(e){
};

function errorCallback(e){
};

module.exports = exportsVar;

});