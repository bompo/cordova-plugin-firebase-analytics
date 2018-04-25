var exec = require("cordova/exec");
var PLUGIN_NAME = "FirebaseAnalytics";

module.exports = {
    logEvent: function(name, propKey, propValue) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "logEvent", [name, propKey, propValue]);
        });
    },
    setUserId: function(userId) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "setUserId", [userId]);
        });
    },
    setUserProperty: function(name, value) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "setUserProperty", [name, value]);
        });
    },
    setEnabled: function(enabled) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "setEnabled", [enabled]);
        });
    },
    setCurrentScreen: function(name) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "setCurrentScreen", [name]);
        });
    },
    isTestLabDevice: function() {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, PLUGIN_NAME, "isTestLabDevice", []);
        });
    }
};
