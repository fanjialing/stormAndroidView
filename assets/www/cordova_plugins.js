cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
/*
    {
        "file": "plugins/cordova-plugin-whitelist/whitelist.js",
        "id": "cordova-plugin-whitelist.whitelist",
        "runs": true
    },
*/
    {
        "file": "plugins/cordova-plugin-camera/www/CameraConstants.js",
        "id": "cordova-plugin-camera.Camera",
        "clobbers": [
            "Camera"
        ]
    },
    {
        "file": "plugins/cordova-plugin-camera/www/CameraPopoverOptions.js",
        "id": "cordova-plugin-camera.CameraPopoverOptions",
        "clobbers": [
            "CameraPopoverOptions"
        ]
    },
    {
        "file": "plugins/cordova-plugin-camera/www/Camera.js",
        "id": "cordova-plugin-camera.camera",
        "clobbers": [
            "navigator.camera"
        ]
    },
    {
        "file": "plugins/intbird-plugin-package/showtoast.js",
        "id": "intbird-plugins-package.showtoast",
        "clobbers": [
            "window.showtoast"
        ]
    },
    {
        "file": "plugins/intbird-plugin-package/callactivity.js",
        "id": "intbird-plugins-package.callactivity",
         "clobbers": [
            "window.go"
         ]
    },
    {
        "file": "plugins/intbird-plugin-package/console.js",
        "id": "intbird-plugins-package.console",
         "clobbers": [
            "window.console"
         ]
    },
     {
        "file": "plugins/intbird-plugin-package/baidupush.js",
        "id": "intbird-plugins-package.baidupush",
         "clobbers": [
            "window.baidupush"
         ]
    },
    {
        "file": "plugins/intbird-plugin-package/device.js",
        "id": "intbird-plugins-package.device",
         "clobbers": [
            "window.device"
         ]
    },
      {
        "file": "plugins/intbird-plugin-package/retreat.js",
        "id": "intbird-plugins-package.retreat",
         "clobbers": [
            "window.goback"
         ]
    },
    {
        "file": "plugins/intbird-plugin-package/networksetting.js",
        "id": "intbird-plugins-package.networksetting",
         "clobbers": [
            "window.network"
         ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.0.0",
    "cordova-plugin-camera": "1.1.0",
    "intbird-plugins-package": "1.1.0",
}
// BOTTOM OF METADATA
});