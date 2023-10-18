var global = (function() { return this; })();

if (typeof global.window === 'undefined') {
    // create a custom window object to emulate the normal browser window
    var window = {navigator:{userAgent:"atmosphere.js"},document:{},location:{},JSON:JSON};

    window.WebSocket = require("ws");
    window.EventSource = require("eventsource");
    window.XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
    global.window = window;

    global.WebSocket = window.WebSocket;
    global.EventSource = window.EventSource;
    global.navigator = window.navigator;
    global.document = window.document;
}

module.exports = require('./lib/atmosphere');
